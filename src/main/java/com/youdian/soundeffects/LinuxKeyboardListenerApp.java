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
public class LinuxKeyboardListenerApp {
    private LinuxKeyboardListener keyboardListener;
    private FirstMusicThread firstMusicThread;


    public void before() {

        keyboardListener = new LinuxKeyboardListener();
        firstMusicThread = new FirstMusicThread();


    }

    public void listening() {
        try {
            Thread.sleep(1000);
            keyboardListener.init();
            firstMusicThread.init();

            keyboardListener.callback((type, nativeKeyEvent) -> {
                switch (type) {
                    case LinuxKeyboardListener.TYPED:
                        break;
                    case LinuxKeyboardListener.PRESSED:
                        System.out.println("按下" + nativeKeyEvent.getKeyCode());
                        int code = nativeKeyEvent.getKeyCode();
                        switch (code) {
                            case 1:
                                firstMusicThread.destroy();

                                break;
                            case 87:

                                break;
                            case 88:
                                firstMusicThread.destroy();
                                break;
                            case 28:
                            case 42:
                            case 57:
                            case 39:
                                firstMusicThread.unListening();

                                break;
                            case 66:
                                firstMusicThread = new FirstMusicThread();
                                firstMusicThread.huifu();
                                firstMusicThread.init();
                                firstMusicThread.listening();
                                firstMusicThread.resume();

                                break;
                            default:
                        }
                        //开启音频播放
                        firstMusicThread.resume();

                        // 按下q取消键盘监听
                        if (nativeKeyEvent.getKeyCode() == 16) {
                            keyboardListener.unListening();
                            firstMusicThread.unListening();


                        }
                        break;
                    case LinuxKeyboardListener.RELEASED:
                        System.out.println("弹起" + nativeKeyEvent.getKeyCode());
                        firstMusicThread.unListening();
                        break;
                    default:
                }
            });

            keyboardListener.listening();
            firstMusicThread.listening();

        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        new Thread(() -> {
            while (true) {
                try {

                    Thread.sleep(6000);
                    keyboardListener.unListening();
                    firstMusicThread.unListening();


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                keyboardListener.resume();

                System.out.println("测试自动恢复");
            }
        }).start();

        while (true) {

        }

    }

    public void after() {
        keyboardListener.destroy();
        firstMusicThread.destroy();
        keyboardListener.unListening();

        firstMusicThread.unListening();


    }
}