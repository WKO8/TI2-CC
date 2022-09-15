package dao;

import model.Usuario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class UsuarioDAO extends DAO {	
	public UsuarioDAO() {
		super();
		connect();
	}
	
	
	public void exit() {
		close();
	}
	
	
	public boolean insert(Usuario user) {
		boolean status = false;
		try {  
			Statement st = conn.createStatement();
			st.executeUpdate("INSERT INTO x (login, senha, sexo) "
					       + "VALUES ('"+ user.getLogin() + "', '"  
					       + user.getPasswd() + "', '" + user.getGender() + "');");
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}

	
	public Usuario get(int code) {
		Usuario user = null;
		
		try {
			Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM x WHERE code="+code;
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	 user = new Usuario(rs.getInt("codigo"), rs.getString("login"), rs.getString("senha"), 
	                				   rs.getString("sexo"));
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return user;
	}
	
	
	public List<Usuario> get() {
		return get("");
	}

	
	public List<Usuario> getOrderByCodigo() {
		return get("codigo");		
	}
	
	
	public List<Usuario> getOrderByLogin() {
		return get("login");		
	}
	
	
	public List<Usuario> getOrderBySenha() {
		return get("senha");		
	}
	
	public List<Usuario> getOrderBySexo() {
		return get("sexo");		
	}
	
	private List<Usuario> get(String orderBy) {
		List<Usuario> users = new ArrayList<Usuario>();
		try {
			Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM x" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	Usuario p = new Usuario(rs.getInt("codigo"), rs.getString("login"), rs.getString("senha"), 
     				   					rs.getString("sexo"));
	            users.add(p);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return users;
	}
	
	
	public boolean update(Usuario user) {
		boolean status = false;
		try {  
			String sql = "UPDATE x SET login = '" + user.getLogin() + "', "
					   + "senha = '" + user.getPasswd() + "', " 
					   + "sexo = '" + user.getGender()
					   + "' WHERE codigo = " + user.getCode();
			PreparedStatement st = conn.prepareStatement(sql);
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	
	public boolean delete(int code) {
		boolean status = false;
		try {  
			Statement st = conn.createStatement();
			st.executeUpdate("DELETE FROM x WHERE codigo = " + code);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public int getLastCodigo() {
		Usuario[] users = null;
		
		try {
			Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM x");		
	         if(rs.next()){
	             rs.last();
	             users = new Usuario[rs.getRow()];
	             rs.beforeFirst();

	             for(int i = 0; rs.next(); i++) {
	                users[i] = new Usuario(rs.getInt("codigo"), rs.getString("login"), 
	                		                  rs.getString("senha"), rs.getString("sexo"));
	             }
	          }
	          st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		int newCode = 0;
		if (users != null) {
			newCode = users[users.length-1].getCode() + 1;
		}
		else {
			newCode = 1;
		}
		
		return newCode;
	}
}