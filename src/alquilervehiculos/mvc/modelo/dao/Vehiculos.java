package alquilervehiculos.mvc.modelo.dao;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import alquilervehiculos.mvc.modelo.dominio.ExcepcionAlquilerVehiculos;
import alquilervehiculos.mvc.modelo.dominio.vehiculo.TipoVehiculo;
import alquilervehiculos.mvc.modelo.dominio.vehiculo.Vehiculo;

/**
 * 
 * @author john
 *
 */
public class Vehiculos {

	private Map<String, Vehiculo> vehiculos;
	private final String FICHERO_VEHICULOS =  "datos/vehiculos.dat";
	
	/**
	 * Constructor de la clase Vehiculos empleando el conjunto Map 
	 */
	public Vehiculos () {
		vehiculos = new HashMap<String, Vehiculo>();
	}
	
	/**
	 * Método que devuelve una lista de Vehículos
	 */
	public List<Vehiculo> getVehiculos() {
		return new Vector<Vehiculo>(vehiculos.values());
	}
	
	/**
	 * Método para leer ficheros desde archivo
	 */
	public void leerVehiculos() {
		File fichero = new File(FICHERO_VEHICULOS);
		ObjectInputStream entrada;
		
		try {
			entrada = new ObjectInputStream(new FileInputStream(fichero));
			try {
				while(true) {
					Vehiculo vehiculo = (Vehiculo) entrada.readObject();
					vehiculos.put(vehiculo.getMatricula(), vehiculo);
				}
			}catch (EOFException eo) {
				entrada.close();
				System.out.println("Fichero de vehículos leído correctamente.");
			}catch (ClassNotFoundException e) {
				System.out.println("ERROR: No se ha encontrado la clase que hay que leer.");
			}catch(IOException e) {
				System.out.println("ERROR: Error inesperado de Entrada/Salida");
			}
		}catch(IOException e) {
			System.out.println("ERROR: No se puede abrir el fichero de vehículos.");
		}
	}
	
	/**
	 * Método que escribe datos en un fichero
	 */
	public void escribirVehiculos() {
		File fichero = new File(FICHERO_VEHICULOS);
		try {
			ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(fichero));
			for (Vehiculo vehiculo : vehiculos.values()) {
				salida.writeObject(vehiculo);
			}
			salida.close();
			System.out.println("Fichero de vehículos escrito correctamente.");
		}catch(FileNotFoundException e) {
			System.out.println("ERROR: No se puede crear el fichero de vehículos.");
		}catch(IOException e) {
			System.out.println("ERROR: Error inesperado de Entrada/Salida");
		}
	}
	
	/**
	 * Método para añadir los Vehículos a la lista de Vehículo
	 * @param vehiculo
	 * @param tipoVehiculo
	 */
	public void anadirVehiculo (Vehiculo vehiculo, TipoVehiculo tipoVehiculo) {
		if (vehiculos.containsKey(vehiculo.getMatricula())) {
			throw new ExcepcionAlquilerVehiculos ("Ya existe un vehículo con esa matrícula.");
		}else {
			vehiculos.put(vehiculo.getMatricula(), vehiculo);
		}
	}
	
	/**
	 * Método que elimina Vehiculos de la lista
	 * @param matricula
	 */
	public void borrarVehiculo (String matricula) {
		if(vehiculos.containsKey(matricula)) {
			vehiculos.remove(matricula);
		}else {
			throw new ExcepcionAlquilerVehiculos ("No existe el vehículo que se desea eliminar.");
		}
	}
	
	/**
	 * 
	 * @return el turismo buscado
	 */
	public Vehiculo buscarVehiculo (String matricula) {
		if(vehiculos.containsKey(matricula)) {
			return vehiculos.get(matricula);
		}else {
			throw new ExcepcionAlquilerVehiculos ("No se encuentra el vehículo.");
		}
	}
}
