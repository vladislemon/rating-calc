package util;

import main.App;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * slimon
 * 05.07.2014
 */
public class ImageUtil {

    public static BufferedImage getScreenShot() {
        Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        BufferedImage capture = null;
        try {
            capture = new Robot().createScreenCapture(screenRect);
        } catch (AWTException e) {
            App.onError(e);
        }
        return capture;
    }

    /*public static boolean containsColor(BufferedImage image, Color color, int startX, int startY,
                                        int width, int height) {
        int[] pixels = image.getRGB(startX, startY, width, height, null, 0, width);
        for(int pixel : pixels) {
            if(new Color(pixel).equals(color)) {
                return true;
            }
        }
        return false;
    }*/

    public static boolean containsColors(BufferedImage image, Color[] colors, int startX, int startY,
                                        int width, int height) {
        int[] pixels = image.getRGB(startX, startY, width, height, null, 0, width);
        for(int pixel : pixels) {
            for(Color c : colors) {
                if(new Color(pixel).equals(c)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static BufferedImage getSubImage(BufferedImage image, int startX, int startY,
                                            int width, int height) {
        int[] pixels = image.getRGB(startX, startY, width, height, null, 0, width);
        BufferedImage ret = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        ret.setRGB(0, 0, width, height, pixels, 0, width);
        return ret;
    }
}
