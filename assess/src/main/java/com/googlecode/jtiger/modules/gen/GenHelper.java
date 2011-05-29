package com.googlecode.jtiger.modules.gen;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.sf.cglib.beans.BeanCopier.Generator;

import org.apache.commons.lang.ClassUtils;
import org.apache.ddlutils.model.Column;
import org.apache.ddlutils.model.Database;
import org.apache.ddlutils.model.Table;
import org.springframework.util.StringUtils;

import com.googlecode.jtiger.core.Constants;
import com.ibm.icu.math.BigDecimal;


public abstract class GenHelper {
  public static final String TABLE_NAME_SPLITOR = "_";
  public static final String PK = "id";
  
  /**
   * 根据给定的表名，从Database中找到Table
   */
  public static Table getTable(Database db, String tablename) {
    Table[] tables = db.getTables();
    if(tables == null) {
      return null;
    }
    
    for(int i = 0; i < tables.length; i++) {
      String name = tables[i].getName();
      if(name.equalsIgnoreCase(tablename)) {
        return tables[i];
      }
    }
    return null;
  }
  
  /**
   * 表名转类名
   */
  public static String t2M(Table table) {
    String name = Inflector.getInstance().singularize(table.getName());//复数转单数
    String[] sections = name.split(TABLE_NAME_SPLITOR);
    StringBuilder buf = new StringBuilder(name.length());
    
    for(String section : sections) {
       buf.append(section.substring(0, 1).toUpperCase()).append(section.substring(1));
    }
    
    return buf.toString();
  }
  
  /**
   * 返回主键的列名
   * FIXME：不支持复合主键
   */
  public static String pk(Table table) {
    Column[] cols = table.getPrimaryKeyColumns();
    if(cols != null) {
      return cols[0].getName();
    } else {
      throw new IllegalStateException("no primary key for '" + table.getName() + "'");
    }
  }
  
  /**
   * 返回主键的属性名
   * FIXME：不支持复合主键
   */
  public static String id(Table table) {
    Column[] cols = table.getPrimaryKeyColumns();
    if(cols != null) {
      return f2p(cols[0]);
    }
    return PK;
  }
  
  /**
   * 表名转变量名
   */
  public static String t2V(Table table) {
    String name = t2M(table);
    return name.substring(0, 1).toLowerCase() + name.substring(1);
  }
  
  /**
   * 列名转属性名
   */
  public static String f2p(Column column) {
    String name = column.getName();//复数转单数
    String[] sections = name.split(TABLE_NAME_SPLITOR);
    if(sections == null) {
      throw new IllegalArgumentException("no column name.");
    }
    StringBuilder buf = new StringBuilder(name.length()).append(sections[0].toLowerCase());
    if(sections.length > 1) {
      for(int i = 1; i < sections.length; i++) {
         buf.append(sections[i].substring(0, 1).toUpperCase())
         .append(sections[i].substring(1).toLowerCase());
      }
    }
    return buf.toString();
  }
  
  /**
   * java源代码目录
   */
  public static File getJavaSource() {
    URL classpath = Generator.class.getClassLoader().getResource("");
    File temp = new File(classpath.getFile()).getParentFile().getParentFile().getParentFile();
    return new File(temp.getPath() + File.separator + "java"); //源代码
  }
  
  /**
   * i18n目录
   */
  public static File getI18nSource() {
    URL classpath = Generator.class.getClassLoader().getResource("");
    File temp = new File(classpath.getFile()).getParentFile();
    return new File(temp.getPath() + File.separator + "i18n");
  }
  
  /**
   * web源代码目录，到WEB-INF/views
   */
  public static File getWebSource() {
    URL classpath = Generator.class.getClassLoader().getResource("");
    File temp = new File(classpath.getFile()).getParentFile();
    return new File(temp.getPath() + File.separator + "views"); //web源代码
  }
  
  /**
   * 包名转换为路径，开头为{@link File#separator}
   */
  public static String package2Path(String packageName) {
    if(!StringUtils.hasText(packageName)) {
      return "";
    }
    String[] subFolders = packageName.split("\\.");
    StringBuffer buf = new StringBuffer(255);
    for(int i = 0; i < subFolders.length; i ++) {
      buf.append(File.separator).append(subFolders[i]);
    }
    return buf.toString();
  }
  
  /**
   * 表名转换为类名
   */
  public static String table2Class(String table) {
    Table t = new Table();
    t.setName(table);
    return GenHelper.t2M(t);
  }
  
  /**
   * 表名转换为模块名(类名小写)
   */
  public static String table2Model(String table) {
    return table2Class(table).toLowerCase();
  }
  
  /**
   * 转换所有列名为属性名
   */
  public static String[] getProperties(Table table) {
    Column[] cols = table.getColumns();
    String[] names = new String[cols.length];
    
    for(int i = 0; i < cols.length; i++) {
      names[i] = f2p(cols[i]);
    }
    return names;
  }
  
  /**
   * 返回所有列对应的java数据类型的短类名
   */
  public static String[] getJavaTypes(Table table) {
    Column[] cols = table.getColumns();
    String[] types = new String[cols.length];
    Map<Integer, Class<?>> typesMap = getTypesMap();
    
    for(int i = 0; i < cols.length; i++) {
      Class<?> clazz = typesMap.get(cols[i].getTypeCode());
      if(cols[i].getName().toLowerCase().endsWith("time") || 
          cols[i].getName().toLowerCase().endsWith("date")) {//MySql的日期类型居然是String
        clazz = Date.class;
      }
      if(clazz == null) {
        clazz = String.class;
      }  
      types[i] = ClassUtils.getShortClassName(clazz);
    }
    
    return types;
  }
  
  /**
   * 返回需要导入的java类
   */
  public static Set<String> getJavaImports(Table table) {
    Column[] cols = table.getColumns();
    Set<String> types = new HashSet<String>();
    Map<Integer, Class<?>> typesMap = getTypesMap();
    
    for(int i = 0; i < cols.length; i++) {
      Class<?> clazz = typesMap.get(cols[i].getTypeCode());
      if(cols[i].getName().toLowerCase().endsWith("time") || 
          cols[i].getName().toLowerCase().endsWith("date")) {//MySql的日期类型居然是String
        clazz = Date.class;
      }
      if(clazz != null && !ClassUtils.getPackageName(clazz).equals("java.lang")) {
        types.add(clazz.getName());
      }
      if(cols[i].isRequired() && !cols[i].isPrimaryKey()) {
        types.add(javax.validation.constraints.NotNull.class.getName());
      }
    }
    
    return types;
  }
  
  /**
   * 返回JDBC类型和Java类型的对应关系
   */
  public static Map<Integer, Class<?>> getTypesMap() {
    Map<Integer, Class<?>> map = new HashMap<Integer, Class<?>>();
    map.put(Types.SMALLINT, Boolean.class);
    map.put(Types.BOOLEAN, Boolean.class);
    map.put(Types.CHAR, String.class);
    map.put(Types.DATE, Date.class);
    map.put(Types.TIME, Date.class);
    map.put(Types.VARCHAR, String.class);
    map.put(Types.LONGVARCHAR, String.class);
    map.put(Types.INTEGER, Integer.class);
    map.put(Types.FLOAT, Float.class);
    map.put(Types.DOUBLE, Double.class);
    map.put(Types.DECIMAL, BigDecimal.class);
    map.put(Types.NUMERIC, Double.class);
    
    return map;
  }
  
  public static Map<String, Object> makeModel(Table table, String packageName) {
    Map<String, Object> data = new HashMap<String, Object>();
    data.put("packageName", packageName);
    data.put("table", table);
    data.put("tableName", table.getName());
    //data.put("modelName", t2V(table));
    data.put("className", t2M(table));
    data.put("properties", getProperties(table));
    data.put("pk", pk(table));
    data.put("id", id(table));
    data.put("typesMap", getTypesMap());
    data.put("javaTypes", getJavaTypes(table));
    data.put("javaImports", getJavaImports(table));
    data.put("dateFormat", Constants.RESOURCE_BUNDLE.getString("date.format"));
    
    return data;
  }
  
  public static void write(String path, String content) {
    File file = new File(path);
    if(!file.exists()) {
      try {
        new File(file.getParent()).mkdirs();
        file.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
        return;
      }
    }
    OutputStreamWriter w = null;

    try {
      w = new OutputStreamWriter(new FileOutputStream(new File(path)), "UTF-8");
      w.write(content);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (w != null) {
        try {
          w.flush();
        } catch (IOException e) {
          e.printStackTrace();
        }
        try {
          w.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
 
}
