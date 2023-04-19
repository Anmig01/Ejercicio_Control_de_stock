package com.alura.jdbc.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
		final Connection con = new ConnectionFactory().recuperaConexion();
		try (con) {
			String sqlInsert = "UPDATE producto SET " + "NOMBRE = ?, DESCRIPCION = ?, CANTIDAD = ? WHERE id = ?";

			final PreparedStatement statement = con.prepareStatement(sqlInsert);
			try (statement) {
				statement.setString(0, nombre);
				statement.setString(1, descripcion);
				statement.setInt(2, cantidad);
				statement.setInt(3, id);

				statement.execute();

				int updateCount = statement.getUpdateCount();
				return updateCount;
			}
		}
	}

	public int eliminar(Integer id) throws SQLException {
		// TODO
		final Connection con = new ConnectionFactory().recuperaConexion();
		try (con) {
			String sqlInsert = "DELETE FROM PRODUCTO WHERE ID = ?";

			final PreparedStatement statement = con.prepareStatement(sqlInsert);
			try (statement) {
				statement.setInt(1, id);

				statement.execute();

				int updateCount = statement.getUpdateCount();

				return updateCount;
			}
		}

	}

	public List<Map<String, String>> listar() throws SQLException {
		// TODO
		final Connection con = new ConnectionFactory().recuperaConexion();
		try (con) {
			String sqlInsert = "SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM PRODUCTO";

			final PreparedStatement statement = con.prepareStatement(sqlInsert);
			try (statement) {
				statement.execute();

				ResultSet resultSet = statement.getResultSet();
				// List<Map<String,String>> resultado = new ArrayList<>();
				List<Map<String, String>> resultado = new ArrayList<>();

				while (resultSet.next()) {
					Map<String, String> fila = new HashMap<>();

					fila.put("ID", String.valueOf(resultSet.getInt("ID")));
					fila.put("NOMBRE", resultSet.getString("NOMBRE"));
					fila.put("DESCRIPCION", resultSet.getString("DESCRIPCION"));
					fila.put("CANTIDAD", String.valueOf(resultSet.getString("CANTIDAD")));

					resultado.add(fila);

				}
				return resultado;
			}
		}
	}

	public void guardar(Map<String, String> producto) throws SQLException {
		// TODO
		String nombre = producto.get("NOMBRE");
		String descripcion = producto.get("DESCRIPCION");
		int cantidad = Integer.valueOf(producto.get("CANTIDAD"));
		Integer maximoCantidad = 50;

		final Connection con = new ConnectionFactory().recuperaConexion();
		try (con) {

			con.setAutoCommit(false);

			String sqlInsert = "INSERT INTO PRODUCTO (nombre, descripcion, cantidad) " + "VALUES (?, ?, ?)";

			/*
			 * producto.get("NOMBRE") + "', '" + producto.get("DESCRIPCION") + "'," +
			 * producto.get("CANTIDAD")+");";
			 */

			final PreparedStatement statement = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);

			try (statement) {
				do {
					int cantidadParaGuardar = Math.min(cantidad, maximoCantidad);
					ejecutaRegistro(nombre, descripcion, cantidadParaGuardar, statement);
					cantidad -= maximoCantidad;
				} while (cantidad > 0);

				con.commit();
			} catch (Exception e) {
				con.rollback();
			}

		}
	}

	private void ejecutaRegistro(String nombre, String descripcion, Integer cantidad, PreparedStatement statement)
			throws SQLException {
		statement.setString(1, nombre);
		statement.setString(2, descripcion);
		statement.setInt(3, cantidad);
		statement.execute();

		final ResultSet resultSet = statement.getGeneratedKeys();
		try (resultSet) {
			while (resultSet.next()) {
				System.out.println(String.format("Fue insertado el producto con el ID %d", resultSet.getInt(1)));
			}
		}

	}

}
