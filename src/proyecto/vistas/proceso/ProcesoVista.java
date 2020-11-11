package proyecto.vistas.proceso;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import proyecto.componentes.actividad.ActividadComponente;
import proyecto.controlador.proceso.ProcesoControlador;
import proyecto.modelo.Actividad;
import proyecto.modelo.Proceso;
import proyecto.vistas.Vista;
import proyecto.vistas.actividad.ActividadFormularioVista;

import java.io.IOException;

public class ProcesoVista extends Vista<Proceso> {

    private ProcesoControlador controlador;
    private Proceso seleccionado;

    @Override
    public void cargar() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ProcesoVista.fxml"));
        this.componente = loader.load();
        this.controlador = loader.getController();
    }

    @Override
    public void abrir(Vista<?> anteriorVista, Proceso proceso) throws IOException {
        this.controlador.getId().setText(proceso.getId());
        this.controlador.getNombre().setText(proceso.getNombre());
        this.controlador.getDuracion().setText((proceso.getMinimaDuracion() == proceso.getMaximaDuracion() ? "" : "De ") + proceso.getMinimaDuracion() + (proceso.getMinimaDuracion() == proceso.getMaximaDuracion() ? ""  : " a " + proceso.getMaximaDuracion()) + " minutos");
        this.controlador.setSeleccionado(proceso);
        this.seleccionado = proceso;
        cargarActividades();
    }

    @Override
    public void cerrar(Vista<?> siguienteVista) {
        if (siguienteVista instanceof ActividadFormularioVista) {
            ((ActividadFormularioVista) siguienteVista).setProceso(this.seleccionado);
        }
        this.seleccionado = null;
        this.controlador.getLista().getChildren().clear();
    }

    @Override
    public void recargar() throws IOException {
        this.controlador.getLista().getChildren().clear();
        cargarActividades();
    }

    private void cargarActividades() throws IOException {
        for (Actividad actividad: this.seleccionado.getActividades()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/proyecto/componentes/actividad/ActividadComponente.fxml"));
            ActividadComponente componenteActividad = new ActividadComponente(actividad);
            loader.setController(componenteActividad);
            BorderPane elemento = loader.load();
            componenteActividad.setElemento(elemento);
            elemento.getProperties().put("CONTROLADOR", componenteActividad);
            componenteActividad.getNombre().setText(actividad.getNombre());
            componenteActividad.getDescripcion().setText(actividad.getDescripcion());
            componenteActividad.getTareas().setText(actividad.getCantidadDeTareas() + "");
            componenteActividad.getDuracion().setText((actividad.getMinimaDuracion() == actividad.getMaximaDuracion() ? "" : "De ") + actividad.getMinimaDuracion() + (actividad.getMinimaDuracion() == actividad.getMaximaDuracion() ? ""  : " a " + actividad.getMaximaDuracion()) + " minutos");
            if (!actividad.isOpcional()) componenteActividad.getNombreIcono().setVisible(true);
            this.controlador.getLista().getChildren().add(elemento);
        }
    }

}
