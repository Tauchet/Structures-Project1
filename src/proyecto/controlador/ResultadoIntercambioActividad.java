package proyecto.controlador;

import proyecto.modelo.Actividad;

public class ResultadoIntercambioActividad {

    private final Actividad actividadSeleccionada;
    private final boolean intercambiarTareas;

    public ResultadoIntercambioActividad(Actividad actividadSeleccionada, boolean intercambiarTareas) {
        this.actividadSeleccionada = actividadSeleccionada;
        this.intercambiarTareas = intercambiarTareas;
    }

    public Actividad getActividadSeleccionada() {
        return actividadSeleccionada;
    }

    public boolean isIntercambiarTareas() {
        return intercambiarTareas;
    }

}
