package com.googlecode.jtiger.modules.gen.writers;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.googlecode.jtiger.modules.gen.FileContentGen;
import com.googlecode.jtiger.modules.gen.GenHelper;
import com.googlecode.jtiger.modules.gen.GeneratedFileWriter;

@Component
public class ModelFileWriter implements GeneratedFileWriter {
  private static Logger logger = LoggerFactory.getLogger(ModelFileWriter.class);
  @Autowired
  protected FileContentGen gen;

  @Override
  public void write(String table, String packageName) {
    String cont = gen.gen(table, packageName, "java_model.ftl");

    String path = new StringBuffer(1000).append(
        GenHelper.getJavaSource().getPath()).append(
        GenHelper.package2Path(packageName)).append(File.separator).append(
        GenHelper.table2Model(table)).append(File.separator).append("model")
        .append(File.separator).append(GenHelper.table2Class(table)).append(
            ".java").toString();

    GenHelper.write(path, cont);
    logger.info("Model class for table '{}' generated.", table);
  }

}
