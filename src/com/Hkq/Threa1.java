package com.Hkq;


import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.sound.sampled.*;
import java.io.*;
import java.net.URL;

//键盘按下后处理音频播放事件类==线程类
public   class Threa1  implements Runnable {
    private String filename;
    private final int EXTERNAL_BUFFER_SIZE = 524288; // 128Kb
    private InputStream in;
    private AudioStream audioStream;

    private boolean [] on_off=null;
    public Threa1(boolean [] on_off){
        this.on_off = on_off;
    }


    public void run()  {
        AudioPlayer.player.stop(audioStream);
        AudioInputStream audioInputStream = null;
        SourceDataLine auline = null;
        //如何解决

        try {
            in = new FileInputStream(Filemain.class.getClassLoader().getResource("com/Hkq/a1.wav").getPath());
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

//        URL url = Thread.currentThread().getContextClassLoader()
//                .getResource(filename);
//        try {

//            File soundFile = new File("E:\\wx\\MousKey\\src\\com\\Hkq\\a1.wav");
//            audioInputStream = AudioSystem.getAudioInputStream(soundFile);
//            AudioFormat format = audioInputStream.getFormat();
//            DataLine.Info info = new DataLine.Info(SourceDataLine.class
//                    , format);
//            auline = (SourceDataLine) AudioSystem.getLine(info);
//            auline.open(format);
//            auline.start();
//
//            int nBytesRead = 0;
//            byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];
//            while (nBytesRead != -1) {
//                nBytesRead = audioInputStream.read(abData,
//                        0, abData.length);
//
//                if (nBytesRead >= 0) {
//                    auline.write(abData, 0, nBytesRead);
//
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (auline != null) {
//                auline.drain();
//                auline.close();
//            }
//        }
    }
}
