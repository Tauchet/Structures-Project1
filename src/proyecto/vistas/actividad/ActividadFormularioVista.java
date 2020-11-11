package proyecto.vistas.actividad;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXMLLoader;
import proyecto.controlador.actividad.ActividadFormularioControlador;
import proyecto.modelo.Actividad;
import proyecto.modelo.Proceso;
import proyecto.vistas.Vista;

import java.io.IOException;

public class ActividadFormularioVista extends Vista<Actividad> {

    private ActividadFormularioControlador controlador;
    private Actividad seleccionada;
    private Proceso proceso;

    @Override
    public void cargar() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ActividadFormularioVista.fxml"));
        this.componente = loader.load();
        this.controlador = loader.getController();
        this.controlador.getCampos().getChildren().remove(this.controlador.getDondeAgregarActividad());
        this.controlador.getCampos().getChildren().remove(this.controlador.getSiguienteActividad());
        this.controlador.getCampos().getChildren().remove(this.controlador.getUltimaActividad());
    }

    @Override
    public void abrir(Vista<?> anteriorVista, Actividad seleccionada) {

        // Un nuevo proceso
        FontAwesomeIconView icon = (FontAwesomeIconView) this.controlador.getBotonAgregar().getGraphic();
        this.seleccionada = seleccionada;
        this.controlador.setProcesoSeleccionado(this.proceso);
        this.controlador.setSeleccionado(seleccionada);

        if (this.proceso.getUltimaActividadCreada() != null) this.controlador.getNombreUltimaActividad().setText(this.proceso.getUltimaActividadCreada().getNombre());

        if (seleccionada == null) {

            // Nueva actividad
            this.controlador.setGenerarAutoId(true);
            this.controlador.getCampoCodigo().setDisable(false);
            this.controlador.getCampoOpcional().setSelected(false);
            this.controlador.getCampoCodigo().setText("");
            this.controlador.getCampoNombre().setText("");
            this.controlador.getCampoDescripcion().setText("");
            this.controlador.getCampoDondeAgregar().getItems().clear();
            this.controlador.getCampoActividadSiguiente().getItems().clear();
            this.controlador.getOpciones().getChildren().remove(this.controlador.getBotonEliminar());

            // Mostrar actividades ya creadas.
            for (Actividad actividad: this.proceso.getActividades()) {
                this.controlador.getCampoActividadSiguiente().getItems().add(actividad);
            }
            this.controlador.getCampoActividadSiguiente().getSelectionModel().selectFirst();

            // Actividades
            this.controlador.getCampoDondeAgregar().getItems().add("Como última actividad.");
            if (this.proceso.getActividades().getLongitud() != 0) {
                this.controlador.getCampoDondeAgregar().getItems().add("Después de la actividad siguiente.");
                this.controlador.getCampoDondeAgregar().getItems().add("Después de la última actividad creada.");
            }

            this.controlador.getCampoDondeAgregar().getSelectionModel().selectFirst();
            this.controlador.getCampos().getChildren().add(this.controlador.getCampos().getChildren().size() - 1, this.controlador.getDondeAgregarActividad());
            this.controlador.getTitulo().setText("Nuevo Actividad");

        } else {

            // Modificación o eliminación de un proceso
            icon.setIcon(FontAwesomeIcon.CHECK);
            this.controlador.getCampoCodigo().setDisable(true);
            this.controlador.getBotonAgregar().setText("Guardar Cambios");

            // Cargamos la información
            this.controlador.getCampoCodigo().setText(seleccionada.getCodigo());
            this.controlador.getCampoNombre().setText(seleccionada.getNombre());
            this.controlador.getCampoDescripcion().setText(seleccionada.getDescripcion());
            this.controlador.getCampoOpcional().setSelected(seleccionada.isOpcional());

            this.controlador.getTitulo().setText("Modificar Actividad");

        }

    }

    @Override
    public void cerrar(Vista<?> siguienteVista) {

        this.controlador.getCampos().getChildren().remove(this.controlador.getSiguienteActividad());
        this.controlador.getCampos().getChildren().remove(this.controlador.getUltimaActividad());
        this.controlador.getCampos().getChildren().remove(this.controlador.getDondeAgregarActividad());

        if (this.seleccionada == null) {
            this.controlador.getOpciones().getChildren().add(1, this.controlador.getBotonEliminar());
        }

        this.proceso = null;

    }

    public void setProceso(Proceso proceso) {
        this.proceso = proceso;
    }

}
