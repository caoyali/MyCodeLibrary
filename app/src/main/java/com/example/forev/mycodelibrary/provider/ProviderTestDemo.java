package com.example.forev.mycodelibrary.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.forev.mycodelibrary.data.MainDataBaseHelper;

public class ProviderTestDemo extends ContentProvider {
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {

        sUriMatcher.addURI("com.example.forev.mycodelibrary.provider", "table3", 1);

        sUriMatcher.addURI("com.example.forev.mycodelibrary.provider", "table3/#", 2);
    }

    private MainDataBaseHelper mOpenHelper;
    private SQLiteDatabase mDb;
    @Override
    public boolean onCreate() {
        //用户初始化提供程序，Android系统会在创建提供程序之后立即调用此方法！
        // 当ContentResolver对象尝试访问提供程序时，系统才会创建它。

        //切记onCreate()方法里的逻辑一定不要太耗时。
        //此时仅仅创建了一个对象而已。运行不耗时！
        //数据库专门的helper，有创建数据库的逻辑，但是！仅仅在打开库的时候发现没有匹配的数据库才会调用创建数据库的逻辑。
        //而这里仅仅是new 了一个对象，并没有涉及访问的逻辑。所以不会建库。
        mOpenHelper = new MainDataBaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        //从你的提供程序检索数据。所用参数选择要查询的表，要返回的行和列以及排序。返回一个Cursor对象。

        //事实上你可以根据uri， 用UriMatcher匹配，是哪张表的！！这样的话可以更灵活的得出你请求的结果。
        return mDb.query("main", projection, selection, selectionArgs, null, null,sortOrder);
    }

    //详见 {@link https://developer.android.google.cn/guide/topics/providers/content-provider-creating#TableMIMETypes}
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        //返回内容URI对应的MIME类型。
        return null;
    }

    //代表你对jpeg,png,gif的图片资源感兴趣。
    // 如果是 "*\/jpeg"，代表只要是 jpeg格式的，你都感兴趣，管他是不是图片。
    //如果是 image/*则代表只要是图像，不管什么格式你都感兴趣。
    //如果你不对任何文件感兴趣，请返回null！
    private static String[] sTreamTypes = { "image/jpeg", "image/png", "image/gif"};
    @Nullable
    @Override
    public String[] getStreamTypes(@NonNull Uri uri, @NonNull String mimeTypeFilter) {
        return sTreamTypes;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        //在提供程序中插入一个行，在您的提供程序中插入一个新行。使用参数选择目标表并获取要使用的列值。 返回新插入行的内容 URI。

        //获取可写数据库， 如果发现不匹配，就会调用helper的onCreate()方法.
        mDb = mOpenHelper.getWritableDatabase();

        long id = mDb.insert("main", null, values);
        Uri newUri = ContentUris.withAppendedId(uri,  id);
        return newUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        //从您的提供程序中删除行。使用参数选择要删除的表和行。 返回已删除的行数。

        // delete() 方法不需要从您的数据存储中实际删除行。 如果您将同步适配器与提供程序一起使用，
        // 应该考虑为已删除的行添加“删除”标志，而不是将行整个移除。
        // 同步适配器可以检查是否存在已删除的行，并将它们从服务器中移除，然后再将它们从提供程序中删除。
        int rowsNumberDelete = mDb.update("main", null, selection, selectionArgs);
        return rowsNumberDelete;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        //更新您提供程序中的现有行。使用参数选择要更新的表和行，并获取更新后的列值。 返回已更新的行数。
        int rowsNumberUpdate = mDb.update("main", values, selection, selectionArgs);
        return rowsNumberUpdate;
    }
}
