package alquilervehiculos.aplicacion;

import alquilervehiculos.mvc.controlador.ControladorAlquilerVehiculos;
import alquilervehiculos.mvc.controlador.IControladorAlquilerVehiculos;
import alquilervehiculos.mvc.modelo.AlquilerVehiculos;
import alquilervehiculos.mvc.modelo.IModeloAlquilerVehiculos;
import alquilervehiculos.mvc.vista.IVistaAlquilerVehiculos;
import alquilervehiculos.mvc.vista.iugrafica.IUGrafica;

public class PrincipalIUGraficaFicheros {

	public static void main(String[] args) {
		IModeloAlquilerVehiculos modelo = new AlquilerVehiculos();
		IVistaAlquilerVehiculos vista = new IUGrafica();
		IControladorAlquilerVehiculos controlador = new ControladorAlquilerVehiculos(modelo, vista);
		
		controlador.comenzar();
	}

}
