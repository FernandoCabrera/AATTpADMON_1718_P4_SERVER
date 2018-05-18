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
		
		

		
		 @RequestMapping(value="/autenticar/{nick}/{dni}/{fechaString}/{hashB64}", method = RequestMethod.GET)
		    public @ResponseBody String login (@PathVariable (value="nick")String nick,@PathVariable(value="dni")String dni, @PathVariable(value="fechaString")String fechaString,@PathVariable(value="hashB64")String hashB64,HttpServletRequest request,Model model) {
		    String respuestaServidor=null;
		    String response=null;
		    //Respuesta servidor
	        //ResponseEntity<Usuario> respuestaServidor;
	        
	        Base64.Decoder decoder = Base64.getDecoder();
	        byte[] decodedByteArray = decoder.decode(nick);
	 
	        String nick1 = new String(decodedByteArray);
		
	     //Obtenemos la clave secreta de BBDD e funcion del nick y dni
	     if(dao.buscaUsuario(nick1,dni) !=null ){
	    	 String clavesecreta=dao.obtenerclave(nick1,dni);
	    	 
	    	//Falta clavepublica
	    	 
	    	//Hacemos el hash
	 	    String hashobtenido=nick1+dni+fechaString+clavesecreta;
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
	       //Caso que el hash sea distinto
	           if(hashB64==null || !hashB64.equals(hashserv) ){
	            
	         //Devuelvo respuestaHTTP
	         respuestaServidor="401 UNAUTHORIZED";
	        		 //new ResponseEntity <Usuario>(HttpStatus.UNAUTHORIZED);  
	         
	       }else
	           //si coincide hash
	         //Devuelvo respuestaHTTP
	         respuestaServidor="200 OK";
	         //new ResponseEntity <Usuario>(HttpStatus.OK);
	                
	       }
	      else { 
	        //Devuelvo respuestaHTTP
	       respuestaServidor="400 BAD REQUEST";
	    		   //new ResponseEntity <Usuario>(HttpStatus.BAD_REQUEST);
	   }  
	     
	    return respuestaServidor;	
}
		 
		@RequestMapping(value="/autenticar/{nick}", method = RequestMethod.GET)
		    public @ResponseBody String Login(@PathVariable (value="nick")String nick, HttpServletRequest request,Model model) {
		    //String respuestaServidor=null;
		    String response=null;
		  //Respuesta servidor
		    return "200 OK";
	     //Obtenemos la clave secreta de BBDD e funcion del nick y dni
		}

}
	

