package com.example.forev.mycodelibrary;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.os.Build;
import android.service.media.MediaBrowserService;
import android.support.annotation.RequiresApi;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AudioAndVideoAct extends BaseActivity {

    /*fu!! 这篇实在是看不懂啊！*/


//    private IntentFilter intentFilter = new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
//
//    private AudioManager.OnAudioFocusChangeListener afChangeListener;
//    private BecomingNoisyReceiver myNoisyAudioStreamReceiver = new BecomingNoisyReceiver();
//    private MediaSyleNotification myPlayerNotification;
//    private MediaSessionCompat mediaSession;
//    private MediaBrowserService service;
//    private SomeKindOfPlayer player;
//    AudioFocusRequest audioFocusRequest;

    MediaSessionCompat.Callback callback = new MediaSessionCompat.Callback() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onPlay() {
//            AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//            //request audio focus for playback, this registers the afChangeListener
//            AudioAttributes attrs = (new AudioAttributes.Builder()
//                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC))
//                    .build();
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                audioFocusRequest = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
//                        .setOnAudioFocusChangeListener(afChangeListener)
//                        .setAudioAttributes(attrs)
//                        .build();
//            }
//            int result = am.requestAudioFocus(audioFocusRequest);
//            if (AudioManager.AUDIOFOCUS_REQUEST_GRANTED == result) {
//                startService(new Intent(this, MediaBrowserService.class));
//                mediaSession.setActive(true);
//                player.start();
//                registerReceiver(myNoisyAudioStreamReceiver, intentFilter);
//                service.startForeground(id, myPlayerNotification);
//            }
        }

        @Override
        public void onPause() {
//            AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//            player.pause();
//            unregisterReceiver(myNoisyAudioStreamReceiver);
//            service.stopForeground(false);
        }

        @Override
        public void onStop() {
//            AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//            //Abandon audio focus
//            am.abandonAudioFocusRequest(audioFocusRequest);
//            unregisterReceiver(myNoisyAudioStreamReceiver);
//            service.stopSelf();
//            mediaSession.setActive(false);
//            player.stop();
//            service.stopForeground(false);
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_audeo_and_video;
    }

    @Override
    protected void initView() {

    }
}
