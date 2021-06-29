package com.Yash.todo.utilsfound;

import java.util.Locale;
import java.util.ResourceBundle;
import static com.Yash.todo.utilsfound.Constants.MESSAGE_BUNDLE_FILE_NAME;

public class MessageReader {
	static Locale locale = new Locale("en", "US");
	static ResourceBundle rb = ResourceBundle.getBundle(MESSAGE_BUNDLE_FILE_NAME, locale);
	
	public static String getValue(String key) {
		return rb.getString(key);
	}
}