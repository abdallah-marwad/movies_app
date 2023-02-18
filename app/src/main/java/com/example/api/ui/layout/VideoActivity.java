package com.example.api.ui.layout;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.api.R;

public class VideoActivity extends AppCompatActivity {
    VideoView videoView;
    MediaController mediaController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

//        videoView = findViewById(R.id.video_view);
//        mediaController = new MediaController(this);
//        videoView.setVideoURI(Uri.parse("https://www.youtube.com/watch?v=RlOB3UALvrQ"));
//        mediaController.setAnchorView(videoView);
//        videoView.setMediaController(mediaController);
//
//        videoView.setOnPreparedListener(mediaPlayer -> {
//            videoView.start();
//        });

    }
}