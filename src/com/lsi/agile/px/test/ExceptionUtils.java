package com.lsi.agile.px.test;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtils {
	public static String analyzeThrowable(Throwable given_t) {
		StringBuilder t_txt = new StringBuilder();
		t_txt.append("...Message is [ ");
		if (given_t.getLocalizedMessage() != null)
			t_txt.append(given_t.getLocalizedMessage());
		else if (given_t.getMessage() != null) {
			t_txt.append(given_t.getMessage());
		}
		t_txt.append(" ] - ");
		t_txt.append(" and Cause is [ ");
		t_txt.append(convertStackTraceToString(given_t));
		t_txt.append(" ]");
		return t_txt.toString();
	}

	public static String convertStackTraceToString(Throwable te) {
		try {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			te.printStackTrace(pw);
			return new StringBuilder().append("------\r\n").append(sw.toString()).append("------\r\n").toString();
		} catch (Exception e2) {
		}
		return "bad stack2string";
	}
}
