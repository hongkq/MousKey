package com.youdian.soundeffects.hkq;

import com.youdian.soundeffects.LinuxKeyboardListenerApp;
import com.youdian.soundeffects.WindowsKeyboardListenerApp;
import com.youdian.soundeffects.util.ThreadUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.InputStream;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @author hkq
 * UI布局文件
 */
public class RegisterUI extends JFrame implements MusicListener {

    /**
     * 停止任务
     */
    public static final int STOP = 0;
    /**
     * 任务运行中
     */
    public static final int RUNNING = 1;
    /**
     * 重新运行
     */
    public static final int RESUME = 2;
    static SystemTray tray = SystemTray.getSystemTray ( );
    private static TrayIcon trayIcon = null;
    private static RegisterUI frame = new RegisterUI ( );
    public InputStream musics;
    public int uid = 2;
    ScheduledThreadPoolExecutor UIpoolExecutor;
    private JPanel contentPane;
    private JLabel labelAcademic;
    private JLabel labelHeader;
    private JLabel slidertext;
    private JSlider slider;
    private JComboBox <String> comboAcademy;
    private JButton buttonSave;
    private JButton buttonCancel;
    private ImageIcon imageIcon;
    private volatile int resume = RESUME;
    private Runnable runnable;
    private volatile boolean isRun = true;
    private Object callback;
    private FirstMusicThread firstMusicThread;
    private LinuxKeyboardListener linuxKeyboardListener;
    private String b = "机械键盘";
    private String c = "弓箭";
    private LinuxKeyboardListenerApp linuxKeyboardListenerApp;
    private WindowsKeyboardListenerApp windowsKeyboardListenerApp;


    @Override
    public void init() {

        UIpoolExecutor = ThreadUtil.newExecutorService ( 1 , this.getClass ( ).getName ( ) );
        linuxKeyboardListenerApp = new LinuxKeyboardListenerApp ( );
        windowsKeyboardListenerApp = new WindowsKeyboardListenerApp ( );

        setForeground ( SystemColor.activeCaption );
        setTitle ( "Hickeys" );
        setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE );
        setSize ( 300 , 200 );
        setLocationRelativeTo ( null );
        contentPane = new JPanel ( );
        contentPane.setBorder ( new EmptyBorder ( 5 , 5 , 5 , 5 ) );
        setContentPane ( contentPane );
        contentPane.setLayout ( null );
        this.setUndecorated ( true );
        this.setResizable ( false );
        this.getRootPane ( ).setWindowDecorationStyle ( JRootPane.PLAIN_DIALOG );

        labelAcademic = new JLabel ( "选择声音：" );
        labelAcademic.setBounds ( 30 , 30 , 72 , 18 );
        contentPane.add ( labelAcademic );

        labelHeader = new JLabel ( "音量控制：" );
        labelHeader.setBounds ( 30 , 70 , 72 , 18 );
        contentPane.add ( labelHeader );


        //音量滑动条
        slider = new JSlider ( );
        slider.setBounds ( 97 , 70 , 140 , 40 );
        slider.setPaintTicks ( true );
        contentPane.add ( slider );
        slider.setMajorTickSpacing ( 20 );
        slider.setMinorTickSpacing ( 5 );
        slider.setPaintLabels ( true );


        buttonSave = new JButton ( "隐  藏" );
        buttonSave.setBounds ( 20 , 120 , 113 , 27 );
        contentPane.add ( buttonSave );
        buttonSave.addActionListener ( e -> {
            frame.setVisible ( false );


            /***
             * 窗口最小化到任务栏托盘
             */

            //托盘图标
            ImageIcon trayImg = new ImageIcon ( RegisterUI.class.getClassLoader ( ).getResource ( "qq.jpg" ).getPath ( ) );
            destroy ( );

            //增加托盘右击菜单
            PopupMenu pop = new PopupMenu ( );
            MenuItem show = new MenuItem ( "还原" );
            MenuItem exit = new MenuItem ( "退出" );
            // 按下还原键
            show.addActionListener ( new ActionListener ( ) {
                                         @Override
                                         public void actionPerformed(ActionEvent e) {
                                             tray.remove ( trayIcon );
                                             frame.setVisible ( true );
                                             frame.setExtendedState ( JFrame.NORMAL );
                                             frame.toFront ( );

                                         }
                                     }


            );
            // 按下退出键
            exit.addActionListener ( new ActionListener ( ) {
                @Override
                public void actionPerformed(ActionEvent e) {
                    tray.remove ( trayIcon );
                    System.exit ( 0 );
                    linuxKeyboardListenerApp.after ( );
                    windowsKeyboardListenerApp.after ( );

                }
            } );

            pop.add ( show );
            pop.add ( exit );

            trayIcon = new TrayIcon ( trayImg.getImage ( ) , "键盘提示音" , pop );
            trayIcon.setImageAutoSize ( true );

            try {
                tray.add ( trayIcon );
            } catch (AWTException e1) {
                e1.printStackTrace ( );
            }


        } );

        buttonCancel = new JButton ( "退  出" );
        buttonCancel.setBounds ( 153 , 120 , 113 , 27 );
        contentPane.add ( buttonCancel );
        buttonCancel.addActionListener ( e -> {
            System.exit ( 0 );
            linuxKeyboardListenerApp.after ( );

        } );


        //声音选择框
        comboAcademy = new JComboBox <String> ( );
        comboAcademy.addItem ( b );
        comboAcademy.addItem ( c );
        comboAcademy.setBounds ( 97 , 30 , 140 , 24 );
        contentPane.add ( comboAcademy );


    }

    @Override
    public void listening() {
        if (runnable == null) {
            newTask ( );
            UIpoolExecutor.submit ( runnable );
        } else {
            throw new IllegalArgumentException ( "listening() 仅允许执行一次" );
        }

    }

    private void newTask() {
        runnable = () -> {

            while (isRun) {
                if (resume == RESUME) {
                    // 监听
                    try {
                        // Create the register window object
                        frame.init ( );
                        frame.setVisible ( true );
                        comboAcademy.addItemListener ( new ItemListener ( ) {
                            @Override
                            public void itemStateChanged(ItemEvent e) {
                                JComboBox as = (JComboBox) e.getSource ( );
                                String abc = (String) as.getSelectedItem ( );
                                SelectUid su = new SelectUid ( );

                                switch (abc) {
                                    case "机械键盘":
                                        su.setUid ( 0 );

                                        break;
                                    case "弓箭":
                                        su.setUid ( 1 );

                                        break;

                                    default:


                                }
                            }
                        } );

                    } catch (Exception e) {
                        e.printStackTrace ( );
                    }


            }
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


        }
    }

    ;
}

    @Override
    public void callback(Object callback) {
        this.callback = callback;

    }

    @Override
    public void unListening() {
        resume = STOP;
    }

    @Override
    public void resume() {
        resume = RESUME;
    }

    @Override
    public void destroy() {
        isRun = false;
        unListening ( );
        UIpoolExecutor.shutdown ( );

    }

}




