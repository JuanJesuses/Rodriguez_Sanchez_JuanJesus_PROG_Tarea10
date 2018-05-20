package alquilervehiculos.mvc.modelo.bd;

import java.util.List;

import alquilervehiculos.mvc.modelo.IModeloAlquilerVehiculos;
import alquilervehiculos.mvc.modelo.bd.dao.Alquileres;
import alquilervehiculos.mvc.modelo.bd.dao.Clientes;
import alquilervehiculos.mvc.modelo.bd.dao.Vehiculos;
import alquilervehiculos.mvc.modelo.dominio.Alquiler;
import alquilervehiculos.mvc.modelo.dominio.Cliente;
import alquilervehiculos.mvc.modelo.dominio.DireccionPostal;
import alquilervehiculos.mvc.modelo.dominio.ExcepcionAlquilerVehiculos;
import alquilervehiculos.mvc.modelo.dominio.vehiculo.DatosTecnicosVehiculos;
import alquilervehiculos.mvc.modelo.dominio.vehiculo.TipoVehiculo;
import alquilervehiculos.mvc.modelo.dominio.vehiculo.Vehiculo;

public class AlquilerVehiculos implements IModeloAlquilerVehiculos {
	
	private Clientes clientes;
	private Vehiculos vehiculos;
	private Alquileres alquileres;
	
	/**
	 * Constructor de la clase Alquiler Vehiculos que instancia
	 * los objetos Clientes, Vehiculos y Alquileres
	 */
	public AlquilerVehiculos() {
		
		clientes = new Clientes();
		vehiculos = new Vehiculos();
		alquileres = new Alquileres();
		
	}
	
	
	@Override
	public void anadirCliente (Cliente cliente) {
		clientes.anadir(cliente);
	}
	
	@Override
	public void borrarCliente (String dni) {
		clientes.borrar(dni);
	}
	
	
	@Override
	public Cliente buscarCliente (String dni) {
		return clientes.buscar(dni);
	}
	
	@Override
	public List <Cliente> obtenerClientes(){
		return clientes.obtenerClientes();
	}
	
	
	@Override
	public void anadirVehiculo (Vehiculo vehiculo, TipoVehiculo tipoVehiculo) {
		vehiculos.anadir(vehiculo);
	}
	
	
	@Override
	public void borrarVehiculo(String matricula) {
		vehiculos.borrar(matricula);
	}
	
	
	@Override
	public Vehiculo buscarVehiculo (String matricula) {
		return vehiculos.buscar(matricula);
	}
	
	
	@Override
	public List<Vehiculo> obtenerVehiculos() {
		return vehiculos.obtenerVehiculos();
	}
	
		@Override
	public void abrirAlquiler (Cliente cliente, Vehiculo vehiculo) {
		comprobarExistenciaVehiculo(vehiculo);
		alquileres.abrir(cliente, vehiculo);
	}
	
	private void comprobarExistenciaVehiculo(Vehiculo vehiculo) {
		if(vehiculos.buscar(vehiculo.getMatricula()) == null) {
			throw new ExcepcionAlquilerVehiculos ("El vehículo no existe.");
		}
	}
	
	
	@Override
	public void cerrarAlquiler (Cliente cliente, Vehiculo vehiculo) {
		comprobarExistenciaVehiculo(vehiculo);
		alquileres.cerrar(cliente, vehiculo);
	}
	
	
	@Override
	public List<Alquiler> obtenerAlquileres() {
		return alquileres.obtenerAlquileres();
	}
	
	@Override
	public List<Alquiler> obtenerAlquileresAbiertos(){
		//return alquileres.obtenerAlquileresAbiertos();
		return null;
	}
	
	@Override
	public List<Alquiler> obtenerAlquileresCliente(String dni){
		//return alquileres.obtenerAlquileresCliente(dni);
		return null;
	}
	
	@Override
	public List<Alquiler> obtenerAlquileresVehiculo(String matricula){
		//return alquileres.obtenerAlquileresVehiculo(matricula);
		return null;
	}
	
	@Override
	public void leerVehiculos() {
		//vehiculos.leerVehiculos();
	}
	
	@Override
	public void escribirVehiculos() {
		//vehiculos.escribirVehiculos();
	}
	
	@Override
	public void leerClientes() {
		//clientes.leerClientes();
	}
	
	@Override
	public void escribirClientes() {
		//clientes.escribirClientes();
	}
	
	@Override
	public void leerAlquileres() {
		//alquileres.leerAlquileres();
	}
	
	@Override
	public void escribirAlquileres() {
		//alquileres.escribirAlquileres();
	}

	
	
	/**@Override
	public void anadirDatosPrueba() {
		
		Cliente cliente1 = new Cliente ("Bilbo Bolsón", "12345678A", new DireccionPostal("C/La Runa, 32", "Bolsón Cerrado", "01005"));
		Cliente cliente2 = new Cliente ("Meriadoc Brandigamo", "23456789B", new DireccionPostal("C/La Runa, 72", "Hobbitón", "63541"));
		Cliente cliente3 = new Cliente ("Peregrin Tuc", "34567890C", new DireccionPostal("C/La Runa, 29", "Bolsón Cerrado", "22456"));
		
		anadirCliente(cliente1);
		anadirCliente(cliente2);
		anadirCliente(cliente3);
		
		Vehiculo vehiculo1 = TipoVehiculo.TURISMO.getInstancia("0236NMJ", "Audi", "Q7", new DatosTecnicosVehiculos (3000, 7, 3500));
		Vehiculo vehiculo2 = TipoVehiculo.AUTOBUS.getInstancia("7628HKG", "PEGASO", "NONAINO", new DatosTecnicosVehiculos (5000, 54, 12500));
		Vehiculo vehiculo3 = TipoVehiculo.DE_CARGA.getInstancia("2356CDH", "IVECO", "DAILY", new DatosTecnicosVehiculos (5000, 6, 35000));
		
		anadirVehiculo(vehiculo1, TipoVehiculo.TURISMO);
		anadirVehiculo(vehiculo2, TipoVehiculo.AUTOBUS);
		anadirVehiculo(vehiculo3, TipoVehiculo.DE_CARGA);
		
		abrirAlquiler(cliente1, vehiculo1);
		abrirAlquiler(cliente2, vehiculo2);
		abrirAlquiler(cliente3, vehiculo3);
		
	}*/

}
