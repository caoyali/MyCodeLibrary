package com.example.forev.mycodelibrary;
import android.util.Log;

import com.example.forev.mycodelibrary.fragment.BaseFragment;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;

/**详情请见{@link # https://www.jianshu.com/p/10c29883eac1}*/
public class ReflexDemoAct extends BaseActivity {
    public final static String TAG = "ReflexDemoAct";

    enum E { A, B }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_reflex_demo;
    }

    @Override
    protected void initView() {
        //获取class类的几种方式
        Log.i(TAG,"1 通过对象实例获取class==============================");
        Class stringClass = "abc".getClass();
        Log.i(TAG,"普通对象的class：" + stringClass);
        Log.i(TAG,"2 通过类的类型获取class==============================");
        Class easyTypeClass = int.class;
        Log.i(TAG,"基本类型的class：" + easyTypeClass);
        Class enumClass = E.A.getClass();
        Log.i(TAG,"enum类型的class：" + enumClass);
        Log.i(TAG,"3 通过类的全局限定名获取class==============================");
        try {
            Class nameClass = Class.forName("java.lang.String");
            Log.i(TAG,"通过全局限定名 java.lang.String 获取class: " + nameClass);
            Class cDoubleArrayClass = Class.forName("[D");
            Log.i(TAG,"通过全局限定名 [D 获取double[].class:" + cDoubleArrayClass);
            Class cStringArrayClass = Class.forName("[[Ljava.lang.String;");
            Log.i(TAG,"通过全局限定名 [[Ljava.lang.String; 获取String[][].class: " + cStringArrayClass);
            Log.i(TAG,"4 通过包装类的TYPE字段获取class==============================");
            Class doubleClass = Double.TYPE;
            Log.i(TAG,"基本类型的包装类也可以通过 TYPE字段获取Class：" + doubleClass);
            Log.i(TAG,"5 获取指定类的父类 class==============================");
            Class baseFraClass = BaseFragment.class;
            Log.i(TAG,"BaseFragment的Class对象: " + baseFraClass);
            Class baseFraFatherClass = baseFraClass.getSuperclass();
            Log.i(TAG,"BaseFragment的父类的Class对象: " + baseFraFatherClass);

            Log.i(TAG,"获取类的详细信息, 修饰符，类名，泛型类型，父类， 以hashMap类为例====================");
            Class hashMapClass = HashMap.class;
            Log.i(TAG,"hashMap 的 name为：" + hashMapClass.getName());
            Log.i(TAG,"hashMap 的 SimpleName为：" + hashMapClass.getSimpleName());
            Log.i(TAG,"hashMap 的 类修饰符为：" + Modifier.toString(hashMapClass.getModifiers()));

            TypeVariable[] tv = hashMapClass.getTypeParameters();
            StringBuilder pas = new StringBuilder();
            for (int i = 0; i < tv.length; i++) {
                TypeVariable t = tv[i];
                pas.append(t.getName() + "  ");
            }
            Log.i(TAG,"hashMap 的 泛型为：" + pas);

            Type[] ts = hashMapClass.getGenericInterfaces();
            StringBuilder genericInterface = new StringBuilder();
            for (int i = 0; i < ts.length; i++) {
                genericInterface.append(ts[i].toString() + "  ");
            }
            Log.i(TAG,"hashMap 的 实现接口, getGenericInterfaces ：" + genericInterface);

            Type[] tss = hashMapClass.getInterfaces();
            StringBuilder interfaces = new StringBuilder();
            for (int i = 0; i < tss.length; i++) {
                interfaces.append(tss[i].toString() + "  ");
            }
            Log.i(TAG,"hashMap 的 实现接口, getInterfaces ：" + interfaces);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}

