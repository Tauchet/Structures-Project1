package proyecto.modelo;

public class BusquedaResultado {

    private final String direccion;
    private final String entidad;
    private final String descripcion;
    private final Object valor;

    public BusquedaResultado(String direccion, String  entidad, String descripcion, Object valor) {
        this.direccion = direccion;
        this.entidad = entidad;
        this.descripcion = descripcion;
        this.valor = valor;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getEntidad() {
        return entidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Object getValor() {
        return this.valor;
    }

}
