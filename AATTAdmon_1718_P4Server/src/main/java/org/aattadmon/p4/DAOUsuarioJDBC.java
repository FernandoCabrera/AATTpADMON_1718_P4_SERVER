package org.aattadmon.p4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import javax.sql.DataSource;

//Indica que el bean es un dao
@Repository
public class DAOUsuarioJDBC implements DAOUsuarioInterface {

	private JdbcTemplate jdbcTemplate;
	private DataSource dataSource; 
	
	/*El template normalmente se usar� dentro del DAO y hay que
	configurarlo con un DataSource, que tendr� a su vez la configuraci�n
	de la base de datos.
	Una forma com�n de hacerlo es especificando un m�todo setter para el
	DataSource, que ser� usado por el framework con la t�cnica de
	inyecci�n de dependencias*/
	//El template se instancia cuando el framework
	//inyecte el DataSource
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource; //Opcional
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		}
	
	
	//Obtener clave secreta
	public String obtenerclave(String nick,String dni){
		String sql = "select claveSecreta from usuarios where Usuario = ? AND DNI=?";
		Object[ ] parametros = {nick,dni}; //Array de objetos
		Mapper mapper = new Mapper();
		List <Usuario> usuarios =  this.jdbcTemplate.query(sql, parametros, mapper);
		if (usuarios.isEmpty()) return null;
		else  return usuarios.get(0).getClaveSecreta();
		
	}
	
	
	
	
	
    //Metodo que muestra los usuarios
	//Para leer una tabla, se dispone del m�todo jdbcTemplate.query, que
	//devuelve una lista de objetos
	public List<Usuario> muestraUser() {
		String sql = "select * from usuarios";
		Mapper mapper = new Mapper();
		List<Usuario> usuarios = this.jdbcTemplate.query(sql,mapper);
		return usuarios;
		
		}
	
	//Devuelve el usuario buscado o null si no existe
	//Usuario bbdd y nif bdd
		
	public Usuario buscaUsuario(String user,String dni){ 
		
		String sql = "select * from usuarios where Usuario = ? AND DNI=?";
		Object[ ] parametros = {user,dni}; //Array de objetos
		Mapper mapper = new Mapper();
		List<Usuario> usuarios = this.jdbcTemplate.query(sql, parametros, mapper);
		if (usuarios.isEmpty()) return null;
		else  return usuarios.get(0);
		
		}
	




	
	
}

