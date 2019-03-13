package com.example.forev.mycodelibrary;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import butterknife.BindView;

public class HookTestAct extends BaseActivity{
    @BindView(R.id.mTestHookBtn)
    Button mTestHookBtn;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_hook_test;
    }

    @Override
    protected void initView() {
        //在事件传送的过程中截获或者监控事件的传输，将自身代码与系统代码进行融入。也就是执行自己的方法。面向切片编程
        mTestHookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("HookTestAct", "点击了hook按钮");
                hookOnclickListener(v);
            }
        });
    }

    public static void hookOnclickListener(View view) {
        try {
//            通过反射调用方法getListnerInfo获取ListenerInfo变量
//            修改ListenerInfo中存储的onClickListener,将这个listener钓出来，再结合自己自定义的listener进行配合
//            重新配值
//            进行切片式的编程

//            使用场景，假设我针对系统中所有的listener都进行某种执行前或者执行后统一的处理，其实最快的解决方案就是钓鱼方式
//            假设我不采取钓鱼的方式，那么我可能自定义一个Listener，然后系统内所有的setListener都设置成你写的那个Listener，
//            动的代码可能会很少，无非就是全局搜索使用方法然后替换，，但是如果代码不是你写的呢？是人家写好的SDK的呢？
//            而且人家在SDK中拟定了点击操作。这个就行不通了！！！

//            同样的假设你就自暴自弃不动脑子自己生码，在每一个new OnclickListener 的地方 都加同样的代码吧，，你依然无法解决
//            其他SDK中进行操作这种问题！！！
//            这种情况下，hook机制解决的问题最多，，但是缺点也是显而易见的，就是别人的SDK一旦改个相关代码发个新版本，例如改个方法名字吧，
//            然后你再更新一下SDK，，你的代码就 hold 不住了。

//            使用场景诸如 全局自定义长按复制粘贴功能，并给出统一的反馈。
            Method getListenerInfoMethod = View.class.getDeclaredMethod("getListenerInfo");
            getListenerInfoMethod.setAccessible(true);
            Object listnerInfo = getListenerInfoMethod.invoke(view);

            Class listenerInfoClass = Class.forName("android.view.View$ListenerInfo");
            Field mOnclickListener = listenerInfoClass.getDeclaredField("mOnClickListener");
            mOnclickListener.setAccessible(true);
            View.OnClickListener originOnclickListener = (View.OnClickListener) mOnclickListener.get(listnerInfo);
            MyHookOnclickListener myHookOnclickListener = new MyHookOnclickListener(originOnclickListener);
            mOnclickListener.set(listnerInfo, myHookOnclickListener);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }


    public static class MyHookOnclickListener implements View.OnClickListener{
        private View.OnClickListener mOnclickListener;

        public MyHookOnclickListener(View.OnClickListener mOnclickListener) {
            this.mOnclickListener = mOnclickListener;
        }

        @Override
        public void onClick(View v) {
            Log.i("MyHookOnclickListener", "hook onclickListener！");
            mOnclickListener.onClick(v);
        }
    }
}
