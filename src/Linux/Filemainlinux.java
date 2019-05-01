package Linux;
/*writer:Hkq
time: 19.04.27 15.05
mein: 设置窗口大小，页面布局，弹出窗口的快捷键，设置声音滚动条，获取鼠标键盘的后台进程
* */
//以下三行用于引入添加组件、设置布局管理器及处理事件所需的软件包

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

//下行说明主类派生自JFrame/框架类，要实现ActionListener接口以处理动作事件
class Filemaininux extends JFrame implements ActionListener, ItemListener, ChangeListener {
    //以下四行用于声明要加到框架窗口中的所有组件
    JComboBox adminType;
    JLabel title, oneButton, twoButton, threeButton;
    JPasswordField poject;
    JButton backButton, ExitButton;
    JSlider slider, slider2;

    public Filemaininux() {
        super ( "Hing keys" );//调用父类构造方法，设置窗口标题
        setSize ( 400 , 300 );//设置窗口大小
        getContentPane ( ).setLayout ( null );//设置布局管理器，将窗口分成5行2列，行、列间保留10个像素的空白
//    setIconImage(Toolkit.getDefaultToolkit().createImage( "../images/qq.png" ));  //为该窗体设置一个Logo 这个无法使用
        setResizable ( false );//实现窗体没有放大功能
        setLocation ( 500 , 300 );//在屏幕中设置显示的位置
        setVisible ( true );   //显示



//以下8行具体创建组件实例
        title = new JLabel ( "Hing Keys" );
        title.setBounds ( 185 , 10 , 100 , 25 );
        oneButton = new JLabel ( "选择声音 :" );
        oneButton.setBounds ( 40 , 40 , 100 , 25 );
        adminType = new JComboBox ( new String[]{"机械键盘" , "打字机" , "钢琴"} );
        adminType.setBounds ( new Rectangle ( 140 , 40 , 100 , 25 ) );
        twoButton = new JLabel ( "音量控制 :" );
        twoButton.setBounds ( 40 , 80 , 100 , 25 );
        slider = new JSlider ( );//音量滑动条
        slider.setBounds ( 140 , 80 , 140 , 25 );
        slider.setPaintTicks ( true );
        //设置主、次刻度的间距
        slider.setMajorTickSpacing ( 20 );
        slider.setMinorTickSpacing ( 5 );
        slider.setPaintLabels ( true );
        threeButton = new JLabel ( "音调 :" );
        threeButton.setBounds ( 40 , 120 , 100 , 25 );
        slider2 = new JSlider ( );//音调滑动条
        slider2.setBounds ( 140 , 120 , 140 , 25 );
        backButton = new JButton ( "隐藏" );
        backButton.setBounds ( 100 , 160 , 80 , 25 );
        ExitButton = new JButton ( "退出" );
        ExitButton.setBounds ( 200 , 160 , 80 , 25 );
        //以下两行设置用于保存结果的标签的前景色属性
        title.setForeground ( Color.blue );
        oneButton.setForeground ( Color.black );
        twoButton.setForeground ( Color.black );
        threeButton.setForeground ( Color.black );
        backButton.setForeground ( Color.blue );
        ExitButton.setForeground ( Color.blue );
//以下为按钮、滑动条注册监听者
        adminType.addItemListener ( new MyItemListener ( ) );//为下拉框注册监听器
        backButton.addActionListener ( this );
        ExitButton.addActionListener ( this );
        slider.addChangeListener ( this );
        slider2.addChangeListener ( this );

        //以下六行将所有组件加入到框架窗口中
        getContentPane ( ).add ( title );
        getContentPane ( ).add ( oneButton );
        getContentPane ( ).add ( twoButton );
        getContentPane ( ).add ( threeButton );
        getContentPane ( ).add ( ExitButton );
        getContentPane ( ).add ( backButton );
        getContentPane ( ).add ( adminType );
        getContentPane ( ).add ( slider );
        getContentPane ( ).add ( slider2 );


//以下一行用于窗口事件监听者注册
        //  addWindowListener ( new com.Hkq.main.WindowManager ( ) );
        setVisible ( true );//使框架窗口变为可见


    }
    //以下函数用于处理按钮动作事件

    @Override
    public void actionPerformed(ActionEvent e) {


    }

    @Override
    public void itemStateChanged(ItemEvent e) {

    }

    //以下函数用于处理下拉选择框
    private class MyItemListener implements ItemListener {

        /**
         *
         * @param e
         */
        @Override
        public void itemStateChanged(ItemEvent e) {
            JComboBox lt = (JComboBox) e.getSource ( );
            String clr = (String) lt.getSelectedItem ( );
            System.out.println ( "打印输出:" + clr );

            switch (clr) {
                case "机械键盘":

                    LinuxManMachineInterface linux=new LinuxManMachineInterface ();

                    break;
                case "打字机":

                    break;
                case "钢琴":

                    break;
                default:

            }
        }
    }

    //以下函数用于处理滑动条动作事件
    @Override
    public void stateChanged(ChangeEvent e) {

    }


    //以下为系统主函数，是程序的入口
    public static void main(String args[]) {

        new Filemaininux ( );//创建框架窗口实例


    }



}



