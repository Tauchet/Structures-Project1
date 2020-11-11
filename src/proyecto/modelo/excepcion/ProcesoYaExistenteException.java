package proyecto.modelo.excepcion;

public class ProcesoYaExistenteException extends ModeloExcepcion {

    public ProcesoYaExistenteException() {
        super("¡Ya existe un proceso con esta misma id!");
    }

}
