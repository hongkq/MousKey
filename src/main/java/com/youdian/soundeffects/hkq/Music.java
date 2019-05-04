package com.youdian.soundeffects.hkq;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.SourceDataLine;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author hkq
 * @date 2019/05/01
 */
public class Music implements Runnable{
    private InputStream in;
    private AudioStream audioStream;

    private boolean [] on_off=null;
    public Music(boolean [] on_off){
        this.on_off = on_off;
    }


    @Override
    public void run()  {
        AudioPlayer.player.stop(audioStream);
        AudioInputStream audioInputStream = null;
        SourceDataLine auline = null;


        try {
            in = new FileInputStream ( Music.class.getClassLoader().getResource("a1.wav").getPath());
            // 创建音频流对象
            try {
                audioStream = new AudioStream(in);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 使用音频播放器播放声音
            AudioPlayer.player.start(audioStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
}
