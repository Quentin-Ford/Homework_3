package com.example.homework3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView music;
    Button play;
    Button restart;
    Button editor;
    ImageView picture;

    MusicService musicService;
    MusicCompletionReceiver musicCompletionReceiver;
    Intent startMusicServiceIntent;
    boolean isBound = false;
    boolean isInitialized = false;

    public static final String INITIALIZE_STATUS = "initialization status";
    public static final String MUSIC_PLAYING = "music playing";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        music = (TextView) findViewById(R.id.music_text);
        play = (Button) findViewById(R.id.play_button);
        restart = (Button) findViewById(R.id.restart_button);
        editor = (Button) findViewById(R.id.to_editor);
        play.setOnClickListener(this);
        restart.setOnClickListener(this);
        editor.setOnClickListener(this);
        picture = (ImageView) findViewById(R.id.imageView);

        if(savedInstanceState != null){
            isInitialized = savedInstanceState.getBoolean(INITIALIZE_STATUS);
        }

        startMusicServiceIntent= new Intent(this, MusicService.class);

        if(!isInitialized){
            startService(startMusicServiceIntent);
            isInitialized = true;
        }

        musicCompletionReceiver = new MusicCompletionReceiver(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == play.getId()) {
            playClick(view);
        }
        else if (view.getId() == restart.getId()) {
            musicService.restartMusic();
        }
        else if (view.getId() == editor.getId()) {
            Intent intent = new Intent(this, Editor.class);
            startActivity(intent);
        }
    }

    public void playClick(View view) {
        if (isBound) {
            switch (musicService.getPlayingStatus()){
                case 0:
                    musicService.startMusic();
                    play.setText("Pause");
                    break;
                case 1:
                    musicService.pauseMusic();
                    play.setText("Resume");
                    break;
                case 2:
                    musicService.resumeMusic();
                    play.setText("Pause");
                    break;
            }
        }
    }


    public void updateName(String musicName) {

        music.setText(musicName);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(isInitialized && !isBound){
            bindService(startMusicServiceIntent, musicServiceConnection, Context.BIND_AUTO_CREATE);
        }

        registerReceiver(musicCompletionReceiver, new IntentFilter(MusicService.COMPLETE_INTENT));
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(isBound){
            unbindService(musicServiceConnection);
            isBound= false;
        }

        unregisterReceiver(musicCompletionReceiver);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(INITIALIZE_STATUS, isInitialized);
        super.onSaveInstanceState(outState);
    }


    private ServiceConnection musicServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MusicService.MyBinder binder = (MusicService.MyBinder) iBinder;
            musicService = binder.getService();
            isBound = true;

            switch (musicService.getPlayingStatus()) {
                case 0:
                    play.setText("Start");
                    break;
                case 1:
                    play.setText("Pause");
                    break;
                case 2:
                    play.setText("Resume");
                    break;
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            musicService = null;
            isBound = false;
        }
    };
}