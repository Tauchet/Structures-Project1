package proyecto.modelo;

import proyecto.Aplicacion;
import proyecto.controlador.ResultadoIntercambioActividad;
import proyecto.modelo.estructura.Cola;
import proyecto.modelo.estructura.ListaEnlazadaSimple;
import proyecto.modelo.excepcion.CampoVacioExcepcion;
import proyecto.modelo.excepcion.NingunaSugerenciaExcepcion;
import proyecto.modelo.excepcion.TareasOpcionalesSeguidasExcepcion;

public class Actividad {

    private final Proceso proceso;
    private String codigo;
    private String nombre;
    private String descripcion;
    private boolean opcional;
    private Cola<Tarea> tareas;

    private int minimaDuracion;
    private int maximaDuracion;

    public Actividad(Proceso proceso, String codigo, String nombre, String descripcion, boolean opcional) {
        this.proceso = proceso;
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.opcional = opcional;
        this.tareas = new Cola<>();
    }

    public ListaEnlazadaSimple<Tarea> buscarTareasSugeridas(String busqueda) throws CampoVacioExcepcion, NingunaSugerenciaExcepcion {
        Validacion.entrada("busqueda", busqueda);

        ListaEnlazadaSimple<Tarea> sugerencias = new ListaEnlazadaSimple<>();
        for (Tarea tarea: tareas) {
            String lowerCase = tarea.getDescripcion().toLowerCase().trim();
            if (lowerCase.startsWith(busqueda.toLowerCase()) || lowerCase.contains(busqueda.toLowerCase())) {
                sugerencias.agregar(tarea);
            }
        }

        if (sugerencias.estaVacia()) {
            throw new NingunaSugerenciaExcepcion(busqueda);
        }

        return sugerencias;
    }

    public Tarea agregarTarea(String descripcion, int duracion, boolean opcional, int posicion) throws CampoVacioExcepcion, TareasOpcionalesSeguidasExcepcion {

        Validacion.entrada("descripcion", descripcion);

        if (opcional) {

            Tarea anteriorTarea = null;
            Tarea siguienteTarea = null;

            if (posicion < 0) {
                anteriorTarea = this.tareas.getUltimoValor();
            } else {

                int indice = 0;
                for (Tarea actualTarea: this.tareas) {

                    if (siguienteTarea != null) {
                        anteriorTarea = actualTarea;
                    }
                    siguienteTarea = actualTarea;

                    indice++;
                    if (indice >= posicion) {
                       break;
                    }

                }

            }

            if (anteriorTarea != null && anteriorTarea.isOpcional()) {
                throw new TareasOpcionalesSeguidasExcepcion("¡En la posición anterior a esta nueva tarea, ya existe una tarea opcional!");
            }

            if (siguienteTarea != null && siguienteTarea.isOpcional()) {
                throw new TareasOpcionalesSeguidasExcepcion("¡En la posición siguiente a esta nueva tarea, ya existe una tarea opcional!");
            }

        }

        Tarea tarea = new Tarea(this, descripcion, duracion, opcional);
        this.tareas.encolar(posicion < 0 ? this.tareas.getLongitud() : posicion, tarea);
        calcularDuracion();

        return tarea;

    }

    public void modificarTarea(Tarea seleccionado, String valorDescripcion, int duracion, boolean valorOpcional) throws CampoVacioExcepcion, TareasOpcionalesSeguidasExcepcion {

        Validacion.entrada("tarea", seleccionado);
        Validacion.entrada("descripcion", valorDescripcion);

        if (valorOpcional) {

            Tarea anteriorTarea = null;
            Tarea siguienteTarea = null;
            boolean encontrado = false;

            for (Tarea tarea: this.tareas) {

                if (encontrado) {
                    siguienteTarea = tarea;
                    break;
                }

                if (tarea.equals(seleccionado)) {
                    encontrado = true;
                }

                anteriorTarea = tarea;

            }

            if (anteriorTarea != null && anteriorTarea.isOpcional()) {
                throw new TareasOpcionalesSeguidasExcepcion("¡En la posición anterior a esta nueva tarea, ya existe una tarea opcional!");
            }

            if (siguienteTarea != null && siguienteTarea.isOpcional()) {
                throw new TareasOpcionalesSeguidasExcepcion("¡En la posición siguiente a esta nueva tarea, ya existe una tarea opcional!");
            }

        }

        seleccionado.setDescripcion(valorDescripcion);
        seleccionado.setDuracionEnMinutos(duracion);
        seleccionado.setOpcional(valorOpcional);

        calcularDuracion();

    }

    public void eliminarTarea(Tarea seleccionado) throws CampoVacioExcepcion, TareasOpcionalesSeguidasExcepcion {

        Validacion.entrada("tarea", seleccionado);

        if (!seleccionado.isOpcional()) {
            Tarea anteriorTarea = null;
            Tarea siguienteTarea = null;
            boolean encontrado = false;

            for (Tarea tarea: this.tareas) {

                if (encontrado) {
                    siguienteTarea = tarea;
                    break;
                }

                if (tarea.equals(seleccionado)) {
                    encontrado = true;
                } else anteriorTarea = tarea;

            }

            if (anteriorTarea != null && anteriorTarea.isOpcional() && siguienteTarea != null && siguienteTarea.isOpcional()) {
                throw new TareasOpcionalesSeguidasExcepcion("Al eliminar esta tarea se quedan dos tareas opcionales seguidas.");
            }
        }

        this.tareas.desencolar(seleccionado);
        calcularDuracion();

    }

    public void intercambiar(Actividad siguienteActividad, boolean intercambiarTareas) throws CampoVacioExcepcion {
        Validacion.entrada("actividad", siguienteActividad);
        this.proceso.getActividades().intercambirPosicion(this, siguienteActividad);
        if (intercambiarTareas) {
            Cola<Tarea> cola = siguienteActividad.getTareas();
            siguienteActividad.setTareas(this.tareas);
            this.tareas = cola;
            for (Tarea siguienteTarea: siguienteActividad.getTareas()) {
                siguienteTarea.setActividad(siguienteActividad);
            }
            for (Tarea actualTarea: this.tareas) {
                actualTarea.setActividad(this);
            }
            this.calcularDuracion();
            siguienteActividad.calcularDuracion();
        }
    }

    private void calcularDuracion() {

        this.minimaDuracion = 0;
        this.maximaDuracion = 0;

        for (Tarea tarea: tareas) {
            if (!tarea.isOpcional()) {
                this.minimaDuracion += tarea.getDuracionEnMinutos();
            }
            this.maximaDuracion += tarea.getDuracionEnMinutos();
        }

        this.proceso.calcularDuracion();

    }

    public int getCantidadDeTareas() {
        return this.tareas.getLongitud();
    }

    public Proceso getProceso() {
        return proceso;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getMinimaDuracion() {
        return minimaDuracion;
    }

    public int getMaximaDuracion() {
        return maximaDuracion;
    }

    public Cola<Tarea> getTareas() {
        return tareas;
    }

    public void setTareas(Cola<Tarea> tareas) {
        this.tareas = tareas;
    }

    public boolean isOpcional() {
        return opcional;
    }

    public void setOpcional(boolean opcional) {
        this.opcional = opcional;
    }

    @Override
    public String toString() {
        return this.nombre;
    }



}
