package proyecto.modelo.excepcion;

public class NingunaSugerenciaExcepcion extends ModeloExcepcion {

    public NingunaSugerenciaExcepcion(String busqueda) {
        super("¡No se ha encontrado algún elemento con la búsqueda " + busqueda + "!");
    }

}
