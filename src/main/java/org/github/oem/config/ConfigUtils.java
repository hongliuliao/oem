package org.github.oem.config;

import java.util.ResourceBundle;

public class ConfigUtils {
	public static ResourceBundle rb = ResourceBundle.getBundle("oem");
	public static int dataStartRow=Integer.parseInt(rb.getString("dataStartRow"));
	public static boolean isMultiSheet=Boolean.valueOf(rb.getString("isMultiSheet")).booleanValue();
	
}
