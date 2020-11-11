package proyecto.componentes.actividad;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import proyecto.Aplicacion;
import proyecto.componentes.Componente;
import proyecto.controlador.ResultadoIntercambioActividad;
import proyecto.modelo.Actividad;
import proyecto.modelo.Proceso;
import proyecto.modelo.excepcion.CampoVacioExcepcion;
import proyecto.vistas.TipoDeVista;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ActividadComponente extends Componente<Actividad> implements Initializable {

    @FXML
    private Label nombre;

    @FXML
    private Label descripcion;

    @FXML
    private Label tareas;

    @FXML
    private Label duracion;

    @FXML
    private FontAwesomeIconView nombreIcono;

    public ActividadComponente(Actividad actividad) {
        super(actividad);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void abrir(MouseEvent event) {
        Aplicacion.getInstance().getInterfaz().abrir(TipoDeVista.ACTIVIDAD, this.valor);
    }

    @FXML
    void intercambiar(ActionEvent event) {
        ResultadoIntercambioActividad resultado;
        try {
            resultado = Aplicacion.getInstance().abrirDialogoIntercambiarActividad(this.valor);
            if (resultado != null) {
                this.valor.intercambiar(resultado.getActividadSeleccionada(), resultado.isIntercambiarTareas());
                Aplicacion.getInstance().getInterfaz().recargar();
            }
        } catch (IOException | CampoVacioExcepcion e) {
            Aplicacion.getInstance().abrirDialogoError("¡No se ha hecho de manera correcta el intercambio, seguramente!");
            e.printStackTrace();
        }
    }

    public Label getNombre() {
        return nombre;
    }

    public Label getDescripcion() {
        return descripcion;
    }

    public Label getTareas() {
        return tareas;
    }

    public Label getDuracion() {
        return duracion;
    }

    public FontAwesomeIconView getNombreIcono() {
        return nombreIcono;
    }

}
