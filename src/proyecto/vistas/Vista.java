package proyecto.vistas;

import javafx.scene.layout.Pane;

import java.io.IOException;

public abstract class Vista<T> {

    protected Pane componente;

    public abstract void cargar() throws IOException;
    public abstract void abrir(Vista<?> anteriorVista, T valor) throws IOException;
    public abstract void cerrar(Vista<?> siguienteVista);

    public void recargar() throws IOException {}

    public Pane getComponente() {
        return componente;
    }

}
