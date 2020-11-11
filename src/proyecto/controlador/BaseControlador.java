package proyecto.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import proyecto.Aplicacion;
import proyecto.modelo.Contenedor;
import proyecto.modelo.excepcion.ActividadExistenteExcepcion;
import proyecto.modelo.excepcion.CampoVacioExcepcion;
import proyecto.modelo.excepcion.ProcesoYaExistenteException;
import proyecto.modelo.excepcion.TareasOpcionalesSeguidasExcepcion;
import proyecto.vistas.BusquedaGeneralVista;
import proyecto.vistas.TipoDeVista;
import proyecto.vistas.Vista;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BaseControlador implements Initializable {

    @FXML
    private HBox menu;

    @FXML
    private Button botonInicio;

    @FXML
    private Button botonBusqueda;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void inicio(ActionEvent event) {
        if (!botonInicio.getStyleClass().contains("active")) {
            botonBusqueda.getStyleClass().remove("active");
            botonInicio.getStyleClass().add("active");
            Aplicacion.getInstance().getInterfaz().abrir(TipoDeVista.GENERAL, null);
        }
    }

    @FXML
    void busquedaAvanzada(ActionEvent event) {
        if (!botonBusqueda.getStyleClass().contains("active")) {
            botonBusqueda.getStyleClass().add("active");
            botonInicio.getStyleClass().remove("active");
            BusquedaGeneralVista vista = (BusquedaGeneralVista) Aplicacion.getInstance().getInterfaz().abrir(TipoDeVista.BUSQUEDA_GENERAL, null);
            vista.getControlador().setBaseControlador(this);
        }
    }

    @FXML
    void guardarContenedor(ActionEvent event) {
        Aplicacion.getInstance().guardarContenedor();
    }

    @FXML
    void nuevoContenedor(ActionEvent event) {
        if (Aplicacion.getInstance().validarContenedor()) {
            Aplicacion.getInstance().setContenedor(Contenedor.generar());
            Aplicacion.getInstance().getInterfaz().abrir(TipoDeVista.GENERAL, null);
            menu.setVisible(true);
        }
    }

    @FXML
    void cargarContenedor(ActionEvent event) {
        if (Aplicacion.getInstance().validarContenedor()) {
            try {
                if (Aplicacion.getInstance().cargarContenedor()) {
                    Aplicacion.getInstance().getInterfaz().abrir(TipoDeVista.GENERAL, null);
                    menu.setVisible(true);
                }
            } catch (TareasOpcionalesSeguidasExcepcion | ActividadExistenteExcepcion | ProcesoYaExistenteException | CampoVacioExcepcion | IOException exception) {
                exception.printStackTrace();
                Aplicacion.getInstance().abrirDialogoError("¡El estilo del archivo no es el correcto!");
            }
        }
    }

    public Button getBotonBusqueda() {
        return botonBusqueda;
    }

    public Button getBotonInicio() {
        return botonInicio;
    }

}
