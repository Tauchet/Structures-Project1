package proyecto.modelo;

import proyecto.Aplicacion;
import proyecto.modelo.estructura.ListaDobleCircular;
import proyecto.modelo.estructura.ListaEnlazadaSimple;
import proyecto.modelo.excepcion.ActividadExistenteExcepcion;
import proyecto.modelo.excepcion.CampoVacioExcepcion;
import proyecto.modelo.excepcion.NingunaSugerenciaExcepcion;

public class Proceso {

    private final String id;
    private String nombre;

    private final ListaDobleCircular<Actividad> listaActividades;
    private Actividad ultimaActividadCreada;

    private int minimaDuracion;
    private int maximaDuracion;

    public Proceso(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.listaActividades = new ListaDobleCircular<>();
    }

    public ListaEnlazadaSimple<Actividad> buscarActividadesSugeridas(String busqueda) throws CampoVacioExcepcion, NingunaSugerenciaExcepcion {
        Validacion.entrada("busqueda", busqueda);

        ListaEnlazadaSimple<Actividad> sugerencias = new ListaEnlazadaSimple<>();
        for (Actividad actividad: listaActividades) {
            if (actividad.getNombre().toLowerCase().startsWith(busqueda.toLowerCase())) {
                sugerencias.agregar(actividad);
            }
        }

        if (sugerencias.estaVacia()) {
            throw new NingunaSugerenciaExcepcion(busqueda);
        }

        return sugerencias;
    }

    public Actividad agregarActividad(String id, String nombre, String descripcion, boolean opcional, int dondeAgregar, Actividad actividadSeleccionada) throws CampoVacioExcepcion, ActividadExistenteExcepcion {

        Validacion.entrada("codigo", id);
        Validacion.entrada("nombre", nombre);
        Validacion.entrada("descripcion", descripcion);

        for (Actividad actividad: listaActividades) {
            if (actividad.getCodigo().equalsIgnoreCase(id)) {
                throw new ActividadExistenteExcepcion("codigo");
            }
            if (actividad.getNombre().trim().equalsIgnoreCase(nombre.trim())) {
                throw new ActividadExistenteExcepcion("nombre");
            }
        }

        Actividad actividad = new Actividad(this, id, nombre, descripcion, opcional);

        if (dondeAgregar == 2) {
            this.listaActividades.agregarDespues(this.ultimaActividadCreada, actividad);
        } else if (dondeAgregar == 1) {
            this.listaActividades.agregarDespues(actividadSeleccionada, actividad);
        } this.listaActividades.agregar(actividad);

        this.ultimaActividadCreada = actividad;
        calcularDuracion();

        return actividad;
    }

    public void modificarActividad(Actividad seleccionado, String valorNombre, String valorDescripcion, boolean opcional) throws CampoVacioExcepcion {

        Validacion.entrada("actividad", seleccionado);
        Validacion.entrada("nombre", valorNombre);
        Validacion.entrada("descripcion", valorDescripcion);

        seleccionado.setNombre(valorNombre);
        seleccionado.setDescripcion(valorDescripcion);
        seleccionado.setOpcional(opcional);

        calcularDuracion();
    }

    public void eliminarActividad(Actividad seleccionado) throws CampoVacioExcepcion {

        Validacion.entrada("actividad", seleccionado);

        listaActividades.eliminar(seleccionado);
        if (seleccionado == this.ultimaActividadCreada) this.ultimaActividadCreada = listaActividades.getPrimerValor();

        calcularDuracion();
    }

    protected void calcularDuracion() {

        this.minimaDuracion = 0;
        this.maximaDuracion = 0;

        for (Actividad actividad: this.listaActividades) {
            if (!actividad.isOpcional()) {
                this.minimaDuracion += actividad.getMinimaDuracion();
            }
            this.maximaDuracion += actividad.getMaximaDuracion();
        }

    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ListaDobleCircular<Actividad> getActividades() {
        return listaActividades;
    }

    public Actividad getUltimaActividadCreada() {
        return ultimaActividadCreada;
    }

    public void setUltimaActividadCreada(Actividad ultimaActividadCreada) {
        this.ultimaActividadCreada = ultimaActividadCreada;
    }

    public int getMinimaDuracion() {
        return minimaDuracion;
    }

    public int getMaximaDuracion() {
        return maximaDuracion;
    }

    public int getCantidadDeActividades() {
        return this.listaActividades.getLongitud();
    }

}
