package app;

import static spark.Spark.*;
import service.UsuarioService;


public class Aplicacao {
	
	private static UsuarioService userService = new UsuarioService();
	
    public static void main(String[] args) {
        port(6789);
        
        staticFiles.location("/public");
        
        post("/user/insert", (request, response) -> userService.insert(request, response));

        get("/user/:codigo", (request, response) -> userService.get(request, response));
        
        get("/user/list/:orderby", (request, response) -> userService.getAll(request, response));

        get("/user/update/:codigo", (request, response) -> userService.getToUpdate(request, response));
        
        post("/user/update/:codigo", (request, response) -> userService.update(request, response));
           
        get("/user/delete/:codigo", (request, response) -> userService.delete(request, response));

             
    }
}