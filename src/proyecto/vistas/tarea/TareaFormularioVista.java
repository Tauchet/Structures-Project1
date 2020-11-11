package proyecto.vistas.tarea;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXMLLoader;
import proyecto.controlador.tarea.TareaFormularioControlador;
import proyecto.modelo.Actividad;
import proyecto.modelo.Tarea;
import proyecto.vistas.Vista;

import java.io.IOException;

public class TareaFormularioVista extends Vista<Tarea> {

    private TareaFormularioControlador controlador;
    private Tarea seleccionada;
    private Actividad actividad;

    @Override
    public void cargar() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TareaFormularioVista.fxml"));
        this.componente = loader.load();
        this.controlador = loader.getController();
        this.controlador.getCampos().getChildren().remove(this.controlador.getPosicionTarea());
        this.controlador.getCampoDondeAgregar().getItems().add("Como última tarea.");
        this.controlador.getCampoDondeAgregar().getItems().add("En una posición determinada.");
        this.controlador.getCampos().getChildren().remove(this.controlador.getDondeAgregarTarea());
    }

    @Override
    public void abrir(Vista<?> anteriorVista, Tarea seleccionada) {

        // Un nuevo proceso
        FontAwesomeIconView icon = (FontAwesomeIconView) this.controlador.getBotonAgregar().getGraphic();
        this.seleccionada = seleccionada;
        this.controlador.setSeleccionado(seleccionada);
        this.controlador.setActividadSeleccionada(seleccionada != null ? seleccionada.getActividad() : this.actividad);

        if (seleccionada == null) {

            // Nueva tarea
            this.controlador.getCampoDescripcion().setText("");
            this.controlador.getCampoDuracion().setText("");
            this.controlador.getOpciones().getChildren().remove(this.controlador.getBotonEliminar());
            this.controlador.getCampos().getChildren().add(this.controlador.getCampos().getChildren().size() - 1, this.controlador.getDondeAgregarTarea());
            this.controlador.getCampoDondeAgregar().getSelectionModel().selectFirst();
            this.controlador.getCampoOpcional().setSelected(false);

            this.controlador.getTitulo().setText("Crear Tarea");

        } else {

            // Modificación o eliminación de un tarea
            icon.setIcon(FontAwesomeIcon.CHECK);
            this.controlador.getBotonAgregar().setText("Guardar Cambios");

            // Cargamos la información
            this.controlador.getCampoOpcional().setSelected(seleccionada.isOpcional());
            this.controlador.getCampoDescripcion().setText(seleccionada.getDescripcion());
            this.controlador.getCampoDuracion().setText(seleccionada.getDuracionEnMinutos() + "");

            this.controlador.getTitulo().setText("Modificar Tarea");

        }

    }

    @Override
    public void cerrar(Vista<?> siguienteVista) {

        this.controlador.getCampos().getChildren().remove(this.controlador.getPosicionTarea());
        this.controlador.getCampos().getChildren().remove(this.controlador.getDondeAgregarTarea());

        if (this.seleccionada == null) {
            this.controlador.getOpciones().getChildren().add(1, this.controlador.getBotonEliminar());
        }

        this.actividad = null;

    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }

}
