package com.youdian.soundeffects;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;
import com.youdian.soundeffects.hkq.FirstMusicThread;
import com.youdian.soundeffects.hkq.RegisterUI;
import com.youdian.soundeffects.hkq.SecondMusicThread;
import com.youdian.soundeffects.hkq.WindowsKeyboardListener;


/**
 * 测试Windows环境键盘监听
 *
 * @author DHB
 */
@SuppressWarnings("all")
public class WindowsKeyboardListenerApp {

    private WindowsKeyboardListener keyboardListener;
    private FirstMusicThread firstMusicThread;
    private SecondMusicThread secondMusicThread;


    /**
     * 测试Windows环境键盘监听播放音频
     *
     * @author hkq
     * 2019-05-06 21:00
     */

    public void before() {

        keyboardListener = new WindowsKeyboardListener ( );
        firstMusicThread = new FirstMusicThread ( );
        secondMusicThread=new SecondMusicThread ();

    }


    public void listening() {
        keyboardListener.init ( );
        firstMusicThread.init (  );

        // 监听回调
        keyboardListener.callback ( hhook -> {
            User32.LowLevelKeyboardProc keyboardHook = (code , wParam , info) -> {
                if (code >= 0) {
                    switch (wParam.intValue ( )) {
                        case WinUser.WM_KEYUP:
                        case WinUser.WM_KEYDOWN:
                        case WinUser.WM_SYSKEYUP:
                        case WinUser.WM_SYSKEYDOWN:
                            System.err.println ( "in callback, key=" + info.vkCode );
                            int codes=info.vkCode;
                            switch (code) {
                                case 1:
                                    firstMusicThread.destroy ();
                                    secondMusicThread.destroy ( );
                                    break;
                                case 88:
                                    secondMusicThread.destroy ( );
                                    break;
                                case 87:
                                    firstMusicThread.destroy ();
                                case 28:
                                case 42:
                                case 57:
                                case 39:
                                    firstMusicThread.unListening ( );
                                    secondMusicThread.resume ( );
                                    break;
                                case 60:
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
                            firstMusicThread.resume ( );
                            break;
                        default:

                    }
                }
                Pointer pointer = info.getPointer ( );
                long peer = Pointer.nativeValue ( pointer );
                return User32.INSTANCE.CallNextHookEx ( hhook , code , wParam , new WinDef.LPARAM ( peer ) );
            };
            return keyboardHook;
        } );

        // 测试停止后运行
        new Thread ( () -> {
            try {
                while (true) {
                    Thread.sleep ( 10000 );
                    keyboardListener.unListening ( );
                    firstMusicThread.unListening ();
                    System.out.println ( "停止" );
                    Thread.sleep ( 5000 );
                    System.out.println ( "重新运行" );
                    keyboardListener.resume ( );
                }
            } catch (InterruptedException e) {
                e.printStackTrace ( );
            }

        } ).start ( );


        keyboardListener.listening ( );
        firstMusicThread.listening ( );

        while (true) {
            // 阻塞
        }
    }


    public void after() {
        keyboardListener.unListening ( );
        keyboardListener.destroy ( );
        firstMusicThread.unListening ( );
        firstMusicThread.destroy ( );

    }
}