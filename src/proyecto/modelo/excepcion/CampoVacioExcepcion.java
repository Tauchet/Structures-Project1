package proyecto.modelo.excepcion;

public class CampoVacioExcepcion extends ModeloExcepcion {

    public CampoVacioExcepcion(String id) {
        super("¡El campo " + id + " se encuentra vacío!");
    }

}
