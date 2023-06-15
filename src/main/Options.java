package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Options {

	private static Properties prop = new Properties();
    private static File config = new File("settings.cfg");
	
	public static void load() {
		if(!config.exists()) {
			createDefaultConfig();
		} else {
			try {
				prop.load(new FileInputStream(config));
			} catch (IOException e) {
                App.onError(e);
			}
		}
	}
	
	public static void save() {
		try {
            config.createNewFile();
			prop.store(new FileOutputStream(config), null);
		} catch (IOException e) {
            App.onError(e);
		}
	}
	
	
	private static void createDefaultConfig() {
		
		try {
            config.createNewFile();
		} catch (IOException e) {
            App.onError(e);
		}
		
		prop.clear();
		prop.setProperty("color", "35, 223, 48");
        prop.setProperty("time", "30m");
        prop.setProperty("randTime", "10m");
        prop.setProperty("coordsEnabled", "true");
        prop.setProperty("coords", "450, 167, 630, 370");
        prop.setProperty("delayAfterRefresh", "5000");

		try {
			prop.store(new FileOutputStream(config), null);
		} catch (IOException e) {
            App.onError(e);
		}
	
	}
	
	public static String get(String s) {
		if(prop.containsKey(s)) {
			return prop.getProperty(s);
		}
		
		//Info.warning("param."+s+" was not found in config, using default value");
		return "knf";
	}
	
	public static void set(String s, String f) {
		prop.setProperty(s, f);
	}
	
}
