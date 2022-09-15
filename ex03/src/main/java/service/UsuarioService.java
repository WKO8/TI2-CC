package service;

import java.util.Scanner;
import java.io.File;
import java.util.List;
import dao.UsuarioDAO;
import model.Usuario;
import spark.Request;
import spark.Response;


public class UsuarioService {

	private UsuarioDAO userDAO = new UsuarioDAO();
	private String form;
	private final int FORM_INSERT = 1;
	private final int FORM_DETAIL = 2;
	private final int FORM_UPDATE = 3;
	private final int FORM_ORDERBY_CODIGO = 1;
	private final int FORM_ORDERBY_LOGIN = 2;
	private final int FORM_ORDERBY_SEXO = 3;
	private final int FORM_ORDERBY_SENHA = 4;
	
	
	public UsuarioService() {
		makeForm();
	}

	
	public void makeForm() {
		makeForm(FORM_INSERT, new Usuario(), FORM_ORDERBY_LOGIN);
	}

	
	public void makeForm(int orderBy) {
		makeForm(FORM_INSERT, new Usuario(), orderBy);
	}

	
	public void makeForm(int type, Usuario user, int orderBy) {
		String filename = "form.html";
		form = "";
		try{
			Scanner entrada = new Scanner(new File(filename));
		    while(entrada.hasNext()){
		    	form += (entrada.nextLine() + "\n");
		    }
		    entrada.close();
		}  catch (Exception e) { System.out.println(e.getMessage()); }
		
		String aUser = "";
		if(type != FORM_INSERT) {
			aUser += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			aUser += "\t\t<tr>";
			aUser += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/usuario/list/1\">Novo Usuario</a></b></font></td>";
			aUser += "\t\t</tr>";
			aUser += "\t</table>";
			aUser += "\t<br>";			
		}
		
		if(type == FORM_INSERT || type == FORM_UPDATE) {
			String action = "/user/";
			String name, login, buttonLabel, passwd, gender;
			if (type == FORM_INSERT){
				action += "insert";
				name = "Inserir Usuario";
				login = "John Doe";
				passwd = "senha123";
				gender = "M";
				buttonLabel = "Inserir";
			} else {
				action += "update/" + user.getCode();
				name = "Atualizar Usuario (Codigo " + user.getCode() + ")";
				login = user.getLogin();
				passwd = user.getPasswd();
				gender = user.getGender();
				buttonLabel = "Atualizar";
			}
			aUser += "\t<form class=\"form--register\" action=\"" + action + "\" method=\"post\" codigo=\"form-add\">";
			aUser += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			aUser += "\t\t<tr>";
			aUser += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;" + name + "</b></font></td>";
			aUser += "\t\t</tr>";
			aUser += "\t\t<tr>";
			aUser += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			aUser += "\t\t</tr>";
			aUser += "\t\t<tr>";
			aUser += "\t\t\t<td>&nbsp;Login: <input class=\"input--register\" type=\"text\" name=\"login\" value=\""+ login +"\"></td>";
			aUser += "\t\t\t<td>Senha: <input class=\"input--register\" type=\"text\" name=\"senha\" value=\""+ passwd +"\"></td>";
			aUser += "\t\t\t<td>Sexo: <input class=\"input--register\" type=\"text\" name=\"sexo\" value=\""+ gender +"\"></td>";
			aUser += "\t\t</tr>";
			aUser += "\t\t<tr>";
			aUser += "\t\t\t<td align=\"center\"><input type=\"submit\" value=\""+ buttonLabel +"\" class=\"input--main__style input--button\"></td>";
			aUser += "\t\t</tr>";
			aUser += "\t</table>";
			aUser += "\t</form>";		
		} else if (type == FORM_DETAIL){
			aUser += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			aUser += "\t\t<tr>";
			aUser += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Detalhar Usuario (Codigo " + user.getCode() + ")</b></font></td>";
			aUser += "\t\t</tr>";
			aUser += "\t\t<tr>";
			aUser += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			aUser += "\t\t</tr>";
			aUser += "\t\t<tr>";
			aUser += "\t\t\t<td>&nbsp;Login: "+ user.getLogin() +"</td>";
			aUser += "\t\t\t<td>Senha: "+ user.getPasswd() +"</td>";
			aUser += "\t\t\t<td>Sexo: "+ user.getGender() +"</td>";
			aUser += "\t\t</tr>";
			aUser += "\t\t<tr>";
			aUser += "\t\t\t<td>&nbsp;</td>";
			aUser += "\t\t</tr>";
			aUser += "\t</table>";		
		} else {
			System.out.println("ERRO! Tipo não identificado " + type);
		}
		form = form.replaceFirst("<UM-USUARIO>", aUser);
		
		String list = new String("<table width=\"80%\" align=\"center\" bgcolor=\"#f3f3f3\">");
		list += "\n<tr><td colspan=\"6\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Relação de Usuarios</b></font></td></tr>\n" +
				"\n<tr><td colspan=\"6\">&nbsp;</td></tr>\n" +
    			"\n<tr>\n" + 
        		"\t<td><a href=\"/user/list/" + FORM_ORDERBY_CODIGO + "\"><b>Code</b></a></td>\n" +
        		"\t<td><a href=\"/user/list/" + FORM_ORDERBY_LOGIN + "\"><b>Login</b></a></td>\n" +
        		"\t<td><a href=\"/user/list/" + FORM_ORDERBY_SEXO + "\"><b>Gender</b></a></td>\n" +
        		"\t<td><a href=\"/user/list/" + FORM_ORDERBY_SENHA + "\"><b>Passwd</b></a></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Detalhar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Atualizar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Excluir</b></td>\n" +
        		"</tr>\n";
		
		List<Usuario> users;
		if (orderBy == FORM_ORDERBY_CODIGO) {                 	users = userDAO.getOrderByCodigo();
		} else if (orderBy == FORM_ORDERBY_LOGIN) {		users = userDAO.getOrderByLogin();
		} else if (orderBy == FORM_ORDERBY_SEXO) {			users = userDAO.getOrderBySexo();
		} else if (orderBy == FORM_ORDERBY_SENHA) {			users = userDAO.getOrderBySenha();
		} else {											users = userDAO.get();
		}

		int i = 0;
		String bgcolor = "";
		for (Usuario p : users) {
			bgcolor = (i++ % 2 == 0) ? "#fff5dd" : "#dddddd";
			list += "\n<tr bgcolor=\""+ bgcolor +"\">\n" + 
            		  "\t<td>" + p.getCode() + "</td>\n" +
            		  "\t<td>" + p.getLogin() + "</td>\n" +
            		  "\t<td>" + p.getGender() + "</td>\n" +
            		  "\t<td>" + p.getPasswd() + "</td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/user/" + p.getCode() + "\"><img src=\"/image/detail.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/user/update/" + p.getCode() + "\"><img src=\"/image/update.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/user/delete/" + p.getCode() + "\"><img src=\"/image/delete.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "</tr>\n";
		}
		list += "</table>";		
		form = form.replaceFirst("<LISTAR-USUARIO>", list);				
	}
	
	
	public Object insert(Request request, Response response) {
		String login = request.queryParams("login");
		String passwd = request.queryParams("passwd");
		String gender = request.queryParams("gender");

		String resp = "";
		
		Usuario user = new Usuario(-1, login, passwd, gender);
		
		if(userDAO.insert(user) == true) {
            resp = "Usuario (" + login + ") inserido!";
            response.status(201); // 201 Created
		} else {
			resp = "Usuario (" + login + ") não inserido!";
			response.status(404); // 404 Not found
		}
			
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" codigo=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" codigo=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object get(Request request, Response response) {
		int code = Integer.parseInt(request.params(":codigo"));		
		Usuario user = (Usuario) userDAO.get(code);
		
		if (user != null) {
			response.status(200); // success
			makeForm(FORM_DETAIL, user, FORM_ORDERBY_LOGIN);
        } else {
            response.status(404); // 404 Not found
            String resp = "Usuario " + code + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" codigo=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" codigo=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}

	
	public Object getToUpdate(Request request, Response response) {
		int code = Integer.parseInt(request.params(":codigo"));		
		Usuario user = (Usuario) userDAO.get(code);
		
		if (user != null) {
			response.status(200); // success
			makeForm(FORM_UPDATE, user, FORM_ORDERBY_LOGIN);
        } else {
            response.status(404); // 404 Not found
            String resp = "Usuario " + code + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" codigo=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" codigo=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}
	
	
	public Object getAll(Request request, Response response) {
		int orderBy = Integer.parseInt(request.params(":orderby"));
		makeForm(orderBy);
	    response.header("Content-Type", "text/html");
	    response.header("Content-Encoding", "UTF-8");
		return form;
	}			
	
	public Object update(Request request, Response response) {
        int codigo = Integer.parseInt(request.params(":codigo"));
		Usuario user = userDAO.get(codigo);
        String resp = "";       

        if (user != null) {
        	user.setLogin(request.queryParams("login"));
        	user.setPasswd(request.queryParams("passwd"));
        	user.setGender(request.queryParams("gender"));
        	userDAO.update(user);
        	response.status(200); // success
            resp = "Usuario (Codigo " + user.getCode() + ") atualizado!";
        } else {
            response.status(404); // 404 Not found
            resp = "Usuario (Codigo \" + user.getCode() + \") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" codigo=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" codigo=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object delete(Request request, Response response) {
        int code = Integer.parseInt(request.params(":codigo"));
        Usuario user = userDAO.get(code);
        String resp = "";       
        if (user != null) {
        	userDAO.delete(code);
            response.status(200); // success
            resp = "Usuario (" + code + ") excluído!";
        } else {
            response.status(404); // 404 Not found
            resp = "Usuario (" + code + ") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" codigo=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" codigo=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}
}