package proyecto.vistas.proceso;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXMLLoader;
import proyecto.controlador.proceso.ProcesoFormularioControlador;
import proyecto.modelo.Proceso;
import proyecto.vistas.Vista;

import java.io.IOException;

public class ProcesoFormularioVista extends Vista<Proceso> {

    private ProcesoFormularioControlador controlador;
    private Proceso seleccionado;

    @Override
    public void cargar() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ProcesoFormularioVista.fxml"));
        this.componente = loader.load();
        this.controlador = loader.getController();
    }

    @Override
    public void abrir(Vista<?> anteriorVista, Proceso seleccionado) {

        // Un nuevo proceso
        FontAwesomeIconView icon = (FontAwesomeIconView) this.controlador.getBotonAgregar().getGraphic();
        this.controlador.setSeleccionado(seleccionado);
        this.seleccionado = seleccionado;

        if (seleccionado == null) {

            // Nuevo proceso
            this.controlador.setGenerarAutoId(true);
            this.controlador.getOpciones().getChildren().remove(this.controlador.getBotonEliminar());
            this.controlador.getCampoId().setDisable(false);
            this.controlador.getCampoId().setText("");
            this.controlador.getCampoNombre().setText("");
            this.controlador.getTitulo().setText("Nuevo Proceso");

        } else {

            // Modificación o eliminación de un proceso
            this.controlador.getTitulo().setText("Modificar Proceso");
            icon.setIcon(FontAwesomeIcon.CHECK);
            this.controlador.getCampoId().setDisable(true);
            this.controlador.getBotonAgregar().setText("Guardar Cambios");

            // Cargamos la información
            this.controlador.getCampoId().setText(seleccionado.getId());
            this.controlador.getCampoNombre().setText(seleccionado.getNombre());

        }

    }

    @Override
    public void cerrar(Vista<?> siguienteVista) {

        if (this.seleccionado == null) {
            this.controlador.getOpciones().getChildren().add(1, this.controlador.getBotonEliminar());
        }

        this.seleccionado = null;
    }

}
