package com.youdian.soundeffects;


import com.youdian.soundeffects.hkq.FirstMusicThread;
import com.youdian.soundeffects.hkq.LinuxKeyboardListener;
import com.youdian.soundeffects.hkq.RegisterUI;
import com.youdian.soundeffects.hkq.SecondMusicThread;

/**
 * Linux环境键盘监听
 *
 * @author DHB
 */
@SuppressWarnings("all")
public class LinuxKeyboardListenerApp {
    private RegisterUI registerUI;
    private LinuxKeyboardListener keyboardListener;
    private FirstMusicThread firstMusicThread;
    private SecondMusicThread secondMusicThread;

    public void before() {
        registerUI = new RegisterUI ( );
        keyboardListener = new LinuxKeyboardListener ( );
        firstMusicThread = new FirstMusicThread ( );
        secondMusicThread = new SecondMusicThread ( );

    }

    public void listening() {
        registerUI.init ( );
        registerUI.listening ( );
        try {
            Thread.sleep ( 1000 );
            keyboardListener.init ( );
            firstMusicThread.init ( );
            secondMusicThread.init ( );
            keyboardListener.callback ( (type , nativeKeyEvent) -> {
                switch (type) {
                    case LinuxKeyboardListener.TYPED:
                        break;
                    case LinuxKeyboardListener.PRESSED:
                        System.out.println ( "按下" + nativeKeyEvent.getKeyCode ( ) );
                        int code = nativeKeyEvent.getKeyCode ( );
                        switch (code) {
                            case 65:
                                firstMusicThread.destroy ();
                                secondMusicThread.destroy ( );
                                break;
                            case 87:
                                secondMusicThread.destroy ( );
                                break;
                            case 88:
                                firstMusicThread.destroy ();
                                break;
                            case 28:
                            case 42:
                            case 57:
                            case 39:
                                firstMusicThread.unListening ( );
                                secondMusicThread.resume ( );
                                break;
                            case 66:
                                firstMusicThread = new FirstMusicThread ( );
                                firstMusicThread.huifu ( );
                                firstMusicThread.init ();
                                firstMusicThread.listening ();
                                firstMusicThread.resume ();
                                secondMusicThread=new SecondMusicThread ();
                                secondMusicThread.init ();
                                secondMusicThread.listening ();
                                secondMusicThread.resume ();
                                break;
                            default:
                        }
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
            secondMusicThread.listening ( );
        } catch (InterruptedException e1) {
            e1.printStackTrace ( );
        }
        new Thread ( () -> {
            while (true) {
                try {

                    Thread.sleep ( 6000 );
                    keyboardListener.unListening ( );
                    firstMusicThread.unListening ( );
                    secondMusicThread.unListening ( );
                    registerUI.destroy ( );
                    registerUI.unListening ( );

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
        RegisterUI registerUI = new RegisterUI ( );
        registerUI.destroy ( );
        registerUI.unListening ( );
        secondMusicThread.unListening ( );
        secondMusicThread.destroy ( );
    }
}