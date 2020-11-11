package proyecto.vistas.actividad;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import proyecto.componentes.tarea.TareaComponente;
import proyecto.controlador.actividad.ActividadControlador;
import proyecto.modelo.Actividad;
import proyecto.modelo.Tarea;
import proyecto.vistas.Vista;
import proyecto.vistas.tarea.TareaFormularioVista;

import java.io.IOException;

public class ActividadVista extends Vista<Actividad> {

    private ActividadControlador controlador;
    private Actividad seleccionada;

    @Override
    public void cargar() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ActividadVista.fxml"));
        this.componente = loader.load();
        this.controlador = loader.getController();
    }

    @Override
    public void abrir(Vista<?> anteriorVista, Actividad seleccionada) throws IOException {

        this.seleccionada = seleccionada;

        this.controlador.getNombre().setText(seleccionada.getNombre());
        this.controlador.getDescripcion().setText(seleccionada.getDescripcion());
        this.controlador.getDuracion().setText((seleccionada.getMinimaDuracion() == seleccionada.getMaximaDuracion() ? "" : "De ") + seleccionada.getMinimaDuracion() + (seleccionada.getMinimaDuracion() == seleccionada.getMaximaDuracion() ? ""  : " a " + seleccionada.getMaximaDuracion()) + " minutos");
        this.controlador.setSeleccionado(seleccionada);

        int posicion = 1;
        for (Tarea tarea: seleccionada.getTareas()) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/proyecto/componentes/tarea/TareaComponente.fxml"));
            TareaComponente componenteTarea = new TareaComponente(tarea);
            loader.setController(componenteTarea);
            BorderPane elemento = loader.load();
            componenteTarea.setElemento(elemento);
            elemento.getProperties().put("CONTROLADOR", componenteTarea);

            componenteTarea.getPosicion().setText("" + posicion);
            if (tarea.isOpcional()) componenteTarea.getPosicion().setGraphic(null);

            componenteTarea.getNombre().setText(tarea.getDescripcion());
            componenteTarea.getDuracion().setText(tarea.getDuracionEnMinutos() + " minuto" + (tarea.getDuracionEnMinutos() != 1 ? "s" : ""));

            this.controlador.getLista().getChildren().add(elemento);
            posicion++;

        }

    }

    @Override
    public void cerrar(Vista<?> siguienteVista) {
        if (siguienteVista instanceof TareaFormularioVista) {
            ((TareaFormularioVista) siguienteVista).setActividad(this.seleccionada);
        }
        this.controlador.getLista().getChildren().clear();
        this.seleccionada = null;
    }
}
