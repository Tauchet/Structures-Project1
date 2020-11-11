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

public class DialogoGuardarCambiosControlador implements Initializable {

    private int resultado;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void confirmar(ActionEvent event) {
        this.resultado = 1;
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    public void descartar(ActionEvent event) {
        this.resultado = 2;
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    public void cancelar(ActionEvent event) {
        this.resultado = 0;
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public int getResultado() {
        return resultado;
    }

}
