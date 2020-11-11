package proyecto.modelo.excepcion;

public class ProcesoNoEncontradoExcepcion extends ModeloExcepcion {


    public ProcesoNoEncontradoExcepcion(String proceso) {
        super("¡El proceso " + proceso + " no se ha logrado encontrar!");
    }

}
