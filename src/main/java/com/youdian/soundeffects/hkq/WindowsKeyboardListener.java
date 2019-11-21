package com.youdian.soundeffects.hkq;


import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;
import com.youdian.soundeffects.util.ThreadUtil;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.function.Function;

/**
 * windows系统键盘监听
 *
 * @author hkq
 */
public class WindowsKeyboardListener implements KeyboardListener<Function<User32.HHOOK, User32.LowLevelKeyboardProc>> {


    /**
     * 停止任务
     */
    private static final int STOP = 0;
    /**
     * 任务运行中
     */
    private static final int RUNNING = 1;
    /**
     * 重新运行
     */
    private static final int RESUME = 2;
    private volatile User32.HHOOK hhk;
    private User32.LowLevelKeyboardProc keyboardHook;
    private User32 lib;
    private WinDef.HMODULE hModule;
    private ScheduledThreadPoolExecutor executorService;
    private volatile int resume = RESUME;
    private volatile boolean isRun = true;
    private Runnable runnable;

    @Override
    public void init() {
        lib = User32.INSTANCE;
        hModule = Kernel32.INSTANCE.GetModuleHandle(null);
        executorService = ThreadUtil.newExecutorService(1, this.getClass().getSimpleName());
    }

    @Override
    public void listening() {
        if (runnable == null) {
            newTask();
            executorService.submit(runnable);
        } else {
            throw new IllegalArgumentException("listening() 仅允许执行一次");
        }
    }

    @Override
    public void callback(Function<User32.HHOOK, User32.LowLevelKeyboardProc> callback) {
        this.keyboardHook = callback.apply(hhk);
    }


    /**
     * 创建监听线程
     */
    private void newTask() {
        runnable = () -> {
            User32.MSG msg = new User32.MSG();
            while (isRun) {
                if (resume == RESUME) {
                    // 监听
                    hhk = lib.SetWindowsHookEx(WinUser.WH_KEYBOARD_LL, keyboardHook, hModule, 0);
                    // 复位
                    resume = RUNNING;
                }
                if (resume == RUNNING) {
                    /*
                     * 处理键盘事件，分发到键盘监听上
                     * https://stackoverflow.com/questions/10684631/key-listener-written-in-java-jna-cannot-stop-the-thread
                     * PeekMessage 非阻塞
                     * GetMessage  阻塞
                     * */
                    lib.PeekMessage(msg, null, 0, 0, 0);
                }
            }
        };
    }

    /**
     * 取消监听
     */
    @Override
    public void unListening() {
        lib.UnhookWindowsHookEx(hhk);
        resume = STOP;
    }

    /**
     * 恢复监听
     */
    @Override
    public void resume() {
        resume = RESUME;
    }

    /**
     * 结束
     */
    @Override
    public void destroy() {
        isRun = false;
        unListening();
        executorService.shutdown();
    }

}
