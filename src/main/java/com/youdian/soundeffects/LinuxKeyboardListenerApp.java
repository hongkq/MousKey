package com.youdian.soundeffects;


import com.youdian.soundeffects.hkq.FirstMusicThread;
import com.youdian.soundeffects.hkq.LinuxKeyboardListener;
import com.youdian.soundeffects.hkq.RegisterUI;


/**
 * Linux环境键盘监听
 *
 * @author DHB
 */
@SuppressWarnings("all")
public class LinuxKeyboardListenerApp  {
    private LinuxKeyboardListener keyboardListener;
    private FirstMusicThread firstMusicThread;
    private RegisterUI registerUI;



    public void before() {
        keyboardListener = new LinuxKeyboardListener ( );
        firstMusicThread = new FirstMusicThread ( );
        registerUI=new RegisterUI ();
    }


    public void listening() {
        keyboardListener.init ( );
        firstMusicThread.init ( );
        registerUI.init ();
        registerUI.listening ();
        keyboardListener.callback ( (type , nativeKeyEvent) -> {
            switch (type) {
                case LinuxKeyboardListener.TYPED:
                    firstMusicThread.unListening ( );
                    break;
                case LinuxKeyboardListener.PRESSED:
                    System.out.println ( "按下" + nativeKeyEvent.getKeyCode ( ) );
                    //开启音频播放
                    firstMusicThread.resume ( );

                    // 按下q取消键盘监听
                    if (nativeKeyEvent.getKeyCode ( ) == 16) {
                        keyboardListener.unListening ( );
                        firstMusicThread.unListening ( );


                    }
                    break;
                case LinuxKeyboardListener.RELEASED:
                    System.out.println ( "弹起" + nativeKeyEvent.getKeyCode ( ) );
                    firstMusicThread.unListening ( );
                    break;
                default:
            }
        } );
        keyboardListener.listening ( );
        firstMusicThread.listening ( );
        new Thread ( () -> {
            while (true) {
                try {
                    Thread.sleep ( 5000 );
                } catch (InterruptedException e) {
                    e.printStackTrace ( );
                }
                keyboardListener.resume ( );
                System.out.println ( "测试自动恢复" );
            }
        } ).start ( );

        while (true) {

        }
    }

    public void after() {
        keyboardListener.unListening ( );
        keyboardListener.destroy ( );
        firstMusicThread.unListening ( );
        firstMusicThread.destroy ( );
        registerUI.unListening ();
        registerUI.destroy ();
    }
}