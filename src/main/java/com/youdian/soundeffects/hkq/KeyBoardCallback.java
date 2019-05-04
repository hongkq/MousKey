package com.youdian.soundeffects.hkq;

/**
 * 键盘监听回调
 *
 * @author hkq
 */
@FunctionalInterface
public interface KeyBoardCallback<T> {

    /**
     * 回调
     *
     * @param type 键盘事件类型
     * @param t    类型
     */
    void callback(int type , T t);

}
