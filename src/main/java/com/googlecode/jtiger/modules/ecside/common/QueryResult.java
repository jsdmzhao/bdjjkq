package com.googlecode.jtiger.modules.ecside.common;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.googlecode.jtiger.modules.ecside.common.log.LogHandler;
public class QueryResult {
	
	private Log logger = LogFactory.getLog(QueryResult.class);
	
	private ResultSet resultSet = null;
	private Statement statement = null;
	private Connection connection=null;
	private DataSource dataSource=null;
	 
	
	public QueryResult() {

	}
	
	public QueryResult(ResultSet resultSet,Statement statement,Connection connection,DataSource dataSource) {
		setResultSet(resultSet);
		setStatement(statement);
		setConnection(connection);
		setDataSource(dataSource);
	}
	
	public void closeAll(){

		close(resultSet,statement,connection);

	}
	
	
	protected final void closeConnection(Connection con) {
		if(con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public void close(ResultSet rest,Statement pstmt,Connection conn){
		try {
			close(rest);
		} catch (SQLException e) {
			LogHandler.errorLog(logger, e);
		}
		close(pstmt,conn);
	}

	public void close(Statement pstmt,Connection conn){
		try {
			close(pstmt);
		} catch (SQLException e) {
			LogHandler.errorLog(logger, e);
		}
		close(conn);
	}
	

	public void close(ResultSet rest) throws SQLException{
		if(rest != null) rest.close();
	}
	
	public void close(Statement pstmt) throws SQLException{
		if(pstmt != null) pstmt.close();
	}
	
	public void close(Connection conn){
		if(conn != null) closeConnection(conn);
	}
	
	

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public ResultSet getResultSet() {
		return resultSet;
	}

	public void setResultSet(ResultSet resultSet) {
		this.resultSet = resultSet;
	}

	public Statement getStatement() {
		return statement;
	}

	public void setStatement(Statement statement) {
		this.statement = statement;
	}

	
}
