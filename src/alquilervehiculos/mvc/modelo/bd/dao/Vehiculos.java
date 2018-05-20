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
import alquilervehiculos.mvc.modelo.dominio.ExcepcionAlquilerVehiculos;
import alquilervehiculos.mvc.modelo.dominio.vehiculo.DatosTecnicosVehiculos;
import alquilervehiculos.mvc.modelo.dominio.vehiculo.TipoVehiculo;
import alquilervehiculos.mvc.modelo.dominio.vehiculo.Vehiculo;

public class Vehiculos {
	
	public List<Vehiculo> obtenerVehiculos() {
		List<Vehiculo> vehiculos = new Vector<Vehiculo>();
		Connection conexion = accesoBD.estableceConexion();
		try {
			String sentenciaStr = "select tipo, matricula, marca, modelo, cilindrada, numeroPlazas, pma, disponible from vehiculos";
			Statement sentencia = (Statement) conexion.createStatement();
			ResultSet filas = sentencia.executeQuery(sentenciaStr);
			while (filas.next()) {
				TipoVehiculo tipoVehiculo = TipoVehiculo.valueOf(filas.getString(1));
				String matricula = filas.getString(2);
				String marca = filas.getString(3);
				String modelo = filas.getString("modelo");
				int cilindrada = filas.getInt("cilindrada");
				int numeroPlazas = filas.getInt("numeroPlazas");
				int pma = filas.getInt("pma");
				boolean disponible = filas.getBoolean("disponible");
				Vehiculo vehiculo = tipoVehiculo.getInstancia(matricula, marca, modelo, 
						new DatosTecnicosVehiculos(cilindrada, numeroPlazas, pma));
				vehiculo.setDisponible(disponible);
				vehiculos.add(vehiculo);
			}
		} catch (SQLException e) {
			accesoBD.cierraConexion(conexion);
			throw new ExcepcionAlquilerVehiculos("SQL Exception: "+ e.toString());
		}
		accesoBD.cierraConexion(conexion);
		return vehiculos;
	}
	
	public void anadir(Vehiculo vehiculo) {
		Connection conexion = accesoBD.estableceConexion();
		try {
			String sentenciaStr = "insert into vehiculos values (null, ?, ?, ?, ?, ?, ?, ?, true)";
			PreparedStatement sentencia = (PreparedStatement) conexion.prepareStatement(sentenciaStr);
			sentencia.setString(1, vehiculo.getMatricula());
			sentencia.setString(2, vehiculo.getMarca());
			sentencia.setString(3, vehiculo.getModelo());
			DatosTecnicosVehiculos datosTecnicos = vehiculo.getDatosTecnicosVehiculo();
			sentencia.setInt(4,  datosTecnicos.getCilindrada());
			sentencia.setInt(5,  datosTecnicos.getNumeroPlazas());
			sentencia.setInt(6,  datosTecnicos.getPma());
			sentencia.setString(7, vehiculo.getTipoVehiculo().name());
			sentencia.executeUpdate();
		} catch (MySQLIntegrityConstraintViolationException e) {
			accesoBD.cierraConexion(conexion);
			throw new ExcepcionAlquilerVehiculos("Ya existe un vehículo con esa matrícula");
		} catch (SQLException e) {
			accesoBD.cierraConexion(conexion);
			throw new ExcepcionAlquilerVehiculos("SQL Exception: "+ e.toString());
		}
		accesoBD.cierraConexion(conexion);
	}

	public void borrar(String matricula) {
		Connection conexion = accesoBD.estableceConexion();
		try {
			String sentenciaStr = "delete from vehiculos where matricula = ?";
			PreparedStatement sentencia = (PreparedStatement) conexion.prepareStatement(sentenciaStr);
			sentencia.setString(1, matricula);
			if (sentencia.executeUpdate() == 0) {
				accesoBD.cierraConexion(conexion);
				throw new ExcepcionAlquilerVehiculos("No existe ningún vehículo con esa matrícula");
			}
		} catch (MySQLIntegrityConstraintViolationException e) {
			accesoBD.cierraConexion(conexion);
			throw new ExcepcionAlquilerVehiculos("No se puede borrar un vehículo que ya tiene alquileres");
		} catch (SQLException e) {
			accesoBD.cierraConexion(conexion);
			throw new ExcepcionAlquilerVehiculos("SQL Exception: "+ e.toString());
		}
		accesoBD.cierraConexion(conexion);
	}

	public Vehiculo buscar(String matricula) {
		Vehiculo vehiculo = null;
		Connection conexion = accesoBD.estableceConexion();
		try {
			String sentenciaStr = "select tipo, marca, modelo, cilindrada, numeroPlazas, pma, disponible from vehiculos where matricula = ?";
			PreparedStatement sentencia = (PreparedStatement) conexion.prepareStatement(sentenciaStr);
			sentencia.setString(1, matricula);
			ResultSet filas = sentencia.executeQuery();
			if (filas.next()) {
				TipoVehiculo tipoVehiculo = TipoVehiculo.valueOf(filas.getString(1));
				String marca = filas.getString(2);
				String modelo = filas.getString("modelo");
				int cilindrada = filas.getInt("cilindrada");
				int numeroPlazas = filas.getInt("numeroPlazas");
				int pma = filas.getInt("pma");
				boolean disponible = filas.getBoolean("disponible");
				vehiculo = tipoVehiculo.getInstancia(matricula, marca, modelo, 
						new DatosTecnicosVehiculos(cilindrada, numeroPlazas, pma));
				vehiculo.setDisponible(disponible);
			}
		} catch (SQLException e) {
			accesoBD.cierraConexion(conexion);
			throw new ExcepcionAlquilerVehiculos("SQL Exception: "+ e.toString());
		}
		accesoBD.cierraConexion(conexion);
		return vehiculo;
	}
	
	public static int getIdentificador(String matricula) {
		int identificador = -1;
		Connection conexion = accesoBD.estableceConexion();
		try {
			String sentenciaStr = "select identificador from vehiculos where matricula = ?";
			PreparedStatement sentencia = (PreparedStatement) conexion.prepareStatement(sentenciaStr);
			sentencia.setString(1, matricula);
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
	
	public static Vehiculo buscar(int identificador) {
		Vehiculo vehiculo = null;
		Connection conexion = accesoBD.estableceConexion();
		try {
			String sentenciaStr = "select tipo, matricula, marca, modelo, cilindrada, numeroPlazas, pma, disponible from vehiculos where identificador = ?";
			PreparedStatement sentencia = (PreparedStatement) conexion.prepareStatement(sentenciaStr);
			sentencia.setInt(1, identificador);
			ResultSet filas = sentencia.executeQuery();
			if (filas.next()) {
				TipoVehiculo tipoVehiculo = TipoVehiculo.valueOf(filas.getString(1));
				String matricula = filas.getString(2);
				String marca = filas.getString(3);
				String modelo = filas.getString("modelo");
				int cilindrada = filas.getInt("cilindrada");
				int numeroPlazas = filas.getInt("numeroPlazas");
				int pma = filas.getInt("pma");
				boolean disponible = filas.getBoolean("disponible");
				vehiculo = tipoVehiculo.getInstancia(matricula, marca, modelo, 
						new DatosTecnicosVehiculos(cilindrada, numeroPlazas, pma));
				vehiculo.setDisponible(disponible);
			}
		} catch (SQLException e) {
			accesoBD.cierraConexion(conexion);
			throw new ExcepcionAlquilerVehiculos("SQL Exception: "+ e.toString());
		}
		accesoBD.cierraConexion(conexion);
		return vehiculo;
	}
	
	public static Vehiculo setDiponible(int identificador, boolean disponible) {
		Vehiculo vehiculo = null;
		Connection conexion = accesoBD.estableceConexion();
		try {
			String sentenciaStr = "update vehiculos set disponible = ? where identificador = ?";
			PreparedStatement sentencia = (PreparedStatement) conexion.prepareStatement(sentenciaStr);
			sentencia.setBoolean(1, disponible);
			sentencia.setInt(2, identificador);
			sentencia.executeUpdate();
		} catch (SQLException e) {
			accesoBD.cierraConexion(conexion);
			throw new ExcepcionAlquilerVehiculos("SQL Exception: "+ e.toString());
		}
		accesoBD.cierraConexion(conexion);
		return vehiculo;
	}

}
