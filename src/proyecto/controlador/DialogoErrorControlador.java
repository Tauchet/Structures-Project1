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

public class DialogoErrorControlador implements Initializable {

    @FXML
    private Label descripcion;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void confirmar(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public Label getDescripcion() {
        return descripcion;
    }

}
