package com.example.homework3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;

public class Editor extends AppCompatActivity implements View.OnClickListener {
    Spinner music;
    Spinner sound1;
    Spinner sound2;
    Spinner sound3;
    SeekBar seek1;
    SeekBar seek2;
    SeekBar seek3;
    Button exit;

    int current;

    public final static String MUSIC = "music name";

    public final static String SOUND1 = "sound 1";
    public final static String SOUND2 = "sound 2";
    public final static String SOUND3 = "sound 3";

    public final static String SEEK1 = "seek 1";
    public final static String SEEK2 = "seek 2";
    public final static String SEEK3 = "seek 3";

    public final static String MAX_SEEK = "max seek";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        //Set each item to their appropriate id
        music = (Spinner) findViewById(R.id.music_spinner);
        sound1 = (Spinner) findViewById(R.id.sound_spinner1);
        sound2 = (Spinner) findViewById(R.id.sound_spinner2);
        sound3 = (Spinner) findViewById(R.id.sound_spinner3);
        seek1 = (SeekBar) findViewById(R.id.seekBar);
        seek2 = (SeekBar) findViewById(R.id.seekBar2);
        seek3 = (SeekBar) findViewById(R.id.seekBar3);
        exit = (Button) findViewById(R.id.exit_editor);

        current = 0;

        //set listeners
        exit.setOnClickListener(this);
        music.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                musicHandler(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sound1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setSound1(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sound2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setSound2(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sound3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setSound3(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        seek1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                MainActivity.musicService.setStart1(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seek2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                MainActivity.musicService.setStart2(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seek3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                MainActivity.musicService.setStart3(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        if(savedInstanceState != null){
            //get the saved spinner entries
            music.setSelection(savedInstanceState.getInt(MUSIC));
            sound1.setSelection(savedInstanceState.getInt(SOUND1));
            sound2.setSelection(savedInstanceState.getInt(SOUND2));
            sound3.setSelection(savedInstanceState.getInt(SOUND3));
            //set max value for the seek bars
            seek1.setMax(savedInstanceState.getInt(MAX_SEEK));
            seek2.setMax(savedInstanceState.getInt(MAX_SEEK));
            seek3.setMax(savedInstanceState.getInt(MAX_SEEK));
            //get the saved seek bar entries
            seek1.setProgress(savedInstanceState.getInt(SEEK1));
            seek2.setProgress(savedInstanceState.getInt(SEEK2));
            seek3.setProgress(savedInstanceState.getInt(SEEK3));

            current = savedInstanceState.getInt(MUSIC);
        }
    }

    private void setSound1(int position) {
        MainActivity.musicService.setSound1(position);
    }

    private void setSound2(int position) {
        MainActivity.musicService.setSound2(position);
    }

    private void setSound3(int position) {
        MainActivity.musicService.setSound3(position);
    }

    private void musicHandler(int position) {
        if (current != position) {
            if (position == 0) {
                //set a new max
                seek1.setMax(39);
                seek2.setMax(39);
                seek3.setMax(39);
            } else if (position == 1) {
                //set a new max
                seek1.setMax(61);
                seek2.setMax(61);
                seek3.setMax(61);
            } else if (position == 2) {
                //set a new max
                seek1.setMax(123);
                seek2.setMax(123);
                seek3.setMax(123);
            }
            //reset seek bar progress
            seek1.setProgress(0);
            seek2.setProgress(0);
            seek3.setProgress(0);
            //set the new music
            MainActivity.musicService.setMusic(position);
            MainActivity.getReceiver().updateName(music.getSelectedItem().toString());

            current = position;

            MusicService.stopTask();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //save the entries on the spinners
        outState.putInt(MUSIC, music.getSelectedItemPosition());
        outState.putInt(SOUND1, sound1.getSelectedItemPosition());
        outState.putInt(SOUND2, sound2.getSelectedItemPosition());
        outState.putInt(SOUND3, sound3.getSelectedItemPosition());
        //save position of the seek bars
        outState.putInt(SEEK1, seek1.getProgress());
        outState.putInt(SEEK2, seek2.getProgress());
        outState.putInt(SEEK3, seek3.getProgress());
        //save the maximum for the seek bars
        outState.putInt(MAX_SEEK, seek1.getMax());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {
        MainActivity.getReceiver().updatePicture("default");
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivityIfNeeded(intent, 0);
    }
}