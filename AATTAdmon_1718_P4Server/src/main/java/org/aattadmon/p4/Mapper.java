package org.aattadmon.p4;


import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

//Es una clase en el que el programador implementa qué ha de hacerse para 
//cada iteración (fila), típicamente el mapeo al DTO

public class Mapper implements RowMapper<Usuario>{
//este mapeo sólo hace faltaimplementarlo una vez y los métodos
//del DAO lo usan simplemente mediante una instancia de la clase
	
public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException{
Usuario usuario = new Usuario();
usuario.setNick(rs.getString("Usuario"));
usuario.setDni(rs.getString("DNI"));
usuario.setClaveSecreta(rs.getString("claveSecreta"));

return usuario;
}
}
