package proyecto;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import proyecto.controlador.*;
import proyecto.modelo.Actividad;
import proyecto.modelo.Contenedor;
import proyecto.modelo.Proceso;
import proyecto.modelo.Tarea;
import proyecto.modelo.excepcion.ActividadExistenteExcepcion;
import proyecto.modelo.excepcion.CampoVacioExcepcion;
import proyecto.modelo.excepcion.ProcesoYaExistenteException;
import proyecto.modelo.excepcion.TareasOpcionalesSeguidasExcepcion;
import proyecto.vistas.TipoDeVista;

import java.io.File;
import java.io.IOException;

public class Aplicacion extends Application {

	private final Interfaz interfaz;

	public static Aplicacion instance;

	private Stage escenarioPrincipal;
	private Contenedor contenedor;

	public Aplicacion() {
		instance = this;
		this.interfaz = new Interfaz(this);
	}

	public boolean cargarContenedor() throws TareasOpcionalesSeguidasExcepcion, ActividadExistenteExcepcion, ProcesoYaExistenteException, CampoVacioExcepcion, IOException {

		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("Archivo de Procesos", "*.json")
		);
		File archivoSeleccionado = fileChooser.showOpenDialog(Aplicacion.getInstance().getEscenarioPrincipal());
		if (archivoSeleccionado == null) {
			Aplicacion.getInstance().abrirDialogoError("¡No se ha seleccionado algún archivo!");
			return false;
		}

		this.contenedor = Contenedor.cargar(archivoSeleccionado);
		return true;

	}

	public boolean validarContenedor() {
		try {
			if (Aplicacion.getInstance().getContenedor() != null && Aplicacion.getInstance().getContenedor().isModificado()) {

				int resultado;
				do {
					resultado = Aplicacion.getInstance().abrirDialogoGuardarCambios();
					if (resultado == 1) {
						if (Aplicacion.getInstance().guardarContenedor()) {
							resultado = -1;
						}
					}
				} while (resultado == 1);

				if (resultado == 0) {
					return false;
				}

			}
		} catch (Throwable ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean guardarContenedor() {
		if (this.contenedor.isModificado()) {
			try {

				if (this.contenedor.getArchivo() == null) {

					FileChooser fileChooser = new FileChooser();
					fileChooser.getExtensionFilters().addAll(
							new FileChooser.ExtensionFilter("Archivo de Procesos", "*.json")
					);
					File archivoSeleccionado = fileChooser.showSaveDialog(Aplicacion.getInstance().getEscenarioPrincipal());
					if (archivoSeleccionado == null) {
						Aplicacion.getInstance().abrirDialogoError("¡No se ha seleccionado algún archivo para guardar el contenedor!");
						return false;
					}
					this.contenedor.setArchivo(archivoSeleccionado);

				}

				this.contenedor.guardar(this.contenedor.getArchivo());

			} catch (Throwable ex) {

				Aplicacion.getInstance().abrirDialogoError("¡No se ha podido guardar el archivo correctamente!");

				ex.printStackTrace();
				return false;
			}
		}
		return true;
	}

	public void eliminarProcesoView(Proceso seleccionado) {
		boolean confirmacion = false;
		try {
			confirmacion = Aplicacion.getInstance().abrirDialogoConfirmacion(FontAwesomeIcon.TRASH, "Eliminación del Proceso", seleccionado.getNombre(), "¿Estás seguro de eliminar el proceso " + seleccionado.getNombre() + "?");
		} catch (IOException e) {
			Aplicacion.getInstance().abrirDialogoError("¡Ha ocurrido un error al abrir el dialogo!");
		}
		if (confirmacion) {
			try {
				this.contenedor.eliminarProceso(seleccionado);
				Aplicacion.getInstance().modificado();
			} catch (Throwable ex) {
				Aplicacion.getInstance().abrirDialogoError("¡No se ha hecho de manera correcta el eliminado!");
				ex.printStackTrace();
				return;
			}
			getInterfaz().abrir(TipoDeVista.GENERAL, null);
		}
	}

	public void eliminarActividadView(Actividad seleccionado) {
		boolean confirmacion = false;
		try {
			confirmacion = Aplicacion.getInstance().abrirDialogoConfirmacion(FontAwesomeIcon.TRASH, "Eliminación de la Actividad", seleccionado.getNombre(), "¿Estás seguro de eliminar la actividad " + seleccionado.getNombre() + "?");
		} catch (IOException e) {
			Aplicacion.getInstance().abrirDialogoError("¡Ha ocurrido un error, al intentar abrir el dialogo!");
			e.printStackTrace();
		}
		if (confirmacion) {
			try {
				seleccionado.getProceso().eliminarActividad(seleccionado);
				Aplicacion.getInstance().modificado();
			} catch (Throwable ex) {
				Aplicacion.getInstance().abrirDialogoError("¡No se ha hecho de manera correcta, seguramente!");
				ex.printStackTrace();
				return;
			}
			getInterfaz().abrir(TipoDeVista.PROCESO, seleccionado.getProceso());
		}
	}

	public void eliminarTareaView(Tarea seleccionado) {
		boolean confirmacion = false;
		try {
			confirmacion = Aplicacion.getInstance().abrirDialogoConfirmacion(FontAwesomeIcon.TRASH, "Eliminación de la Tarea", null, "¿Estás seguro de eliminar esta tarea?");
		} catch (IOException e) {
			Aplicacion.getInstance().abrirDialogoError("¡No se ha hecho de manera correcta, seguramente!");
			e.printStackTrace();
		}
		if (confirmacion) {
			try {
				seleccionado.getActividad().eliminarTarea(seleccionado);
				Aplicacion.getInstance().modificado();
			} catch (CampoVacioExcepcion | TareasOpcionalesSeguidasExcepcion campoVacioExcepcion) {
				Aplicacion.getInstance().abrirDialogoError(campoVacioExcepcion.getMessage());
			}
			getInterfaz().abrir(TipoDeVista.ACTIVIDAD, seleccionado.getActividad());
		}
	}

	public ResultadoIntercambioActividad abrirDialogoIntercambiarActividad(Actividad actividad) throws IOException {

		if (actividad.getProceso().getActividades().getLongitud() < 2) {
			// No hay ninguna otra actividad.
			return null;
		}

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/proyecto/vistas/DialogoIntercambiarActividad.fxml"));
		BorderPane ventana = loader.load();

		DialogoIntercambioActividad controlador = loader.getController();
		controlador.getActividad().setText(actividad.getNombre());
		for (Actividad siguienteActividad: actividad.getProceso().getActividades()) {
			if (siguienteActividad != actividad) controlador.getCampoActividad().getItems().add(siguienteActividad);
		}
		controlador.getCampoActividad().getSelectionModel().selectFirst();

		Stage escenario = new Stage();
		escenario.setTitle("Dialogo de Intercambio de Actividades");
		escenario.initModality(Modality.WINDOW_MODAL);
		escenario.initOwner(this.escenarioPrincipal);
		escenario.setResizable(false);

		Scene escena = new Scene(ventana);
		escenario.setScene(escena);
		escenario.showAndWait();

		// Se canceló la solución
		if (!controlador.isResultado()) {
			return null;
		}

		return new ResultadoIntercambioActividad(controlador.getCampoActividad().getSelectionModel().getSelectedItem(), controlador.getCampoTareas().isSelected());
	}

	public boolean abrirDialogoConfirmacion(FontAwesomeIcon icon, String titulo, String subTitulo, String texto) throws IOException {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("vistas/DialogoConfirmacion.fxml"));
		BorderPane ventana = loader.load();

		DialogoConfirmacionControlador controlador = loader.getController();
		if (icon != null) controlador.getIcono().setIcon(icon);
		controlador.getTitulo().setText(titulo);
		controlador.getDescripcion().setText(texto);

		if (subTitulo != null) {
			controlador.getSubTitulo().setText(subTitulo);
		} else controlador.getContenedorTitulo().getChildren().remove(controlador.getSubTitulo());

		Stage escenario = new Stage();
		escenario.setTitle("Dialogo de Confirmación");
		escenario.initModality(Modality.WINDOW_MODAL);
		escenario.initOwner(this.escenarioPrincipal);
		escenario.setResizable(false);

		Scene escena = new Scene(ventana);
		escenario.setScene(escena);
		escenario.showAndWait();

		return controlador.getResultado();
	}

	public void abrirDialogoError(String texto){

		FXMLLoader loader = new FXMLLoader(getClass().getResource("vistas/DialogoError.fxml"));
		BorderPane ventana = null;
		try {
			ventana = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		DialogoErrorControlador controlador = loader.getController();
		controlador.getDescripcion().setText(texto);

		Stage escenario = new Stage();
		escenario.setTitle("Dialogo de Error");
		escenario.initModality(Modality.WINDOW_MODAL);
		escenario.initOwner(this.escenarioPrincipal);
		escenario.setResizable(false);

		Scene escena = new Scene(ventana);
		escenario.setScene(escena);
		escenario.showAndWait();

	}

	public int abrirDialogoGuardarCambios() throws IOException {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("vistas/DialogoGuardarCambios.fxml"));
		BorderPane ventana = loader.load();

		DialogoGuardarCambiosControlador controlador = loader.getController();
		Stage escenario = new Stage();
		escenario.setTitle("Dialogo de Confirmación");
		escenario.initModality(Modality.WINDOW_MODAL);
		escenario.initOwner(this.escenarioPrincipal);
		escenario.setResizable(false);

		Scene escena = new Scene(ventana);
		escenario.setScene(escena);
		escenario.showAndWait();

		return controlador.getResultado();
	}

	@Override
	public void start(Stage stage) throws Exception {

		this.escenarioPrincipal = stage;

		FXMLLoader loader = new FXMLLoader(getClass().getResource("vistas/BaseVista.fxml"));
		BorderPane viewGeneral = loader.load();
		this.interfaz.setViewGeneral(viewGeneral);

		Scene scene = new Scene(viewGeneral);
		scene.getStylesheets().add(this.getClass().getResource("/estilos.css").toExternalForm());
		stage.setScene(scene);
		stage.show();

	}

	@Override
	public void stop() throws Exception {
		validarContenedor();
		super.stop();
	}

	public void modificado() {
		if (this.contenedor != null) {
			this.contenedor.setModificado(true);
		}
	}

	public Stage getEscenarioPrincipal() {
		return escenarioPrincipal;
	}

	public static void main(String[] args) {
		launch();
	}

	public static Aplicacion getInstance() {
		return instance;
	}

	public Contenedor getContenedor() {
		return contenedor;
	}

	public void setContenedor(Contenedor contenedor) {
		this.contenedor = contenedor;
	}

	public Interfaz getInterfaz() {
		return interfaz;
	}



}
