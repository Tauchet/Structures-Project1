package proyecto.modelo.excepcion;

public class NingunaSugerenciaExcepcion extends ModeloExcepcion {

    public NingunaSugerenciaExcepcion(String busqueda) {
        super("�No se ha encontrado alg�n elemento con la b�squeda " + busqueda + "!");
    }

}
