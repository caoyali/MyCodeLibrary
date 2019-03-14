package com.example.forev.mycodelibrary;
import android.database.Cursor;
import android.provider.UserDictionary;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;

public class ProviderTestAct extends BaseActivity {
    @BindView(R.id.mSearchText)
    EditText mSearchText;
    @BindView(R.id.mOkButton)
    Button mOkButton;
    @BindView(R.id.mListView)
    ListView mListView;

    //事实上我们主要用到的数据就是word，和local这两个。 为什么非得加上了个_ID呢？看上去没有用处呀。
    //原因是，本界面想使用内容提供程序查出来的cursor作为参数传入一个Android提供的专门的Adapter中。
    //利用这个adapter，与ListView结合，使信息展示到界面上。
    //但是这种专门的Adapter中，没有_ID键是不行的。查询结果里面必须有_ID这一列，才可以正常展示。
    String[] mProjection = {
            UserDictionary.Words._ID,
            UserDictionary.Words.WORD,
            UserDictionary.Words.LOCALE
    };

    String mSelection = null;
    String[] mSelectionArgs = {""};
    String mSearchString = "";
    @Override
    protected int getLayoutId() {
        return R.layout.activity_provider_test;
    }

    @Override
    protected void initView() {

    }

    @OnClick({R.id.mOkButton})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.mOkButton:
                if (TextUtils.isEmpty(mSearchText.getText().toString())){
                    mSelection = null;
                    mSelectionArgs[0] = "";
                } else {
                    mSearchString = mSearchText.getText().toString();
                    //这里有个需要注意的点是，请务必用 ？占位符来防止SQL注入问题。尤其是当前代码中 表现出来的情景
                    //及有个选择用户的输入框。让你自己输入。如果不用占位符，用户随意输入一些特殊的字串可能会被拼凑出
                    //一个另外的sql语句导致数据出现异常。
                    mSelection = UserDictionary.Words.WORD + " = ?";
                    mSelectionArgs[0] = mSearchString;
                }

                //cursor是一个游标对象。cursor对象会为查询结果提供随机读取访问权限。通过这个对象
                //可以循环访问结果中的行，确定每个列的数据类型，从列中获取数据，并检查结果的其他属性。
                //某些Cursor实现会在提供程序的数据发生改变时自动更新对象或者在Cursor更改时触发观察程序对象中的方法！
                //如果没有与条件匹配的行，会返回一个cursor.getCount()为0的cursor对象。
                //如果发生内部错误，查询结果将取决于具体的提供程序，可能会选择返回null或者引发异常。

                /**
                 * 提供程序本身可能会根据查询的对象性质来限制对列的访问。例如联系人提供程序会限定只有同步适配器才能
                 * 访问某些列，因此不会将他们返回至Activity或者服务。
                 */
                Cursor cursor = getContentResolver().query(
                        UserDictionary.Words.CONTENT_URI,
                        mProjection,
                        mSelection,   //是一个表示条件的语句此处为 word = ?;
                        mSelectionArgs,//上一行里面的？占位符。 是一个数组。
                        ""//排序
                );
                if (null == cursor){
                    Toast.makeText(this, "结果为null！", 3000).show();
                } else if (cursor.getCount() < 1){
                    Toast.makeText(this, "一行都没有！", 3000).show();
                } else {
                    Toast.makeText(this, "有结果", 3000).show();
                    setListView(cursor);
                    ergodicCursor(cursor);
                }
                break;
        }
    }

    int[] mItemTextViews = {R.id.mWord, R.id.mLocal};
    String[] mItemValueMapColumnName = {UserDictionary.Words.WORD, UserDictionary.Words.LOCALE};

    /**
     * 将查询结果展示到列表里面
     * cursor的查询结果里面一定要带_ID这一列！！！_ID 是Android 内容提供器相关数据里面必带的主键。
     * @param cursor
     */
    private void setListView(Cursor cursor){
        if (null == cursor){
            return;
        }

        //SimpleCursorAdapter 是 android 提供的一种adapter。
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(
                this,
                R.layout.item_provider_act_list,
                cursor,
                mItemValueMapColumnName,
                mItemTextViews,
                0
        );

        mListView.setAdapter(simpleCursorAdapter);
    }

    private void ergodicCursor(Cursor cursor){
        if (null == cursor){
            return;
        }
        int columnCount = cursor.getColumnCount();
        StringBuilder builder = new StringBuilder();
        while (cursor.moveToNext()){
            for (int i = 0; i < columnCount; i++) {
                builder.append(cursor.getColumnName(i) + " = "+ cursor.getString(i) + ", ");
            }
            builder.append("\n");
        }

        Log.i("ProviderTestAct", builder.toString());
    }
}
