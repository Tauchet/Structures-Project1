package proyecto.componentes.proceso;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import proyecto.Aplicacion;
import proyecto.componentes.Componente;
import proyecto.modelo.Proceso;
import proyecto.vistas.TipoDeVista;

import java.net.URL;
import java.util.ResourceBundle;

public class ProcesoComponente extends Componente<Proceso> implements Initializable {

    @FXML
    private Label nombre;

    @FXML
    private Label id;

    @FXML
    private Label cantidadActividades;

    @FXML
    private Label duracion;

    public ProcesoComponente(Proceso proceso) {
        super(proceso);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void abrir(MouseEvent event) {
        Aplicacion.getInstance().getInterfaz().abrir(TipoDeVista.PROCESO, this.valor);
    }

    public Label getNombre() {
        return nombre;
    }

    public Label getId() {
        return id;
    }

    public Label getCantidadActividades() {
        return cantidadActividades;
    }

    public Label getDuracion() {
        return duracion;
    }


}
