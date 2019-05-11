package com.youdian.soundeffects.hkq;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 测试Windows环境键盘监听
 *
 * @author DHB
 */
@SuppressWarnings("all")
public class WindowsKeyboardListenerTest {

    private WindowsKeyboardListener keyboardListener;
    private FirstMusicThread firstMusicThread;

    /**
     * 测试Windows环境键盘监听播放音频
     *
     * @author hkq
     * 2019-05-06 21:00
     */
    @Before
    public void before() {
        keyboardListener = new WindowsKeyboardListener ( );
        firstMusicThread = new FirstMusicThread ( );
    }

    @Test
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
                            firstMusicThread.resume ( );
                            break;
                        default:
                            break;
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

    @After
    public void after() {
        keyboardListener.unListening ( );
        keyboardListener.destroy ( );
        firstMusicThread.unListening ( );
        firstMusicThread.destroy ( );
    }
}