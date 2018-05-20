package alquilervehiculos.mvc.controlador;

import java.util.List;

import alquilervehiculos.mvc.modelo.IModeloAlquilerVehiculos;
import alquilervehiculos.mvc.modelo.dao.Alquileres;
import alquilervehiculos.mvc.modelo.dao.Clientes;
import alquilervehiculos.mvc.modelo.dao.Vehiculos;
import alquilervehiculos.mvc.modelo.dominio.Alquiler;
import alquilervehiculos.mvc.modelo.dominio.Cliente;
import alquilervehiculos.mvc.modelo.dominio.vehiculo.TipoVehiculo;
import alquilervehiculos.mvc.modelo.dominio.vehiculo.Vehiculo;
import alquilervehiculos.mvc.vista.IVistaAlquilerVehiculos;

public class ControladorAlquilerVehiculos implements IControladorAlquilerVehiculos{
	
	private IModeloAlquilerVehiculos modelo;
	private IVistaAlquilerVehiculos vista;
	
	/**
	 * Constructor que crea un controlador y recibe dos parámetros
	 * @param modelo
	 * @param vista
	 */
	public ControladorAlquilerVehiculos(IModeloAlquilerVehiculos modelo, IVistaAlquilerVehiculos vista) {
		this.vista = vista;
		this.modelo = modelo;
		vista.setControlador(this);
	}
	
	
	@Override
	public void comenzar() {
		//modelo.anadirDatosPrueba();
		
		modelo.leerClientes();
		modelo.leerVehiculos();
		modelo.leerAlquileres();
		vista.comenzar();
	}
	
	@Override
	public void salir() {
		modelo.escribirVehiculos();
		modelo.escribirClientes();
		modelo.escribirAlquileres();
	}
	
	
	@Override
	public void anadirVehiculo(Vehiculo vehiculo, TipoVehiculo tipoVehiculo) {
		modelo.anadirVehiculo(vehiculo, tipoVehiculo);
	}
	
	
	@Override
	public void borrarVehiculo(String matricula) {
		modelo.borrarVehiculo(matricula);
	}
	
	@Override
	public Vehiculo buscarVehiculo(String matricula) {
		return modelo.buscarVehiculo(matricula);
	}
	
	
	@Override
	public List<Vehiculo> obtenerVehiculos() {
		return modelo.obtenerVehiculos();
	}
	
	@Override
	public void anadirCliente(Cliente cliente) {
		modelo.anadirCliente(cliente);
	}
	
	@Override
	public void borrarCliente (String dni) {
		modelo.borrarCliente(dni);
	}
	
	
	@Override
	public Cliente buscarCliente(String dni) {
		return modelo.buscarCliente(dni);
	}
	
	@Override
	public List<Cliente> obtenerClientes(){
		return modelo.obtenerClientes();
	}
	
	@Override
	public void abrirAlquiler(Cliente cliente, Vehiculo vehiculo) {
		modelo.abrirAlquiler(cliente, vehiculo);
	}
	
	@Override
	public void cerrarAlquiler(Cliente cliente, Vehiculo vehiculo) {
		modelo.cerrarAlquiler(cliente, vehiculo);
	}

	@Override
	public List<Alquiler> obtenerAlquileres() {
		return modelo.obtenerAlquileres();
	}
	
	@Override
	public List<Alquiler> obtenerAlquileresAbiertos() {
		return modelo.obtenerAlquileresAbiertos();
	}
	
	@Override
	public List<Alquiler> obtenerAlquileresCliente(String dni){
		return modelo.obtenerAlquileresCliente(dni);
	}
	
	@Override
	public List<Alquiler> obtenerAlquileresVehiculo(String matricula){
		return modelo.obtenerAlquileresVehiculo(matricula);
	}
	
	/**
	 * Método que llama al método que añade los datos de prueba
	 * para comprobar el funcionamiento de la aplicación
	 */
	/**public void anadirDatosPrueba() {
		modelo.anadirDatosPrueba();
	}*/
	
}
