package com.googlecode.jtiger.modules.gen;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Generator {
  private static Logger logger = LoggerFactory.getLogger(Generator.class);
  
  static ApplicationContext ctx;
  
  static {
    ctx = new ClassPathXmlApplicationContext(new String[]{
        "META-INF/spring/applicationContext.xml"
    });
    
  }
  
  public static void main(String[] args) {
    if(args == null || args.length < 2) {
      logger.error("没有足够的参数");
      return;
    }
    
    String pkg = args[0]; //包名    
    for(int i = 1; i < args.length; i++)  {
      try {
        perform(args[i], pkg);
      } catch(Exception e) {
        logger.error(e.getMessage());
        e.printStackTrace();
      }
    }
    
    
    System.exit(0);
  }
  
  /**
   * 生成一个实体的各种文件
   */
  private static void perform(String table, String pkg) {
    Map<String, GeneratedFileWriter> writersMap = ctx.getBeansOfType(GeneratedFileWriter.class);
    if(MapUtils.isNotEmpty(writersMap)) {
      Collection<GeneratedFileWriter> writers = writersMap.values();
      for(GeneratedFileWriter writer : writers) {
        writer.write(table, pkg);
      }
    }
  }
  
 
  
  
}
