package proyecto.vistas;

import javafx.fxml.FXMLLoader;
import proyecto.controlador.BusquedaGeneralControlador;

import java.io.IOException;

public class BusquedaGeneralVista extends Vista<Object> {

    private BusquedaGeneralControlador controlador;

    @Override
    public void cargar() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/proyecto/vistas/BusquedaGeneralVista.fxml"));
        this.componente = loader.load();
        this.controlador = loader.getController();
    }

    @Override
    public void abrir(Vista<?> anteriorVista, Object valor) throws IOException {

    }

    @Override
    public void cerrar(Vista<?> siguienteVista) {
        this.controlador.getLista().getChildren().clear();
    }

    public BusquedaGeneralControlador getControlador() {
        return controlador;
    }

}
