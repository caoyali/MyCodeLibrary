package com.example.forev.mycodelibrary;
import android.util.Log;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**详情请见{@link # https://www.jianshu.com/p/10c29883eac1}*/
public class ReflexDemoAct extends BaseActivity {
    private final static String TAG = "ReflexDemoAct";

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
//            Class baseFraClass = BaseFragment.class;
//            Log.i(TAG,"BaseFragment的Class对象: " + baseFraClass);
//            Class baseFraFatherClass = baseFraClass.getSuperclass();
//            Log.i(TAG,"BaseFragment的父类的Class对象: " + baseFraFatherClass);

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

            List<Class> fathers = new ArrayList<>();
            addAncestor(ReflexDemoAct.class, fathers);
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < fathers.size(); i++) {
                Class c = fathers.get(i);
                builder.append(c.getCanonicalName() + "\n");
            }
            Log.i(TAG, "ReflexDemoAct 的继承树为 ： \n" + builder.toString());

            Annotation[] annotations = ReflexDemoAct.class.getAnnotations();
            StringBuilder annotationString = new StringBuilder();
            if(null == annotations || annotations.length == 0){
                Log.i(TAG, "ReflectDemoAct类没有注解");
            } else {
                for (int i = 0; i < annotations.length; i++) {
                    Annotation annotation = annotations[i];
                    annotationString.append(annotation.toString() + "   ");
                }
                Log.i(TAG, "ReflectDemoAct类的Runtime类型的注解是： "+ annotationString.toString());
            }

            //以下是变量，方法，构造方法对应的类对应
            Log.i(TAG, Field.class.getCanonicalName() + "， 对应类的变量");
            Log.i(TAG, Method.class.getCanonicalName() + ", 对应类的方法");
            Log.i(TAG, Constructor.class.getCanonicalName() + ", 对应类的构造方法");

            Log.i(TAG, "但是，上面的方法正常情况下都不能获取private修饰的变量");
            Log.i(TAG, AccessibleObject.class.getCanonicalName() + ", 通过它的setAccessible()方法来取消Java语言访问权限的检查");
            Log.i(TAG, "很庆幸的是，Field, Method, Constructor方法均继承自AccessibleObject类，均可以设置这个入口,这样就可以访问private的内容了");

            logFieldUses();
            logMethodUses();
            logConstructorUses();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void addAncestor(Class<?> cla, List<Class> claList){
        Class<?> fatherCla = cla.getSuperclass();
        if (null != fatherCla){
            claList.add(fatherCla);
            addAncestor(fatherCla, claList);
        }
    }

    private void logFieldUses() {
        Class reflexActClass = Cat.class;
        Field[] fields = reflexActClass.getDeclaredFields();
        StringBuilder fieldBuild = new StringBuilder();
        fieldBuild.append("\n");
        for (int i = 0; i < fields.length; i++) {
            Field f = fields[i];
            fieldBuild.append("变量名:" + f.getName() + " ");
            fieldBuild.append("类型:" + f.getType() + " ");
            fieldBuild.append("修饰符:"+ Modifier.toString(f.getModifiers()) + " ");
            Annotation[] as = f.getAnnotations();
            if (null == as || as.length == 0){
                fieldBuild.append("没有注解\n");
            } else {
                for (int k = 0; k < as.length; k++) {
                    Annotation an = as[k];
                    fieldBuild.append("注解" + k + ":" + an.toString() + " ");
                }
            }

            fieldBuild.append("\n");
        }
        Log.i(TAG, "通过 getFields() 得到的public成员变量为： " + fieldBuild.toString());

        try {
            Cat cat = new Cat("yayali", 3);
            Field name = reflexActClass.getDeclaredField("name");
            //name是private类型的，所以要设置一下才可以访问。
            name.setAccessible(true);
            String nameValue = (String) name.get(cat);
            Log.i(TAG, "name的值为：" + nameValue);

            Field age = reflexActClass.getField("age");
            int ageValue = (int) age.get(cat);
            Log.i(TAG, "age的值为：" + ageValue);

            Field tag = reflexActClass.getField("TAG");
            String tagValue = (String) tag.get(cat);
            Log.i(TAG, "TAG的值为：" + tagValue);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void logMethodUses(){
        Class catClass = Cat.class;
        Method[] methods = catClass.getDeclaredMethods();
        StringBuilder builder = new StringBuilder();
        builder.append("\n");
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            builder.append("方法名为：" + method.getName() + " ");
            builder.append("修饰符为：" + Modifier.toString(method.getModifiers()) + " ");
            builder.append("返回类型为：" + method.getGenericReturnType().toString() + " ");
            //方法抛出的异常
            Type[] exceptionTypes = method.getGenericExceptionTypes();
            if (0 == exceptionTypes.length){
                builder.append("没有异常！ ");
            } else {
                builder.append("\"\\n 抛出的异常为：\\n\"");
                for (int k = 0; k < exceptionTypes.length; k++) {
                    Type type = exceptionTypes[k];
                    builder.append(type.toString() + "\n");
                }
            }

            //方法的参数，包括类型，名称，
            Type[] ptypes = method.getGenericParameterTypes();
            if (0 == ptypes.length){
                builder.append("没有参数！ ");
            } else {
                builder.append("参数类型为：\n");
                for (int c = 0; c < ptypes.length; c++) {
                    Type type = ptypes[c];
                    builder.append(type.toString() + "\n");
                }
            }

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                Parameter[] params = method.getParameters();
                if (0 == params.length) {
                    builder.append(" 没有参数");
                } else {
                    for(int y = 0; y < params.length; y++) {
                        Parameter parameter = params[y];
                        String pName = parameter.getName();
                        String type = parameter.getType().toString();
                        builder.append(" 参数" + y + "的名字是：" + pName + " 类型是：" + type + "\n");
                    }
                }

            }

            //判断是否是可变参数
            builder.append(method.isSynthetic() ? "有可变参数！ " : "没有可变参数！ ");

            builder.append("\n");
        }

        Log.i(TAG, "method相关的信息为： " + builder.toString());

        Log.i(TAG, "\n 开始通过反射调用方法！");
        Cat cat = new Cat("huahua", 6);
        Method method = null;
        try {
            //重载机制的存在，我们得传入一个描述具体传什么类型的参数
            method = Cat.class.getMethod("eat", String.class);
            method.invoke(cat, "鱼干");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void logConstructorUses(){
        Log.i(TAG, "测试构造方法！");
        try {
            Class catClass = Cat.class;
            Constructor constructor = catClass.getConstructor(String.class, int.class);
            Cat cat = (Cat) constructor.newInstance("黑猫警长", 12);

            Method sleepMethod = catClass.getMethod("sleep");
            sleepMethod.invoke(cat);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}

