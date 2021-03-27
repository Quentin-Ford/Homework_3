package com.example.homework3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MusicCompletionReceiver extends BroadcastReceiver {

    MainActivity mainActivity;

    public MusicCompletionReceiver(){
        //empty constructor
    }

    public MusicCompletionReceiver(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String musicName= intent.getStringExtra(MusicService.MUSICNAME);
        mainActivity.updateName(musicName);
    }

    public void updateName(String musicName){
        mainActivity.updateName(musicName);
    }

    public void updatePicture(String pictureName){
        mainActivity.updatePicture(pictureName);
    }
}
