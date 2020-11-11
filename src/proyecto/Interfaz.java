package proyecto;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import proyecto.controlador.BusquedaGeneralControlador;
import proyecto.vistas.BusquedaGeneralVista;
import proyecto.vistas.TipoDeVista;
import proyecto.vistas.Vista;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Interfaz {

    private Stage stage;
    private Scene scene;

    private BorderPane viewGeneral;

    private final Map<TipoDeVista, Vista<?>> vistasCache;
    private Vista<?> vistaActual;

    public Interfaz(Aplicacion aplicacion) {
        this.vistasCache = new HashMap<>();
    }

    public Vista abrir(TipoDeVista tipo, Object valor) {

        Vista vista = this.vistasCache.getOrDefault(tipo, null);
        if (vista == null) {
            try {
                vista = tipo.generar();
                vista.cargar();
                this.vistasCache.put(tipo, vista);
            } catch (Throwable ex) {
                Aplicacion.getInstance().abrirDialogoError("¡La vista que se intenta cargar, no está disponible!");
                ex.printStackTrace();
                return null;
            }
        }

        try {
            if (this.vistaActual != null) {
                this.vistaActual.cerrar(vista);
            }
            vista.abrir(this.vistaActual, valor);
        } catch (Throwable ex) {
            Aplicacion.getInstance().abrirDialogoError("¡Ha ocurrido un error al intentar moverse de vista!");
            ex.printStackTrace();
            return null;
        }

        this.vistaActual = vista;
        viewGeneral.setCenter(vista.getComponente());

        return vista;
    }

    public void recargar() throws IOException {
        if (this.vistaActual != null) {
            this.vistaActual.recargar();
        }
    }

    public void setViewGeneral(BorderPane viewGeneral) {
        this.viewGeneral = viewGeneral;
    }

}
