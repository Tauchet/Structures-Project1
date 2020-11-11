package proyecto.modelo.excepcion;

public class ActividadExistenteExcepcion extends ModeloExcepcion {

    public ActividadExistenteExcepcion(String campo) {
        super("¡Ya existe una actividad con este mismo " + campo + "!");
    }

}
