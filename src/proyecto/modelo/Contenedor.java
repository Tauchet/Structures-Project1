package proyecto.modelo;

import com.google.gson.*;
import proyecto.modelo.estructura.ListaEnlazadaSimple;
import proyecto.modelo.excepcion.*;

import java.io.*;
import java.util.UUID;

public class Contenedor {

    private static final Gson gson = new GsonBuilder().create();
    private final ListaEnlazadaSimple<Proceso> procesos = new ListaEnlazadaSimple<>();

    private final String codigo;
    private File archivo;
    private boolean modificado;

    public Contenedor(String codigo, File archivo) {
        this.codigo = codigo;
        this.archivo = archivo;
    }

    public String getCodigo() {
        return codigo;
    }

    public File getArchivo() {
        return archivo;
    }

    public void setArchivo(File archivo) {
        this.archivo = archivo;
    }

    public void guardar(File archivo) throws IOException {
        if (this.modificado) {

            this.modificado = false;

            JsonObject contenedorDocumento = new JsonObject();
            JsonArray procesosArreglo = new JsonArray();
            contenedorDocumento.addProperty("id", this.codigo);
            contenedorDocumento.add("procesos", procesosArreglo);

            for (Proceso proceso: this.procesos) {

                JsonObject procesoDocumento = new JsonObject();
                JsonArray actividadesArreglo = new JsonArray();

                procesoDocumento.addProperty("id", proceso.getId());
                procesoDocumento.addProperty("nombre", proceso.getNombre());
                if (proceso.getUltimaActividadCreada() != null) procesoDocumento.addProperty("ultimaActividad", proceso.getUltimaActividadCreada().getCodigo());
                procesoDocumento.add("actividades", actividadesArreglo);
                procesosArreglo.add(procesoDocumento);

                for (Actividad actividad: proceso.getActividades()) {

                    JsonObject actividadDocumento = new JsonObject();
                    JsonArray tareasArreglo = new JsonArray();
                    actividadDocumento.addProperty("codigo", actividad.getCodigo());
                    actividadDocumento.addProperty("nombre", actividad.getNombre());
                    actividadDocumento.addProperty("descripcion", actividad.getDescripcion());
                    actividadDocumento.addProperty("opcional", actividad.isOpcional());
                    actividadDocumento.add("tareas", tareasArreglo);
                    actividadesArreglo.add(actividadDocumento);

                    for (Tarea tarea: actividad.getTareas()) {
                        JsonObject tareaDocumento = new JsonObject();
                        tareaDocumento.addProperty("descripcion", tarea.getDescripcion());
                        tareaDocumento.addProperty("duracion", tarea.getDuracionEnMinutos());
                        tareaDocumento.addProperty("opcional", tarea.isOpcional());
                        tareasArreglo.add(tareaDocumento);
                    }

                }

            }

            try (Writer writer = new FileWriter(archivo)) {
                gson.toJson(contenedorDocumento, writer);
            }

        }
    }

    public static Contenedor generar() {
        return new Contenedor(UUID.randomUUID().toString(),null);
    }

    public static Contenedor cargar(File archivo) throws IOException, ProcesoYaExistenteException, CampoVacioExcepcion, ActividadExistenteExcepcion, TareasOpcionalesSeguidasExcepcion {

        if ((!archivo.exists()) && archivo.createNewFile()) {
            return new Contenedor(UUID.randomUUID().toString(),archivo);
        }


        JsonObject contenedorDocumento = gson.fromJson(new FileReader(archivo), JsonObject.class);
        Contenedor contenedor = new Contenedor(contenedorDocumento.get("id").getAsString(), archivo);
        JsonArray procesos = contenedorDocumento.getAsJsonArray("procesos");

        for (JsonElement procesoElemento: procesos) {

            JsonObject procesoDocumento = procesoElemento.getAsJsonObject();
            String procesoId = procesoDocumento.get("id").getAsString();
            String procesoNombre = procesoDocumento.get("nombre").getAsString();
            String codigoUltimaActividad = procesoDocumento.has("ultimaActividad") ? procesoDocumento.get("ultimaActividad").getAsString() : null;

            Proceso proceso = contenedor.agregarProceso(procesoId, procesoNombre);

            JsonArray actividades = procesoDocumento.getAsJsonArray("actividades");
            for (JsonElement actividadElemento: actividades) {

                JsonObject actividadDocumento = actividadElemento.getAsJsonObject();
                String actividadCodigo = actividadDocumento.get("codigo").getAsString();
                String actividadNombre = actividadDocumento.get("nombre").getAsString();
                String actividadDescripcion = actividadDocumento.get("descripcion").getAsString();

                Actividad actividad = proceso.agregarActividad(actividadCodigo, actividadNombre, actividadDescripcion, actividadDocumento.get("opcional").getAsBoolean(), 0, null);
                if (actividadCodigo.equalsIgnoreCase(codigoUltimaActividad)) {
                    proceso.setUltimaActividadCreada(actividad);
                }

                JsonArray tareas = actividadDocumento.getAsJsonArray("tareas");
                for (JsonElement tareaElemento: tareas) {

                    JsonObject tareaDocumento = tareaElemento.getAsJsonObject();
                    String descripcion = tareaDocumento.get("descripcion").getAsString();
                    int duracion = tareaDocumento.get("duracion").getAsInt();
                    boolean opcional = tareaDocumento.get("opcional").getAsBoolean();
                    actividad.agregarTarea(descripcion, duracion, opcional, -1);

                }

            }


        }

        contenedor.setModificado(false);
        return contenedor;
    }

    public ListaEnlazadaSimple<Proceso> buscarProcesosSugeridos(String busqueda) throws CampoVacioExcepcion, NingunaSugerenciaExcepcion {
        Validacion.entrada("busqueda", busqueda);

        ListaEnlazadaSimple<Proceso> sugerencias = new ListaEnlazadaSimple<>();
        for (Proceso proceso: this.procesos) {
            if (proceso.getNombre().toLowerCase().startsWith(busqueda.toLowerCase())) {
                sugerencias.agregar(proceso);
            }
        }

        if (sugerencias.estaVacia()) {
            throw new NingunaSugerenciaExcepcion(busqueda);
        }

        return sugerencias;
    }

    public ListaEnlazadaSimple<BusquedaResultado> buscarAvanzadamente(String busqueda) throws CampoVacioExcepcion {
        Validacion.entrada("busqueda", busqueda);

        ListaEnlazadaSimple<BusquedaResultado> sugerencias = new ListaEnlazadaSimple<>();
        for (Proceso proceso: this.procesos) {
            if (proceso.getNombre().toLowerCase().startsWith(busqueda.toLowerCase())) {
                sugerencias.agregar(new BusquedaResultado(null, proceso.getId(), proceso.getNombre(), proceso));

                for (Actividad actividad: proceso.getActividades()) {
                    sugerencias.agregar(new BusquedaResultado(proceso.getNombre(), actividad.getNombre(), actividad.getDescripcion(), actividad));

                    for (Tarea tarea: actividad.getTareas()) {
                        sugerencias.agregar(new BusquedaResultado(proceso.getNombre() + " / " + actividad.getNombre(), null, tarea.getDescripcion(), tarea));
                    }

                }

            }
        }

        return sugerencias;
    }

    public Proceso agregarProceso(String id, String nombre) throws CampoVacioExcepcion, ProcesoYaExistenteException {

        Validacion.entrada("id", id);
        Validacion.entrada("nombre", nombre);

        for (Proceso proceso: this.procesos) {
            if (proceso.getId().equalsIgnoreCase(id) || proceso.getNombre().equalsIgnoreCase(id)) {
                throw new ProcesoYaExistenteException();
            }
        }

        Proceso proceso = new Proceso(id, nombre);
        this.procesos.agregar(proceso);
        this.modificado = true;

        return proceso;

    }

    public void modificarProceso(Proceso proceso, String nombre) throws CampoVacioExcepcion {
        Validacion.entrada("proceso", proceso);
        Validacion.entrada("nombre", nombre);
        proceso.setNombre(nombre);
        this.modificado = true;
    }

    public void eliminarProceso(Proceso proceso) throws CampoVacioExcepcion {
        Validacion.entrada("proceso", proceso);
        this.procesos.eliminar(proceso);
        this.modificado = true;
    }

    public ListaEnlazadaSimple<Proceso> getProcesos() {
        return procesos;
    }

    public boolean isModificado() {
        return modificado;
    }

    public void setModificado(boolean modificado) {
        this.modificado = modificado;
    }

}
