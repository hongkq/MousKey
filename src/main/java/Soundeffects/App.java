package Soundeffects;


import Soundeffects.hkq.LinuxKeyBoard;
import Soundeffects.hkq.WinKeyBoard;

/**
 * 根据操作系统判断执行操作
 * @author hkq
 *  @date 2019/05/01
 */
public class App 
{
    public static void main( String[] args )
    {
        // 当前操作系统
        String curOs = System.getProperty("os.name");
        // 当前操作系统架构
        String curArch = System.getProperty ( "os.arch" );
        System.out.println ( curOs );
        switch (curOs) {
            case "Windows":
                boolean[] on_off = {true};
               new WinKeyBoard (on_off);
                break;
            case "Linux":
                new LinuxKeyBoard ();
                break;
            default:
        }

    }
}
