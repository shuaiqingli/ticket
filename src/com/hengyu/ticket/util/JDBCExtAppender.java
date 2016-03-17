package com.hengyu.ticket.util;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import org.apache.log4j.Logger;
import org.apache.log4j.jdbc.JDBCAppender;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCExtAppender extends JDBCAppender {
	private static Logger logger= Logger.getLogger(JDBCExtAppender.class);
	
	private BoneCP dataSource = null;
    
    public JDBCExtAppender() {
    	super();
    	init();
    }
    
    @Override
    protected Connection getConnection() throws SQLException {
    	return dataSource.getConnection();
    }
    
    @Override
    protected void closeConnection(Connection conn) {
		try {
			if(conn != null && !conn.isClosed()){
				conn.close();
			}
		} catch (SQLException e) {
			logger.error("Error closing connection",e);
		}
    }
    
    private void init(){
    	BoneCPConfig config = new BoneCPConfig();
        try {
        	Class.forName("com.mysql.jdbc.Driver");
        	Properties properties = new Properties();
            properties.load(JDBCExtAppender.class.getClassLoader().getResourceAsStream("config.properties"));
            config.setJdbcUrl(properties.getProperty("jdbc.url"));
            config.setUsername(properties.getProperty("jdbc.username"));
            config.setPassword(properties.getProperty("jdbc.password"));
            config.setLazyInit(true);
            config.setMinConnectionsPerPartition(5);
            config.setMaxConnectionsPerPartition(20);
            dataSource = new BoneCP(config);
        } catch (SQLException e) {
        	logger.error("连接池配置加载异常",e);
        } catch (IOException e) {
        	logger.error("加载配置文件IO异常",e);
        } catch (ClassNotFoundException e) {
        	logger.error("加载数据库驱动类异常",e);
		}
    }
}
