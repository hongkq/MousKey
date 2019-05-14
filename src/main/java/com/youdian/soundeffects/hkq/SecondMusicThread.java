package com.youdian.soundeffects.hkq;


import com.youdian.soundeffects.util.ThreadUtil;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ScheduledThreadPoolExecutor;


/**
 * 音效播放监听
 *
 * @author hkq
 */
public class SecondMusicThread implements MusicListener {

    /**
     * 停止任务
     */
    public static final int STOP = 0;
    /**
     * 任务运行中
     */
    public static final int RUNNING = 1;
    /**
     * 重新运行
     */
    public static final int RESUME = 2;
    private ScheduledThreadPoolExecutor secpoolExecutor;
    private volatile int resume = RESUME;
    private volatile boolean isRun = true;
    private Runnable runnable;
    private InputStream url;
    private AudioStream audioStream;
    private Object callback;



    /**
     * 初始化
     *
     * @param
     */
    @Override
    public void init() {
        url = null;
        audioStream = null;
        secpoolExecutor = ThreadUtil.newExecutorService ( 1 , this.getClass ( ).getName ( ) );



    }

    /**
     * 监听
     */
    @Override
    public void listening() {

        if (runnable == null) {
                newTask ( );
            secpoolExecutor.submit ( runnable );
            } else {
                throw new IllegalArgumentException ( "listening() 仅允许执行一次" );
        }

    }

    /**
     * 监听
     *
     * @param callback 回调
     */
    @Override
    public void callback(Object callback) {
        this.callback = callback;
    }


    /**
     * 创建播放线程
     */
    public void newTask() {


        runnable = () -> {
            while (isRun) {
                if (resume == RESUME) {
                    // 播放
                    try {
                            url = this.getClass ( ).getResourceAsStream ( "/a1.wav" );

                        // 创建音频流对象
                        audioStream = new AudioStream ( url );
                        // 使用音频播放器播放声音
                        AudioPlayer.player.start ( audioStream );


                    } catch (FileNotFoundException e) {
                        e.printStackTrace ( );
                    } catch (IOException e) {
                        e.printStackTrace ( );
                    } finally {
                        //如果资源为空，关闭资源
                        try {
                            unListening ();
                            if (url == null) {
                                url.close ( );
                            }
                            if (audioStream == null) {
                                audioStream.close ( );
                            }

                        } catch (IOException e) {
                            e.printStackTrace ( );
                        }

                    }
                    // 复位
                    resume = RUNNING;

                }
                if (resume == RUNNING) {
                    /*
                     * 处理音频事件
                     * https://stackoverflow.com/questions/10684631/key-listener-written-in-java-jna-cannot-stop-the-thread
                     * PeekMessage 非阻塞
                     * GetMessage  阻塞
                     * */

                }
            }

        }
        ;
    }

    /**
     * 取消监听
     * FristMusicThreadl类
     */
    @Override
    public void unListening() {
        resume = STOP;

    }

    /**
     * 恢复监听
     */
    @Override
    public void resume() {
       resume = RESUME;


    }

    /**
     * 结束
     */
    @Override
    public void destroy() {
        isRun = false;
        unListening ( );
        secpoolExecutor.shutdown( );

    }

}