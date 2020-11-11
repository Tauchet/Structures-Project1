package proyecto.controlador.tarea;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import proyecto.Aplicacion;
import proyecto.modelo.Actividad;
import proyecto.modelo.Tarea;
import proyecto.modelo.Validacion;
import proyecto.vistas.TipoDeVista;

import java.net.URL;
import java.util.ResourceBundle;

public class TareaFormularioControlador implements Initializable {

    @FXML
    private Label titulo;

    @FXML
    private HBox opciones;

    @FXML
    private Button botonAgregar;

    @FXML
    private Button botonEliminar;

    @FXML
    private VBox campos;

    @FXML
    private TextArea campoDescripcion;

    @FXML
    private TextField campoDuracion;

    @FXML
    private CheckBox campoOpcional;

    @FXML
    private BorderPane dondeAgregarTarea;

    @FXML
    private ComboBox<String> campoDondeAgregar;

    @FXML
    private BorderPane posicionTarea;

    @FXML
    private TextField campoPosicionTarea;

    // Datos temporales.
    private Actividad actividadSeleccionada;
    private Tarea seleccionado;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void confirmar(ActionEvent event) {

        String valorDescripcion = campoDescripcion.getText();
        String valorDuracion = campoDuracion.getText();
        boolean valorOpcional = campoOpcional.isSelected();

        try {

            int duracion = Validacion.entrarNumero("duracion", valorDuracion);
            Validacion.minimoValor("duracion", duracion, 1);

            int posision = -1;
            if (campoDondeAgregar.getSelectionModel().getSelectedIndex() != 0) {
                posision = Validacion.entrarNumero("posicion", campoPosicionTarea.getText());
                Validacion.minimoValor("duracion", posision, 0);
            }

            if (this.seleccionado != null) {

                // Actualizar datos.
                this.actividadSeleccionada.modificarTarea(this.seleccionado, valorDescripcion, duracion, valorOpcional);

            } else {

                // Nueva tarea
                this.actividadSeleccionada.agregarTarea(valorDescripcion, duracion, valorOpcional, posision);

            }

        } catch (Throwable ex) {
            Aplicacion.getInstance().abrirDialogoError(ex.getMessage());
            return;
        }

        Aplicacion.getInstance().modificado();
        Aplicacion.getInstance().getInterfaz().abrir(TipoDeVista.ACTIVIDAD, this.actividadSeleccionada);

    }

    @FXML
    void eliminar(ActionEvent event) {
        Aplicacion.getInstance().eliminarTareaView(this.seleccionado);
    }

    @FXML
    void cancelar(ActionEvent event) {
        if (this.seleccionado != null) {

            // Mostrar lista de actividades, del proceso
            Aplicacion.getInstance().getInterfaz().abrir(TipoDeVista.ACTIVIDAD, this.seleccionado.getActividad());

        } else {

            // LLevarte al origen de un proceso.
            Aplicacion.getInstance().getInterfaz().abrir(TipoDeVista.GENERAL, null);

        }
    }

    @FXML
    void cambiarDondeAgregar(ActionEvent event) {
        int index = this.campoDondeAgregar.getSelectionModel().getSelectedIndex();
        if (index == 0) {
            this.campos.getChildren().remove(this.posicionTarea);
        } else {
            this.campos.getChildren().add(this.campos.getChildren().size() - 1, this.posicionTarea);
        }
    }

    public HBox getOpciones() {
        return opciones;
    }

    public Button getBotonAgregar() {
        return botonAgregar;
    }

    public Button getBotonEliminar() {
        return botonEliminar;
    }

    public TextArea getCampoDescripcion() {
        return campoDescripcion;
    }

    public TextField getCampoDuracion() {
        return campoDuracion;
    }

    public CheckBox getCampoOpcional() {
        return campoOpcional;
    }

    public VBox getCampos() {
        return campos;
    }

    public ComboBox<String> getCampoDondeAgregar() {
        return campoDondeAgregar;
    }

    public BorderPane getPosicionTarea() {
        return posicionTarea;
    }

    public BorderPane getDondeAgregarTarea() {
        return dondeAgregarTarea;
    }

    public void setActividadSeleccionada(Actividad actividadSeleccionada) {
        this.actividadSeleccionada = actividadSeleccionada;
    }

    public Label getTitulo() {
        return titulo;
    }

    public void setSeleccionado(Tarea seleccionado) {
        this.seleccionado = seleccionado;
    }

}
