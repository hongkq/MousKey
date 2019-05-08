package com.youdian.soundeffects;


/**
 * 程序入口
 *
 * @author hkq
 * @date 2019/05/01
 */
public class App {
    public static void main(String[] args) {
        LinuxKeyboardListenerApp linuxKeyboardListenerApp = new LinuxKeyboardListenerApp ( );
        linuxKeyboardListenerApp.before ( );
        linuxKeyboardListenerApp.listening ( );

        WindowsKeyboardListenerApp windowsKeyboardListenerApp = new WindowsKeyboardListenerApp ( );
        windowsKeyboardListenerApp.before ( );
        windowsKeyboardListenerApp.listening ( );


    }

}
