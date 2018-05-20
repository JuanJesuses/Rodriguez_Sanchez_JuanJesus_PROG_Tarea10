package alquilervehiculos.mvc.vista;

import alquilervehiculos.mvc.controlador.IControladorAlquilerVehiculos;

public interface IVistaAlquilerVehiculos {

	void setControlador(IControladorAlquilerVehiculos controlador);

	void comenzar();

}