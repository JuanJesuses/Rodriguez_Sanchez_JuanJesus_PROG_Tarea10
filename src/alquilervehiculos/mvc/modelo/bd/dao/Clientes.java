package alquilervehiculos.mvc.modelo.bd.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import alquilervehiculos.mvc.modelo.bd.utilidades.accesoBD;
import alquilervehiculos.mvc.modelo.dominio.Cliente;
import alquilervehiculos.mvc.modelo.dominio.DireccionPostal;
import alquilervehiculos.mvc.modelo.dominio.ExcepcionAlquilerVehiculos;

public class Clientes {

	
	public List<Cliente> obtenerClientes() {
		List<Cliente> clientes = new Vector<Cliente>();
		Connection conexion = accesoBD.estableceConexion();
		try {
			String sentenciaStr = "select nombre, dni, calle, localidad, codigoPostal from clientes";
			Statement sentencia = (Statement) conexion.createStatement();
			ResultSet filas = sentencia.executeQuery(sentenciaStr);
			while (filas.next()) {
				String nombre = filas.getString(1);
				String dni = filas.getString(2);
				String calle = filas.getString("calle");
				String localidad = filas.getString("localidad");
				String codigoPostal = filas.getString("codigoPostal");
				Cliente cliente = new Cliente(nombre, dni, new DireccionPostal(calle, localidad, codigoPostal));
				clientes.add(cliente);
			}
		} catch (SQLException e) {
			accesoBD.cierraConexion(conexion);
			throw new ExcepcionAlquilerVehiculos("SQL Exception: "+ e.toString());
		}
		accesoBD.cierraConexion(conexion);
		return clientes;
	}
	
	public void anadir(Cliente cliente) {
		Connection conexion = accesoBD.estableceConexion();
		try {
			String sentenciaStr = "insert into clientes values (null, ?, ?, ?, ?, ?)";
			PreparedStatement sentencia = (PreparedStatement) conexion.prepareStatement(sentenciaStr);
			sentencia.setString(1, cliente.getNombre());
			sentencia.setString(2, cliente.getDni());
			DireccionPostal direccion = cliente.getDireccionPostal();
			sentencia.setString(3,  direccion.getCalle());
			sentencia.setString(4,  direccion.getLocalidad());
			sentencia.setString(5,  direccion.getCodigoPostal());
			sentencia.executeUpdate();
		} catch (MySQLIntegrityConstraintViolationException e) {
			accesoBD.cierraConexion(conexion);
			throw new ExcepcionAlquilerVehiculos("Ya existe un cliente con ese DNI");
		} catch (SQLException e) {
			accesoBD.cierraConexion(conexion);
			throw new ExcepcionAlquilerVehiculos("SQL Exception: "+ e.toString());
		}
		accesoBD.cierraConexion(conexion);
	}
	
	public void borrar(String dni) {
		Connection conexion = accesoBD.estableceConexion();
		try {
			String sentenciaStr = "delete from clientes where dni = ?";
			PreparedStatement sentencia = (PreparedStatement) conexion.prepareStatement(sentenciaStr);
			sentencia.setString(1, dni);
			if (sentencia.executeUpdate() == 0) {
				accesoBD.cierraConexion(conexion);
				throw new ExcepcionAlquilerVehiculos("No existe ningún cliente con ese DNI");
			}
		} catch (MySQLIntegrityConstraintViolationException e) {
			accesoBD.cierraConexion(conexion);
			throw new ExcepcionAlquilerVehiculos("No se puede borrar un cliente que ya tiene alquileres");
		} catch (SQLException e) {
			accesoBD.cierraConexion(conexion);
			throw new ExcepcionAlquilerVehiculos("SQL Exception: "+ e.toString());
		}
		accesoBD.cierraConexion(conexion);
	}
	
	public Cliente buscar(String dni) {
		Cliente cliente = null;
		Connection conexion = accesoBD.estableceConexion();
		try {
			String sentenciaStr = "select nombre, calle, localidad, codigoPostal from clientes where dni = ?";
			PreparedStatement sentencia = (PreparedStatement) conexion.prepareStatement(sentenciaStr);
			sentencia.setString(1, dni);
			ResultSet filas = sentencia.executeQuery();
			if (filas.next()) {
				String nombre = filas.getString(1);
				String calle = filas.getString("calle");
				String localidad = filas.getString("localidad");
				String codigoPostal = filas.getString("codigoPostal");
				cliente = new Cliente(nombre, dni, new DireccionPostal(calle, localidad, codigoPostal));
			}
		} catch (SQLException e) {
			accesoBD.cierraConexion(conexion);
			throw new ExcepcionAlquilerVehiculos("SQL Exception: "+ e.toString());
		}
		accesoBD.cierraConexion(conexion);
		return cliente;
	}
	
	public static int getIdentificador(String dni) {
		int identificador = -1;
		Connection conexion = accesoBD.estableceConexion();
		try {
			String sentenciaStr = "select identificador from clientes where dni = ?";
			PreparedStatement sentencia = (PreparedStatement) conexion.prepareStatement(sentenciaStr);
			sentencia.setString(1, dni);
			ResultSet filas = sentencia.executeQuery();
			if (filas.next()) {
				identificador = filas.getInt(1);
			}
		} catch (SQLException e) {
			accesoBD.cierraConexion(conexion);
			throw new ExcepcionAlquilerVehiculos("SQL Exception: "+ e.toString());
		}
		accesoBD.cierraConexion(conexion);
		return identificador;
	}
	
	public static Cliente buscar(int identificador) {
		Cliente cliente = null;
		Connection conexion = accesoBD.estableceConexion();
		try {
			String sentenciaStr = "select nombre, dni, calle, localidad, codigoPostal from clientes where identificador = ?";
			PreparedStatement sentencia = (PreparedStatement) conexion.prepareStatement(sentenciaStr);
			sentencia.setInt(1, identificador);
			ResultSet filas = sentencia.executeQuery();
			if (filas.next()) {
				String nombre = filas.getString(1);
				String dni = filas.getString(2);
				String calle = filas.getString("calle");
				String localidad = filas.getString("localidad");
				String codigoPostal = filas.getString("codigoPostal");
				cliente = new Cliente(nombre, dni, new DireccionPostal(calle, localidad, codigoPostal));
			}
		} catch (SQLException e) {
			accesoBD.cierraConexion(conexion);
			throw new ExcepcionAlquilerVehiculos("SQL Exception: "+ e.toString());
		}
		accesoBD.cierraConexion(conexion);
		return cliente;
	}

}
