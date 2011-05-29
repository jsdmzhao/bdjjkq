package com.googlecode.jtiger.modules.gen;

import java.util.ResourceBundle;

import javax.sql.DataSource;

import org.apache.ddlutils.Platform;
import org.apache.ddlutils.PlatformFactory;
import org.apache.ddlutils.model.Database;
import org.apache.ddlutils.model.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.googlecode.jtiger.core.util.ResBundleUtil;
import com.googlecode.jtiger.modules.freemarker.FreeMarkerService;

/**
 * 根据表名，包，和模板，生成文件内容，但是不保存文件
 * @author catstiger@gmail.com
 *
 */
@Component
public class FileContentGen {
  private static Logger logger = LoggerFactory.getLogger(FileContentGen.class);
  @Autowired
  private DataSource dataSource;
  @Autowired
  private FreeMarkerService freemarker;
  
  
  
  public String gen(String tablename, String packageName, String template) {
    logger.info("Generating {} for '{}'", template ,tablename);
    Table table;
    Platform platform = PlatformFactory.createNewPlatformInstance(dataSource);
    String schema = ResBundleUtil.getString(ResourceBundle.getBundle("application"), "jdbc.schema", "artrai");
    Database db = platform.readModelFromDatabase(schema);
    table = GenHelper.getTable(db, tablename);
      //table.getColumn(0).is
    return freemarker.processTemplate(template, GenHelper.makeModel(table, packageName));
  }
}
