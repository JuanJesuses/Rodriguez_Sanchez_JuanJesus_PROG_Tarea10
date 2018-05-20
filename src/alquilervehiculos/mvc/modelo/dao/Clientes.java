package alquilervehiculos.mvc.modelo.dao;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import alquilervehiculos.mvc.modelo.dominio.Cliente;
import alquilervehiculos.mvc.modelo.dominio.ExcepcionAlquilerVehiculos;

/**
 * Clase que contiene el mapa donde se almacenarán
 * los objetos de tipo Cliente
 * @author john
 *
 */
public class Clientes {
	
	private Map<String, Cliente> clientes;
	private final String FICHERO_CLIENTES = "datos/clientes.dat";
			
	/**
	 * Constructor de la clase Clientes empleando el conjunto Map
	 */
	public Clientes() {
		clientes = new HashMap<String, Cliente>();
	}
	
	/**
	 * Método que devuelve los clientes de una lista ordenada
	 */
	public List<Cliente> getClientes() {
		
		List<Cliente> clientesOrdenados = new Vector<Cliente>(clientes.values());
		
		Collections.sort(clientesOrdenados, new Comparator<Cliente>() {
			public int compare(Cliente uno, Cliente otro) {
				return uno.getNombre().compareTo(otro.getNombre());
			}
		});
		
		return clientesOrdenados;
	}
	
	/**
	 * Método para leer datos de clientes del fichero.
	 */
	public void leerClientes() {
		
		File fichero = new File (FICHERO_CLIENTES);
		ObjectInputStream entrada;
		
		try {
			entrada = new ObjectInputStream(new FileInputStream(fichero));
			try {
				while(true) {
					Cliente cliente = (Cliente) entrada.readObject();
					clientes.put(cliente.getDni(), cliente);
				}
			}catch (EOFException eo) {
				entrada.close();
				System.out.println("Fichero de clientes leído correctamente.");
				Cliente.aumentarUltimoIdentificador(calcularUltimoIdentificador());
			}catch (ClassNotFoundException e) {
				System.out.println("ERROR: No se encuentra la clase que hay que leer");
			}catch(IOException e) {
				System.out.println("ERROR: Error inesperado de Entrada/Salida");
			}
		}catch (IOException e) {
			System.out.println("ERROR: no se puede abrir el fichero de clientes");
		}	
	}
	
	/**
	 * Método para escribir datos de clientes en un fichero
	 */
	public void escribirClientes() {
		File fichero = new File(FICHERO_CLIENTES);
		
		try {
			ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(fichero));
			for (Cliente cliente : clientes.values()) {
				salida.writeObject(cliente);
			}
			salida.close();
			System.out.println("Fichero de clientes escrito correctamente");
		}catch(FileNotFoundException e) {
			System.out.println("ERROR: no se puede crear el fichero clientes");
		}catch (IOException e) {
			System.out.println("ERROR: Error inesperado de Entrada/Salida");
		}
	}
		
	/**
	 * Método para añadir clientes a la lista ordenada
	 * empleando los métodos disponibles de Map
	 * @param cliente es el cliente a añadir
	 */
	public void anadirCliente (Cliente cliente) {
		if (clientes.containsKey(cliente.getDni())) {
			throw new ExcepcionAlquilerVehiculos("Ya existe un cliente con ese DNI.");
		}else {
			clientes.put(cliente.getDni(), cliente);
		}
	}
	
		
	/**
	 * Método que elimina un cliente de la lista de clientes.
	 * @param dni para buscar el cliente a borrar.
	 */
	public void borrarCliente (String dni) {
		if(clientes.containsKey(dni)) {
			clientes.remove(dni);
		}else {
			throw new ExcepcionAlquilerVehiculos("El cliente a borrar no existe.");
		}
	}
	
	/**
	 * Método para buscar un cliente de una lista ordena
	 * @param dni
	 * @return el cliente si hay coincidecia o null si no existe
	 */
	public Cliente buscarCliente (String dni) {
		if(clientes.containsKey(dni)) {
			return new Cliente(clientes.get(dni));
		}else {
			throw new ExcepcionAlquilerVehiculos ("El cliente introducido no existe.");
		}
	}
	
	/**
	 * Asigna el id a los clientes.
	 * @return el identificador asignado alúltimo cliente
	 */
	private int calcularUltimoIdentificador() {
		int ultimoIdentificador = 0;
				
		for (Cliente cliente : clientes.values()) {
			if(cliente.getIdentificador() > ultimoIdentificador) {
				ultimoIdentificador = cliente.getIdentificador();
			}
		}
		
		return ultimoIdentificador;
	}

}
