package Soundeffects.hkq;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;

/***
 *  Demo LinuxKeyBoard
 * windows系统下实现全局键盘的监听，基于lib目录下的jna-4.0.0jar和jna-platform.jar
 * 我也不明白为啥这个类我改成线程了
 * @author 洪凯庆
 * @date 2019/05/01
 */

public class WinKeyBoard implements Runnable{
    private static WinUser.HHOOK hhk;
    private static WinUser.LowLevelKeyboardProc keyboardHook;
    final static User32 lib = User32.INSTANCE;
    private boolean [] on_off=null;
    public WinKeyBoard(boolean [] on_off){
        this.on_off = on_off;
    }

    @Override
    public void run() {
        WinDef.HMODULE hMod = Kernel32.INSTANCE.GetModuleHandle(null);

        keyboardHook = new WinUser.LowLevelKeyboardProc () {
            public WinDef.LRESULT callback(int nCode, WinDef.WPARAM wParam, WinUser.KBDLLHOOKSTRUCT info) {

                boolean [] on_off={true};
                //开启音频播放线程
               new Thread(new Music (on_off)).run ();

                return lib.CallNextHookEx(hhk, nCode, wParam, info.getPointer());
            }
        };
        hhk = lib.SetWindowsHookEx(User32.WH_KEYBOARD_LL, keyboardHook, hMod, 0);
        int result;
        WinUser.MSG msg = new WinUser.MSG ();
        while ((result = lib.GetMessage(msg, null, 0, 0)) != 0) {

        }
        lib.UnhookWindowsHookEx(hhk);
    }
}
