package com.example.homework3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

    public final static String SOUND1 = "sound 1";
    public final static String SOUND2 = "sound 2";
    public final static String SOUND3 = "sound 3";

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

        exit.setOnClickListener(this);

        //get the saved spinner entries
        if(savedInstanceState != null){
            sound1.setSelection(savedInstanceState.getInt(SOUND1));
            sound2.setSelection(savedInstanceState.getInt(SOUND2));
            sound3.setSelection(savedInstanceState.getInt(SOUND3));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //save the entries on the spinners
        outState.putInt(SOUND1, sound1.getSelectedItemPosition());
        outState.putInt(SOUND2, sound2.getSelectedItemPosition());
        outState.putInt(SOUND3, sound3.getSelectedItemPosition());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}