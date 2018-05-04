package org.aattadmon.p4;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.List;



import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles requests for the application home page.
 */

@Controller
public class HomeController {
	
	
	//Inyectará, como una instancia de dao, un bean de una clase que implemente el interfaz DAOUsuariosInterfaz
	@Autowired
		private DAOUsuarioInterface dao;
		
	
		 @RequestMapping(value="/autenticar}", method = {RequestMethod.POST,RequestMethod.GET})
		    public String Login(@RequestParam ("hash") String hash, HttpServletRequest request,Model model) {
		      
	String respuestaServidor=null;
	
		 //URL 
	     String url="";
		//Variables donde vamos a guardar los atributos introducidos en la url
		String usu,dni,clavepublica,fecha,firma;	
		
		
		usu = request.getParameter("user");
	    dni = request.getParameter("dni"); 
	    fecha = request.getParameter("fecha");
	    clavepublica = request.getParameter("clave"); 
	    firma = request.getParameter("firma"); 
	    
	    //Hacemos el hash
	    String hashobtenido=usu+dni+fecha+clavepublica+firma;
        MessageDigest sha256 = null;
		try {
			sha256 = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			sha256.update(hashobtenido.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String hashserv=Base64.getEncoder().encodeToString(sha256.digest()); //2bb80d5...527a25b
      
	  
		if(hash==null || !hash.equals(hashserv) ){
			
			respuestaServidor="401 UNAUTHORIZED";	
			url="Autentication";//Nos vamos al jsp Autentication
			model.addAttribute("respuestaServidor",respuestaServidor); //Enviamos la respuesta al jsp
		}else{
			
			//Se ha enviado un usuario y un dni
			//Comprobamos que este en BBDD
			
			if(dao.buscaUsuario(usu,dni) !=null ){

	    	   	//si coincide usuario y password 
	            //Muestro el jsp con la info de bddd
	        	//Por tanto hay que recorrer la lista
				respuestaServidor="200 OK";
				model.addAttribute("respuestaServidor",respuestaServidor); //Enviamos la respuesta al jsp
	        	url="Autentication";//Nos vamos al jsp Autentication
	        			
	 }
		
		}	
	    	
	      
		logger.info(" Respuesta "+respuestaServidor); //Informamos del suceso.
	

		
		return url;
			
		
		
		
    	
	
	
}
}
	

