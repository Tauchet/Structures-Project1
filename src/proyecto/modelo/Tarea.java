package proyecto.modelo;

public class Tarea {

    private Actividad actividad;
    private String descripcion;
    private int duracionEnMinutos;
    private boolean opcional;

    public Tarea(Actividad actividad, String descripcion, int duracionEnMinutos, boolean opcional) {
        this.actividad = actividad;
        this.descripcion = descripcion;
        this.duracionEnMinutos = duracionEnMinutos;
        this.opcional = opcional;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getDuracionEnMinutos() {
        return duracionEnMinutos;
    }

    public void setDuracionEnMinutos(int duracionEnMinutos) {
        this.duracionEnMinutos = duracionEnMinutos;
    }

    public boolean isOpcional() {
        return opcional;
    }

    public void setOpcional(boolean opcional) {
        this.opcional = opcional;
    }

    @Override
    public String toString() {
        return "Tarea{" +
                "actividad=" + actividad +
                ", descripcion='" + descripcion + '\'' +
                ", duracionEnMinutos=" + duracionEnMinutos +
                ", opcional=" + opcional +
                '}';
    }

}
