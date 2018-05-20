package alquilervehiculos.mvc.modelo.bd.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Vector;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import alquilervehiculos.mvc.modelo.bd.utilidades.accesoBD;
import alquilervehiculos.mvc.modelo.dominio.Alquiler;
import alquilervehiculos.mvc.modelo.dominio.Cliente;
import alquilervehiculos.mvc.modelo.dominio.ExcepcionAlquilerVehiculos;
import alquilervehiculos.mvc.modelo.dominio.vehiculo.Vehiculo;

public class Alquileres {
	
	public List<Alquiler> obtenerAlquileres() {
		List<Alquiler> alquileres = new Vector<Alquiler>();
		Connection conexion = accesoBD.estableceConexion();
		try {
			String sentenciaStr = "select idCliente, idVehiculo, fecha, dias from alquileres";
			Statement sentencia = (Statement) conexion.createStatement();
			ResultSet filas = sentencia.executeQuery(sentenciaStr);
			while (filas.next()) {
				int idCliente = filas.getInt(1);
				int idVehiculo = filas.getInt(2);
				Timestamp fecha = filas.getTimestamp(3);
				int dias = filas.getInt(4);
				Cliente cliente = Clientes.buscar(idCliente);
				Vehiculo vehiculo = Vehiculos.buscar(idVehiculo);
				Alquiler alquiler = new Alquiler(cliente, vehiculo, fecha, dias);
				alquileres.add(alquiler);
			}
		} catch (SQLException e) {
			accesoBD.cierraConexion(conexion);
			throw new ExcepcionAlquilerVehiculos("SQL Exception: "+ e.toString());
		}
		accesoBD.cierraConexion(conexion);
		return alquileres;
	}

	public void abrir(Cliente cliente, Vehiculo vehiculo) {
		if (!vehiculo.getDisponible())
			throw new ExcepcionAlquilerVehiculos("El vehículo que quiere alquilar no está disponible");
		int idCliente = Clientes.getIdentificador(cliente.getDni());
		int idVehiculo = Vehiculos.getIdentificador(vehiculo.getMatricula());
		Vehiculos.setDiponible(idVehiculo, false);
		Connection conexion = accesoBD.estableceConexion();
		try {
			String sentenciaStr = "insert into alquileres values (?, ?, now(), 0)";
			PreparedStatement sentencia = (PreparedStatement) conexion.prepareStatement(sentenciaStr);
			sentencia.setInt(1, idCliente);
			sentencia.setInt(2, idVehiculo);
			sentencia.executeUpdate();
		} catch (SQLException e) {
			accesoBD.cierraConexion(conexion);
			throw new ExcepcionAlquilerVehiculos("SQL Exception: "+ e.toString());
		}
		accesoBD.cierraConexion(conexion);
	}
	
	public void cerrar(Cliente cliente, Vehiculo vehiculo) {
		int idCliente = Clientes.getIdentificador(cliente.getDni());
		int idVehiculo = Vehiculos.getIdentificador(vehiculo.getMatricula());
		Alquiler alquiler = buscarAbierto(cliente, vehiculo);
		if (alquiler == null)
			throw new ExcepcionAlquilerVehiculos("No hay ningún alquiler abierto para ese cliente y ese vehículo");
		alquiler.close();
		Vehiculos.setDiponible(idVehiculo, true);
		Connection conexion = accesoBD.estableceConexion();
		try {
			String sentenciaStr = "update alquileres set dias = ? where idCliente = ? and idVehiculo = ? and fecha = ?";
			PreparedStatement sentencia = (PreparedStatement) conexion.prepareStatement(sentenciaStr);
			sentencia.setInt(1, alquiler.getDias());
			sentencia.setInt(2, idCliente);
			sentencia.setInt(3, idVehiculo);
			sentencia.setTimestamp(4, new Timestamp(alquiler.getFecha().getTime()));
			sentencia.executeUpdate();
		} catch (SQLException e) {
			accesoBD.cierraConexion(conexion);
			throw new ExcepcionAlquilerVehiculos("SQL Exception: "+ e.toString());
		}
		accesoBD.cierraConexion(conexion);
	}
	
	private Alquiler buscarAbierto(Cliente cliente, Vehiculo vehiculo) {
		Alquiler alquiler = null;
		int idCliente = Clientes.getIdentificador(cliente.getDni());
		int idVehiculo = Vehiculos.getIdentificador(vehiculo.getMatricula());
		Connection conexion = accesoBD.estableceConexion();
		try {
			String sentenciaStr = "select fecha, dias from alquileres where idCliente = ? and idVehiculo = ? and dias = 0";
			PreparedStatement sentencia = (PreparedStatement) conexion.prepareStatement(sentenciaStr);
			sentencia.setInt(1, idCliente);
			sentencia.setInt(2, idVehiculo);;
			ResultSet filas = sentencia.executeQuery();
			if (filas.next()) {
				Timestamp fecha = filas.getTimestamp(1);
				int dias = filas.getInt(2);
				alquiler = new Alquiler(cliente, vehiculo, fecha, dias);
			}
		} catch (SQLException e) {
			accesoBD.cierraConexion(conexion);
			throw new ExcepcionAlquilerVehiculos("SQL Exception: "+ e.toString());
		}
		accesoBD.cierraConexion(conexion);
		return alquiler;
	}
}
