package proyecto.controlador.actividad;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import proyecto.Aplicacion;
import proyecto.modelo.Actividad;
import proyecto.modelo.Proceso;
import proyecto.vistas.TipoDeVista;

import java.net.URL;
import java.util.ResourceBundle;

public class ActividadFormularioControlador implements Initializable {

    @FXML
    private Label titulo;

    @FXML
    private VBox campos;

    @FXML
    private TextField campoCodigo;

    @FXML
    private TextField campoNombre;

    @FXML
    private TextArea campoDescripcion;

    @FXML
    private ComboBox<String> campoDondeAgregar;

    @FXML
    private BorderPane siguienteActividad;

    @FXML
    private BorderPane ultimaActividad;

    @FXML
    private Label nombreUltimaActividad;

    @FXML
    private ComboBox<Actividad> campoActividadSiguiente;

    @FXML
    private HBox opciones;

    @FXML
    private Button botonAgregar;

    @FXML
    private Button botonEliminar;

    @FXML
    private BorderPane dondeAgregarActividad;

    @FXML
    private CheckBox campoOpcional;

    // Datos temporales.
    private boolean generarAutoId;
    private Proceso procesoSeleccionado;
    private Actividad seleccionado;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }



    @FXML
    public void escucharCambioDeCampoCodigo(KeyEvent event) {
        generarAutoId = false;
    }

    @FXML
    public void escucharCambioDeCampoNombre(KeyEvent event) {
        if (this.generarAutoId && this.seleccionado == null) {
            this.campoCodigo.setText(this.campoNombre.getText().replaceAll("[\\s]+", "-").replace(" ", "-").toLowerCase());
        }
    }

    @FXML
    void confirmar(ActionEvent event) {

        String valorCodigo = campoCodigo.getText();
        String valorNombre = campoNombre.getText();
        String valorDescripcion = campoDescripcion.getText();

        try {
            if (this.seleccionado != null) {

                // Actualizar datos.
                this.procesoSeleccionado.modificarActividad(this.seleccionado, valorNombre, valorDescripcion, campoOpcional.isSelected());

            } else {

                // Nuevo proceso
                this.procesoSeleccionado.agregarActividad(valorCodigo, valorNombre, valorDescripcion,
                        campoOpcional.isSelected(),
                        this.campoDondeAgregar.getSelectionModel().getSelectedIndex(),
                        this.campoActividadSiguiente.getSelectionModel().getSelectedItem());

            }

        } catch (Throwable ex) {
            Aplicacion.getInstance().abrirDialogoError(ex.getMessage());
            return;
        }

        Aplicacion.getInstance().modificado();
        Aplicacion.getInstance().getInterfaz().abrir(TipoDeVista.PROCESO, this.procesoSeleccionado);

    }

    @FXML
    void eliminar(ActionEvent event) {
        Aplicacion.getInstance().eliminarActividadView(this.seleccionado);
    }

    @FXML
    void cancelar(ActionEvent event) {
        if (this.seleccionado == null) {
            // Nuevo registro
            Aplicacion.getInstance().getInterfaz().abrir(TipoDeVista.PROCESO, this.procesoSeleccionado);
        } else {
            // Intento de modificación de un registro
            Aplicacion.getInstance().getInterfaz().abrir(TipoDeVista.ACTIVIDAD, this.seleccionado);
        }
    }

    @FXML
    void cambiarDondeAgregar(ActionEvent event) {
        int index = this.campoDondeAgregar.getSelectionModel().getSelectedIndex();

        if (index == 0) {
            this.campos.getChildren().remove(this.siguienteActividad);
            this.campos.getChildren().remove(this.ultimaActividad);
            this.campos.getChildren().remove(this.dondeAgregarActividad);
        } else if (index == 1) {

            if (!this.campos.getChildren().contains(this.siguienteActividad)) {
                this.campos.getChildren().add(this.campos.getChildren().size() - 1, this.siguienteActividad);
            }

            if (!this.campos.getChildren().contains(this.dondeAgregarActividad)) {
                this.campos.getChildren().add(this.campos.getChildren().size() - 1, this.dondeAgregarActividad);
            }

            this.campos.getChildren().remove(this.ultimaActividad);
        } else {

            if (!this.campos.getChildren().contains(this.ultimaActividad)) {
                this.campos.getChildren().add(this.campos.getChildren().size() - 1, this.ultimaActividad);
            }

            this.campos.getChildren().remove(this.siguienteActividad);
            this.campos.getChildren().remove(this.dondeAgregarActividad);

        }

    }

    public Label getTitulo() {
        return titulo;
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

    public VBox getCampos() {
        return campos;
    }

    public TextField getCampoCodigo() {
        return campoCodigo;
    }

    public TextField getCampoNombre() {
        return campoNombre;
    }

    public TextArea getCampoDescripcion() {
        return campoDescripcion;
    }

    public ComboBox<String> getCampoDondeAgregar() {
        return campoDondeAgregar;
    }

    public ComboBox<Actividad> getCampoActividadSiguiente() {
        return campoActividadSiguiente;
    }

    public BorderPane getSiguienteActividad() {
        return siguienteActividad;
    }

    public BorderPane getUltimaActividad() {
        return ultimaActividad;
    }

    public BorderPane getDondeAgregarActividad() {
        return dondeAgregarActividad;
    }

    public Label getNombreUltimaActividad() {
        return nombreUltimaActividad;
    }

    public void setGenerarAutoId(boolean generarAutoId) {
        this.generarAutoId = generarAutoId;
    }

    public void setProcesoSeleccionado(Proceso procesoSeleccionado) {
        this.procesoSeleccionado = procesoSeleccionado;
    }

    public void setSeleccionado(Actividad seleccionado) {
        this.seleccionado = seleccionado;
    }

    public CheckBox getCampoOpcional() {
        return campoOpcional;
    }

}
