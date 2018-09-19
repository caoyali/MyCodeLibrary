package com.example.forev.mycodelibrary.utils;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.forev.mycodelibrary.R;

public class DialogFragmentUtils {

    public static CustomableDialogFragment showCommonLoadingDialog(Context applicationContext,
                                                                   FragmentManager fm,
                                               String text) {
        LayoutInflater inflater = (LayoutInflater)applicationContext.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fragment_dialog_common_loading, null);
        TextView textView = view.findViewById(R.id.text);
        if (!TextUtils.isEmpty(text)) {
            textView.setVisibility(View.VISIBLE);
            textView.setText(text);
        } else {
            textView.setVisibility(View.GONE);
        }

        return showDialogFragment(fm, view, null, null);

    }

    public static CustomableDialogFragment showDialogFragment(FragmentManager fm, View layout,
                                                              View.OnClickListener listener,
                                                              int... clickAbleIds) {
        CustomableDialogFragment customableDialogFragment = new CustomableDialogFragment();
        customableDialogFragment.setViewData(layout, listener, clickAbleIds);
        customableDialogFragment.show(fm, "CustomableDialogFragment");
        return customableDialogFragment;
    }

    public static CustomableDialogFragment showDialogFragment(FragmentManager fm, View layout,
                                                              View.OnClickListener listener,
                                                              boolean isCancelable,
                                                              int... clickAbleIds) {
        CustomableDialogFragment customableDialogFragment = new CustomableDialogFragment();
        customableDialogFragment.setViewData(layout, listener, clickAbleIds);
        customableDialogFragment.setCancelable(isCancelable);
        customableDialogFragment.show(fm, "CustomableDialogFragment");
        return customableDialogFragment;
    }

    public static void showDialog(CustomableDialogFragment fragment, FragmentManager fm) {
        if (null != fragment && null != fragment.getDialog()) {
            if (!fragment.getDialog().isShowing()) {
                fragment.show(fm, "CustomableDialogFragment");
            }
        }
    }

    public static void cancelDialog(CustomableDialogFragment fragment) {
        if (null != fragment && null != fragment.getDialog()) {
            fragment.dismiss();
        }
    }

    /**
     * @author caoyali
     * 一个可以自定义view的DialogFragment。
     * 不要主动调用此类。可在DialogUtils的方法中间接调用。
     */
    public static class CustomableDialogFragment extends DialogFragment {
        private View mLayout;
        private View.OnClickListener mOnclickListener;
        private int[] ids;

        /**
         * 设置弹窗的数据，要展示什么？展示的内容中有哪些view可以触发点击事件。
         * @param layout 想要在弹窗中展示的view.
         * @param onClickListener 点击回调对象。
         * @param viewIds 所有自定义的可点击的view的id，都要写进来。
         */
        public void setViewData(View layout, @Nullable View.OnClickListener onClickListener, @Nullable int... viewIds) {
            this.mLayout = layout;
            this.mOnclickListener = onClickListener;
            this.ids = viewIds;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            if (null != mLayout) {
                getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                //没有listner没必要遍历设置。 没有可绑定listner的view，也没必要设置。
                if (null != mOnclickListener && null != ids && ids.length > 0) {
                    for (int i = 0; i < ids.length; i++) {
                        mLayout.findViewById(ids[i]).setOnClickListener(mOnclickListener);
                    }
                }
            }
            return mLayout;
        }
    }
}
