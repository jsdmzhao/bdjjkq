package com.googlecode.jtiger.core.dao.support;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.Assert;

import com.googlecode.jtiger.core.test.BaseTestCase;

public class DynaDataSourceFactoryTest extends BaseTestCase {
  @Autowired
  private DynaDataSourceFactory ddsf;
  
  public void testGetDataSource() {
    DataSource ds = ddsf.getDataSource("testMySQL");
    Assert.notNull(ds);
    JdbcTemplate jt = new JdbcTemplate(ds);
    List<Map<String, Object>> maps = jt.query("select id, login_id from users", new Object[]{}, 
        new ColumnMapRowMapper());
    if(maps.size() > 0) {
      Map<String, Object> m = maps.get(0);
      System.out.println(m.get("login_id"));
    }
  }
}
