package com.example.pratical_testing_1.MainActivity.utils;

import android.media.MediaPlayer;
import android.media.MediaRecorder;

import java.io.IOException;

public class AudioHelper {
    private MediaRecorder recorder;
    private String filePath;

    public AudioHelper(String filePath) {
        this.filePath = filePath;
    }

    public void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(filePath);
        try {
            recorder.prepare();
            recorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopRecording() {
        recorder.stop();
        recorder.release();
        recorder = null;
    }

    public void playAudio() {
        MediaPlayer player = new MediaPlayer();
        try {
            player.setDataSource(filePath);
            player.prepare();
            player.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

