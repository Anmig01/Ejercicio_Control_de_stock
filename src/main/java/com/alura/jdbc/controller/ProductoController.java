package com.alura.jdbc.controller;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoController {

	public void modificar(String nombre, String descripcion, Integer id) {
		// TODO
	}

	public void eliminar(Integer id) {
		// TODO
	}

	public List<?> listar() throws SQLException {
		// TODO
		Connection con = DriverManager.getConnection(
				"jdbc:mysql://localhost/control_de_stock?useTimeZone=true&serverTimeZone=UTC",
				"root",
				"Nasuligi01");
		Statement statement = con.createStatement();
		
		boolean result = statement.execute("SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM PRODUCTO");
		
		System.out.println(result);
		con.close();
		return new ArrayList<>();
	}

    public void guardar(Object producto) {
		// TODO
	}

}
