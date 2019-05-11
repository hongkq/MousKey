package com.youdian.soundeffects.hkq;

/**
 * 音效监听接口
 *
 * @author hkq
 */
public interface MusicListener<T> {
    /**
     * 初始化
     *
     * @param
     */
    void init();

    /**
     * 监听
     */
    void listening();

    /**
     * 监听
     *
     * @param callback 回调
     */
    void callback(T callback);

    /**
     * 取消监听
     */
    void unListening();

    /**
     * 恢复监听
     */
    void resume();

    /**
     * 结束
     */
    void destroy();
}


