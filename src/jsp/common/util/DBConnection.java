package jsp.common.util;

import java.io.*;
import java.sql.*;

import javax.naming.*;
import javax.sql.*;

public class DBConnection {
	public static Connection getConnection() throws SQLException, IOException, NamingException{
		Context initCtx = new InitialContext();
		Context envCtx = (Context)initCtx.lookup("java:comp/env");
		DataSource ds = (DataSource) envCtx.lookup("jdbc/orcl");
		Connection conn = ds.getConnection();
		
		return conn;
		
	}
}
