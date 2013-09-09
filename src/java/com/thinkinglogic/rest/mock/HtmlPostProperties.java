/**
 *
 */
package com.thinkinglogic.rest.mock;

import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * Extension of {@link Properties} that loads properties from the request body of an html post form, and returns the
 * string from {@link #toString()}.
 * 
 */
public class HtmlPostProperties extends Properties {

	/** serialVersionUID. */
	private static final long serialVersionUID = 8253643911173484142L;

	private static final Logger logger = Logger.getLogger(HtmlPostProperties.class);

	private final String toString;

	/**
	 * Constructor accepting the value to parse for properties, and to return from {@link #toString}.
	 */
	public HtmlPostProperties(final String string) {
		this.toString = string;
		if (string.indexOf("=") > 0) {
			try {
				String[] split = string.split("&", 0);
				for (String params : split) {
					String[] pair = params.split("=", 2);
					this.put(pair[0], pair[1]);
				}
			} catch (RuntimeException e) {
				logger.error("Unable to read properties from " + string, e);
			}
		}
	}

	@Override
	public String toString() {
		return toString;
	}

}
