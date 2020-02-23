package com.lsc.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.material.snackbar.Snackbar;
import com.lsc.login.fragment.LoginFragment;

public class MainActivity extends AppCompatActivity {

    public static final int REGISTER_REQUEST_CODE = 123;

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setActionBar();

        initFragment();
    }

    /**
     * 设置Action属性
     * 如何响应对它的点击呢？并不是设置侦听器，而是需要在Activity类中重写父类的方法：
     * onOptionsItemSelected()
     */
    private void setActionBar() {
        ActionBar actionBar = getSupportActionBar(); //得到ActionBar实例
        actionBar.setDisplayHomeAsUpEnabled(true); //设置显示返回图标
    }

    /**
     * 设置Fragment
     */
    private void initFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        LoginFragment loginFragment = new LoginFragment();
        fragmentTransaction.add(R.id.fragment_container,loginFragment);
        fragmentTransaction.commit();
    }
    /**
     * ActionBar上的返回图标其实也是一个菜单项，其ID是内置的，叫作android.R.id.home。
     * 我们获取菜单项的ID，然后进行比较，如果是返回图标被选择了，就向用户发出提示。
     * 注意在这个方法中，当一个菜单项被响应后，应返回true。
     * 还有，注意Snackbar.make()方法的第一个参数，是一个按钮，
     * 并不是想把提示显示在按钮中，而是会从按钮开始自动找一个合适的父控件来显示提示。
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.popBackStack();//从栈中弹出当前的Fragment。
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void forResult() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivityForResult(intent, REGISTER_REQUEST_CODE);
    }

}
