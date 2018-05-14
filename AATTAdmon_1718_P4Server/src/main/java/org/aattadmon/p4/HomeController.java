package org.aattadmon.p4;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.List;



import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
	
		//Metodo get  falta {clavePublicaB64}
		
		

		
		 @RequestMapping(value="/autenticar{nick}{dni}{fechaString}{hashB64}", method = RequestMethod.GET)
		    public @ResponseBody ResponseEntity Login(@PathVariable (value="nick")String nick,@PathVariable(value="dni")String dni, @PathVariable(value="fechaString")String fechaString,@PathVariable(value="hashB64")String hashB64,HttpServletRequest request,Model model) {
		    String respuestaServidor=null;
		    String response=null;
		
	     //Obtenemos la clave secreta de BBDD e funcion del nick y dni
	     if(dao.buscaUsuario(nick,dni) !=null ){
	    	 String clavesecreta=dao.obtenerclave(nick,dni);
	    	 
	    	//Falta clavepublica
	    	 
	    	//Hacemos el hash
	 	    String hashobtenido=nick+dni+fechaString+clavesecreta;
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
	         
	         //Caso que el hash sea distinto
	         if(hashserv==null || !hashB64.equals(hashserv) ){
	 			
	 			respuestaServidor="401 UNAUTHORIZED";	
	 			response="Usuario no tiene acceso";//Mensage
	 			
	 			model.addAttribute("respuestaServidor",respuestaServidor); //Enviamos la respuesta al jsp
	 		}else
	 	    	   	//si coincide hash
	 	            //Muestro el jsp con la info de bddd
	 	        	
	 				respuestaServidor="200 OK";
	 				model.addAttribute("respuestaServidor",respuestaServidor); //Enviamos la respuesta al jsp
	 				response="Usuario autenticado con exito";//Mensage
	 	        	 
	     }
	    else { 
		 respuestaServidor="400 BAD REQUEST";
		model.addAttribute("respuestaServidor",respuestaServidor); //Enviamos la respuesta al jsp
    	
    	response="Usuario y dni no registrados en BBDD ";//Mensage
	 }	
	    //logger.info(response+" Respuesta "+respuestaServidor); //Informamos del suceso.
			
	    	
	      
	     ResponseEntity<Usuario> resp=new ResponseEntity <Usuario>(HttpStatus.CREATED);

		
		return resp;
			
		
		
		
    	
	
	
}
		
	
	/*
		 @RequestMapping(value="/autenticar", method = {RequestMethod.POST,RequestMethod.GET})
		    public ResponseEntity Login(@RequestBody Usuario user, HttpServletRequest request,Model model) {
		    String respuestaServidor=null;
		    String response=null;
		 //URL 
	     String url="";
	     String usu=user.getNick();	
	     String dni=user.getDni();
	     String fecha=user.getFechaString();
	     String clavepublica=user.getClavePublicaB64();
	     //Pasar clave publica a String 
	    String clavepublicaString=Base64.getDecoder().decode(clavepublica).toString();
	     
	     String hashrec=user.getHashB64();
	     //Obtenemos la clave secreta de BBDD e funcion del nick y dni
	     if(dao.buscaUsuario(usu,dni) !=null ){
	    	 String clavesecreta=dao.obtenerclave(usu,dni);
	    	 
	    	 
	    	//Hacemos el hash
	 	    String hashobtenido=usu+dni+fecha+clavepublicaString+clavesecreta;
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
	         
	         //Caso que el hash sea distinto
	         if(hashrec==null || !hashrec.equals(hashserv) ){
	 			
	 			respuestaServidor="401 UNAUTHORIZED";	
	 			response="Usuario no tiene acceso";//Mensage
	 			
	 			model.addAttribute("respuestaServidor",respuestaServidor); //Enviamos la respuesta al jsp
	 		}else
	 	    	   	//si coincide hash
	 	            //Muestro el jsp con la info de bddd
	 	        	
	 				respuestaServidor="200 OK";
	 				model.addAttribute("respuestaServidor",respuestaServidor); //Enviamos la respuesta al jsp
	 				response="Usuario autenticado con exito";//Mensage
	 	        	 
	     }
	    else { 
		 respuestaServidor="400 BAD REQUEST";
		model.addAttribute("respuestaServidor",respuestaServidor); //Enviamos la respuesta al jsp
    	
    	response="Usuario y dni no registrados en BBDD ";//Mensage
	 }	
	    //logger.info(response+" Respuesta "+respuestaServidor); //Informamos del suceso.
			
	    	
	      
	     ResponseEntity<Usuario> resp=new ResponseEntity <Usuario>(user, HttpStatus.CREATED);

		
		return resp;
			
		
		
		
    	
	
	
}
*/
}
	

