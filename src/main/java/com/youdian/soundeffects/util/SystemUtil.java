package com.youdian.soundeffects.util;

/**
 * 系统工具
 *
 * @author DHB
 */
public class SystemUtil {

    enum SystemName {

        /**
         * Windows
         */
        WINDOWS,

        /**
         * Linux
         */
        LINUX,

        /**
         * 其它
         */
        OTHER

    }

    /**
     * 获取系统名称
     *
     * @return 系统名称
     */
    public static SystemName getSystemName() {
        String os = System.getProperty("os.name");
        switch (os) {
            case "Windows":
                return SystemName.WINDOWS;
            case "Linux":
                return SystemName.LINUX;
            default:
                return SystemName.OTHER;
        }
    }
}
