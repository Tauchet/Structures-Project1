package proyecto.componentes.tarea;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import proyecto.Aplicacion;
import proyecto.componentes.Componente;
import proyecto.modelo.Proceso;
import proyecto.modelo.Tarea;
import proyecto.vistas.TipoDeVista;

import java.net.URL;
import java.util.ResourceBundle;

public class TareaComponente extends Componente<Tarea> implements Initializable {

    @FXML
    private Label nombre;

    @FXML
    private Label duracion;

    @FXML
    private Label posicion;

    @FXML
    private FontAwesomeIconView posicionIcono;

    public TareaComponente(Tarea tarea) {
        super(tarea);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void abrir(MouseEvent event) {
        Aplicacion.getInstance().getInterfaz().abrir(TipoDeVista.TAREA_FORMULARIO, this.valor);
    }

    public Label getNombre() {
        return nombre;
    }

    public Label getDuracion() {
        return duracion;
    }

    public Label getPosicion() {
        return posicion;
    }

    public FontAwesomeIconView getPosicionIcono() {
        return posicionIcono;
    }

}
