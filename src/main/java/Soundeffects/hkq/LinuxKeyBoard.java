package Soundeffects.hkq;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.dispatcher.DefaultDispatchService;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import java.util.logging.Logger;

/**
 * Demo LinuxKeyBoard
 *linux系统键盘事件监听，使用了lib目录下的jnativehook.jar
 * @author hkq
 * @date 2019/05/01
 */
public class LinuxKeyBoard  implements NativeKeyListener {

    private static final Logger logger = Logger.getLogger( GlobalScreen.class.getPackage().getName());

    /**
     * Demo LinuxKeyBoard
     *
     * @author hkq
     * @date 2019/05/01
     */
    public LinuxKeyBoard() {

        //添加监听调度员，如果是Swing程序，就用SwingDispatchService，不是就用默认的
        GlobalScreen.setEventDispatcher(new DefaultDispatchService ());
        try {
            //注册监听
            GlobalScreen.registerNativeHook();
            //加入键盘监听，
            GlobalScreen.addNativeKeyListener(this);
        } catch (NativeHookException e) {
            e.printStackTrace();
        }
    }



    /***
     *
     * @param nativeEvent
     * 实现键盘监听的三个方法，根据自己情况实现
     */
    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeEvent) {

    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeEvent) {
        boolean [] on_off={true};
        //开启音频播放线程
        new Thread(new Music(on_off)).run ();

    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeEvent) {
    }
}
