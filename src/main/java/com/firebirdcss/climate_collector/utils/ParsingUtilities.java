/**
 * All rights reserved, Copyright 2018 by Scott Griffis
 */
package com.firebirdcss.climate_collector.utils;

/**
 * 
 * @author Scott Griffis
 *
 */
public final class ParsingUtilities {
	/**
	 * Parses the contents of a {@link String} that exists between two given keywords.
	 * 
	 * @param inputString - The inputString as {@link String}
	 * @param beginKeyword - The beginKeyword as {@link String}
	 * @param endKeyword - The endKeyword as {@link String}
	 * @return Returns the parsed result as {@link String}
	 */
	public static String parseBetweenKeywords(String inputString, String beginKeyword, String endKeyword) {
		
		return parseBetweenKeywords(inputString, beginKeyword, endKeyword, 0);
	}
	
	/**
	 * Parses the contents of a {@link String} that exists between two given keywords, starting from a 
	 * given beginIndex.
	 * 
	 * @param inputString - The inputString as {@link String}
	 * @param beginKeyword - The beginKeyword as {@link String}
	 * @param endKeyword - The endKeyword as {@link String}
	 * @param beginIndex - The beginIndex as <code>int</code>
	 * @return Returns the parsed result as {@link String}
	 */
	public static String parseBetweenKeywords(String inputString, String beginKeyword, String endKeyword, int beginIndex) {
		if (inputString != null && beginKeyword != null && endKeyword != null && beginIndex > -1 && beginIndex < inputString.length()) {
			int beginKeyIndex = inputString.indexOf(beginKeyword, beginIndex);
			if (beginKeyIndex != -1) { // Yea! Begin Keyword exists...
				int beginKeyEndIndex = beginKeyIndex + beginKeyword.length();
				int endKeyIndex = inputString.indexOf(endKeyword, beginKeyEndIndex);
				if (endKeyIndex != -1) { // Yea! End Keyword exists...
					
					return inputString.substring(beginKeyEndIndex, endKeyIndex);
				}
			}
		}
		
		return null;
	}
	
	/**
	 * Sanitizes and returns a Float value as derived from the given string value.
	 * 
	 * @param value - The value as {@link String}
	 * @return Returns the Value as {@link Float}
	 */
	public static Float sanitizeFloatString(String value) {
		StringBuilder floatValue = new StringBuilder();
		boolean numberStarted = false;
		for (char c : value.toCharArray()) {
			if ((c >= '0' && c <= '9') || c == '.' || c == '-') {
				numberStarted = true;
				floatValue.append(c);
			} else {
				if (numberStarted) {
					break; // Number is finished...
				}
			}
		}
		Float result = null;
		try {
			result = Float.parseFloat(floatValue.toString());
		} catch (Exception fallThroughAsNull) {}
		
		return result; 
	}
	
	/**
	 * Sanitizes and returns an Integer value as derived from the given string value.
	 * 
	 * @param value - The given string value as {@link String}
	 * @return Returns the sanitized {@link Integer}
	 */
	public static Integer sanitizeIntegerString(String value) {
		StringBuilder integerValue = new StringBuilder();
		boolean numberStarted = false;
		for (char c : value.toCharArray()) {
			if ((c >= '0' && c <= '9') || c == '-') {
				numberStarted = true;
				integerValue.append(c);
			} else {
				if (numberStarted) {
					break; // Number is finished...
				}
			}
		}
		Integer result = null;
		try {
			result = Integer.valueOf(integerValue.toString());
		} catch (Exception fallThroughAsNull) {}
		
		return result; 
	}
	
	/**
	 * Removes leading or trailing quotes from the given string.
	 * 
	 * @param string - The given string as {@link String}
	 * @return Returns the result as {@link String}
	 */
	public static String removeQuotes(String string) {
		if (string != null && !string.isEmpty()) {
			String tempResult = string;
			while (tempResult.startsWith("\"") || tempResult.startsWith("'")) { // Remove the starting quote...
				tempResult = tempResult.substring(1);
			}
			while (tempResult.endsWith("\"") || tempResult.endsWith("'")) { // Remove the ending quote...
				tempResult = tempResult.substring(0, tempResult.length() - 1);
			}
			
			return tempResult;
		}
		
		return null;
	}
	
	/**
	 * Determines if a given string value is numeric.
	 * 
	 * @param stringValue - The stringValue as {@link String}
	 * @return Returns the result as <code>boolean</code>
	 */
	public static boolean isNumeric(String stringValue) {
		if (stringValue != null && !stringValue.isEmpty()) {
			for (char c : stringValue.toCharArray()) {
				if (c > '9' || c < '0') {
					
					return false;
				}
			}
		
			return true;
		}
		
		return false;
	}
}
