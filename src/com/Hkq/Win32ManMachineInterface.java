package com.Hkq;
/*
* 通过Win32实现全局Windows系统键盘事件的后台监听
*
* */
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;

public class Win32ManMachineInterface implements Runnable{

private static WinUser.HHOOK hhk;
    private static WinUser.LowLevelKeyboardProc keyboardHook;
    final static User32 lib = User32.INSTANCE;
    private boolean [] on_off=null;
    public Win32ManMachineInterface(boolean [] on_off){
        this.on_off = on_off;
    }

    public void run() {
        WinDef.HMODULE hMod = Kernel32.INSTANCE.GetModuleHandle(null);

        keyboardHook = new WinUser.LowLevelKeyboardProc () {
            public WinDef.LRESULT callback(int nCode, WinDef.WPARAM wParam, WinUser.KBDLLHOOKSTRUCT info) {


                boolean [] on_off={true};
                new Thread(new Threa1 (on_off)).run (); //开启音频播放线程

                return lib.CallNextHookEx(hhk, nCode, wParam, info.getPointer());
            }
        };
        hhk = lib.SetWindowsHookEx(User32.WH_KEYBOARD_LL, keyboardHook, hMod, 0);
        int result;
        WinUser.MSG msg = new WinUser.MSG ();
        while ((result = lib.GetMessage(msg, null, 0, 0)) != 0) {
//            if (result == -1) {
//                System.out.println("error in get message");
//                break;
//            } else {
//                System.out.println("got message");
//                lib.TranslateMessage(msg);
//               //当消息接收成功后执行如下线程
////                boolean [] on_off={true};
////                new Thread(new Threa1 (on_off)).run (); //开启音频播放线程
//                lib.DispatchMessage(msg);
//            }

        }
        lib.UnhookWindowsHookEx(hhk);
    }
}

