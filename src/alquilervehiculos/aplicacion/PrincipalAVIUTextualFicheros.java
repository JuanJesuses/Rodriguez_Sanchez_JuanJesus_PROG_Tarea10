package alquilervehiculos.aplicacion;

import alquilervehiculos.mvc.controlador.ControladorAlquilerVehiculos;
import alquilervehiculos.mvc.controlador.IControladorAlquilerVehiculos;
import alquilervehiculos.mvc.modelo.AlquilerVehiculos;
import alquilervehiculos.mvc.modelo.IModeloAlquilerVehiculos;
import alquilervehiculos.mvc.modelo.dao.Alquileres;
import alquilervehiculos.mvc.modelo.dao.Clientes;
import alquilervehiculos.mvc.modelo.dao.Vehiculos;
import alquilervehiculos.mvc.modelo.dominio.Alquiler;
import alquilervehiculos.mvc.modelo.dominio.Cliente;
import alquilervehiculos.mvc.modelo.dominio.DireccionPostal;
import alquilervehiculos.mvc.modelo.dominio.ExcepcionAlquilerVehiculos;
import alquilervehiculos.mvc.modelo.dominio.vehiculo.Vehiculo;
import alquilervehiculos.mvc.vista.IVistaAlquilerVehiculos;
import alquilervehiculos.mvc.vista.iutextual.IUTextual;
import alquilervehiculos.mvc.vista.iutextual.utilidades.Entrada;

public class PrincipalAVIUTextualFicheros {

	public static void main(String[] args) {
		
		IModeloAlquilerVehiculos modelo = new AlquilerVehiculos();
		IVistaAlquilerVehiculos vista = new IUTextual();
		IControladorAlquilerVehiculos controlador = new ControladorAlquilerVehiculos(modelo, vista);
		
		controlador.comenzar();
		
	} 
}