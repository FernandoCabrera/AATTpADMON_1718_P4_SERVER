package org.aattadmon.p4;

import java.io.Serializable;

public class Usuario implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nick;
    private String dni;
    private String fechaString;
    private String clavePublicaB64;
    private String hashB64;
    
    public Usuario(){
    	
    }
    public Usuario(String us,String ni){
    	nick=us;
    	dni=ni;
    }
    
	public Usuario(String nick, String dni, String fechaString, String clavePublicaB64, String hashB64) {
		this.nick = nick;
		this.dni = dni;
		this.fechaString = fechaString;
		this.clavePublicaB64 = clavePublicaB64;
		this.hashB64 = hashB64;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getFechaString() {
		return fechaString;
	}
	public void setFechaString(String fechaString) {
		this.fechaString = fechaString;
	}
	public String getClavePublicaB64() {
		return clavePublicaB64;
	}
	public void setClavePublicaB64(String clavePublicaB64) {
		this.clavePublicaB64 = clavePublicaB64;
	}
	public String getHashB64() {
		return hashB64;
	}
	public void setHashB64(String hashB64) {
		this.hashB64 = hashB64;
	}
    
 
          
}

