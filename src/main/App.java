package main;

import gui.Gui;
import util.FileUtil;

/**
 * slimon
 * 07.07.2014
 */
public class App {

    static Gui gui;

    public static void main(String[] args) {
        /*try {
            URLConnection.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(0);*/
        /*
        try {
            FileUtil.writeToFile("1.txt", HTTPUtil.getContentOfHTTPPage("http://blazar.ru/alliance.php", "Windows-1251"), false);
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        /*Thread.sleep(2000);
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_F5);
        robot.keyRelease(KeyEvent.VK_F5);*/
        /*Color color = new Color(35, 223, 48);
        Thread.sleep(2000);
        ImageIO.write(ImageUtil.getScreenShot(), "png", new File("1.png"));
        System.out.println(ImageUtil.containsColor(ImageUtil.getScreenShot(), color));*/


        System.out.println("Blazar Utils by slimon");
        Options.load();
        Observer.init();
        Chat.init();

        gui = new Gui("Blazar Utils v0.2 Dev", 400, 250, 500, 200, true);
        //gui.pack();
        gui.setVisible(true);
    }

    public static void onError(Exception e) {
        String errLog =
                "### " + Observer.getDateAndTime() + "\n"
                + "A error has been detected\n\n"
                + "Error at " + e.getClass() + "\n"
                + "[STACK TRACE]\n";
        StackTraceElement[] trace = e.getStackTrace();
        for (StackTraceElement aTrace : trace) {
            errLog += ("    " + aTrace.toString() + "\n");
        }
        errLog += "[CAUSED BY]\n" + "    " + e.getCause() + "\n\n    " + e.getMessage();
        FileUtil.writeToFile("log.txt", errLog, true);
    }
}
