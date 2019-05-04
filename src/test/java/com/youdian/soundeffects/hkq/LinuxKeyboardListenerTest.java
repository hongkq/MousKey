package com.youdian.soundeffects.hkq;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 测试Linux环境键盘监听
 *
 * @author hkq
 */
@SuppressWarnings("all")
public class LinuxKeyboardListenerTest {
    private LinuxKeyboardListener keyboardListener;

    @Before
    public void before() {
        keyboardListener = new LinuxKeyboardListener();
    }

    @Test
    public void listening() {
        keyboardListener.init();
        keyboardListener.callback((type, nativeKeyEvent) -> {
            switch (type) {
                case LinuxKeyboardListener.TYPED:
                    break;
                case LinuxKeyboardListener.PRESSED:
                    System.out.println("按下" + nativeKeyEvent.getKeyCode());
                    // 按下q取消键盘监听
                    if (nativeKeyEvent.getKeyCode() == 16) {
                        keyboardListener.unListening();
                    }
                    break;
                case LinuxKeyboardListener.RELEASED:
                    System.out.println("弹起" + nativeKeyEvent.getKeyCode());
                    break;
            }
        });
        keyboardListener.listening();
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(5000);
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


    @After
    public void after() {
        keyboardListener.unListening();
        keyboardListener.destroy();
    }
}