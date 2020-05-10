package com.lsc.volley.imitation;

import android.os.Handler;
import android.os.Looper;

import com.android.volley.Cache;
import com.android.volley.CacheDispatcher;
import com.android.volley.ExecutorDelivery;
import com.android.volley.Network;
import com.android.volley.NetworkDispatcher;
//import com.android.volley.Request;
import com.android.volley.ResponseDelivery;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by lsc on 2020/4/19 19:31.
 * E-Mail:2965219926@qq.com
 */
public class LscRequestQueue {
    /** Callback interface for completed requests. */
    public interface RequestFinishedListener<T> {
        /** Called when a request has finished processing. */
        void onRequestFinished(Request<T> request);
    }

    /** 用于为请求生成单调递增的序列号。 */
    private final AtomicInteger mSequenceGenerator = new AtomicInteger();

    /**
     * 请求集合
     */
    private final Set<Request<?>> mCurrentRequests = new HashSet<>();

    /** 缓存队列 */
    private final PriorityBlockingQueue<Request<?>> mCacheQueue = new PriorityBlockingQueue<>();

    /** 进行实际网络请求队列。 */
    private final PriorityBlockingQueue<Request<?>> mNetworkQueue = new PriorityBlockingQueue<>();

    /** 要启动的网络请求调度程序线程的数目。*/
    private static final int DEFAULT_NETWORK_THREAD_POOL_SIZE = 4;

    /** 缓存接口 */
    private final Cache mCache;

    /** 网络请求接口 */
    private final Network mNetwork;

    /** 响应交付到主线程 */
    private final ResponseDelivery mDelivery;

    /** 网络调度 */
    private final NetworkDispatcher[] mDispatchers;

    /** 缓存调度 */
    private CacheDispatcher mCacheDispatcher;

    private final List<RequestFinishedListener> mFinishedListeners = new ArrayList<>();

    /**
     * 创建工作池。在调用{@link #start()}之前不会开始处理。
     *
     * @param cache A Cache to use for persisting responses to disk
     * @param network A Network interface for performing HTTP requests
     * @param threadPoolSize Number of network dispatcher threads to create
     * @param delivery A ResponseDelivery interface for posting responses and errors
     */
    public LscRequestQueue(
            Cache cache, Network network, int threadPoolSize, ResponseDelivery delivery) {
        mCache = cache;
        mNetwork = network;
        mDispatchers = new NetworkDispatcher[threadPoolSize];
        mDelivery = delivery;
    }

    /**
     * 创建工作池。在调用{@link #start()}之前不会开始处理。
     *
     * @param cache A Cache to use for persisting responses to disk
     * @param network A Network interface for performing HTTP requests
     * @param threadPoolSize Number of network dispatcher threads to create
     */
    public LscRequestQueue(Cache cache, Network network, int threadPoolSize) {
        this(
                cache,
                network,
                threadPoolSize,
                new ExecutorDelivery(new Handler(Looper.getMainLooper())));
    }

    /**
     * 创建工作池。在调用{@link #start()}之前不会开始处理。
     *
     * @param cache A Cache to use for persisting responses to disk
     * @param network A Network interface for performing HTTP requests
     */
    public LscRequestQueue(Cache cache, Network network) {
        this(cache, network, DEFAULT_NETWORK_THREAD_POOL_SIZE);
    }

    /** 启动队列中的调度程序 */
    public void start() {
        stop(); // 确保当前运行的调度程序都已停止。
        // 创建缓存分派器并启动它。
        mCacheDispatcher = new CacheDispatcher(mCacheQueue, mNetworkQueue, mCache, mDelivery);
        mCacheDispatcher.start();

        // 创建线程知道满足池大小。
        for (int i = 0; i < mDispatchers.length; i++) {
            NetworkDispatcher networkDispatcher =
                    new NetworkDispatcher(mNetworkQueue, mNetwork, mCache, mDelivery);
            mDispatchers[i] = networkDispatcher;
            networkDispatcher.start();
        }
    }

    /** 停止缓存分派和网络分派. */
    public void stop() {
        if (mCacheDispatcher != null) {
            mCacheDispatcher.quit();
        }
        for (final NetworkDispatcher mDispatcher : mDispatchers) {
            if (mDispatcher != null) {
                mDispatcher.quit();
            }
        }
    }

    /** 获取当前请求序列号 */
    public int getSequenceNumber() {
        return mSequenceGenerator.incrementAndGet();
    }

    /** 获取当前缓存实例 */
    public Cache getCache() {
        return mCache;
    }

    /**
     * 过滤器接口，供{@link LscRequestQueue#cancelAll(RequestFilter)}使用。
     */
    public interface RequestFilter {
        boolean apply(Request<?> request);
    }

    /**
     * 取消此队列中应用给定筛选器的所有请求。
     *
     * @param filter The filtering function to use
     */
    public void cancelAll(RequestFilter filter) {
        synchronized (mCurrentRequests) {
            for (Request<?> request : mCurrentRequests) {
                if (filter.apply(request)) {
                    request.cancel();
                }
            }
        }
    }

    /**
     * 取消此队列中的所有 标记tag 的请求
     * Cancels all requests in this queue with the given tag. Tag must be non-null and equality is
     * by identity.
     */
    public void cancelAll(final Object tag) {
        if (tag == null) {
            throw new IllegalArgumentException("Cannot cancelAll with a null tag");
        }
        cancelAll(
                new RequestFilter() {
                    @Override
                    public boolean apply(Request<?> request) {
                        return request.getTag() == tag;
                    }
                });
    }

    /**
     * 添加一个请求进入 分派队列
     *
     * @param request The request to service
     * @return The passed-in request
     */
    public <T> Request<T> add(Request<T> request) {
        // Tag the request as belonging to this queue and add it to the set of current requests.
        request.setRequestQueue(this);
        synchronized (mCurrentRequests) {
            mCurrentRequests.add(request);
        }

        // Process requests in the order they are added.
        request.setSequence(getSequenceNumber());
        request.addMarker("add-to-queue");

        // If the request is uncacheable, skip the cache queue and go straight to the network.
        if (!request.shouldCache()) {
            mNetworkQueue.add(request);
            return request;
        }
        mCacheQueue.add(request);
        return request;
    }

    /**
     * 从{@link LscRequest#finish(String)}调用，表示给定请求的处理已经完成。
     */
    @SuppressWarnings("unchecked")
    <T> void finish(Request<T> request) {
        // 从当前正在处理的请求集中删除。
        synchronized (mCurrentRequests) {
            mCurrentRequests.remove(request);
        }
        synchronized (mFinishedListeners) {
            for (RequestFinishedListener<T> listener : mFinishedListeners) {
                listener.onRequestFinished(request);
            }
        }
    }

    public <T> void addRequestFinishedListener(RequestFinishedListener<T> listener) {
        synchronized (mFinishedListeners) {
            mFinishedListeners.add(listener);
        }
    }

    /** Remove a RequestFinishedListener. Has no effect if listener was not previously added. */
    public <T> void removeRequestFinishedListener(RequestFinishedListener<T> listener) {
        synchronized (mFinishedListeners) {
            mFinishedListeners.remove(listener);
        }
    }
}
