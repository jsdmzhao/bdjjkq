package com.googlecode.jtiger.assess.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.xwork.StringUtils;

public final class CodesStringUtil {
	private CodesStringUtil() {
	}

	public static String buildTaCodesStr(String str) {
		if (StringUtils.isBlank(str)) {
			return "";
		}
		String regex = "[^\\d\\,\\，\\、]";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		if (m.find()) {
			str = m.replaceAll("");
		}
		str = str.replace('，', ',').replace('、', ',').replace(" ", "").replace(
				",,", ",");
		if (str.startsWith(",")) {
			str = str.substring(1, str.length());
		}
		if (str.endsWith(",")) {
			str = str.substring(0, str.length() - 1);
		}
		String[] codeArr = str.split(",");
		StringBuffer buf = new StringBuffer();
		if (codeArr != null && codeArr.length > 0) {
			for (int i = 0; i < codeArr.length - 1; i++) {
				buf.append("'").append(codeArr[i]).append("',");
			}
			buf.append("'").append(codeArr[codeArr.length - 1]).append("'");
		}
		return buf.toString();
	}
}
