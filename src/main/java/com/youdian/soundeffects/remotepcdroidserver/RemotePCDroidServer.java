package com.youdian.soundeffects.remotepcdroidserver;

import com.youdian.soundeffects.RemotePCDroidServerApp;

/**
 * @author hkq
 */
public abstract class RemotePCDroidServer {
    protected RemotePCDroidServerApp application;

    public RemotePCDroidServer(RemotePCDroidServerApp application) {
        this.application = application;
    }

    public abstract void close();
}
