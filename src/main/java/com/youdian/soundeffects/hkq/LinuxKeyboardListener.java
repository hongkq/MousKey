package com.youdian.soundeffects.hkq;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Linux系统键盘监听
 *
 * @author hkq
 */
public class LinuxKeyboardListener implements KeyboardListener<KeyBoardCallback<NativeKeyEvent>>, NativeKeyListener {


    /**
     * 按下和弹起
     */
    public static final int TYPED = 1;
    /**
     * 按下
     */
    public static final int PRESSED = 2;
    /**
     * 弹起
     */
    public static final int RELEASED = 3;
    private KeyBoardCallback<NativeKeyEvent> callback;
    private volatile boolean isListen = true;

    @Override
    public void init() {
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.WARNING);
        logger.setUseParentHandlers(false);
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void listening() {
        GlobalScreen.addNativeKeyListener(this);
    }

    @Override
    public void callback(KeyBoardCallback<NativeKeyEvent> callback) {
        this.callback = callback;
    }


    /**
     * 取消监听
     */
    @Override
    public void unListening() {
        isListen = false;
    }

    /**
     * 恢复监听
     */
    @Override
    public void resume() {
        isListen = true;
    }

    /**
     * 结束
     */
    @Override
    public void destroy() {
        try {
            GlobalScreen.removeNativeKeyListener(this);
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {
        if (isListen) {
            callback.callback(TYPED, nativeKeyEvent);
        }
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
        if (isListen) {
            callback.callback(PRESSED, nativeKeyEvent);
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
        if (isListen) {
            callback.callback(RELEASED, nativeKeyEvent);
        }
    }
}
