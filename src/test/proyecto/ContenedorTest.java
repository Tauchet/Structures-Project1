package test.proyecto;

import junit.framework.TestCase;
import org.junit.Test;
import proyecto.modelo.Actividad;
import proyecto.modelo.Contenedor;
import proyecto.modelo.Proceso;
import proyecto.modelo.Tarea;
import proyecto.modelo.excepcion.*;

import static org.junit.Assert.assertEquals;

public class ContenedorTest {

    private static final Contenedor contenedor = new Contenedor("gg", null);

    @Test
    public void agregarProceso() throws ProcesoYaExistenteException, CampoVacioExcepcion {
        contenedor.agregarProceso("holi", "Holi");
    }

    @Test (expected = CampoVacioExcepcion.class)
    public void agregarProcesoSinValores() throws ProcesoYaExistenteException, CampoVacioExcepcion {
        contenedor.agregarProceso(null, "Holi");
    }

    @Test (expected = ProcesoYaExistenteException.class)
    public void agregarProcesoConMismaId() throws ProcesoYaExistenteException, CampoVacioExcepcion {
        contenedor.agregarProceso("holi", "Holi22");
    }

    @Test (expected = NingunaSugerenciaExcepcion.class)
    public void busquedaDeProcesosSugeridas() throws NingunaSugerenciaExcepcion, CampoVacioExcepcion, ProcesoYaExistenteException {
        // Búsqueda de procesos
        contenedor.agregarProceso("Proceso1000", "Holi4");
        contenedor.buscarProcesosSugeridos("holi100");
    }

    @Test
    public void sumaGeneral() throws ProcesoYaExistenteException, CampoVacioExcepcion, ActividadExistenteExcepcion, TareasOpcionalesSeguidasExcepcion {
        Proceso proceso = contenedor.agregarProceso("PruebaDeSumas", "Holi2");

        Actividad actividad = proceso.agregarActividad("holi", "Holi", "Una descripción", false, 0, null);
        actividad.agregarTarea("Hola una descripción", 20, false, -1);
        actividad.agregarTarea("Una pentadescripción", 30, true, -1);

        Actividad actividadB = proceso.agregarActividad("holi2", "Holi2", "Una descripción", true, 0, null);
        actividadB.agregarTarea("Uan por 2", 10, true, -1);

        assertEquals(actividad.getCantidadDeTareas(), 2);
        assertEquals(actividad.getMinimaDuracion(), 20);
        assertEquals(actividad.getMaximaDuracion(), 50);

        assertEquals(proceso.getCantidadDeActividades(), 2);
        assertEquals(proceso.getMinimaDuracion(), 20);
        assertEquals(proceso.getMaximaDuracion(), 60);

    }

}
