package alquilervehiculos.mvc.modelo.dominio;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase que gestiona los clientes
 * @author john
 *
 */
public class Cliente implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nombre;
	private String dni;
	private DireccionPostal direccionPostal;
	private int identificador;
	private static int ultimoIdentificador = 0;
		
	
	/**
	 * Constructor con parámetros que cambia el estado del objeto 
	 * a través de los métodos set
	 * @param nombre
	 * @param dni
	 * @param direccionPostal
	 */
	public Cliente (String nombre, String dni, DireccionPostal direccionPostal) {
		
		setNombre(nombre);
		setDni(dni);
		setDireccionPostal(direccionPostal);
		asignarNuevoIdentificador();
		
	}
	
	/**
	 * Constructor copia
	 * @param cliente
	 */
	public Cliente(Cliente cliente) {
		nombre = cliente.getNombre();
		dni = cliente.getDni();
		direccionPostal = cliente.getDireccionPostal();
		identificador = cliente.getIdentificador();
	}
	
	private void asignarNuevoIdentificador() {
		ultimoIdentificador++;
		identificador = ultimoIdentificador;
	}
	
	
	public static void aumentarUltimoIdentificador(int cantidad) {
		if(cantidad >= 0)
			ultimoIdentificador += cantidad;
		else
			throw new ExcepcionAlquilerVehiculos("Sólo se puede aumentar el último identificador");
		
	}
	/**
	 * Método que comprueba que el patrón del DNI
	 * introducido por teclado, es correcto
	 * @param dni
	 * @return
	 */
	public static boolean compruebaDni(String dni) {
		Pattern patron = Pattern.compile("[0-9]{8}[A-Z]");
		Matcher emparejador = patron.matcher(dni);
		
		return emparejador.matches();
	}
	
	private void setNombre(String nombre) {
		if(nombre != null && !nombre.equals("")) {
			this.nombre = nombre;
		}else {
			throw new ExcepcionAlquilerVehiculos("El campo nombre no puede estar vacío.");
		}
	}	

	public String getNombre() {
		return nombre;
	}
	
	private void setDni(String dni) {
		if (compruebaDni(dni)) {
			this.dni = dni;
		}else {
			throw new ExcepcionAlquilerVehiculos("El DNI introducido no es válido.");
		}
	}

	public String getDni() {
		return dni;
	}

	private void setDireccionPostal(DireccionPostal direccionPostal) {
		this.direccionPostal = new DireccionPostal(direccionPostal);
	}
	
	public DireccionPostal getDireccionPostal() {
		return new DireccionPostal(direccionPostal);
	}

	public int getIdentificador() {
		return identificador;
	}
	
	@Override
	public String toString() {
		return String.format("Identificador: %d Nombre: %s DNI: %s %s %n", 
				identificador, nombre, dni, direccionPostal);
	}
	
	@Override
	public boolean equals(Object otro) {
		if(otro == null || !(otro instanceof Cliente)) {
			return false;
		}
		
		if(otro == this) {
			return true;
		}
		
		return (dni.equals(((Cliente)otro).getDni()));
	}
	
	@Override
	public int hashCode() {
		return dni.hashCode();
	}
}
