package com.googlecode.jtiger.core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

public final class RegexUtil {
  public static boolean isMatchs(String exp, String val, boolean isCaseSensitive) {
    if(StringUtils.isBlank(val)) {
      return false;
    }
    String compare = ((String) val).trim();
    if (compare.length() == 0) {
      return false;
    }

    Pattern pattern;
    if (isCaseSensitive) {
      pattern = Pattern.compile(exp);
    } else {
      pattern = Pattern.compile(exp, Pattern.CASE_INSENSITIVE);
    }

    Matcher matcher = pattern.matcher(compare);
    return matcher.matches();
  }
}
