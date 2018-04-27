package org.aattadmon.p4;

import java.io.Serializable;

public class Usuario implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String user;
    private String nif;
    
    public Usuario(String us,String ni){
    	user=us;
     
        nif=ni;
    }
    
    public Usuario(){
    	user="";
        nif="";
    }
    

	@Override
    public String toString(){
        return user+" "+nif;
    }

    public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }
          
}

