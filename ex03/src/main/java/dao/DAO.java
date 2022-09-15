package dao;

import java.sql.*;

public class DAO {
	protected Connection conn;
	
	public DAO() {
		conn = null;
	}
	
	public boolean connect() {
		String driverName = "org.postgresql.Driver";                    
		String serverName = "localhost";
		String mydatabase = "x";
		int port = 5432;
		String url = "jdbc:postgresql://" + serverName + ":" + port +"/" + mydatabase;
		String username = "ti2cc";
		String password = "ti@cc";
		boolean status = false;

		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(url, username, password);
			status = (conn == null);
			System.out.println("Conexão efetuada com o Postgress!");
		} catch (ClassNotFoundException e) { 
			System.err.println("Conexão NÃO efetuada com o Postgress -- Driver não encontrado -- " + e.getMessage());
		} catch (SQLException e) {
			System.err.println("Conexão NÃO efetuada com o Postgress -- " + e.getMessage());
		}

		return status;
	}
	
	public boolean close() {
		boolean status = false;
		
		try {
			conn.close();
			status = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return status;
	}
}