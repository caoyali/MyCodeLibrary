package com.example.forev.mycodelibrary;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;

import com.constraint.AudioTypeEnum;
import com.constraint.CoreProvideTypeEnum;
import com.constraint.ResultBody;
import com.example.forev.mycodelibrary.constant.Config;
import com.example.forev.mycodelibrary.constant.UserInfoConfig;
import com.example.forev.mycodelibrary.utils.LogUtil;
import com.xs.BaseSingEngine;
import com.xs.SingEngine;
import com.xs.impl.OnEndCallback;
import com.xs.utils.AiUtil;

import org.json.JSONObject;

import butterknife.OnClick;

public class AiSoundActivity extends BaseActivity {
    private final static String TAG = "AiSoundActivity";

    private SingEngine mSingEngine;
    private BaseSingEngine.ResultListener mResultListener;
    private OnEndCallback mOnEndCallback;
    private BaseSingEngine.AudioErrorCallback mAudioErrorCallback;

    @Override
    protected int getLayoutId() {
        return R.layout.act_ai_sound;
    }

    @OnClick({R.id.mBeginTest, R.id.mEndTest})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mBeginTest:
                beginTest();
                break;
            case R.id.mEndTest:
                endTest();
                break;
        }
    }

    @Override
    protected void initView() {
        mResultListener = new BaseSingEngine.ResultListener() {
            @Override
            public void onBegin() {
                LogUtil.get().d("onBegin");
            }

            @Override
            public void onResult(JSONObject jsonObject) {
                Log.d(TAG,"onResult jsonObject=" + jsonObject);
            }

            @Override
            public void onEnd(int i, String s) {
                Log.d(TAG,"onEnd");
            }

            @Override
            public void onUpdateVolume(int i) {
                Log.d(TAG,"onUpdateVolume");
            }

            @Override
            public void onFrontVadTimeOut() {
                Log.d(TAG,"onFrontVadTimeOut");
            }

            @Override
            public void onBackVadTimeOut() {
                Log.d(TAG,"onBackVadTimeOut");
            }

            @Override
            public void onRecordingBuffer(byte[] bytes, int i) {
                Log.d(TAG,"onRecordingBuffer");
            }

            @Override
            public void onRecordLengthOut() {
                Log.d(TAG,"onRecordLengthOut");
            }

            @Override
            public void onReady() {
                Log.d(TAG,"onReady");
            }

            @Override
            public void onPlayCompeleted() {
                Log.d(TAG,"onPlayCompeleted");
            }

            @Override
            public void onRecordStop() {
                Log.d(TAG,"onRecordStop");
            }
        };


        mOnEndCallback = new OnEndCallback() {
            @Override
            public void onEnd(ResultBody resultBody) {
                Log.d(TAG,"onEnd, resultBody=" + resultBody);
            }
        };

        mAudioErrorCallback = new BaseSingEngine.AudioErrorCallback() {
            @Override
            public void onAudioError(int i) {
                Log.d(TAG,"onAudioError, i=" + i);
            }
        };

        initXsSdk();
    }

    private void initXsSdk() {
        new Thread() {
            @Override
            public void run() {
                try {
                    //获取引擎实例，设置监听测评对象, 代码中获取的实际是ApplicationContext
                    mSingEngine = SingEngine.newInstance(AiSoundActivity.this);
//                    设置引擎测评监听器
                    mSingEngine.setListener(mResultListener);
//                    设置测评完成的监听器
                    mSingEngine.setOnEndCallback(mOnEndCallback);
//                    设置录音器初始化错误的回调
                    mSingEngine.setAudioErrorCallback(mAudioErrorCallback);
//                    设置音频格式
                    mSingEngine.setAudioType(AudioTypeEnum.WAV);
//                    设置引擎类型
                    mSingEngine.setServerType(CoreProvideTypeEnum.CLOUD);
//                    设置日志级别
                    mSingEngine.setLogLevel(4);
//                    禁用实时音量返回
                    mSingEngine.disableVolume();
//                    对否开启VAD功能
                    mSingEngine.setOpenVad(false, null);
                    mSingEngine.setWavPath(AiUtil.getFilesDir(
                            AiSoundActivity.this.getApplicationContext()).getPath()
                            + "/record_temp/"); // 构建引擎初始化参数
                    mSingEngine.setServerAPI("ws://trial.cloud.ssapi.cn:8080");
//                    构建引擎初始化参数
                    JSONObject cfglnit = mSingEngine.buildInitJson(
                            UserInfoConfig.APPKEY, UserInfoConfig.SECERTKEY);
                    mSingEngine.setNewCfg(cfglnit);

                    mSingEngine.createEngine();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void beginTest() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("coreType", Config.TYPE_SENT);
            jsonObject.put("refText", "present");
            jsonObject.put("rank", 100);

            JSONObject startJson = mSingEngine.buildStartJson(Config.UserID, jsonObject);
            mSingEngine.setStartCfg(startJson);
            mSingEngine.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void endTest() {
        mSingEngine.stop();
    }
}
