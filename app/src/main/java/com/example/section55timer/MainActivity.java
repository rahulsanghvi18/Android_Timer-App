package com.example.section55timer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SeekBar mySeekBar;
    Button startButton;
    TextView timeRemaining;
    MediaPlayer myaudio;
    CountDownTimer timer;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mySeekBar = (SeekBar)findViewById(R.id.seekBar);
        startButton = (Button)findViewById(R.id.startButton);
        timeRemaining = (TextView)findViewById(R.id.currentTimeTextView);
        mySeekBar.setMax(300);
        mySeekBar.setMin(1);
        mySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                String text = String.valueOf((int)(i/60)) + " : " + String.valueOf((i % 60));
                timeRemaining.setText(text);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int progress = mySeekBar.getProgress() * 1000;
                mySeekBar.setEnabled(false);
                if(startButton.getText() == "STOP"){
                    try{
                        myaudio.stop();
                    }catch (Exception e){

                    }

                    try{
                        timer.cancel();
                        timeRemaining.setText("00 : 00");
                    }catch (Exception e){

                    }
                    mySeekBar.setEnabled(true);
                    startButton.setText("START");
                    return;
                }
                startButton.setText("STOP");

                timer = new CountDownTimer(progress, 1000){

                    @Override
                    public void onTick(long l) {
                        String text = String.valueOf((int)(l/60000)) + " : " + String.valueOf((int)((l % 60000)/1000));
                        timeRemaining.setText(text);
                        Log.i("Timer",String.valueOf(l));
                        mySeekBar.setProgress((int)(l/1000));
                    }

                    @Override
                    public void onFinish(){
                        myaudio = MediaPlayer.create(getApplicationContext(),R.raw.alarm);
                        myaudio.start();
                        mySeekBar.setEnabled(true);
                        startButton.setEnabled(true);
                        myaudio.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                startButton.setText("START");
                            }
                        });

                        timeRemaining.setText("00 : 00");
                    }
                }.start();
            }
        });
    }
}
