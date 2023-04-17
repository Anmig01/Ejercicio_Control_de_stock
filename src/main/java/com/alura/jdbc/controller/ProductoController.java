package com.alura.jdbc.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alura.jdbc.factory.ConnectionFactory;

public class ProductoController {

	public int modificar(String nombre, String descripcion, Integer id, Integer cantidad) throws SQLException {
		// TODO
		Connection con = new ConnectionFactory().recuperaConexion();
		Statement statement = con.createStatement();
		
		statement.execute("UPDATE producto SET " 
				+ "NOMBRE = '" + nombre + "'"
				+ ", DESCRIPCION = '" + descripcion + "'" 
				+ ", CANTIDAD = " + cantidad 
				+ " WHERE id = " + id);
		
		int updateCount =  statement.getUpdateCount();
		
		statement.close();
		con.close();
		return updateCount;
	}

	public int eliminar(Integer id) throws SQLException {
		// TODO
		Connection con = new ConnectionFactory().recuperaConexion();
		Statement statement = con.createStatement();
		statement.execute("DELETE FROM PRODUCTO WHERE ID = " + id);
		con.close();
		
		return statement.getUpdateCount();
		
		
	}

	public List<Map<String, String>> listar() throws SQLException {
		// TODO
		Connection con = new ConnectionFactory().recuperaConexion();
		Statement statement = con.createStatement();
		
		statement.execute("SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM PRODUCTO");
		
		ResultSet resultSet = statement.getResultSet();
		//List<Map<String,String>> resultado = new ArrayList<>();
		List<Map<String,String>> resultado = new ArrayList<>();
		
		while (resultSet.next()) {
			Map<String, String> fila = new HashMap<>();
			
			fila.put("ID", String.valueOf(resultSet.getInt("ID")));
			fila.put("NOMBRE", resultSet.getString("NOMBRE"));
			fila.put("DESCRIPCION", resultSet.getString("DESCRIPCION"));
			fila.put("CANTIDAD", String.valueOf(resultSet.getString("CANTIDAD")));
			
			resultado.add(fila);
			
		}
		
		//System.out.println(result);
		con.close();
		
		
		return resultado;
	}

    public void guardar(Map<String, String> producto) throws SQLException {
		// TODO
    	Connection con = new ConnectionFactory().recuperaConexion();
    	Statement statement = con.createStatement();
    	statement.execute("INSERT INTO PRODUCTO (nombre, descripcion, cantidad) "
    			+ "VALUES ('"+ producto.get("NOMBRE") + "', '"
    			+ producto.get("DESCRIPCION") + "',"
    			+ producto.get("CANTIDAD")+");", Statement.RETURN_GENERATED_KEYS);
    	
    	ResultSet resultSet = statement.getGeneratedKeys();
    	while (resultSet.next()) {
    		System.out.println(String.format("Fue insertado el producto con el ID %d", resultSet.getInt(1)));
			
		}
    	con.close();
	}

}
