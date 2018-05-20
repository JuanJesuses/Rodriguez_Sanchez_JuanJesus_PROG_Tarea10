package alquilervehiculos.mvc.vista.iutextual;

import alquilervehiculos.mvc.controlador.IControladorAlquilerVehiculos;
import alquilervehiculos.mvc.modelo.dao.Alquileres;
import alquilervehiculos.mvc.modelo.dao.Clientes;
import alquilervehiculos.mvc.modelo.dao.Vehiculos;
import alquilervehiculos.mvc.modelo.dominio.Alquiler;
import alquilervehiculos.mvc.modelo.dominio.Cliente;
import alquilervehiculos.mvc.modelo.dominio.ExcepcionAlquilerVehiculos;
import alquilervehiculos.mvc.modelo.dominio.vehiculo.TipoVehiculo;
import alquilervehiculos.mvc.modelo.dominio.vehiculo.Vehiculo;
import alquilervehiculos.mvc.vista.IVistaAlquilerVehiculos;
import alquilervehiculos.mvc.vista.iutextual.utilidades.Consola;

public class IUTextual implements IVistaAlquilerVehiculos {
	
	IControladorAlquilerVehiculos controlador;
	
	public IUTextual () {
		Opcion.setVista(this);
	}
	
	/* (non-Javadoc)
	 * @see AlquilerVehiculos.mvc.vista.IVistaAlquilerVehiculos#setControlador(AlquilerVehiculos.mvc.controlador.IControladorAlquilerVehiculos)
	 */
	@Override
	public void setControlador (IControladorAlquilerVehiculos controlador) {
		this.controlador = controlador;
	}
	
	/* (non-Javadoc)
	 * @see AlquilerVehiculos.mvc.vista.IVistaAlquilerVehiculos#comenzar()
	 */
	@Override
	public void comenzar() {
		int ordinalOpcion;
		
		do {
			Consola.mostrarMenu();
			ordinalOpcion = Consola.elegirOpcion();
			Opcion opcion = Opcion.getOpcionSegunOrdinal(ordinalOpcion);
			opcion.ejecutar();
		}while(ordinalOpcion != Opcion.SALIR.ordinal());
	}
	
	/* (non-Javadoc)
	 * @see AlquilerVehiculos.mvc.vista.IVistaAlquilerVehiculos#salir()
	 */
	
	public void salir() {
		System.out.println("Ha escogido salir del programa.");
		controlador.salir();
	}
	
	/* (non-Javadoc)
	 * @see AlquilerVehiculos.mvc.vista.IVistaAlquilerVehiculos#listarAlquileres()
	 */
	
	public void listarAlquileres() {
		Consola.mostrarCabecera("Listar Alquileres.");
		
		for(Alquiler alquiler : controlador.obtenerAlquileres()) {
			if(alquiler != null) {
				System.out.println(alquiler);
			}
		}
		System.out.println("");
	}
	
	/* (non-Javadoc)
	 * @see AlquilerVehiculos.mvc.vista.IVistaAlquilerVehiculos#cerrarAlquiler()
	 */
	
	public void cerrarAlquiler() {
		Consola.mostrarCabecera("Cerrar alquiler");
		String dni = Consola.leerDni();
		Cliente cliente = controlador.buscarCliente(dni);
		String matricula = Consola.leerMatricula();
		Vehiculo vehiculo = controlador.buscarVehiculo(matricula);
		
		if(cliente == null || vehiculo == null) {
			System.out.println("ERROR: No hay ningún alquiler en curso con los datos seleccionados.");
		}else {
			try {
				controlador.cerrarAlquiler(cliente, vehiculo);
				System.out.println("Alquiler cerrado de forma satisfactoria.");
			}catch(ExcepcionAlquilerVehiculos e) {
				System.out.printf("ERROR: %s%n%n", e.getMessage());
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see AlquilerVehiculos.mvc.vista.IVistaAlquilerVehiculos#abrirAlquiler()
	 */
	
	public void abrirAlquiler() {
		Consola.mostrarCabecera("Abrir Alquiler");
		String dni = Consola.leerDni();
		Cliente cliente = controlador.buscarCliente(dni);
		String matricula = Consola.leerMatricula();
		Vehiculo vehiculo = controlador.buscarVehiculo(matricula);
		
		if(cliente == null || vehiculo == null) {
			System.out.println("ERROR: No hay ningún alquiler en curso con los datos seleccionados.");
		}else {
			try {
				controlador.abrirAlquiler(cliente, vehiculo);
				System.out.println("Alquiler generado de forma satisfactoria.");
			}catch(ExcepcionAlquilerVehiculos e) {
				System.out.printf("ERROR: %s%n%n", e.getMessage());
			}
		}
	}
	
	
	public void obtenerAlquileresAbiertos() {
		Consola.mostrarCabecera("ALQUILERES ABIERTOS");
		
		for(Alquiler alquileresAbiertos : controlador.obtenerAlquileresAbiertos()) {
			System.out.println(alquileresAbiertos);
		}
	}
	
	
	public void obtenerAlquileresCliente() {
		String dni = Consola.leerDni();
		
		try {
			controlador.buscarCliente(dni);
			Consola.mostrarCabecera("Listado de Alquileres por Cliente");
			if(controlador.obtenerAlquileresCliente(dni).size() == 0) {
				System.out.println("El cliente solicitado no tiene alquileres en curso");
			}else {
				for(Alquiler alquileresCliente : controlador.obtenerAlquileresCliente(dni)) {
					System.out.println(alquileresCliente);
				}
			}
		}catch (ExcepcionAlquilerVehiculos e) {
			System.out.printf("\nERROR: %s%n%n", e.getMessage());
		}
	}
	
	public void obtenerAlquileresVehiculo() {
		String matricula = Consola.leerMatricula();
		
		try {
			controlador.buscarVehiculo(matricula);
			Consola.mostrarCabecera("Listado de Alquileres por vehículo");
			if(controlador.obtenerAlquileresVehiculo(matricula).size() == 0) {
				System.out.println("El vehículo solicitado no tiene alquileres en curso.");
			}else {
				for(Alquiler alquileresVehiculo : controlador.obtenerAlquileresVehiculo(matricula)) {
					System.out.println(alquileresVehiculo);
				}
			}
		}catch (ExcepcionAlquilerVehiculos e) {
			System.out.printf("\nERROR: %s%n%n", e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see AlquilerVehiculos.mvc.vista.IVistaAlquilerVehiculos#listaVehiculos()
	 */
	
	public void listaVehiculos() {
		Consola.mostrarCabecera("Listar Vehículos");
		
		for (Vehiculo vehiculo : controlador.obtenerVehiculos()) {
			if(vehiculo != null) {
				System.out.println(vehiculo);
			}
		}
		System.out.println("");
	}
	
	/* (non-Javadoc)
	 * @see AlquilerVehiculos.mvc.vista.IVistaAlquilerVehiculos#buscarVehiculo()
	 */
	
	public void buscarVehiculo() {
		Consola.mostrarCabecera("Buscar Vehículo");
		String matricula = Consola.leerMatricula();
		Vehiculo vehiculoBuscado = controlador.buscarVehiculo(matricula);
		String mensaje = (vehiculoBuscado != null) ? vehiculoBuscado.toString() : "El vehículo no existe.";
		System.out.printf("%s%n%n", mensaje);
	}
	
	/* (non-Javadoc)
	 * @see AlquilerVehiculos.mvc.vista.IVistaAlquilerVehiculos#borrarVehiculo()
	 */
	
	public void borrarVehiculo() {
		Consola.mostrarCabecera("Borrar Vehículo");
		String matricula = Consola.leerMatricula();
		try {
			controlador.borrarVehiculo(matricula);
			System.out.println("Vehículo eliminado correctamente\n");
		}catch (ExcepcionAlquilerVehiculos e) {
			System.out.printf("ERROR: %s%n%n", e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see AlquilerVehiculos.mvc.vista.IVistaAlquilerVehiculos#anadirVehiculo()
	 */
	
	public void anadirVehiculo() {
		Consola.mostrarCabecera("Añadir Vehículo");
		int tipoVehiculo = 0;		
		
		try {
			Vehiculo vehiculo = Consola.leerVehiculo();
			controlador.anadirVehiculo(vehiculo, TipoVehiculo.getTipoVehiculoSegunOrdinal(tipoVehiculo));
			System.out.println("Vehículo añadido de forma satisfactoria.");
		}catch (Exception e){
			System.out.printf("ERROR: %s%n%n", e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see AlquilerVehiculos.mvc.vista.IVistaAlquilerVehiculos#listarClientes()
	 */
	
	public void listarClientes() {
		Consola.mostrarCabecera("Listar Clientes");
		
		for (Cliente cliente : controlador.obtenerClientes()) {
			if(cliente != null) {
				System.out.println(cliente);
			}
		}
		System.out.println("");
	}
	
	/* (non-Javadoc)
	 * @see AlquilerVehiculos.mvc.vista.IVistaAlquilerVehiculos#buscarCliente()
	 */
	
	public void buscarCliente() {
		Consola.mostrarCabecera("Buscar Cliente");
		String dni = Consola.leerDni();
		Cliente clienteBuscado = controlador.buscarCliente(dni);
		String mensaje = (clienteBuscado != null) ? clienteBuscado.toString() : "El cliente no existe.";
		System.out.printf("%s%n%n", mensaje);
	}
	
	/* (non-Javadoc)
	 * @see AlquilerVehiculos.mvc.vista.IVistaAlquilerVehiculos#borrarCliente()
	 */
	
	public void borrarCliente() {
		Consola.mostrarCabecera("Eliminar Cliente");
		String dni =  Consola.leerDni();
		
		try {
			controlador.borrarCliente(dni);
			System.out.println("Cliente eliminado de forma satisfactoria.");
		}catch (Exception e) {
			System.out.printf("ERROR: %s%n%n", e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see AlquilerVehiculos.mvc.vista.IVistaAlquilerVehiculos#anadirCliente()
	 */
	
	public void anadirCliente() {
		Consola.mostrarCabecera("Añadir Cliente");
				
		try {
			Cliente cliente = Consola.leerCliente();
			controlador.anadirCliente(cliente);
			System.out.println("El cliente se ha añadido de forma satisfactoria.");
		}catch(Exception e) {
			System.out.printf("ERROR: %s%n%n", e.getMessage());
		}
	}
}
