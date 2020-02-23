package com.lsc.hook;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.lsc.utils.ReflectInvoke;

/*
* 方案1：重写Activity的startActivityForResult方法
* 我们一般会为App中所有的Activity做一个基类BaseActivity，
* 这样就可以在BaseActivity中重写其中的startActivityForResult方法，
* 于是在每个Activity中，无论调用startActivity还是调用startActivityForResult方法，
* 都会执行BaseActivity中重写的逻辑．这种方式甚至都不能称为Hook，
* 因为我们只是使用面向对象的思想来写程序。
*
* 方案2：对Activity的mInstrumentation字段进行Hook
* Activity中有一个mInstrumentation字段，Activity的startActivityForResult方法
* 会调用mInstrumentation字段的execStartActivity方法，
*   1:EvilInstrumentation类型对象是对这个mInstrumentation对象的包装，
*       所以，虽然Activity的这个mInstrumentation字段被Hook了，
*       但在执行Activity的startActivity方法时，实际调用的仍然是Instrumentation的
*       execStartActivity方法，我们只是在EvilInstrumentation这个包装类中，额外打印了一行日志，
*   2:缺点
*       这种Hook的方式有个很大的缺点——只针对于当前Activity生效，
*       因为它只修改了当前Activity实例的mInstrumentation字段。
*       我们可以把这段Hook代码从MainActivity的onCreate方法中转移到自定义的BaseActivity中，
*       这样所有继承自BaseActivity的Activity，它们的mInstrumentation字段就都被Hook了
*
* 方案3：Instrumentation的execStartActivity方法会调用AMN的startActivity方法
*
*
*
* */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        planTwo();
    }

    /**
     * 这种Hook的方式有个很大的缺点——只针对于当前Activity生效，因为它只修改了当前Activity实例的mInstrumentation字段。
     * 我们可以把这段Hook代码从MainActivity的onCreate方法中转移到自定义的BaseActivity中，
     * 这样所有继承自BaseActivity的Activity，它们的mInstrumentation字段就都被Hook了
     */
    private void planTwo(){
        //得到 mInstrumentation字段
        Instrumentation instrumentation = (Instrumentation) ReflectInvoke.
                getFieldObject(Activity.class,this, "mInstrumentation");
        //得到代理对象
        Instrumentation evilInstrumentation = new EvilInstrumentation(instrumentation);
        //狸猫换太子
        ReflectInvoke.setFieldObject(Activity.class,this,
                "mInstrumentation",evilInstrumentation);

        Button button = new Button(this);
        LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        button.setLayoutParams(btnParams);
        button.setText("方案2");
        setContentView(button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                startActivity(intent);
            }
        });
    }

    private void planThree(){


    }

    private void addContentView(){
        LinearLayout linearLayout = new LinearLayout(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        Button button = new Button(this);
        LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        button.setLayoutParams(btnParams);
        button.setText("跳转到SecondActivity");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                startActivity(intent);
            }
        });
        linearLayout.addView(button);
        super.addContentView(linearLayout,layoutParams);
    }
}
