package com.example.hypernotas;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class VisualizarVideos extends AppCompatActivity {

    VideoView vid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_videos);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        vid=findViewById(R.id.vdvideos);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(vid);
        Uri ruta=Uri.parse(getIntent().getStringExtra("Uri"));
        vid.setMediaController(mediaController);
        vid.setVideoURI(ruta);
        vid.start();
    }
}