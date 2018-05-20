package alquilervehiculos.mvc.modelo.dominio;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import alquilervehiculos.mvc.modelo.dominio.Cliente;
import alquilervehiculos.mvc.modelo.dominio.vehiculo.Vehiculo;

/**
 * Clase que gestiona los clientes y vehículos
 * implicados en un alquiler
 * @author john
 *
 */
public class Alquiler implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date fecha;
	private int dias;
	private final SimpleDateFormat FORMATO_FECHA = new SimpleDateFormat ("dd/MM/yyyy HH:mm");
	private final int MS_DIA = 1000*60*60*24;
	private final double PRECIO_DIA = 30.0;
	private Cliente cliente;
	private Vehiculo vehiculo;
		
	public Alquiler (Cliente cliente, Vehiculo vehiculo) {
		
		setCliente(cliente);
		setVehiculo(vehiculo);
		fecha = new Date();
		dias = 0;
		vehiculo.setDisponible(false);
		
	}
	
    public Alquiler(Cliente cliente, Vehiculo turismo, Date fecha, int dias) {
        this.cliente = cliente;
        this.vehiculo = turismo;
        this.fecha = fecha;
        this.dias = dias;
    }
	
	public Date getFecha() {
		return fecha;
	}

	public int getDias() {
		return dias;
	}
	
	private void setCliente(Cliente cliente) {
		if (cliente != null) {
			this.cliente = cliente;
		}else {
			throw new ExcepcionAlquilerVehiculos("Para iniciar el alquiler debe agregar un cliente.");
		}
	}
	public Cliente getCliente() {
		return cliente;
	}
	
	private void setVehiculo(Vehiculo vehiculo) {
		if (vehiculo != null) {
			this.vehiculo = vehiculo;
		}else {
			throw new ExcepcionAlquilerVehiculos("Para iniciar el alquiler debe agregar un vehículo.");
		}
	}

	public Vehiculo getVehiculo() {
		return vehiculo;
	}
	
	public double getPrecio() {
		return PRECIO_DIA * getDias() + vehiculo.getPrecioEspecifico();
	}
	
	/**
	 * Método que establece la fecha de entrega del vehículo,
	 * la cantidad de días que ha estado alquilado y pone
	 * el vehículo disponible para un nuevo alquiler
	 */
	public void close() {
		Date entrega = new Date(); //entrega es la fecha de devolución del vehículo
		dias = difDias(entrega, fecha); // dias son los días que el vehículo ha estado en alquiler
		vehiculo.setDisponible(true); //Establce el turismo a disponible
	}
	
	/**
	 * Método que calcula la diferencia entre dos fechas
	 * @param fechaRecogida
	 * @param fechaEntrega
	 * @return el número de días transcurrido entre esas fechas
	 */
	private int difDias(Date fechaRecogida, Date fechaEntrega) {
		long milisegundos = fechaRecogida.getTime() - fechaEntrega.getTime();
		long dias = milisegundos / MS_DIA;
		
		return (int) dias+1;
	}
	
	
	@Override
	public String toString() {
		return String.format("\n-::ALQUILER::- \nFecha: %s Días: %d Precio: %.2f \nCLIENTE: %s \n%s", 
								FORMATO_FECHA.format(getFecha()), dias, getPrecio(), getCliente(), getVehiculo());
	}
	
	public boolean equals (Object otro) {
		if (otro == null || !(otro instanceof Alquiler)) {
			return false;
		}
		if(otro == this) {
			return true;
		}
		return vehiculo.getMatricula().equals(((Alquiler) otro).getVehiculo().getMatricula());
	}
	
	public int HashCode() {
		return vehiculo.getMatricula().hashCode();
	}

}
