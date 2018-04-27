package org.aattadmon.p4;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	//Constantes
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class); //Auto-generado
	
	//Inyectará, como una instancia de dao, un bean de una clase que implemente el interfaz DAOUsuariosInterfaz
		@Autowired
		private DAOUsuarioInterface dao;
		
	
	//Anotación para asignar solicitudes web a métodos en clases de manejo de solicitudes con firmas de métodos flexibles.
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale,Model model) {
		
		return "home";
	}
	
	//Este Servlet nos sirve para comprobar desde el servidor lso usuarios qque estan BBDD
	//y nos devueve los usuarios que hay en ella
	
	//Anotación para asignar solicitudes web a métodos en clases de manejo
	//de solicitudes con firmas de métodos flexibles.
	
	@RequestMapping(value = "/Servlet1", method = {RequestMethod.GET,RequestMethod.POST})
	public String Servlet1  (HttpServletRequest request, Model model) {
		//Este servlet tendrá la función de autentificación con la BBDD
		
		 //Hay que comprobar si el usuario esta en bbdd
		 //Variables donde vamos a guardar los atributos introducidos en la url
		String usu,pass;	
		 
	     usu = request.getParameter("user");
	     pass = request.getParameter("pass"); 
	     //URL 
	     String url="";
			
			List <Usuario> lista = dao.muestraUser();
			//comprobamos que  usuario y pass estan en el sistema
	      if(dao.buscaUsuario(usu,pass) !=null ){

	    	   	//si coincide usuario y password 
	            //Muestro el jsp con la info de bddd
	        	
	        	//Empleamos model.addAttribute en vez de req.setAttribute para  agregar el atributo 
	        			
	        			url="usuarios";
	        			//Hay que ponerlo asi porque el servlet.context esta puesto prefix /WEB-INF/views/
			        	//Sufifix .jsp
	        			
	        			model.addAttribute("lista", lista);
	    		
	        	
	     
	 }else{
	        	//Caso que no coincidan volvemos a home jsp
	        	
			 url="home";
			
		
		}	
	      //Devolvemos la url
	        return url;
	        
		
	
}
	@RequestMapping(value = "/autentication", method = {RequestMethod.POST,RequestMethod.GET})
	public String login(HttpServletRequest request,Model model){
	String respuestaServidor=null;
	String response=null;
		 //URL 
	     String url="";
		//Variables donde vamos a guardar los atributos introducidos en la url
		String usu,dni;	
		 
	    usu = request.getParameter("user");
	    dni = request.getParameter("pass"); 

		//Si no manda usuario y dni
		if(usu==null || dni==null){
			
			respuestaServidor="401 UNAUTHORIZED";	
			
			model.addAttribute("respuestaServidor",respuestaServidor); //Enviamos la respuesta al jsp.
			url="Autentication";//Nos vamos al jsp Autentication		
		}else{
			
			//Se ha enviado un usuario y un dni
			//Comprobamos que este en BBDD
			
			if(dao.buscaUsuario(usu,dni) !=null ){

	    	   	//si coincide usuario y password 
	            //Muestro el jsp con la info de bddd
	        	//Por tanto hay que recorrer la lista
				respuestaServidor="200 OK";
				model.addAttribute("respuestaServidor",respuestaServidor); //Enviamos la respuesta al jsp.
				
	        	url="Autentication";//Nos vamos al jsp Autentication
	        			
	 }else{
	        	//Caso que no coincidan 

		respuestaServidor="400 BAD REQUEST"; //solicitud incorrecta
		model.addAttribute("respuestaServidor",respuestaServidor); //Enviamos la respuesta al jsp.
		
     	url="Autentication";//Nos vamos al jsp Autentication
			
		
		}	
			
	    	
	      
		
	
}
		logger.info(" Respuesta "+respuestaServidor); //Informamos del suceso.
		return url;
			
		
		
		
    	
	
	}
	

}