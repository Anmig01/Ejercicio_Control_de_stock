package com.alura.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.alura.jdbc.modelo.Producto;

public class ProductoDAO {

	final private Connection con;

	public ProductoDAO(Connection con) {
		
		this.con = con;
	}
	
	public Connection getCon() {
		return this.con;
	}

	public void guardar(Producto producto) {

		try {

			String sqlInsert = "INSERT INTO PRODUCTO (nombre, descripcion, cantidad, categoria_id) " + "VALUES (?, ?, ?,?)";

			final PreparedStatement statement = this.con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);

			try (statement) {
				ejecutaRegistro(producto, statement);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	private void ejecutaRegistro(Producto producto, PreparedStatement statement) throws SQLException {
		statement.setString(1, producto.getNombre());
		statement.setString(2, producto.getDescripcion());
		statement.setInt(3, producto.getCantidad());
		statement.setInt(4, producto.getCategoriaId());
		statement.execute();

		final ResultSet resultSet = statement.getGeneratedKeys();
		try (resultSet) {
			while (resultSet.next()) {
				producto.setId(resultSet.getInt(1));
				System.out.println(String.format("Fue insertado el producto %s", producto));
			}
		}

	}

	public List<Producto> listar() {
		
		try  {
			String sqlInsert = "SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM PRODUCTO";

			final PreparedStatement statement = this.con.prepareStatement(sqlInsert);
			try (statement) {
				statement.execute();

				ResultSet resultSet = statement.getResultSet();
				List<Producto> resultado = new ArrayList<>();

				while (resultSet.next()) {
					Producto fila = new Producto(resultSet.getInt("ID"),resultSet.getString("NOMBRE"),resultSet.getString("DESCRIPCION"),resultSet.getInt("CANTIDAD"));
				
					resultado.add(fila);

				}
				return resultado;
			}
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int eliminar(Integer id) {
		
		try {
			String sqlInsert = "DELETE FROM PRODUCTO WHERE ID = ?";

			final PreparedStatement statement = this.con.prepareStatement(sqlInsert);
			try (statement) {
				
				statement.setInt(1, id);

				statement.execute();

				int updateCount = statement.getUpdateCount();

				return updateCount;
			}
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public int modificar(String nombre, String descripcion, Integer id, Integer cantidad) {
		try{
			String sqlInsert = "UPDATE producto SET " + "NOMBRE = ?, DESCRIPCION = ?, CANTIDAD = ? WHERE id = ?";

			final PreparedStatement statement = this.con.prepareStatement(sqlInsert);
			try (statement) {
				statement.setString(0, nombre);
				statement.setString(1, descripcion);
				statement.setInt(2, cantidad);
				statement.setInt(3, id);

				statement.execute();

				int updateCount = statement.getUpdateCount();
				return updateCount;
			}
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Producto> listar(Integer categoriaId) {
		List<Producto> resultado = new ArrayList<>();
		try  {
			String sqlInsert = "SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM PRODUCTO "
					+ "WHERE CATEGORIA_ID = ?";
			System.out.println(sqlInsert);
			final PreparedStatement statement = this.con.prepareStatement(sqlInsert);
			try (statement) {
				statement.setInt(1, categoriaId);
				statement.execute();

				ResultSet resultSet = statement.getResultSet();
				

				while (resultSet.next()) {
					Producto fila = new Producto(resultSet.getInt("ID"),resultSet.getString("NOMBRE"),resultSet.getString("DESCRIPCION"),resultSet.getInt("CANTIDAD"));
				
					resultado.add(fila);

				}
		
			}
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return resultado;
	
	}
	

}
