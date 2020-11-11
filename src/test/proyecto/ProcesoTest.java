package test.proyecto;

import junit.framework.TestCase;
import org.junit.Test;
import proyecto.modelo.Actividad;
import proyecto.modelo.Contenedor;
import proyecto.modelo.Proceso;
import proyecto.modelo.excepcion.ActividadExistenteExcepcion;
import proyecto.modelo.excepcion.CampoVacioExcepcion;
import proyecto.modelo.excepcion.NingunaSugerenciaExcepcion;
import proyecto.modelo.excepcion.ProcesoYaExistenteException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProcesoTest {

    private static final Contenedor contenedor = new Contenedor("gg", null);

    @Test
    public void agregarActividad() throws CampoVacioExcepcion, ActividadExistenteExcepcion, ProcesoYaExistenteException {
        Proceso proceso = contenedor.agregarProceso("holi3", "Holi2");
        proceso.agregarActividad("holi", "Holi", "Una descripcion", false, 0, null);
    }

    @Test (expected = ActividadExistenteExcepcion.class)
    public void agregarActividadExistente() throws CampoVacioExcepcion, ActividadExistenteExcepcion, NingunaSugerenciaExcepcion, ProcesoYaExistenteException {
        Proceso proceso = contenedor.agregarProceso("holi400", "Holi");
        proceso.agregarActividad("holi2", "Holi", "Una descripcion", false, 0, null);
        proceso.agregarActividad("holi3", "Holi", "Una descripcion", false, 0, null);
    }

    @Test
    public void intercambiarActividadPrimeraAFinal() throws CampoVacioExcepcion, ActividadExistenteExcepcion, NingunaSugerenciaExcepcion, ProcesoYaExistenteException {

        Proceso proceso = contenedor.agregarProceso("PruebaIntercambio", "PruebaIntercambio");
        proceso.agregarActividad("Hola1", "Hola1", "Una descripcion", false, 0, null);
        proceso.agregarActividad("Hola2", "Hola2", "Una descripcion", false, 0, null);
        proceso.agregarActividad("Hola3", "Hola3", "Una descripcion", false, 0, null);

        Actividad actividad = proceso.buscarActividadesSugeridas("Hola1").getPrimerValor();
        Actividad cambioActividad = proceso.buscarActividadesSugeridas("Hola3").getPrimerValor();

        actividad.intercambiar(cambioActividad, false);

        assertEquals(actividad, proceso.getActividades().getUltimoValor());
        assertEquals(cambioActividad, proceso.getActividades().getPrimerValor());

    }

    @Test
    public void agregarActividadDespuesDeOtra() throws CampoVacioExcepcion, ActividadExistenteExcepcion, NingunaSugerenciaExcepcion, ProcesoYaExistenteException {

        Proceso proceso = contenedor.agregarProceso("PruebaDespues", "PruebaDespues");
        Actividad actividad = proceso.agregarActividad("Hola2", "Hola2", "Una descripcion", false, 0, null);
        proceso.agregarActividad("Hola3", "Hola3", "Una descripcion", false, 0, null);
        Actividad actividadNueva = proceso.agregarActividad("Hola1", "Hola1", "Una descripcion", false, 1, actividad);

        List<Actividad> actividadList = new ArrayList<>();
        proceso.getActividades().forEach(actividadList::add);

        assertEquals(actividadList.get(1), actividadNueva);

    }

    @Test
    public void agregarActividadDespuesDeLaUltimaCreada() throws CampoVacioExcepcion, ActividadExistenteExcepcion, NingunaSugerenciaExcepcion, ProcesoYaExistenteException {

        Proceso proceso = contenedor.agregarProceso("PruebaDespuesUltima", "PruebaDespuesUltima");
        Actividad actividad = proceso.agregarActividad("Hola2", "Hola2", "Una descripcion", false, 0, null);
        proceso.agregarActividad("Hola3", "Hola3", "Una descripcion", false, 0, null);
        proceso.agregarActividad("Hola1", "Hola1", "Una descripcion", false, 1, actividad);
        Actividad actividadNueva = proceso.agregarActividad("Hola4", "Hola4", "Una descripcion", false, 2, null);

        /*

            Resultado.
            - Hola2
            - Hola1
            - Hola4
            - Hola3

         */

        List<Actividad> actividadList = new ArrayList<>();
        proceso.getActividades().forEach(actividadList::add);
        assertEquals(actividadList.get(2), actividadNueva);

    }


}
