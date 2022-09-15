package model;

public class Usuario {
	private int code;
	private String login;
	private String passwd;
	private String gender;
	
	public Usuario() {
		this.login = "";
		this.passwd = "";
		this.gender = "";
		this.code = -1;
	}
	
	public Usuario(int code, String login, String passwd, String gender) {
		this.code = code;
		this.login = login;
		this.passwd = passwd;
		this.gender = gender;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "Usuario [code=" + code + ", login=" + login + ", passwd=" + passwd + ", gender=" + gender + "]";
	}	
}
