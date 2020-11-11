package proyecto.vistas;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import proyecto.Aplicacion;
import proyecto.componentes.proceso.ProcesoComponente;
import proyecto.controlador.PrincipalControlador;
import proyecto.modelo.Proceso;

import java.io.IOException;

public class PrincipalVista extends Vista<Object> {

    private PrincipalControlador controlador;

    @Override
    public void cargar() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/proyecto/vistas/PrincipalVista.fxml"));
        this.componente = loader.load();
        this.controlador = loader.getController();
    }

    @Override
    public void abrir(Vista<?> anteriorVista, Object valor) throws IOException {
        this.controlador.getCodigo().setText(Aplicacion.getInstance().getContenedor().getCodigo());
        for (Proceso proceso: Aplicacion.getInstance().getContenedor().getProcesos()) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/proyecto/componentes/proceso/ProcesoComponente.fxml"));
            ProcesoComponente controlador = new ProcesoComponente(proceso);
            loader.setController(controlador);
            BorderPane ventana = loader.load();
            controlador.setElemento(ventana);
            ventana.getProperties().put("CONTROLADOR", controlador);

            controlador.getNombre().setText(proceso.getNombre());
            controlador.getId().setText(proceso.getId());
            controlador.getCantidadActividades().setText("" + proceso.getCantidadDeActividades());
            controlador.getDuracion().setText((proceso.getMinimaDuracion() == proceso.getMaximaDuracion() ? "" : "De ") + proceso.getMinimaDuracion() + (proceso.getMinimaDuracion() == proceso.getMaximaDuracion() ? ""  : " a " + proceso.getMaximaDuracion()) + " minutos");

            this.controlador.getLista().getChildren().add(ventana);
        }

    }

    @Override
    public void cerrar(Vista<?> siguienteVista) {
        this.controlador.getLista().getChildren().clear();
    }

}
