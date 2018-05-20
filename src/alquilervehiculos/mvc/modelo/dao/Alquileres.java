package alquilervehiculos.mvc.modelo.dao;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Vector;

import alquilervehiculos.mvc.modelo.dominio.Alquiler;
import alquilervehiculos.mvc.modelo.dominio.Cliente;
import alquilervehiculos.mvc.modelo.dominio.ExcepcionAlquilerVehiculos;
import alquilervehiculos.mvc.modelo.dominio.vehiculo.Vehiculo;

/**
 * 
 * @author john
 *
 */
public class Alquileres {

	private List<Alquiler> alquileres;
	private final String FICHERO_ALQUILERES = "datos/alquileres.dat";
	
	/**
	 * 
	 */
	public Alquileres() {
		alquileres = new Vector<Alquiler>();
	}
	
	/**
	 * 
	 */
	public List<Alquiler> getAlquileres() {
		return new Vector<Alquiler>(alquileres);
	}
	
	/**
	 * Método para leer datos de alquileres desde un fichero
	 */
	public void leerAlquileres() {
		File fichero = new File(FICHERO_ALQUILERES);
		ObjectInputStream entrada;
		
		try {
			entrada = new ObjectInputStream(new FileInputStream(fichero));
			try {
				while(true) {
					Alquiler alquiler = (Alquiler) entrada.readObject();
					alquileres.add(alquiler);
				}
			}catch (EOFException eo) {
				entrada.close();
				System.out.println("Fichero de alquileres leído correctamente");
			}catch(ClassNotFoundException e) {
				System.out.println("ERROR: no se encuentra la clase que hay que leer.");
			}catch(IOException e) {
				System.out.println("ERROR: Error inesperado de Entrada/Salida");
			}
		}catch(IOException e) {
			System.out.println("ERROR: No se puede abrir el fichero de alquileres");
		}
	}
	
	/**
	 * Método para escribir datos en un fichero
	 */
	public void escribirAlquileres() {
		File fichero = new File(FICHERO_ALQUILERES);
		
		try {
			ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(fichero));
			for (Alquiler alquiler : alquileres) {
			salida.writeObject(alquiler);
			}
			salida.close();
			System.out.println("Fichero de alquileres escrito correctamente");
		}catch(FileNotFoundException e) {
			System.out.println("ERROR: no puedo crear el fichero de clientes");
		}catch(IOException e) {
			System.out.println("ERROR: Error inesperado de Entrada/Salida");
		}
	}
	
	private void compruebaExistenciaOtroAbierto(Cliente cliente, Vehiculo vehiculo) {
		int indice = 0;
		
		while(indiceNoSuperaTamano(indice)) {
			if(alquileres.get(indice).getVehiculo().getMatricula().equals(vehiculo.getMatricula())
			   && alquileres.get(indice).getDias()== 0) {
				throw new ExcepcionAlquilerVehiculos ("Ya existe un alquiler abierto para ese vehículo.");
			}else {
				indice++;
			}
		}
	}
	
	/**
	 * Método que inicia un alquiler y pone el vehículo seleccionado
	 * por el cliente como no disponible.
	 * @param cliente El cliente que alquila.
	 * @param vehiculo El turismo elegido por el cliente.
	 */
	public void abrirAlquiler (Cliente cliente, Vehiculo vehiculo) {
		compruebaExistenciaOtroAbierto(cliente, vehiculo);
		alquileres.add(new Alquiler (cliente, vehiculo));
	}
	
	/**
	 * Comprueba que no se supera el tamaño del array.
	 * @param indice posición dentro del array.
	 * @return true si no se ha superado el tamaño y false si lo ha hecho.
	 */
	private boolean indiceNoSuperaTamano(int indice) {
		return indice < alquileres.size();
	}
	
	/**
	 * Método que cierra un alquiler abierto y pone el vehículo disponible
	 * para un nuevo alquiler.
	 * @param cliente que tiene un alquiler abierto para cerrar dicho alquiler.
	 * @param vehiculo elegido por el cliente.
	 */
	public void cerrarAlquiler (Cliente cliente, Vehiculo vehiculo) {
		int indice = buscarAlquilerAbierto(cliente, vehiculo);
		
		if (indiceNoSuperaTamano(indice)) {
			alquileres.get(indice).close();
			vehiculo.setDisponible(true);
		}else {
			throw new ExcepcionAlquilerVehiculos ("No hay ningún alquiler abierto para ese vehículo.");
		}
	}
	
	/**
	 * Método que busca un alquiler abierto
	 * @param cliente el cliente que tiene un vehículo alquilado.
	 * @param vehiculo el turismo que ha elegido el cliente.
	 * @return posición del array donde se encuentra el alquiler abierto.
	 */
	private int buscarAlquilerAbierto(Cliente cliente, Vehiculo vehiculo) {
		int indice = 0;
		boolean encontrado = false;
		
		while (indiceNoSuperaTamano(indice) && !encontrado) {
			if(alquileres.get(indice).getVehiculo().getMatricula().equals(vehiculo.getMatricula())
			   && alquileres.get(indice).getDias() == 0){
				encontrado = true;
			}else {
				indice++;
			}
		}
		return encontrado ? indice : alquileres.size();
	}
	
	public List<Alquiler> obtenerAlquileresAbiertos(){
		int posicion = 0;
		
		List<Alquiler> alquileresAbiertos = new Vector<Alquiler>();
		while(posicion < alquileres.size()) {
			if(alquileres.get(posicion).getDias() == 0) {
				alquileresAbiertos.add(alquileres.get(posicion));
			}
			posicion++;
		}
		
		return alquileresAbiertos;
	}
	
	public List<Alquiler> obtenerAlquileresCliente(String dni){
		int posicion = 0;
		List<Alquiler> alquileresCliente = new Vector<Alquiler>();
		
		while(posicion < alquileres.size()) {
			if(alquileres.get(posicion).getCliente().getDni().equals(dni)) {
				alquileresCliente.add(alquileres.get(posicion));
			}
			
			posicion++;
		}
		
		return alquileresCliente;
		
	}
	
	public List<Alquiler> obtenerAlquileresVehiculo(String matricula){
		int posicion = 0;
		List<Alquiler> alquileresVehiculo = new Vector<Alquiler>();
		
		while(posicion < alquileres.size()) {
			if(alquileres.get(posicion).getVehiculo().getMatricula().equals(matricula)) {
				alquileresVehiculo.add(alquileres.get(posicion));
			}
			
			posicion++;
		}
		
		return alquileresVehiculo;
	}

}
