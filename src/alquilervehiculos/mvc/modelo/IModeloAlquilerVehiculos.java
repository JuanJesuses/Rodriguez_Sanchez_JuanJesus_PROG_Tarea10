package alquilervehiculos.mvc.modelo;

import java.util.List;

import alquilervehiculos.mvc.modelo.dominio.Alquiler;
import alquilervehiculos.mvc.modelo.dominio.Cliente;
import alquilervehiculos.mvc.modelo.dominio.vehiculo.TipoVehiculo;
import alquilervehiculos.mvc.modelo.dominio.vehiculo.Vehiculo;

public interface IModeloAlquilerVehiculos {

	void anadirCliente(Cliente cliente);

	void borrarCliente(String dni);

	Cliente buscarCliente(String dni);
	
	List <Cliente> obtenerClientes();

	void anadirVehiculo(Vehiculo vehiculo, TipoVehiculo tipoVehiculo);

	void borrarVehiculo(String matricula);

	Vehiculo buscarVehiculo(String matricula);
	
	List <Vehiculo> obtenerVehiculos();

	void abrirAlquiler(Cliente cliente, Vehiculo vehiculo);

	void cerrarAlquiler(Cliente cliente, Vehiculo vehiculo);
	
	List <Alquiler> obtenerAlquileres();
	
	List <Alquiler> obtenerAlquileresAbiertos();
	
	List <Alquiler> obtenerAlquileresCliente(String dni);
	
	List <Alquiler> obtenerAlquileresVehiculo(String matricula);

	//void anadirDatosPrueba();
	
	void leerVehiculos();
	
	void escribirVehiculos();
	
	void leerClientes();
	
	void escribirClientes();
	
	void leerAlquileres();
	
	void escribirAlquileres();

}