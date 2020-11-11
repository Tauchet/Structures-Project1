package proyecto.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import proyecto.Aplicacion;
import proyecto.modelo.Contenedor;
import proyecto.modelo.Proceso;
import proyecto.modelo.estructura.ListaEnlazadaSimple;
import proyecto.modelo.excepcion.CampoVacioExcepcion;
import proyecto.modelo.excepcion.NingunaSugerenciaExcepcion;
import proyecto.vistas.TipoDeVista;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class PrincipalControlador extends BusquedaControlador<Proceso> implements Initializable {

    @FXML
    private Label codigo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void agregarProceso(ActionEvent event) {
        Aplicacion.getInstance().getInterfaz().abrir(TipoDeVista.PROCESO_FORMULARIO, null);
    }

    @FXML
    void guardar(ActionEvent event) {
        Aplicacion.getInstance().guardarContenedor();
    }

    @Override
    public ListaEnlazadaSimple<Proceso> buscarSugerencias(String busqueda) throws NingunaSugerenciaExcepcion, CampoVacioExcepcion {
        return Aplicacion.getInstance().getContenedor().buscarProcesosSugeridos(busqueda);
    }

    public Label getCodigo() {
        return codigo;
    }

}
