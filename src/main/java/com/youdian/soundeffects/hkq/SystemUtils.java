package com.youdian.soundeffects.hkq;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.logging.Logger;

/**
 * Created by Administrator on 2018/6/26 0026.
 * 系统工具类
 */
public class SystemUtils {

    private static final Logger logger = Logger.getGlobal();

    /**
     * 控制音量
     * <p/>
     * 约定在应用根目录下的 temp 目录中放置3个vbs文件
     * volumeMute.vbs：用于静音
     * volumeAdd.vbs：增加音量
     * volumeMinus.vbs：减小音量
     * 文件以及文件的内容采用 Java 代码动态生成，不存在时则新建，存在时则直接调用
     *
     * @param type 0：静音/取消静音    1：增加音量  2：减小音量
     */
    public static void controlSystemVolume(String type) {
        try {
            if (type == null || "".equals(type.trim())) {
                logger.info("type 参数为空,不进行操作...");
            }
            /**tempFile：vbs 文件
             * vbsMessage：vbs 文件的内容*/
            String vbsMessage = "";
            File tempFile = null;
            Runtime runtime = Runtime.getRuntime();
            switch (type) {
                case "0":
                    tempFile = new File("temp", "volumeMute.vbs");
                    vbsMessage = !tempFile.exists() ? "CreateObject(\"Wscript.Shell\").Sendkeys \"棴\"" : "";
                    break;
                case "1":
                    tempFile = new File("temp", "volumeAdd.vbs");
                    vbsMessage = !tempFile.exists() ? "CreateObject(\"Wscript.Shell\").Sendkeys \"棷\"" : "";
                    break;
                case "2":
                    tempFile = new File("temp", "volumeMinus.vbs");
                    vbsMessage = !tempFile.exists() ? "CreateObject(\"Wscript.Shell\").Sendkeys \"棶\"" : "";
                    break;
                default:
                    return;
            }
            /**
             * 当3个vbs文件不存在时，则创建它们，应用默认编码为 utf-8 时，创建的 vbs 脚本运行时报错
             * 于是使用 OutputStreamWriter 将 vbs 文件编码改成gbd就正常了
             */
            if (!tempFile.exists() && !vbsMessage.equals("")) {
                if (!tempFile.getParentFile().exists()) {
                    tempFile.getParentFile().mkdirs();
                }
                tempFile.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "GBK");
                outputStreamWriter.write(vbsMessage);
                outputStreamWriter.flush();
                outputStreamWriter.close();
                logger.info("vbs 文件不存在，新建成功：" + tempFile.getAbsolutePath());
            }
            runtime.exec("wscript " + tempFile.getAbsolutePath()).waitFor();
            logger.info("音量控制完成.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public  void Utils(int i)  {

        if(i>50){
            i=(i-50)/2;
            for (int j = 0; j < i; j++) {
                //增加音量
                controlSystemVolume("1");

            }
        }else{
            i=(50-i)/2;
            for (int j = 0; j < i; j++) {
                //减少音量
                controlSystemVolume("2");

            }
        }
    }
}
