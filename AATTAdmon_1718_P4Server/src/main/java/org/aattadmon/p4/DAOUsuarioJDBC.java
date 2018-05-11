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
	
	/*El template normalmente se usará dentro del DAO y hay que
	configurarlo con un DataSource, que tendrá a su vez la configuración
	de la base de datos.
	Una forma común de hacerlo es especificando un método setter para el
	DataSource, que será usado por el framework con la técnica de
	inyección de dependencias*/
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
		Usuario usuarios = (Usuario) this.jdbcTemplate.query(sql, parametros, mapper);
		if (usuarios==null) return null;
		else  return usuarios.getClaveSecreta();
		
	}
	
	
	
	
	
    //Metodo que muestra los usuarios
	//Para leer una tabla, se dispone del método jdbcTemplate.query, que
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
	
	//Insertar usuarios
	//Para operaciones INSERT, UPDATE o DELETE se usa el método jdbcTemplate.update
	public void addUser(String name,String surname1,String surname2,String dni) {
		
		char name1=name.charAt(0);
		char surname=surname2.charAt(0);
		String us=name1+surname1+surname;
		String sql="INSERT INTO usuarios (Usuario,Nombre,Apellido1,Apellido2,NIF)"
				+ " VALUES ('"+us+"','"+name+"','"+surname1+"','"+surname2+"','"+dni+"');";
		//Los valores a sustituir  se pasan como un array de objetos.
		
		Object[ ] parametros = {us,name,surname1,surname2,dni};
		this.jdbcTemplate.update(sql, parametros);
}




	
	
}

