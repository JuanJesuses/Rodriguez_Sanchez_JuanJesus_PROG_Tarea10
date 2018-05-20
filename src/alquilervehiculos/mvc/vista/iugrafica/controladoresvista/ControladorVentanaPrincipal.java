package alquilervehiculos.mvc.vista.iugrafica.controladoresvista;

import java.awt.Toolkit;
import java.awt.font.ImageGraphicAttribute;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.swing.text.TabableView;

import org.omg.PortableServer.ServantRetentionPolicyValue;

import alquilervehiculos.mvc.modelo.dao.Clientes;
import alquilervehiculos.mvc.modelo.dao.Vehiculos;
import alquilervehiculos.mvc.modelo.dominio.Alquiler;
import alquilervehiculos.mvc.modelo.dominio.Cliente;
import alquilervehiculos.mvc.modelo.dominio.DireccionPostal;
import alquilervehiculos.mvc.modelo.dominio.ExcepcionAlquilerVehiculos;
import alquilervehiculos.mvc.modelo.dominio.vehiculo.DatosTecnicosVehiculos;
import alquilervehiculos.mvc.modelo.dominio.vehiculo.TipoVehiculo;
import alquilervehiculos.mvc.modelo.dominio.vehiculo.Vehiculo;
import alquilervehiculos.mvc.vista.iugrafica.IUGrafica;
import alquilervehiculos.mvc.vista.iugrafica.utilidades.Dialogos;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ControladorVentanaPrincipal {

	// ----------CLIENTES-------//

	// Botones pestaña Cliente - Alta Clientes -- Buscar/eliminar Clientes
	@FXML
	private Button btAceptarCliente, btCancelarCliente, btAceptarBuscarCliente, btCancelarBuscarCliente;

	// Campos de texto pestaña Cliente - Alta Clientes
	@FXML
	private TextField tfNombre, tfDni, tfDireccion, tfLocalidad, tfCodigoPostal, tfBuscarCliente;

	// Campos de texto GridPane buscarCliente
	@FXML
	private TextField tfNBuscarCliente, tfDBuscarCliente, tfDrBuscarCliente, tfLBuscarCliente, tfCpBuscarCliente;

	// Grupo para agrupar los radio botones de la pestaña Clientes - Buscar/Eliminar
	// Clientes
	@FXML
	private ToggleGroup tgClientes;

	// Radio botones de la pestaña Clientes - Buscar/Eliminar Clientes
	@FXML
	private RadioButton rbBuscarCliente, rbEliminarCliente;

	// VBox donde se agrupan los radio botones del toggleGroup tgClientes
	@FXML
	private VBox vbBuscarCliente;

	// Tabla pestaña Clientes - Listado de Clientes
	@FXML
	private TableView<Cliente> tClientes;
	@FXML
	private TableColumn<Cliente, String> tcNombre, tcDni, tcDireccion, tcLocalidad, tcCodigoPostal;

	private ObservableList<Cliente> clientes = FXCollections.observableArrayList();

	private Cliente cliente = null;

	// ------- VEHÍCULOS---------//

	@FXML
	private Button btAceptarVehiculo, btCancelarVehiculo, btAceptarBuscarVehiculo, btCancelarBuscarVehiculo;

	@FXML
	private RadioButton rbTurismo, rbDeCarga, rbAutobus, rbBuscarVehiculo, rbEliminarVehiculo;

	@FXML
	private VBox vbBuscarVehiculo;

	@FXML
	private ToggleGroup tgVehiculos, tgBuscarVehiculos;

	@FXML
	private TextField tFMatricula, tFMarca, tFModelo, tFCilindrada, tFNumeroP, tFPma;

	// Campos de texto para mostrar vehículo en pestaña Vehículo - Buscar/Eliminar
	// Vehículos
	@FXML
	private TextField tfBuscarMatricula, tfTipoVehiculo, tfMatriculaBuscarVehiculo, tfMarcaBuscarVehiculo,
			tfModeloBuscarVehiculo, tfCilindradaBuscarVehiculo, tfNumPlazasBuscarVehiculo, tfPmaBuscarVehiculo;

	@FXML
	TableView<Vehiculo> tVehiculos;

	@FXML
	TableColumn<Vehiculo, String> tcMatricula, tcMarca, tcModelo;
	@FXML
	TableColumn<Vehiculo, Number> tcCilindrada, tcNPlazas, tcPma;
	@FXML
	TableColumn<Vehiculo, TipoVehiculo> tcTipoVehiculo;

	private ObservableList<Vehiculo> vehiculos = FXCollections.observableArrayList();

	private Vehiculo vehiculo = null;

	// ----------ALQUILERES----------//

	@FXML
	private TextField tfDniAlquiler, tfMatriculaAlquiler;

	@FXML
	private Button btIniciarAlquiler, btCerrarAlquiler, btMostrarAlquileres;

	@FXML
	private RadioButton rbListadoAlquileres, rbListadoAlquileresAbiertos;

	@FXML
	private ToggleGroup tgAlquileres;

	@FXML
	TableView<Alquiler> tAlquileresInicio;

	@FXML
	private TableColumn<Alquiler, Number> tcDias;
	@FXML
	private TableColumn<Alquiler, Double> tcPrecio;
	@FXML
	private TableColumn<Alquiler, String> tcClienteAlquiler, tcVehiculoAlquiler, tcFecha;

	private ObservableList<Alquiler> alquileres = FXCollections.observableArrayList();

	private ObservableList<Alquiler> alquileresAbiertos = FXCollections.observableArrayList();

	private Alquiler alquiler = null;

	private final SimpleDateFormat FORMATO_FECHA = new SimpleDateFormat("dd/MM/yyyy HH:mm");

	public Cliente getCliente() {
		DireccionPostal direccion = new DireccionPostal(tfDrBuscarCliente.getText(), tfLBuscarCliente.getText(),
				tfCpBuscarCliente.getText());
		return new Cliente(tfNBuscarCliente.getText(), tfDBuscarCliente.getText(), direccion);
	}

	public void setCliente(Cliente cliente) {
		if (cliente != null) {
			tfNBuscarCliente.setText(cliente.getNombre());
			tfDBuscarCliente.setText(cliente.getDni());
			tfDrBuscarCliente.setText(cliente.getDireccionPostal().getCalle());
			tfLBuscarCliente.setText(cliente.getDireccionPostal().getLocalidad());
			tfCpBuscarCliente.setText(cliente.getDireccionPostal().getCodigoPostal());
		} else {
			tfNBuscarCliente.setText("");
			tfDBuscarCliente.setText("");
			tfDrBuscarCliente.setText("");
			tfLBuscarCliente.setText("");
			tfCpBuscarCliente.setText("");
		}
	}

	public void setVehiculo(Vehiculo vehiculo) {
		if (vehiculo != null) {
			tfTipoVehiculo.setText(vehiculo.getTipoVehiculo().toString());
			tfMatriculaBuscarVehiculo.setText(vehiculo.getMatricula());
			tfMarcaBuscarVehiculo.setText(vehiculo.getMarca());
			tfModeloBuscarVehiculo.setText(vehiculo.getModelo());
			tfCilindradaBuscarVehiculo.setUserData(vehiculo.getDatosTecnicosVehiculo().getCilindrada());
			tfNumPlazasBuscarVehiculo.setUserData(vehiculo.getDatosTecnicosVehiculo().getNumeroPlazas());
			tfPmaBuscarVehiculo.setUserData(vehiculo.getDatosTecnicosVehiculo().getPma());
		} else {
			tfTipoVehiculo.setText("");
			tfMatriculaBuscarVehiculo.setText("");
			tfMarcaBuscarVehiculo.setText("");
			tfModeloBuscarVehiculo.setText("");
			tfCilindradaBuscarVehiculo.setText("");
			tfNumPlazasBuscarVehiculo.setText("");
			tfPmaBuscarVehiculo.setText("");
		}

	}

	public void setAlquiler(Alquiler alquiler) {
		if (alquiler != null) {
			tcFecha.setText(FORMATO_FECHA.format(alquiler.getFecha()));
			tcDias.setUserData(alquiler.getDias());
			tcPrecio.setUserData(alquiler.getPrecio());
			tcClienteAlquiler.setUserData(alquiler.getCliente().getNombre());
			tcVehiculoAlquiler.setUserData(alquiler.getVehiculo().getMatricula());
		} else {
			tcFecha.setText("");
			tcDias.setText("");
			tcPrecio.setText("");
			tcClienteAlquiler.setText("");
			tcVehiculoAlquiler.setText("");
		}
	}

	@FXML
	private void initialize() {

		// ----------INITIALIZE CLIENTES-----------//

		// Agrupar radio botones pestaña clientes
		tgClientes = new ToggleGroup();
		rbBuscarCliente.setToggleGroup(tgClientes);
		rbEliminarCliente.setToggleGroup(tgClientes);

		// Agrupar radio botones pestaña vehiculos para elegir el tipo de vehículo
		tgVehiculos = new ToggleGroup();
		rbTurismo.setToggleGroup(tgVehiculos);
		rbAutobus.setToggleGroup(tgVehiculos);
		rbDeCarga.setToggleGroup(tgVehiculos);

		// Agrupar radio botones pestaña vehículos para seleccionar buscar o eliminar
		// vehículo
		tgBuscarVehiculos = new ToggleGroup();
		rbBuscarVehiculo.setToggleGroup(tgBuscarVehiculos);
		rbEliminarVehiculo.setToggleGroup(tgBuscarVehiculos);

		// radio botón pestaña Alquiler para seleccionar el listado de alquileres
		// abiertos
		rbListadoAlquileresAbiertos.setToggleGroup(tgAlquileres);

		// Tabla y columnas para la pestaña Clientes - Listado de Clientes
		tcNombre.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nombre"));
		tcDni.setCellValueFactory(new PropertyValueFactory<Cliente, String>("dni"));
		tcDireccion.setCellValueFactory(
				cliente -> new ReadOnlyStringWrapper(cliente.getValue().getDireccionPostal().getCalle()));
		tcLocalidad.setCellValueFactory(
				cliente -> new ReadOnlyStringWrapper(cliente.getValue().getDireccionPostal().getLocalidad()));
		tcCodigoPostal.setCellValueFactory(
				cliente -> new ReadOnlyStringWrapper(cliente.getValue().getDireccionPostal().getCodigoPostal()));

		clientes = FXCollections.observableArrayList(IUGrafica.controladorMVC.obtenerClientes());
		tClientes.setItems(clientes);
		tClientes.getSelectionModel().select(0);
		tClientes.getSortOrder().add(tcNombre);

		// ----------INITIALIZE VEHÍCULOS----------//

		// Tabla y columnas para la pestaña Vehículos - Listado de vehículos
		tcTipoVehiculo
				.setCellValueFactory(vehiculo -> new ReadOnlyObjectWrapper<>(vehiculo.getValue().getTipoVehiculo()));
		tcMatricula.setCellValueFactory(new PropertyValueFactory<Vehiculo, String>("matricula"));
		tcMarca.setCellValueFactory(new PropertyValueFactory<Vehiculo, String>("marca"));
		tcModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
		tcCilindrada.setCellValueFactory(
				vehiculo -> new ReadOnlyIntegerWrapper(vehiculo.getValue().getDatosTecnicosVehiculo().getCilindrada()));
		tcNPlazas.setCellValueFactory(vehiculo -> new ReadOnlyIntegerWrapper(
				vehiculo.getValue().getDatosTecnicosVehiculo().getNumeroPlazas()));
		tcPma.setCellValueFactory(
				vehiculo -> new ReadOnlyIntegerWrapper(vehiculo.getValue().getDatosTecnicosVehiculo().getPma()));

		vehiculos = FXCollections.observableArrayList(IUGrafica.controladorMVC.obtenerVehiculos());
		tVehiculos.setItems(vehiculos);
		tVehiculos.getSelectionModel().select(0);
		tVehiculos.getSortOrder().add(tcMatricula);

		// ----------INITIALIZE ALQUILERES---------//

		// Tabla y columnas para la pestaña Alquileres - Iniciar Alquileres
		tcFecha.setCellValueFactory(
				alquiler -> new ReadOnlyStringWrapper(FORMATO_FECHA.format(alquiler.getValue().getFecha())));
		tcDias.setCellValueFactory(new PropertyValueFactory<Alquiler, Number>("Dias"));
		tcPrecio.setCellValueFactory(new PropertyValueFactory<Alquiler, Double>("Precio"));
		tcClienteAlquiler.setCellValueFactory(
				alquiler -> new ReadOnlyObjectWrapper<>(alquiler.getValue().getCliente().getNombre()));
		tcVehiculoAlquiler.setCellValueFactory(
				alquiler -> new ReadOnlyObjectWrapper<>(alquiler.getValue().getVehiculo().getMatricula()));

		alquileres = FXCollections.observableArrayList(IUGrafica.controladorMVC.obtenerAlquileres());
		tAlquileresInicio.setItems(alquileres);
		tAlquileresInicio.getSelectionModel().select(0);
		tAlquileresInicio.getSortOrder().add(tcFecha);

	}

	// --------ACCIONES PESTAÑA CLIENTES---------//

	@FXML
	private void anadirCliente() {
		try {
			DireccionPostal direccionPostal = new DireccionPostal(tfDireccion.getText(), tfLocalidad.getText(),
					tfCodigoPostal.getText());
			Cliente cliente = new Cliente(tfNombre.getText(), tfDni.getText(), direccionPostal);
			IUGrafica.controladorMVC.anadirCliente(cliente);
			Dialogos.mostrarDialogoInformacion("Añadir Cliente", "Cliente añadido correctamente");
			tfNombre.setText("");
			tfDni.setText("");
			tfDireccion.setText("");
			tfLocalidad.setText("");
			tfCodigoPostal.setText("");
		} catch (ExcepcionAlquilerVehiculos e) {
			Dialogos.mostrarDialogoError("Añadir Cliente", e.getMessage());
		}
	}

	@FXML
	private void eliminarCliente() {
		Stage propietario = (Stage) tClientes.getScene().getWindow();
		tfBuscarCliente.getText();
		if (Dialogos.mostrarDialogoConfirmacion("Borrar Cliente", "¿Seguro que desea eliminar a este cliente?",
				propietario)) {
			IUGrafica.controladorMVC.borrarCliente(tfBuscarCliente.getText());
			clientes.remove(cliente);
			Dialogos.mostrarDialogoInformacion("Borrar Cliente", "Cliente Borrado Correctamente");
		}
	}

	private void mostrarCliente() {

		String dniCliente = tfBuscarCliente.getText();
		try {
			if (cliente.compruebaDni(dniCliente)) {
				Cliente cliente = IUGrafica.controladorMVC.buscarCliente(dniCliente);
				if (cliente != null) {
					setCliente(cliente);
				}
			}
		} catch (ExcepcionAlquilerVehiculos e) {
			Dialogos.mostrarDialogoError("Cliente no encontrado", "No existe ningún cliente con ese DNI");
		}
	}

	@FXML
	private void accionCliente() {

		// Si el radio button no está deshabilitado, llama a eliminar cliente
		if (rbEliminarCliente.isSelected()) {
			eliminarCliente();
		} else if (rbBuscarCliente.isSelected()) {
			mostrarCliente();
		}
		tfBuscarCliente.setText("");
	}

	@FXML
	private void compruebaNombre(KeyEvent e) {
		String texto = tfNombre.getText();
		int longitud = texto.length();
		if (longitud > 50) {
			e.consume();
			Toolkit.getDefaultToolkit().beep();
		}

		if (!e.getCharacter().matches("[A-Za-z]") && !e.getCharacter().matches(" ")
				&& !Character.isISOControl(e.getCharacter().charAt(0))) {
			e.consume();
			Toolkit.getDefaultToolkit().beep();
		}
	}

	@FXML
	private void cancelarCliente() {
		vbBuscarCliente.setDisable(true);
		tfBuscarCliente.setText("");
	}

	@FXML
	private void rbBuscarClienteSeleccionado() {
		vbBuscarCliente.setDisable(false);
	}

	@FXML
	private void rbEliminarClienteSeleccionado() {
		vbBuscarCliente.setDisable(false);
		rbEliminarCliente.setDisable(false);
	}

	// -------ACCIONES PESTAÑA VEHÍCULOS--------//

	@FXML
	private void anadirVehiculo(ActionEvent event) throws IOException {
		if (event.getSource() == btAceptarVehiculo) {
			Vehiculo vehiculo = null;
			if (rbTurismo.isSelected()) {
				try {
					vehiculo = TipoVehiculo.TURISMO.getInstancia(tFMatricula.getText(), tFMarca.getText(),
							tFModelo.getText(), new DatosTecnicosVehiculos(Integer.parseInt(tFCilindrada.getText()),
									Integer.parseInt(tFNumeroP.getText()), Integer.parseInt(tFPma.getText())));
					IUGrafica.controladorMVC.anadirVehiculo(vehiculo, TipoVehiculo.TURISMO);
					Dialogos.mostrarDialogoInformacion("Vehículos", "Vehículo añadido");
				} catch (ExcepcionAlquilerVehiculos e) {
					Dialogos.mostrarDialogoError("Vehículos", e.getMessage());
				}
			} else if (rbAutobus.isSelected()) {
				try {
					vehiculo = TipoVehiculo.AUTOBUS.getInstancia(tFMatricula.getText(), tFMarca.getText(),
							tFModelo.getText(), new DatosTecnicosVehiculos(Integer.parseInt(tFCilindrada.getText()),
									Integer.parseInt(tFNumeroP.getText()), Integer.parseInt(tFPma.getText())));
					IUGrafica.controladorMVC.anadirVehiculo(vehiculo, TipoVehiculo.AUTOBUS);
					Dialogos.mostrarDialogoInformacion("Vehículos", "Vehículo añadido");
				} catch (ExcepcionAlquilerVehiculos e) {
					Dialogos.mostrarDialogoError("Vehículos", e.getMessage());
					;
				}

			} else if (rbDeCarga.isSelected()) {
				try {
					vehiculo = TipoVehiculo.DE_CARGA.getInstancia(tFMatricula.getText(), tFMarca.getText(),
							tFModelo.getText(), new DatosTecnicosVehiculos(Integer.parseInt(tFCilindrada.getText()),
									Integer.parseInt(tFNumeroP.getText()), Integer.parseInt(tFPma.getText())));
					IUGrafica.controladorMVC.anadirVehiculo(vehiculo, TipoVehiculo.DE_CARGA);
					Dialogos.mostrarDialogoInformacion("Vehículos", "Vehículo añadido");
				} catch (ExcepcionAlquilerVehiculos e) {
					Dialogos.mostrarDialogoError("Vehículos", e.getMessage());
				}
			} else {
				Dialogos.mostrarDialogoError("Vehículo", "Debe seleccionar un tipo de vehículo");
			}

		}

	}

	@FXML
	private void eliminarVehiculo() {
		Stage propietario = (Stage) tVehiculos.getScene().getWindow();
		tfBuscarMatricula.getText();
		if (Dialogos.mostrarDialogoConfirmacion("Borrar Vehículo", "¿Seguro que desea eliminar este vehículo?",
				propietario)) {
			IUGrafica.controladorMVC.borrarVehiculo(tfBuscarMatricula.getText());
			vehiculos.remove(vehiculo);
			Dialogos.mostrarDialogoInformacion("Borrar Vehículo", "Vehículo eliminado correctamente");
		}
	}

	private void mostrarVehiculo() {
		String matricula = tfBuscarMatricula.getText();
		try {
			if (vehiculo.compruebaMatricula(matricula)) {
				Vehiculo vehiculo = IUGrafica.controladorMVC.buscarVehiculo(matricula);
				if (vehiculo != null) {
					setVehiculo(vehiculo);
				}
			}
		} catch (ExcepcionAlquilerVehiculos e) {
			Dialogos.mostrarDialogoError("Vehículo no encontrado",
					"No existe ningún vehículo con la matrícula introducida.");
		}
	} 

	@FXML
	private void accionVehiculo() {

		// Si el radio button no está deshabilitado, llama a eliminar vehiculo
		if (rbEliminarVehiculo.isSelected()) {
			eliminarVehiculo();
		} else if (rbBuscarVehiculo.isSelected()) {
			mostrarVehiculo();
		}
		tfBuscarMatricula.setText("");
	}

	@FXML
	private void cancelarVehiculo() {
		vbBuscarVehiculo.setDisable(true);
		tfBuscarMatricula.setText("");
	}

	@FXML
	private void rbBuscarVehiculoSeleccionado() {
		vbBuscarVehiculo.setDisable(false);
	}

	@FXML
	private void rbEliminarVehiculoSeleccionado() {
		vbBuscarVehiculo.setDisable(false);
		rbEliminarVehiculo.setDisable(false);
	}

	// ---------ACCIONES PESTAÑA ALQUILERES------------//

	@FXML
	private void iniciarAlquiler(ActionEvent evento) throws IOException {
		if (evento.getSource() == btIniciarAlquiler) {
			try {
				String dniIniciar = tfDniAlquiler.getText();
				String matriculaIniciar = tfMatriculaAlquiler.getText();

				Cliente cliente = IUGrafica.controladorMVC.buscarCliente(dniIniciar);
				Vehiculo vehiculo = IUGrafica.controladorMVC.buscarVehiculo(matriculaIniciar);
				IUGrafica.controladorMVC.abrirAlquiler(cliente, vehiculo);

				Dialogos.mostrarDialogoInformacion("Iniciar Alquiler", "Alquiler iniciado correctamente");
				tfDniAlquiler.setText("");
				tfMatriculaAlquiler.setText("");

			} catch (ExcepcionAlquilerVehiculos e) {
				Dialogos.mostrarDialogoError("Iniciar alquiler", e.getMessage());
			}
		}
	}

	@FXML
	private void cerrarAlquiler(ActionEvent evento) throws IOException {
		if (evento.getSource() == btCerrarAlquiler) {
			try {
				String dniCerrar = tfDniAlquiler.getText();
				String matriculaCerrar = tfMatriculaAlquiler.getText();

				Cliente cliente = IUGrafica.controladorMVC.buscarCliente(dniCerrar);
				Vehiculo vehiculo = IUGrafica.controladorMVC.buscarVehiculo(matriculaCerrar);
				IUGrafica.controladorMVC.cerrarAlquiler(cliente, vehiculo);

				Dialogos.mostrarDialogoInformacion("Cerrar Alquiler", "Alquiler cerrado correctamente");
				tfDniAlquiler.setText("");
				tfMatriculaAlquiler.setText("");

			} catch (ExcepcionAlquilerVehiculos e) {
				Dialogos.mostrarDialogoError("Cerrar Alquiler", e.getMessage());
			}
		}
	}

	@FXML
	private void accionAlquiler() {
		if (rbListadoAlquileresAbiertos.isSelected()) {
			alquileresAbiertos = FXCollections
					.observableArrayList(IUGrafica.controladorMVC.obtenerAlquileresAbiertos());
			tAlquileresInicio.setItems(alquileresAbiertos);
			tAlquileresInicio.getSelectionModel().select(0);
			tAlquileresInicio.getSortOrder().add(tcFecha);
		} else {
			alquileres = FXCollections.observableArrayList(IUGrafica.controladorMVC.obtenerAlquileres());
			tAlquileresInicio.setItems(alquileres);
			tAlquileresInicio.getSelectionModel().select(0);
			tAlquileresInicio.getSortOrder().add(tcFecha);
		}
	}

}
