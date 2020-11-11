package proyecto.controlador.actividad;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import proyecto.Aplicacion;
import proyecto.controlador.BusquedaControlador;
import proyecto.modelo.Actividad;
import proyecto.modelo.Tarea;
import proyecto.modelo.estructura.ListaEnlazadaSimple;
import proyecto.modelo.excepcion.CampoVacioExcepcion;
import proyecto.modelo.excepcion.NingunaSugerenciaExcepcion;
import proyecto.vistas.TipoDeVista;

import java.net.URL;
import java.util.ResourceBundle;

public class ActividadControlador extends BusquedaControlador<Tarea> implements Initializable {

    @FXML
    private Label nombre;

    @FXML
    private Label descripcion;

    @FXML
    private Label duracion;

    // Temporales
    private Actividad seleccionado;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void agregarTarea(ActionEvent event) {
        Aplicacion.getInstance().getInterfaz().abrir(TipoDeVista.TAREA_FORMULARIO, null);
    }

    @FXML
    void eliminarActividad(ActionEvent event) {
        Aplicacion.getInstance().eliminarActividadView(this.seleccionado);
    }

    @FXML
    void modificarActividad(ActionEvent event) {
        Aplicacion.getInstance().getInterfaz().abrir(TipoDeVista.ACTIVIDAD_FORMULARIO, this.seleccionado);
    }

    @FXML
    void regresarAtras(ActionEvent event) {
        Aplicacion.getInstance().getInterfaz().abrir(TipoDeVista.PROCESO, this.seleccionado.getProceso());
    }

    @Override
    public ListaEnlazadaSimple<Tarea> buscarSugerencias(String busqueda) throws NingunaSugerenciaExcepcion, CampoVacioExcepcion {
        return this.seleccionado.buscarTareasSugeridas(busqueda);
    }

    public Label getNombre() {
        return nombre;
    }

    public Label getDescripcion() {
        return descripcion;
    }

    public Label getDuracion() {
        return duracion;
    }

    public void setSeleccionado(Actividad seleccionado) {
        this.seleccionado = seleccionado;
    }

}
