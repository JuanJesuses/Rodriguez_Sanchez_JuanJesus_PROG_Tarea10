package alquilervehiculos.mvc.vista.iugrafica;

import alquilervehiculos.mvc.controlador.IControladorAlquilerVehiculos;
import alquilervehiculos.mvc.vista.IVistaAlquilerVehiculos;
import alquilervehiculos.mvc.vista.iugrafica.utilidades.Dialogos;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class IUGrafica extends Application implements IVistaAlquilerVehiculos{
	
	public static IControladorAlquilerVehiculos controladorMVC;

	@Override
	public void start(Stage escenarioPrincipal) {
		try {
			SplitPane raiz = (SplitPane)FXMLLoader.load(
					getClass().getResource("/alquilervehiculos/mvc/vista/iugrafica/vistas/VentanaPrincipal.fxml"));
			Scene escena = new Scene(raiz);
			escenarioPrincipal.setOnCloseRequest(e -> confirmarSalida(escenarioPrincipal, e));
			escenarioPrincipal.setTitle("Alquiler de Vehículos");
			escenarioPrincipal.setScene(escena);
			escenarioPrincipal.setResizable(false);
			escenarioPrincipal.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void confirmarSalida(Stage escenarioPrincipal, WindowEvent e) {
		if(Dialogos.mostrarDialogoConfirmacion("Salir", "¿Estás seguro de que deseas salir?", escenarioPrincipal)) {
			controladorMVC.salir();
			escenarioPrincipal.close();
		}else {
			e.consume();
		}
	}

	/*public static void main(String[] args) {
		launch(args);
	}*/

	@Override
	public void setControlador(IControladorAlquilerVehiculos controlador) {
		IUGrafica.controladorMVC = controlador;
	}

	@Override
	public void comenzar() {
		launch(this.getClass());
	}

	
}