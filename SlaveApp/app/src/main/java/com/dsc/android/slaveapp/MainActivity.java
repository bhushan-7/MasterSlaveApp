package com.dsc.android.slaveapp;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

public class MainActivity extends Activity {

    Server server;
    TextView infoip, msg;
    LinearLayout linearLayout,linearLayoutafter;
    VideoView videoView;
    SeekBar seekBar;
    ImageView imageView;
    Button buttonChoose;
    Intent intent;
    String uri;
    Handler handler;
    Boolean aBoolean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        infoip = (TextView) findViewById(R.id.infoip);
        msg = (TextView) findViewById(R.id.msg);
        videoView=(VideoView) findViewById(R.id.videoview);
        imageView=(ImageView) findViewById(R.id.togglebutton);
        buttonChoose=(Button) findViewById(R.id.buttonchoose);
        seekBar=(SeekBar) findViewById(R.id.seekbar);
        linearLayout=(LinearLayout) findViewById(R.id.llc);
        linearLayout.setAlpha(0);
        server = new Server(this);
        infoip.setText(server.getIpAddress() + ":" + server.getPort());
        linearLayoutafter=(LinearLayout) findViewById(R.id.llafter);

        //
        intent=getIntent();

        uri= intent.getExtras().getString("path");
        videoView.setVideoPath(uri);
        handler=new Handler();
        videoView.pause();
        aBoolean=false;
        updateseekbar();
    }


    /////
    public void updateseekbar() {
        handler.postDelayed(updateTimeTask,100);
    }

    public Runnable updateTimeTask=new Runnable() {
        @Override
        public void run() {
            seekBar.setProgress(videoView.getCurrentPosition());
            seekBar.setMax(videoView.getDuration());
            handler.postDelayed(this,100);

            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    handler.removeCallbacks(updateTimeTask);
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    handler.removeCallbacks(updateTimeTask);
                    videoView.seekTo(seekBar.getProgress());
                    updateseekbar();

                }
            });


        }
    };


    public void toggle_method(View v){
        if (aBoolean){
            videoView.pause();
            aBoolean=false;
            imageView.setImageResource(R.drawable.ic_play_arrow_black_24dp);

        }
        else if (aBoolean == false){
            videoView.start();
            updateseekbar();
            aBoolean=true;
            imageView.setImageResource(R.drawable.ic_pause_black_24dp);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        server.onDestroy();
    }
}