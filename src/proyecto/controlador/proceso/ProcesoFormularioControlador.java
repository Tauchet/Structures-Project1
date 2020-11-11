package proyecto.controlador.proceso;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import proyecto.Aplicacion;
import proyecto.modelo.Proceso;
import proyecto.vistas.TipoDeVista;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProcesoFormularioControlador implements Initializable {

    @FXML
    private Label titulo;

    @FXML
    private HBox opciones;

    @FXML
    private Button botonAgregar;

    @FXML
    private Button botonEliminar;

    @FXML
    private TextField campoId;

    @FXML
    private TextField campoNombre;

    // Datos temporales.
    private boolean generarAutoId;
    private Proceso seleccionado;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void escucharCambioDeCampoId(KeyEvent event) {
        generarAutoId = false;
    }

    @FXML
    public void escucharCambioDeCampoNombre(KeyEvent event) {
        if (this.generarAutoId && this.seleccionado == null) {
            this.campoId.setText(this.campoNombre.getText().replaceAll("[\\s]+", "-").replace(" ", "-").toLowerCase());
        }
    }

    @FXML
    void confirmar(ActionEvent event) {

        String valorId = campoId.getText();
        String valorNombre = campoNombre.getText();

        Proceso resultado = this.seleccionado;
        try {
            if (this.seleccionado != null) {

                // Actualizar datos.
                Aplicacion.getInstance().getContenedor().modificarProceso(this.seleccionado, valorNombre);

            } else {

                // Nuevo proceso
                resultado = Aplicacion.getInstance().getContenedor().agregarProceso(valorId, valorNombre);

            }
        } catch (Throwable ex) {
            Aplicacion.getInstance().abrirDialogoError(ex.getMessage());
            return;
        }

        Aplicacion.getInstance().modificado();
        Aplicacion.getInstance().getInterfaz().abrir(TipoDeVista.PROCESO, resultado);

    }

    @FXML
    void eliminar(ActionEvent event) {
        Aplicacion.getInstance().eliminarProcesoView(this.seleccionado);
    }

    @FXML
    void cancelar(ActionEvent event) {
        if (this.seleccionado != null) {

            // Mostrar lista de actividades, del proceso
            Aplicacion.getInstance().getInterfaz().abrir(TipoDeVista.PROCESO, this.seleccionado);

        } else {

            // LLevarte al origen de un proceso.
            Aplicacion.getInstance().getInterfaz().abrir(TipoDeVista.GENERAL, null);

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

    public TextField getCampoId() {
        return campoId;
    }

    public TextField getCampoNombre() {
        return campoNombre;
    }

    public void setGenerarAutoId(boolean generarAutoId) {
        this.generarAutoId = generarAutoId;
    }

    public void setSeleccionado(Proceso seleccionado) {
        this.seleccionado = seleccionado;
    }

}
