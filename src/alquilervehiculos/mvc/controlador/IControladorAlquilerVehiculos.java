package alquilervehiculos.mvc.controlador;

import java.util.List;

import alquilervehiculos.mvc.modelo.dominio.Alquiler;
import alquilervehiculos.mvc.modelo.dominio.Cliente;
import alquilervehiculos.mvc.modelo.dominio.vehiculo.TipoVehiculo;
import alquilervehiculos.mvc.modelo.dominio.vehiculo.Vehiculo;

public interface IControladorAlquilerVehiculos {

	void comenzar();
	
	void salir();

	void anadirVehiculo(Vehiculo vehiculo, TipoVehiculo tipoVehiculo);

	void borrarVehiculo(String matricula);

	Vehiculo buscarVehiculo(String matricula);
	
	List <Vehiculo> obtenerVehiculos();

	void anadirCliente(Cliente cliente);

	void borrarCliente(String dni);

	Cliente buscarCliente(String dni);
	
	List <Cliente> obtenerClientes();

	void abrirAlquiler(Cliente cliente, Vehiculo vehiculo);

	void cerrarAlquiler(Cliente cliente, Vehiculo vehiculo);
	
	List <Alquiler> obtenerAlquileres();
	
	List <Alquiler> obtenerAlquileresAbiertos();
	
	List <Alquiler> obtenerAlquileresCliente(String dni);
	
	List <Alquiler> obtenerAlquileresVehiculo(String matricula);

	
}