package old;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static old.StringEditor.cutChar;
import static old.StringEditor.getWord;
import static old.StringEditor.pow;

public class Main {

	public static void main(String[] args) throws IOException, InterruptedException {
		File in1 = new File("in.txt");
		File out;
		File in2;
		for(int i = 1;; i++) {
			if(!new File("outs/out" + i + ".txt").exists()) {
				out = new File("outs/out" + i + ".txt");
				out.createNewFile();
				in2 = new File("outs/out" + (i - 1) + ".txt");
				break;
			}
		}
		String rating, login, number, growth, line, head, total;
		ArrayList<String> temp = new ArrayList<String>();
		int rt, ort, trt = 0, tort = 0;
		BufferedReader br = new BufferedReader(new FileReader(in1));
		BufferedWriter bw = new BufferedWriter(new FileWriter(out, true));
		bw.write("");
		for(int i = 0; (line = br.readLine()) != null; i++)
			temp.add(i, line);
		String[][] lines = new String[temp.size() + 1][4];
		for(int i = 1; i < temp.size() + 1; i++) {
			line = temp.get(i-1);
			rt = getRating(line);
			ort = getOldRating(in2, line);
			number = getWord(line, 1) + ")";
			login = getWord(line, 2);
			rating = addPoints(ort) + " -:- " + addPoints(rt);
			if(ort == 0)
				growth = "�������������";
			else
				growth = getSign(rt - ort) + addPoints(rt - ort) + " -:- " + getSign(rt - ort) + floatToString(((float)rt / (float)ort - 1f) * 100f) + "%";
			lines[i][0] = number; lines[i][1] = login; lines[i][2] = rating; lines[i][3] = growth;
			trt += rt;
			tort += ort;
		}
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");
		Date date = new Date();
		temp.clear();
		head = dateFormat.format(date);
		total = "���� �� �������: " + getSign(trt - tort) + addPoints(trt - tort) + ",    " + getSign(trt - tort) + floatToString(((float)trt / (float)tort - 1f) * 100f) + "%";
		lines[0][0] = "�"; lines[0][1] = "�������"; lines[0][2] = "����   -   �����"; lines[0][3] = "����  (%)";
		String all = head + System.lineSeparator() + format(lines) + System.lineSeparator() + total;
		bw.write(all);
		File file = new File("img/" + out.getName().split("\\.")[0] + ".png");
		BufferedImage img = stringToBufferedImage(head, 1);
		ImageIO.write(img, "png", file);
		br.close();
		bw.close();
		Thread.sleep(3000);
	}
	
	private static BufferedImage stringToBufferedImage(String s, int stringNum) {
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();
        Font f = new Font("Lucida Console", Font.PLAIN, 36);
        g.setFont(f);
        
        FontRenderContext frc = g.getFontMetrics().getFontRenderContext();
        Rectangle2D rect = f.getStringBounds(s, frc);
        
        img = new BufferedImage((int) Math.ceil(rect.getWidth()), (int) Math.ceil(rect.getHeight()), BufferedImage.TYPE_INT_ARGB);
        g = img.getGraphics();
        g.setColor(Color.black);
        g.setFont(f);

        FontMetrics fm = g.getFontMetrics();
        int x = 0;
        int y = fm.getAscent() * stringNum;
        g.drawString(s, x, y);

        g.dispose();
        return img;
    }
	
	private static BufferedImage stringsToBufferedImage(String[] s) {
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();
        Font f = new Font("Lucida Console", Font.PLAIN, 36);
        g.setFont(f);
        
        FontRenderContext frc = g.getFontMetrics().getFontRenderContext();
        Rectangle2D rect = f.getStringBounds(String.valueOf(s), frc);
        
        img = new BufferedImage((int) Math.ceil(rect.getWidth()), (int) Math.ceil(rect.getHeight()), BufferedImage.TYPE_INT_ARGB);
        g = img.getGraphics();
        g.setColor(Color.black);
        g.setFont(f);

        FontMetrics fm = g.getFontMetrics();
        int x = 0;
        int y = fm.getAscent();
        //g.drawString(s, x, y);

        g.dispose();
        return img;
    }
	
	private static String getSign(float f) {
		if(f > 0)
			return "+";
		return "";
	}

	
	private static String addPoints(int in) {
		char chars[];
		boolean minus = false;
		if(in < 0) {
			chars = Integer.toString(-in).toCharArray();
			minus = true;
		}
		else
			chars = Integer.toString(in).toCharArray();
		String result = "";
		for(int i = 0; i < chars.length; i++) {
			
			if(i != 0 && i != chars.length - 1 && (chars.length - i) % 3 == 0) {
				result += ".";
			}
			result += chars[i];
		}
		if(minus)
			return "-" + result;
		return result;
	}

	
	private static String floatToString(float f) {
		char[] ch = (f + "").toCharArray();
		int k = 0;
		for(int i = 0; i < ch.length; i++) {
			if(ch[i] == '.')
				k = i;
		}
		String s = f + "";
		if(k > 0)
			return s.substring(0, k) + s.substring(k, Math.min(k + 3, s.length() - k + 1));
		return s;
	}
	
	private static int getRating(String line) {
		for(int i = 3;; i++) {
			for(int n = 0; n < 10; n++) {
				if(getWord(line, i).contains(n + "")) {
					return Integer.parseInt(cutChar(getWord(line, i), '.'));
				}
			}
		}
	}
	
	private static String format(String[][] lines) {
		String result = "";
		String s = "";
		ArrayList<Integer> lenghts = new ArrayList<Integer>();
		for(int i = 0; i < lines.length; i++) {
			for(int k = 0; k < lines[0].length; k++) {
				if(i == 0)
					lenghts.add(k, 0);
				if(lines[i][k].length() > lenghts.get(k)) {
					lenghts.remove(k);
					lenghts.add(k, lines[i][k].length());
				}
			}
		}
		for(int i = 0; i < lines.length; i++) {
			for(int k = 0; k < lines[0].length; k++) {
				lines[i][k] += pow(" ", lenghts.get(k) - lines[i][k].length());
				if(k > 0 && k < 3)
					lines[i][k] += "  |  ";
				else if(k < 3)
					lines[i][k] += "  ";
				s += lines[i][k];
			}
			result += s + System.lineSeparator();
			s = "";
		}
		return result;
	}
	
	private static String[] formatArray(String[][] lines) {
		String result = "";
		String s = "";
		ArrayList<Integer> lenghts = new ArrayList<Integer>();
		for(int i = 0; i < lines.length; i++) {
			for(int k = 0; k < lines[0].length; k++) {
				if(i == 0)
					lenghts.add(k, 0);
				if(lines[i][k].length() > lenghts.get(k)) {
					lenghts.remove(k);
					lenghts.add(k, lines[i][k].length());
				}
			}
		}
		for(int i = 0; i < lines.length; i++) {
			for(int k = 0; k < lines[0].length; k++) {
				lines[i][k] += pow(" ", lenghts.get(k) - lines[i][k].length()); //Какой мудак это писал?
				if(k > 0 && k < 3)
					lines[i][k] += "  |  ";
				else if(k < 3)
					lines[i][k] += "  ";
				s += lines[i][k];
			}
			result += s + System.lineSeparator();
			s = "";
		}
		return new String[]{result};
	}
	
	
	private static int getOldRating(File in2, String line) throws IOException {
		BufferedReader obr = new BufferedReader(new FileReader(in2));
		String s;
		while((s = obr.readLine()) != null) {
			if(getWord(s, 2).equalsIgnoreCase(getWord(line, 2))) {
				obr.close();
				//returnh Integer.parseInt(cutChar(getRating(s) + "", '.'));
				System.out.println(s);
				return Integer.parseInt(cutChar(getWord(s, 6), '.'));
			}
		}
		obr.close();
		return 0;
	}
}