package alquilervehiculos.mvc.vista.iutextual.utilidades;

import alquilervehiculos.mvc.modelo.dominio.Cliente;
import alquilervehiculos.mvc.modelo.dominio.DireccionPostal;
import alquilervehiculos.mvc.modelo.dominio.ExcepcionAlquilerVehiculos;
import alquilervehiculos.mvc.modelo.dominio.vehiculo.DatosTecnicosVehiculos;
import alquilervehiculos.mvc.modelo.dominio.vehiculo.TipoVehiculo;
import alquilervehiculos.mvc.modelo.dominio.vehiculo.Vehiculo;
import alquilervehiculos.mvc.vista.iutextual.Opcion;

public class Consola {
	
	public static void mostrarMenu() {
		mostrarCabecera("Alquiler de Vehículos");
		
		for (Opcion opcion : Opcion.values()) {
			System.out.println(opcion);
		}
	}
	
	public static void mostrarCabecera(String mensaje) {
		
		System.out.printf("%n%s%n", mensaje);
		System.out.println(String.format("%0" + mensaje.length() + "d%n", 0).replace("0", "-"));
		
	}
	
	public static int elegirOpcion() {
		int ordinalOpcion;
		
		do {
			System.out.println("\nElige una opción: ");
			ordinalOpcion = Entrada.entero();
		}while(!Opcion.esOrdinalValido(ordinalOpcion));
		
		return ordinalOpcion;
	}
	
	public static String leerDni() {
		System.out.println("Introduce el DNI del cliente: ");
		String dni = Entrada.cadena();
		
		return dni;
	}
	
	public static String leerMatricula() {
		System.out.println("Introduce la matrícula del vehículo: ");
		String matricula = Entrada.cadena();
		
		return matricula;
	}
	
	public static Cliente leerCliente() {
		
		Cliente cliente = null;
		System.out.println("Introduce el nombre: ");
		String nombre = Entrada.cadena();
		System.out.println("Introduce el DNI: ");
		String dni = Entrada.cadena();
		System.out.println("Introduce la calle: ");
		String calle = Entrada.cadena();
		System.out.println("Introduce la localidad: ");
		String localidad = Entrada.cadena();
		System.out.println("Introduce el código postal: ");
		String codigoPostal = Entrada.cadena();
		
		try {
			cliente = new Cliente(nombre, dni, new DireccionPostal (calle, localidad, codigoPostal));
		}catch (ExcepcionAlquilerVehiculos e) {
			System.out.printf("ERROR: %s%n%n", e.getMessage());
		}
					
		return cliente;
	}
	
	public static Vehiculo leerVehiculo() {
		Vehiculo nuevoVehiculo = null;
		
		int ordinalVehiculo = elegirTipoVehiculo();
		
		System.out.println("Introduce la matrícula: ");
		String matricula = Entrada.cadena();
		System.out.println("Introduce la marca del vehículo: ");
		String marca = Entrada.cadena();
		System.out.println("Introduce el modelo del vehículo: ");
		String modelo = Entrada.cadena();
		System.out.println("Introduce la cilindrada: ");
		int cilindrada = Entrada.entero();
		System.out.println("Introduce el número de plazas del vehículo: ");
		int numeroPlazas = Entrada.entero();
		System.out.println("Introduce el Peso Máximo Autorizado (PMA): ");
		int pma = Entrada.entero();
		DatosTecnicosVehiculos nuevosDatos = new DatosTecnicosVehiculos(cilindrada, numeroPlazas, pma);
		
		nuevoVehiculo = TipoVehiculo.getTipoVehiculoSegunOrdinal(ordinalVehiculo).getInstancia(matricula, marca, modelo, nuevosDatos);
		
		return nuevoVehiculo;
	}
	
	public static int elegirTipoVehiculo() {
		int ordinalTipoVehiculo;
		
		do {
			System.out.printf("Elige el tipo de vehículo: ( %s)", obtenerTipoDeVehiculo());
			ordinalTipoVehiculo = Entrada.entero();
		}while (!TipoVehiculo.esOrdinalValido(ordinalTipoVehiculo));
		
		return ordinalTipoVehiculo;
	}
	
	public static String obtenerTipoDeVehiculo() {
		StringBuilder tiposDeVehiculo = new StringBuilder("");
		for (TipoVehiculo tipoVehiculo : TipoVehiculo.values()) {
			tiposDeVehiculo.append(tipoVehiculo.ordinal()).append(".- ").append(tipoVehiculo).append(" ");
		}
		return tiposDeVehiculo.toString();
	}
	
}
