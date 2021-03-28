package com.example.homework3;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class MusicService extends Service {

    MusicPlayer musicPlayer;
    static MusicPlayer sound1;
    static MusicPlayer sound2;
    static MusicPlayer sound3;
    static MyAsyncTask myAsyncTask;


    static int start1 = 0;
    static int start2 = 0;
    static int start3 = 0;

    static int seconds = 0;

    private final IBinder iBinder = new MyBinder();

    public static final String COMPLETE_INTENT = "complete intent";
    public static final String MUSICNAME = "music name";



    @Override
    public void onCreate() {
        super.onCreate();
        musicPlayer = new MusicPlayer(this);
        sound1 = new MusicPlayer(this);
        sound2 = new MusicPlayer(this);
        sound3 = new MusicPlayer(this);
        myAsyncTask= new MyAsyncTask();
    }

    public void startMusic(){
        musicPlayer.playMusic();
        myAsyncTask= new MyAsyncTask();
        myAsyncTask.execute(musicPlayer.getMusicLength());
    }

    public void pauseMusic(){
        musicPlayer.pauseMusic();
        if (sound1.getMusicStatus() == 1){
            sound1.pauseMusic();
        }
        if (sound2.getMusicStatus() == 1){
            sound2.pauseMusic();
        }
        if (sound3.getMusicStatus() == 1){
            sound3.pauseMusic();
        }
        myAsyncTask.cancel(true);
    }

    public static void stopTask() {
        myAsyncTask.cancel(true);
    }

    public void rewind() {
        seconds = 0;
    }

    public void resumeMusic(){
        musicPlayer.resumeMusic();
        if (sound1.getMusicStatus() == 2){
            sound1.resumeMusic();
        }
        if (sound2.getMusicStatus() == 2){
            sound2.resumeMusic();
        }
        if (sound3.getMusicStatus() == 2){
            sound3.resumeMusic();
        }
        myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute(musicPlayer.getMusicLength() - seconds);
    }

    public int getPlayingStatus(){
        return musicPlayer.getMusicStatus();
    }

    public void restartMusic() {
        myAsyncTask.cancel(true);
        musicPlayer.restartMusic();
        sound1.reset();
        sound2.reset();
        sound3.reset();
        seconds = 0;
        myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute(musicPlayer.getMusicLength() - seconds);
        MainActivity.getReceiver().updatePicture("default");
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

    public void setSound1(int position){
        sound1.setSoundIndex(position);
    }

    public void setSound2(int position){
        sound2.setSoundIndex(position);
    }

    public void setSound3(int position){
        sound3.setSoundIndex(position);
    }

    public void setStart1(int seconds){
        start1 = seconds;
    }

    public void setStart2(int seconds){
        start2 = seconds;
    }

    public void setStart3(int seconds){
        start3 = seconds;
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

    private static class MyAsyncTask extends AsyncTask<Integer, Integer, Void> {

        @Override
        protected Void doInBackground(Integer... params) {
            while(seconds < params[0]){
                try{
                    //checking if the asynctask has been cancelled, end loop if so
                    if(isCancelled()) break;

                    Thread.sleep(1000);

                    if (seconds == start1){
                        sound1.playSound();
                    }
                    if (seconds == start2){
                        sound2.playSound();
                    }
                    if (seconds == start3){
                        sound3.playSound();
                    }
                    seconds++;

                    //send count to onProgressUpdate to update UI
                    publishProgress(seconds);

                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            seconds = 0;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (sound1.getMusicStatus() == 1){
                MainActivity.getReceiver().updatePicture(sound1.getSoundName());
            }
            else if (sound2.getMusicStatus() == 1){
                MainActivity.getReceiver().updatePicture(sound2.getSoundName());
            }
            else if (sound3.getMusicStatus() == 1){
                MainActivity.getReceiver().updatePicture(sound3.getSoundName());
            }
        }
    }
}
