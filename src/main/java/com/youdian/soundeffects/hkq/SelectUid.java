package com.youdian.soundeffects.hkq;

/**
 * @author hkq
 * 这个RegisterUI类里init()方法的 声音选择框存储类
 * //声音选择框
 *  comboAcademy = new JComboBox <String> ( );
 */
public class SelectUid {
    private int uid;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "SelectUid{" +
                "uid=" + uid +
                '}';
    }
}
