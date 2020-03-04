package com.lsc.qq.adapter;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.widget.PopupMenu;

import com.lsc.qq.R;
import com.lsc.recyclerlisttreeview.ListTree;
import com.lsc.recyclerlisttreeview.ListTreeAdapter;


/**
 * 为RecyclerView提供数据
 */
public class ContactsPageListAdapter extends
        ListTreeAdapter<ContactsPageListAdapter.BaseViewHolder> {

    static public class GroupInfo{
        private String title;
        private int onlineCount;

        public GroupInfo(String title, int onlineCount) {
            this.title = title;
            this.onlineCount = onlineCount;
        }

        public String getTitle() {
            return title;
        }

        public int getOnlineCount() {
            return onlineCount;
        }
    }

    //保存子行信息的类
    public static class ContactInfo {
        //头像,用于设置给ImageView。
        private Bitmap bitmap;
        //标题
        private String title;
        //描述
        private String detail;

        public ContactInfo(Bitmap bitmap, String title, String detail) {
            this.bitmap = bitmap;
            this.title = title;
            this.detail = detail;
        }

        public Bitmap getBitmap() {
            return bitmap;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDetail() {
            return detail;
        }

    }

    public ContactsPageListAdapter(ListTree tree) {
        super(tree);
    }

    public ContactsPageListAdapter(ListTree tree, Bitmap expandIcon, Bitmap collapseIcon) {
        super(tree, expandIcon, collapseIcon);
    }

    @Override
    protected BaseViewHolder onCreateNodeView(ViewGroup parent, int viewType) {

        //获取从layout创建View的对象
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        //创建不同的行View
        if (viewType == R.layout.contacts_group_item) {
            //注意！此处有一个不同！最后一个参数必须传true！
            View view = inflater.inflate(viewType, parent, true);
            //用不同的ViewHolder包装
            return new GroupViewHolder(view);
        } else if (viewType == R.layout.contacts_contact_item) {
            //注意！此处有一个不同！最后一个参数必须传true！
            View view = inflater.inflate(viewType, parent, true);
            //用不同的ViewHolder包装
            return new ContactViewHolder(view);
        } else {
            return null;
        }
    }

    @Override
    protected void onBindNodeViewHolder(BaseViewHolder holder, int position) {
        //获取行控件
        View view = holder.itemView;
        //获取这一行这树对象中对应的节点
        ListTree.TreeNode node = tree.getNodeByPlaneIndex(position);

        if (node.getLayoutResId() == R.layout.contacts_group_item) {
            //group node
            GroupInfo groupInfo = (GroupInfo)node.getData();
            GroupViewHolder gvh = (GroupViewHolder) holder;
            gvh.textViewTitle.setText(groupInfo.getTitle());
            gvh.textViewCount.setText(groupInfo.getOnlineCount() +
                    "/" + node.getChildrenCount());
        } else if (node.getLayoutResId() == R.layout.contacts_contact_item) {
            //child node
            ContactInfo info = (ContactInfo) node.getData();

            ContactViewHolder cvh = (ContactViewHolder) holder;
            cvh.imageViewHead.setImageBitmap(info.getBitmap());
            cvh.textViewTitle.setText(info.getTitle());
            cvh.textViewDetail.setText(info.getDetail());
        }
    }

    //组行和联系人行的Holder基类
    class BaseViewHolder extends ListTreeAdapter.ListTreeViewHolder {
        public BaseViewHolder(View itemView) {
            super(itemView);
        }
    }

    //将ViewHolder声明为Adapter的内部类，反正外面也用不到
    class GroupViewHolder extends BaseViewHolder {

        TextView textViewTitle;//显示标题的控件
        TextView textViewCount;//显示好友数/在线数的控件
        Switch aSwitch;
        TextView textViewMenu;

        public GroupViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewCount = itemView.findViewById(R.id.textViewCount);
        }
    }

    class ContactViewHolder extends BaseViewHolder {
        ImageView imageViewHead;//显示好友头像的控件
        TextView textViewTitle;//显示好友名字的控件
        TextView textViewDetail;//显示好友状态的控件

        public ContactViewHolder(View itemView) {
            super(itemView);

            imageViewHead = itemView.findViewById(R.id.imageViewHead);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDetail = itemView.findViewById(R.id.textViewDetail);
        }
    }
}