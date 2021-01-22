package org.subra.aem.rjs.core.commons.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Raghava Joijode
 */
public class RJSStringUtils extends StringUtils {

	public static final String COMMA = ",";
	public static final String HYPHEN = "-";
	public static final String UNDER_SCORE = "_";
	public static final String SLASH = "/";
	public static final String COLON = ":";
	public static final String SEMI_COLON = ";";
	public static final String AT = "@";
	public static final String AMP = "&";

	private RJSStringUtils() {
		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static boolean isNoneEmpty(String string, String... strings) {
		return isNotEmpty(string) && Arrays.stream(strings).allMatch(RJSStringUtils::isNotEmpty);
	}

	public static boolean isAllBlank(String string, String... strings) {
		return !(isNotBlank(string) || Arrays.stream(strings).anyMatch(RJSStringUtils::isNotBlank));
	}

	public static List<String> getLookUpKeys(final String str) {
		return RJSCollectionUtils.getStreamFromArray(substringsBetween(str, "${", "}")).map(i -> i.contains(":-") ? substringBefore(i, ":-") : i).collect(Collectors.toList());
	}

	public static String encode(final String value, final String charset) throws UnsupportedEncodingException {
		return URLEncoder.encode(value, defaultIfBlank(charset, "UTF-8"));
	}

}