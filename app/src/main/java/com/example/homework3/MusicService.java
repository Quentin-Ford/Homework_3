package com.example.homework3;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class MusicService extends Service {

    MusicPlayer musicPlayer;
    private final IBinder iBinder = new MyBinder();

    public static final String COMPLETE_INTENT = "complete intent";
    public static final String MUSICNAME = "music name";

    @Override
    public void onCreate() {
        super.onCreate();
        musicPlayer = new MusicPlayer(this);
    }

    public void startMusic(){
        musicPlayer.playMusic();
    }

    public void pauseMusic(){
        musicPlayer.pauseMusic();
    }

    public void resumeMusic(){
        musicPlayer.resumeMusic();
    }

    public int getPlayingStatus(){
        return musicPlayer.getMusicStatus();
    }

    public void restartMusic() {
        musicPlayer.restartMusic();
    }

    public void setMusic(int position){
        musicPlayer.setMusicIndex(position);
    }

    public void newMusic(){
        musicPlayer.reset();
    }

    public String getMusic(){
        return musicPlayer.getMusicName();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }


    public class MyBinder extends Binder {
        MusicService getService(){
            return MusicService.this;
        }
    }
}
