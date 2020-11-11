package proyecto.vistas;

import proyecto.vistas.actividad.ActividadFormularioVista;
import proyecto.vistas.actividad.ActividadVista;
import proyecto.vistas.proceso.ProcesoFormularioVista;
import proyecto.vistas.proceso.ProcesoVista;
import proyecto.vistas.tarea.TareaFormularioVista;

public enum TipoDeVista {

    BUSQUEDA_GENERAL (BusquedaGeneralVista.class),
    GENERAL (PrincipalVista.class),
    PROCESO_FORMULARIO (ProcesoFormularioVista.class),
    PROCESO (ProcesoVista.class),
    ACTIVIDAD_FORMULARIO (ActividadFormularioVista.class),
    ACTIVIDAD (ActividadVista.class),
    TAREA_FORMULARIO (TareaFormularioVista.class);

    private final Class<? extends Vista<?>> clase;

    TipoDeVista(Class<? extends Vista<?>> clase) {
        this.clase = clase;
    }

    public Vista<?> generar() throws IllegalAccessException, InstantiationException {
        return this.clase.newInstance();
    }

}
