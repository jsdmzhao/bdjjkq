package com.googlecode.jtiger.core.util;

/**
 * Created by IntelliJ IDEA. User: 裴贺先 Date: 2004-5-17 Time: 10:59:59
 * ClassDescription:取出汉字字符串的拼音首字母
 */
public final class GB2Alpha {

  /**
   * 字母Z使用了两个标签，这里有２７个值
   */ 
  private final static String GB_CHARS
    = "啊芭擦搭蛾发噶哈哈击喀垃妈拿哦啪期然撒塌塌塌挖昔压匝座";
  /**
   * 26个英文字母
   */
  private static final char[] ALPHA_TABLE = { 'A', 'B', 'C', 'D', 'E', 
      'F', 'G', 'H', 'I',
      'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
      'X', 'Y', 'Z' };
  /**
   * 汉字编码表
   */
  private static final int[] TABLE;
  /**
   * 编码表容量
   */
  private static final int TABLE_LENGTH = 27;
  /**
   * 高8位掩码
   */
  private static final int OFFSET_HIGHT = 0xff00;
  /**
   * 低8位掩码
   */
  private static final int OFFSET_LOW = 0xff;
  
  static {
      TABLE = new int[TABLE_LENGTH];
      for (int i = 0; i < TABLE_LENGTH; ++i) {
        TABLE[i] = gbValue(GB_CHARS.charAt(i));
      }
  }
  /**
   * 私有构造器，防止实例化
   */
  private GB2Alpha() {
  }

  /**
   * 主函数,输入字符,得到他的声母, 英文字母返回对应的大写字母
   * 其他非简体汉字返回 '0'
   */
  public static char char2Alpha(char ch) {
    if (ch >= 'a' && ch <= 'z') {
      return (char) (ch - 'a' + 'A');
    }
    if (ch >= 'A' && ch <= 'Z') {
      return ch;
    }

    int gb = gbValue(ch);
    if (gb < TABLE[0]) {
      return '0';
    }

    int i;
    for (i = 0; i < TABLE_LENGTH; ++i) {
      if (match(i, gb)) {
        break;
      }
    }
    
    return (i >= TABLE_LENGTH) ? '0' : ALPHA_TABLE[i];
  }

  
  /**
   * 根据一个包含汉字的字符串返回一个汉字拼音首字母的字符串
   */
  public static String string2Alpha(String sourceStr) {
    String result = "";
    int strLength = sourceStr.length();
    int i;
    try {
      for (i = 0; i < strLength; i++) {
        result += char2Alpha(sourceStr.charAt(i));
      }
    } catch (Exception e) {
    }
    return result;
  }
  /**
   * 
   */
  private static boolean match(int i, int gb) {
    if (gb < TABLE[i]) {
      return false;
    }

    int j = i + 1;

    // 字母Z使用了两个标签
    while (j < TABLE_LENGTH && (TABLE[j] == TABLE[i])) {
      ++j;
    }
    
    return (j == TABLE_LENGTH) ? gb <= TABLE[j] : gb < TABLE[j];
  }

  
  /**
   * 取出汉字的编码
   */
  private static int gbValue(char ch) {
    String str = "";
    str += ch;
    try {
      byte[] bytes = str.getBytes("GB2312");
      if (bytes.length < 2) {
        return 0;
      }
     
      return (bytes[0] << Byte.SIZE & OFFSET_HIGHT) + (bytes[1] & OFFSET_LOW);
    } catch (Exception e) {
      return 0;
    }
  }
  
  public static void main(String args[]) {
  	System.out.println(GB2Alpha.string2Alpha("天天想锻炼，只是太懒。。。"));
  }
}