package test.proyecto;

import org.junit.Test;
import proyecto.modelo.Actividad;
import proyecto.modelo.Contenedor;
import proyecto.modelo.Proceso;
import proyecto.modelo.Tarea;
import proyecto.modelo.excepcion.*;

import static org.junit.Assert.assertEquals;

public class ActividadTest {

    private static final Contenedor contenedor = new Contenedor("gg", null);

    @Test
    public void agregarTarea() throws ProcesoYaExistenteException, CampoVacioExcepcion, ActividadExistenteExcepcion, TareasOpcionalesSeguidasExcepcion {
        Proceso proceso = contenedor.agregarProceso("holi", "Holi");
        Actividad actividad = proceso.agregarActividad("holi", "Holi", "Una descripción", false, 0, null);
        actividad.agregarTarea("Hola una descripción", 10, false, 0);
    }

    @Test
    public void agregarTareaEnPosicionEspecifica() throws ProcesoYaExistenteException, CampoVacioExcepcion, ActividadExistenteExcepcion, TareasOpcionalesSeguidasExcepcion {
        Proceso proceso = contenedor.agregarProceso("holi2", "Holi2");
        Actividad actividad = proceso.agregarActividad("holi", "Holi", "Una descripción", false, 0, null);
        actividad.agregarTarea("Hola una descripción", 10, false, -1);
        actividad.agregarTarea("Una pentadescripción", 10, false, -1);
        Tarea busqueda = actividad.agregarTarea("Uan por 2", 10, false, 0);
        assertEquals(actividad.getTareas().getPrimerValor(), busqueda);
    }

}
