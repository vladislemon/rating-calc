package main;

import timer.Timer;
import timer.TimerListener;
import util.ImageUtil;
import util.Sound;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * slimon
 * 05.07.2014
 */
public class Observer {

    private static Timer timer;
    private static Sound sound;
    private static long time;
    private static boolean isAttacked = false;

    static void init() {
        //sound = new Sound(new File("ALARM.WAV"));
        //sound.setVolume(1F);
    }

    private static boolean searchAttacks() {
        App.gui.attackObserver.timeLeft.setText("-");
        try {
            //Robot robot = new Robot();
            //robot.keyPress(KeyEvent.VK_F5);
            //robot.keyRelease(KeyEvent.VK_F5);
            Thread.sleep(Integer.parseInt(Options.get("delayAfterRefresh")));
            BufferedImage image = ImageUtil.getScreenShot();
            boolean attack;
            if(App.gui.attackObserver.coordsCheckBox.isSelected()) {
                int[] coords = getCoords();
                attack = ImageUtil.containsColors(image, getColors(), coords[0], coords[1], coords[2], coords[3]);
            } else {
                attack = ImageUtil.containsColors(image, getColors(), 0, 0, image.getWidth(), image.getHeight());
            }
            //FileUtil.writeToFile("log.txt", attack, true);
            if(attack) {
                isAttacked = true;
            }
            return attack;
        //} catch (AWTException e) {
         //   App.onError(e);
        } catch (InterruptedException e) {
            App.onError(e);
        }
        return false;
    }

    public static String getDateAndTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/YY HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    private static void alert() {
        App.gui.attackObserver.timeLeft.setText("Тревога!!!");
        App.gui.attackObserver.timeLeft.setForeground(Color.RED);
        while(isAttacked) {
            if(!sound.isPlaying()) {
                sound.play();
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                App.onError(e);
            }
        }
    }

    public static boolean startObserve() {
        System.out.println("Starting observe...");
        timer = new Timer(1);
        timer.register(timerListener);

        Random rand = new Random(System.nanoTime());
        if(getRandTime() > 0) {
            time = getTime() + rand.nextInt(getRandTime()) - rand.nextInt(getRandTime());
        } else {
            time = getTime();
        }
        timer.start();
        return true;
    }

    public static boolean stopObserve() {
        System.out.println("Stopping observe...");
        timer.stopTimer();
        App.gui.attackObserver.timeLeft.setText("-");
        if(isAttacked) {
            isAttacked = false;
            sound.stop();
            App.gui.attackObserver.timeLeft.setForeground(Color.BLACK);
        }
        return true;
    }

    public static void saveSettings() {
        Options.set("color", App.gui.attackObserver.colorField.getText());
        Options.set("time", App.gui.attackObserver.timeField.getText());
        Options.set("randTime", App.gui.attackObserver.timeRandomField.getText());
        Options.set("coordsEnabled", Boolean.toString(App.gui.attackObserver.coordsCheckBox.isSelected()));
        Options.set("coords", App.gui.attackObserver.coordsField.getText());
        Options.save();
    }

    public static void saveScreenShotToFile() {
        File file = new File("test.png");
        if(file.exists()) {
            if(!file.delete()) {
                return;
            }
            try {
                file.createNewFile();
            } catch (IOException e) {
                App.onError(e);
            }
        }
        try {
            int[] c = getCoords();
            ImageIO.write(ImageUtil.getSubImage(ImageUtil.getScreenShot(), c[0], c[1], c[2], c[3]), "png", file);
        } catch (IOException e) {
            App.onError(e);
        }
    }

    private static Color[] getColors() {
        String[] rawRGB = App.gui.attackObserver.colorField.getText().split("\\D+");
        int[][] RGB = new int[rawRGB.length/3][3];
        for(int i = 0; i < rawRGB.length; i++) {
            RGB[i/3][i%3] = Integer.parseInt(rawRGB[i]);
        }
        Color[] ret = new Color[rawRGB.length/3];
        for(int i = 0; i < ret.length; i++) {
            ret[i] = new Color(RGB[i][0], RGB[i][1], RGB[i][2]);
        }
        return ret;
    }

    private static int getRandTime() {
        return parseTimeToInt(App.gui.attackObserver.timeRandomField.getText());
    }

    private static int getTime() {
        return parseTimeToInt(App.gui.attackObserver.timeField.getText());
    }

    private static int parseTimeToInt(String rawTime) {
        String[] rawTimes = rawTime.split("\\s+");
        int time = 0, num;
        for(String s : rawTimes) {
            num = Integer.parseInt(s.split("\\D+")[0]);
            if(s.contains("h") || s.contains("H")) {
                time += num * 60 * 60;
            } else if(s.contains("m") || s.contains("M")) {
                time += num * 60;
            } else {
                time += num;
            }
        }
        return time;
    }

    private static String parseIntToTime(int rawTime) {
        int h = rawTime / 3600;
        int m = (rawTime - h * 3600) / 60;
        int s = rawTime - h * 3600 - m * 60;
        return h + "h " + m + "m " + s + "s";
    }

    private static int[] getCoords() {
        String[] rawCoords = App.gui.attackObserver.coordsField.getText().split("\\D+");
        int[] coords = new int[rawCoords.length];
        for(int i = 0; i < rawCoords.length; i++) {
            coords[i] = Integer.parseInt(rawCoords[i]);
        }
        return coords;
    }

    private static void doVladWork() {
        Robot robot;
        try {
            robot = new Robot();
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
            Thread.sleep(3000);
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
            Thread.sleep(5000);
        } catch (AWTException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static TimerListener timerListener = new TimerListener() {
        @Override
        public void onTick(long currentTick) {
            if(currentTick >= time) {
                timer.stopTimer();
                //doVladWork();
                if(searchAttacks()) {
                    //alert();
                    doVladWork();
                    startObserve();
                } else {
                    startObserve();
                }
            } else {
                App.gui.attackObserver.timeLeft.setText(parseIntToTime((int) (time - currentTick)));
                //gui.timeLeft.setForeground(Color.BLACK);
            }
        }
    };
}
