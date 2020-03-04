package com.lsc.network.rxjava;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.lsc.network.R;
import com.lsc.network.retrofit.RetrofitActivity;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ResponseBody;
import okhttp3.internal.concurrent.Task;
import retrofit2.Call;
import retrofit2.Retrofit;


/*
 *
 * Button -> 被观察者、
 * OnClickListener -> 观察者、
 * setOnClickListener() -> 订阅，
 * onClick() -> 事件
 *
 *Observer 观察者
 * Observable 被观察者
 * Subscribe 订阅
 * 事件  普通事件 onNext() （相当于 onClick() / onEvent()）之外，还定义了两个特殊的事件：onCompleted() 和 onError()。
 *
 *
 * */

public class RxJavaActivity extends AppCompatActivity {

    /*----------------------官方Demo -----------------------*/
    private static final String TAG = "RxAndroidSamples";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);
        findViewById(R.id.button_run_scheduler).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sampleExampleThree();
            }
        });
    }

    public void requestImg() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://www.sn5.com.cn/uploadfile/").build();
                    //Retrofit根据接口实现类并创建实例
                    RetrofitActivity.ChatService service = retrofit.create(RetrofitActivity.ChatService.class);
                    Call<ResponseBody> call = service.testImage();
                    retrofit2.Response<ResponseBody> response = call.execute();
                    Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());

                    Handler handler = new Handler(getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            ImageView imageView = findViewById(R.id.imageView);
                            imageView.setImageBitmap(bitmap);
                        }
                    });
                } catch (IOException e) {

                }
            }
        });
        thread.start();
    }


    /**
     * 假设有这样一个需求：界面上有一个自定义的视图 imageCollectorView ，
     * 它的作用是显示多张图片，并能使用 addImage(Bitmap) 方法来任意增加显示的图片。
     * 现在需要程序将一个给出的目录数组 File[] folders 中每个目录下的 png 图片都加载出来
     * 并显示在 imageCollectorView 中。需要注意的是，由于读取图片的这一过程较为耗时，
     * 需要放在后台执行，而图片的显示则必须在 UI 线程执行。常用的实现方式有多种，我这里贴出其中一种：
     */
    private void addImage() {
        /*new Thread() {
            @Override
            public void run() {
                super.run();
                for (File folder : folders) {
                    File[] files = folder.listFiles();
                    for (File file : files) {
                        if (file.getName().endsWith(".png")) {
                            final Bitmap bitmap = getBitmapFromFile(file);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    imageCollectorView.addImage(bitmap);
                                }
                            });
                        }
                    }
                }
            }
        }.start();

        Observable.from(folders).flatMap(
                new Func1<File, Observable<File>>() {
                    @Override
                    public Observable<File> call(File file) {
                        return Observable.from(file.listFiles());
                    }
                }).filter(new Func1<File, Boolean>() {
            @Override
            public Boolean call(File file) {
                return file.getName().endsWith(".png");
            }
        }).map(new Func1<File, Bitmap>() {
            @Override
            public Bitmap call(File file) {
                return getBitmapFromFile(file);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Bitmap>() {
            @Override
            public void call(Bitmap bitmap) {
                imageCollectorView.addImage(bitmap);
            }
        });
    */}


    /**
     * Observer 观察者
     */
    private void studyObserver() {

        /*不仅基本使用方式一样，实质上，在 RxJava 的 subscribe 过程中，
        Observer 也总是会先被转换成一个 Subscriber 再使用。
        所以如果你只想使用基本功能，选择 Observer 和 Subscriber 是完全一样的。
        它们的区别对于使用者来说主要有两点：
            onStart(): 这是 Subscriber 增加的方法。它会在 subscribe 刚开始，
                而事件还未发送之前被调用，可以用于做一些准备工作，
                例如数据的清零或重置。这是一个可选方法，默认情况下它的实现为空。
                需要注意的是，如果对准备工作的线程有要求（例如弹出一个显示进度的对话框，这必须在主线程执行），
                onStart() 就不适用了，因为它总是在 subscribe 所发生的线程被调用，而不能指定线程。
                要在指定的线程来做准备工作，可以使用 doOnSubscribe() 方法，具体可以在后面的文中看到。
            unsubscribe(): 这是 Subscriber 所实现的另一个接口 Subscription 的方法，用于取消订阅。
                在这个方法被调用后，Subscriber 将不再接收事件。一般在这个方法调用前，可以使用 isUnsubscribed() 先判断一下状态。
                unsubscribe() 这个方法很重要，因为在 subscribe() 之后， Observable 会持有 Subscriber 的引用，
                这个引用如果不能及时被释放，将有内存泄露的风险。所以最好保持一个原则：
                要在不再使用的时候尽快在合适的地方（例如 onPause() onStop() 等方法中）调用 unsubscribe() 来解除引用关系，
                以避免内存泄露的发生。

        */


        //Observer 即观察者，它决定事件触发的时候将有怎样的行为。
        // RxJava 中的 Observer 接口的实现方式：
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onNext(String s) {
                //Log.d(tag, "Item: " + s);
            }

            @Override
            public void onError(Throwable e) {
                //Log.d(tag, "Error!");
            }
        };

        //除了 Observer 接口之外，RxJava 还内置了一个实现了 Observer 的抽象类：Subscriber。
        // Subscriber 对 Observer 接口进行了一些扩展，但他们的基本使用方式是完全一样的：
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {

            }

            @Override
            public void onNext(String s) {

            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        };
    }


    /**
     * Observable 被观察者
     */
    private void studyObservable() {

        //Observable 即被观察者，它决定什么时候触发事件以及触发怎样的事件。
        // RxJava 使用 create() 方法来创建一个 Observable ，并为它定义事件触发规则：
       /* Observable observable1 = Observable.create(new ObservableOnSubscribe<Object>() {

            @Override
            public void subscribe(@NonNull ObservableEmitter<Object> emitter) throws Throwable {

            }
        });


        //just(T...) 的例子和 from(T[]) 的例子，都和 create(OnSubscribe) 的例子是等价的。
        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello");
                subscriber.onNext("Hi");
                subscriber.onNext("Aloha");
                subscriber.onCompleted();
            }
        });

        Observable observable2 = Observable.just("Hello", "Hi", "Aloha");// 将会依次调用：// onNext("Hello");// onNext("Hi");// onNext("Aloha");// onCompleted();

        String[] words = {"Hello", "Hi", "Aloha"};
        Observable observable3 = Observable.from(words);// 将会依次调用：// onNext("Hello");// onNext("Hi");// onNext("Aloha");// onCompleted();
    */}

    public void studySubscribe(){
        //创建了 Observable 和 Observer 之后，再用 subscribe() 方法将它们联结起来，整条链子就可以工作了。代码形式很简单：

        Observable observable = Observable.just("Hello", "Hi", "Aloha");// 将会依次调用：// onNext("Hello");// onNext("Hi");// onNext("Aloha");// onCompleted();
        //Observer 即观察者，它决定事件触发的时候将有怎样的行为。
        // RxJava 中的 Observer 接口的实现方式：
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onNext(String s) {
                Log.d("Rxjava", "Item: " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("Rxjava", "Error!");
            }
        };
        observable.subscribe(observer);// 或者：observable.subscribe(subscriber);

    }


    /*
    https://www.jianshu.com/p/5d761e283f24
    我们来看个具体的例子，加深下理解。本例中需要处理三个任务，分别为A、B、C，
    这三个任务都需要在工作线程中执行，A执行完以后延迟1S执行B，B执行完以后延迟1S执行C。
    以下为公共代码，定义了A、B、C三个任务以及定时器
    */
    public class TaskA implements Runnable{
        @Override
        public void run() {
            Log.i("rxjava","A: " + Thread.currentThread().getName() +
                    "," + System.currentTimeMillis());
        }
    }
    public class TaskB implements Runnable{
        @Override
        public void run() {
            Log.i("rxjava","B: " + Thread.currentThread().getName() +
                    "," + System.currentTimeMillis());
        }
    }
    public class TaskC implements Runnable{
        @Override
        public void run() {
            Log.i("rxjava","C: " + Thread.currentThread().getName() +
                    "," + System.currentTimeMillis());
        }
    }

    public static class Timer{
        private static final Timer INSTANCE = new Timer();
        private Handler handler;
        private Timer(){
            HandlerThread thread = new HandlerThread("timer");
            thread.start();
            handler = new Handler(thread.getLooper());
        }

        public static Timer getInstance(){
            return INSTANCE;
        }
        public void post(Runnable runnable){
            handler.post(runnable);
        }
        public void postDelayed(Runnable runnable,long delay){
            handler.postDelayed(runnable,delay);
        }
    }

    public void testTimer(){
        Timer.getInstance().post(new Runnable() {
            @Override
            public void run() {
                new TaskA().run();
                Timer.getInstance().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        new TaskB().run();
                        Timer.getInstance().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                new TaskC().run();
                            }
                        },1000);
                    }
                },1000);
            }
        });
    }

    public class StreamTimer implements Runnable{

        private List<Task> tasks = new LinkedList<>();
        public StreamTimer(){}

        public StreamTimer next(Runnable runnable){
            return next(runnable,0);
        }
        public StreamTimer next(Runnable runnable,long delay){
            Task task = new Task(runnable,delay);
            tasks.add(task);
            return this;
        }

        public void startUp(){
            startNextTimer();
        }

        private void startNextTimer(){
            if (tasks.isEmpty()){
                return;
            }
            Task task = tasks.get(0);
            Timer.getInstance().postDelayed(this,task.delay);
        }
        @Override
        public void run() {
            Task task = tasks.remove(0);
            task.runnable.run();
            startNextTimer();
        }

        class Task{
            Runnable runnable;
            long delay;

            Task(Runnable runnable,long delay){
                this.runnable = runnable;
                this.delay = delay;
            }
        }
    }

    public void testStreamTimer(){
        new StreamTimer()
                .next(new TaskA())
                .next(new TaskB(),1000)
                .next(new TaskC(),1000)
                .startUp();
    }

    public void sampleExampleOne(){
        //创建被观察者
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
                emitter.onNext("A");
                emitter.onNext("B");
                emitter.onNext("C");
                emitter.onComplete();
            }
        });

        //创建观察者
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.i("rxjava","onSubcribe(),"+ Thread.currentThread().getName());
            }

            @Override
            public void onNext(@NonNull String s) {
                Log.i("rxjava","onNext(),"+s+"，"+ Thread.currentThread().getName());

            }

            @Override
            public void onError(@NonNull Throwable e) {

                Log.i("rxjava","onErrot(),"+ Thread.currentThread().getName());

            }

            @Override
            public void onComplete() {
                Log.i("rxjava","onComplete(),"+ Thread.currentThread().getName());

            }
        };
        //订阅
        observable.subscribe(observer);

        /*2020-03-03 14:47:22.963 13344-13344/com.lsc.network I/rxjava: onSubcribe(),main
          2020-03-03 14:47:22.964 13344-13344/com.lsc.network I/rxjava: onNext(),A，main
          2020-03-03 14:47:22.964 13344-13344/com.lsc.network I/rxjava: onNext(),B，main
          2020-03-03 14:47:22.964 13344-13344/com.lsc.network I/rxjava: onNext(),C，main
          2020-03-03 14:47:22.964 13344-13344/com.lsc.network I/rxjava: onComplete(),main*/
    }

    public void sampleExampleTwo(){
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
                emitter.onNext("A");
                emitter.onNext("A1");
                emitter.onNext("A2");
                emitter.onComplete();
            }
        });
        //subscribe(Consumer<? super T> onNext, Consumer<? super Throwable> onError, Action onComplete, Consumer<? super Disposable> onSubscribe) {
        //
        observable.subscribe(new Consumer<String>() {
                                 @Override
                                 public void accept(String s) throws Throwable {
                                     Log.i("rxjava", s + "," + Thread.currentThread().getName());
                                 }
                             }
                , new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {

                    }
                }, new Action() {
                    @Override
                    public void run() throws Throwable {

                    }
                }
        );

        /*
        * 2020-03-03 15:03:33.613 13593-13593/com.lsc.network I/rxjava: A,main
          2020-03-03 15:03:33.613 13593-13593/com.lsc.network I/rxjava: A1,main
          2020-03-03 15:03:33.613 13593-13593/com.lsc.network I/rxjava: A2,main
        */
    }

    /**
     * Observable.subscribeOn配置Observable工作线程，
     * 使用Observable.observeOn配置Observer工作线程。
     * 很多情况下，置Observable工作线程位于子线程中，
     * 因为可能存在网络请求、数据存取等耗时操作；
     * 而Observer工作线程位于主线程，因为接收到事件后需要刷新UI。
     *
     * subscribeOn和observeOn返回的不是原本的Observable对象，
     * 因此如果没有采用链式调用，在调用这两个方法之后必须重新赋值给Observable对象，如：
     * // 错误调用
     * observable.subscribeOn(xxx)
     *     .observeOn(xxx);
     * observable.subscribe(xxx);
     *
     * // 正确调用
     * observable = observable.subscribeOn(xxx)
     *     .observeOn(xxx);
     * observable.subscribe(xxx);
     *
     *  subscribeOn以第一次调用为准。
     *  observeOn以最后一次调用为准。
     *
     * xJava使用Scheduler来表示线程调度，上面提到的Schedulers.newThread()和AndroidSchedulers.mainThread()
     * 都是由RxJava提供的Scheduler实现类。一般我们不需要手动去实现Scheduler，
     * 而是通过Schedulers或者AndroidSchedulers（Android专用）获取。
     * 下面分别来看下二者所提供的创建能力。
     *
     * Schedulers
     *      newThread
     *      大致等同于new Thread(runnable).start();，线程数没有上限，除了测试场景一般不会用到它。
     *
     *      io
     *      用于I/O操作场景，线程数没有上限。与newThread比较相似，
     *      区别在于该调度器的内部使用了一个无数量上限的线程池，
     *      可以复用空闲的线程，因此效率更高。
     *
     *      computation
     *      用于计算场景，计算指的是CPU密集型计算，即不会被I/O等操作限制性能的操作，
     *      因此不要把I/O操作放在这里。该类型的Scheduler使用固定数量的线程池，
     *      数量为处理器核数。除了阻塞（包括I/O操作、wait等）外，
     *      其他操作都可以使用该调度器，不过通常用处理事件循环，大数据运算等。
     *
     *      single
     *      单线程调度，所有任务都需要排队依次运行。
     *
     *      trampoline
     *      任务在当前线程运行。
     *
     *      from(Executor executor)
     *      使用指定的线程池调度。
     *
     * AndroidSchedulers
     *      mainThread
     *      任务在主线程上运行。
     *
     *      from(Looper looper)
     *
     */
    //线程调度
    public void sampleExampleThree(){

        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
                Log.i("rxjava",   "subscribe," + Thread.currentThread().getName());

                emitter.onNext("A");
                emitter.onNext("A1");
                emitter.onNext("A2");
                emitter.onComplete();
            }
        });

        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Throwable {
                        Log.i("rxjava",   s+"," + Thread.currentThread().getName());

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {

                    }
                }, new Action() {
                    @Override
                    public void run() throws Throwable {
                        Log.i("rxjava",   "onComplete," + Thread.currentThread().getName());

                    }
                });

        /*2020-03-03 15:13:02.764 13922-13970/com.lsc.network I/rxjava: subscribe,RxNewThreadScheduler-1
          2020-03-03 15:13:02.766 13922-13922/com.lsc.network I/rxjava: A,main
          2020-03-03 15:13:02.766 13922-13922/com.lsc.network I/rxjava: A1,main
          2020-03-03 15:13:02.766 13922-13922/com.lsc.network I/rxjava: A2,main
          2020-03-03 15:13:02.766 13922-13922/com.lsc.network I/rxjava: onComplete,main
        */
    }
}