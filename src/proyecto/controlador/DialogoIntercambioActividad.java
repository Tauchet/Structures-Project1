package proyecto.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import proyecto.modelo.Actividad;

public class DialogoIntercambioActividad {

    @FXML
    private Label actividad;

    @FXML
    private ComboBox<Actividad> campoActividad;

    @FXML
    private CheckBox campoTareas;

    // Seleccionado
    private boolean resultado;

    @FXML
    void confirmar(ActionEvent event) {
        this.resultado = true;
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void cancelar(ActionEvent event) {
        this.resultado = false;
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public Label getActividad() {
        return actividad;
    }

    public ComboBox<Actividad> getCampoActividad() {
        return campoActividad;
    }

    public CheckBox getCampoTareas() {
        return campoTareas;
    }

    public boolean isResultado() {
        return resultado;
    }

    public void setResultado(boolean resultado) {
        this.resultado = resultado;
    }

}
