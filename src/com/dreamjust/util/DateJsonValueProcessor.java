package com.dreamjust.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class DateJsonValueProcessor implements
		JsonValueProcessor {
	public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
	private DateFormat dateFormat;

	public DateJsonValueProcessor(String datePattern) {
		try {
			dateFormat = new SimpleDateFormat(datePattern,Locale.UK);
		} catch (Exception ex) {
			dateFormat = new SimpleDateFormat(
					DEFAULT_DATE_PATTERN);
		}
	}

	public Object processArrayValue(Object value,
			JsonConfig jsonConfig) {
		return process(value);
	}

	public Object processObjectValue(String key,
			Object value, JsonConfig jsonConfig) {
		return process(value);
	}

	private Object process(Object value) {
		if (value instanceof Date) {
//			SimpleDateFormat sdf = new SimpleDateFormat(
//					DEFAULT_DATE_PATTERN, Locale.UK);
			return dateFormat.format(value);
		}
		return value == null ? "" : value.toString();
	}
}
