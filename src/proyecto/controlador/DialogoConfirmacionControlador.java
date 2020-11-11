package proyecto.controlador;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class DialogoConfirmacionControlador implements Initializable {

    @FXML
    private VBox contenedorTitulo;

    @FXML
    private Label titulo;

    @FXML
    private Label subTitulo;

    @FXML
    private Label descripcion;

    @FXML
    private FontAwesomeIconView icono;

    private boolean resultado;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void confirmar(ActionEvent event) {
        this.resultado = true;
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    public void cancelar(ActionEvent event) {
        this.resultado = false;
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public VBox getContenedorTitulo() {
        return contenedorTitulo;
    }

    public Label getTitulo() {
        return titulo;
    }

    public Label getSubTitulo() {
        return subTitulo;
    }

    public Label getDescripcion() {
        return descripcion;
    }

    public FontAwesomeIconView getIcono() {
        return icono;
    }

    public boolean getResultado() {
        return resultado;
    }

}
