package com.example.homework3;

import android.media.MediaPlayer;

public class MusicPlayer implements MediaPlayer.OnCompletionListener {

    MediaPlayer player;
    int currentPosition = 0;
    int musicIndex = 0;
    private int musicStatus = 0;//0: before playing, 1 playing, 2 paused
    private MusicService musicService;

    static final int[] MUSICPATH = new int[]{
            R.raw.gotechgo,
            R.raw.fight_song,
            R.raw.march_song
    };

    static final String[] MUSICNAME = new String[]{
            "Go Tech Go!",
            "VT Fight Music",
            "VT March Music"
    };

    static final int[] SOUNDPATH = new int[]{
            R.raw.clapping,
            R.raw.cheering,
            R.raw.lestgohokies
    };

    public MusicPlayer(MusicService service) {

        this.musicService = service;
    }


    public int getMusicStatus() {

        return musicStatus;
    }

    public String getMusicName() {
        return MUSICNAME[musicIndex];
    }

    public void setMusicIndex(int index) {
        musicIndex = index;
    }

    public void playMusic() {
        player= MediaPlayer.create(this.musicService, MUSICPATH[musicIndex]);
        player.start();
        player.setOnCompletionListener(this);
        musicStatus = 1;
    }

    public void pauseMusic() {
        if(player!= null && player.isPlaying()){
            player.pause();
            currentPosition= player.getCurrentPosition();
            musicStatus= 2;
        }
    }

    public void resumeMusic() {
        if(player != null){
            player.seekTo(currentPosition);
            player.start();
            musicStatus = 1;
        }
    }

    public void restartMusic() {
        if (musicStatus != 0) {
            player.release();
            player = null;
            playMusic();
        }
    }

    public void reset() {
        if (musicStatus != 0) {
            player.release();
            player = null;
            musicStatus = 0;
        }
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        player.release();
        player = null;
    }


}