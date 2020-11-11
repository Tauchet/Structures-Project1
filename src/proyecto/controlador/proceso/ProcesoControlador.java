package proyecto.controlador.proceso;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import proyecto.Aplicacion;
import proyecto.controlador.BusquedaControlador;
import proyecto.modelo.Actividad;
import proyecto.modelo.Proceso;
import proyecto.modelo.estructura.ListaEnlazadaSimple;
import proyecto.modelo.excepcion.CampoVacioExcepcion;
import proyecto.modelo.excepcion.NingunaSugerenciaExcepcion;
import proyecto.vistas.TipoDeVista;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class ProcesoControlador extends BusquedaControlador<Actividad> implements Initializable {

    @FXML
    private Label id;

    @FXML
    private Label nombre;

    @FXML
    private Label duracion;

    // Temporales
    private Proceso seleccionado;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void agregarActividad(ActionEvent event) {
        Aplicacion.getInstance().getInterfaz().abrir(TipoDeVista.ACTIVIDAD_FORMULARIO, null);
    }

    @FXML
    public void modificarProceso(ActionEvent event) {
        Aplicacion.getInstance().getInterfaz().abrir(TipoDeVista.PROCESO_FORMULARIO, this.seleccionado);
    }

    @FXML
    public void eliminarProceso(ActionEvent event) {
        Aplicacion.getInstance().eliminarProcesoView(this.seleccionado);
    }

    @FXML
    public void regresarAtras(ActionEvent event) {
        Aplicacion.getInstance().getInterfaz().abrir(TipoDeVista.GENERAL, null);
    }

    @Override
    public ListaEnlazadaSimple<Actividad> buscarSugerencias(String busqueda) throws NingunaSugerenciaExcepcion, CampoVacioExcepcion {
        return this.seleccionado.buscarActividadesSugeridas(busqueda);
    }

    public Label getId() {
        return id;
    }

    public Label getNombre() {
        return nombre;
    }

    public Label getDuracion() {
        return duracion;
    }

    public void setSeleccionado(Proceso seleccionado) {
        this.seleccionado = seleccionado;
    }

}
