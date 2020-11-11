package proyecto.modelo.estructura;

import java.util.Iterator;

public class ListaDobleCircular<T> extends  NodoEstructura<T> implements Iterable<T> {

    private Nodo<T> ultimo;
    private int longitud;

    public ListaDobleCircular() {
        this.primero = null;
        this.ultimo = null;
        this.actual = null;
        this.longitud = 0;
    }

    public boolean estaVacia() {
        return this.longitud == 0;
    }

    public void agregar(T elemento) {
        Nodo<T> nuevoNodo = new Nodo<>(elemento);
        agregar(nuevoNodo);
    }

    public void agregar(Nodo<T> nuevoNodo) {
        if (estaVacia()) {
            nuevoNodo.conectar(nuevoNodo, 1);
            this.primero = nuevoNodo;
        } else {
            this.ultimo.conectar(nuevoNodo, 0);
            nuevoNodo.conectar(ultimo, 1);
        }
        this.ultimo = nuevoNodo;
        this.actual = nuevoNodo;
        this.primero.conectar(this.ultimo, 1);
        this.ultimo.conectar(this.primero, 0);
        this.longitud++;
    }

    public void agregarDespues(T donde, T elemento) {

        Nodo<T> dondeElemento = null;
        while (dondeElemento != this.primero) {
            if (dondeElemento == null) dondeElemento = this.primero;
            if (dondeElemento.getValor().equals(donde)) {
                break;
            }
            dondeElemento = dondeElemento.seguirEnlace(0);
        }

        if (dondeElemento == null) {
            agregar(elemento);
            return;
        }

        Nodo<T> auxiliar = dondeElemento.seguirEnlace(0);
        Nodo<T> nuevoNodo = new Nodo<>(elemento);
        nuevoNodo.conectar(dondeElemento, 1);
        dondeElemento.conectar(nuevoNodo, 0);
        nuevoNodo.conectar(auxiliar, 0);
        auxiliar.conectar(nuevoNodo, 1);
        if (dondeElemento == this.ultimo) {
            this.ultimo = nuevoNodo;
        }
        this.longitud++;

    }

    public boolean intercambirPosicion(T desde, T hasta) {

        if (desde == hasta) {
            return false;
        }

        Nodo<T> auxiliar = null;
        Nodo<T> nodoDesde = null;
        Nodo<T> nodoHasta = null;

        while (auxiliar != this.primero && (nodoDesde == null || nodoHasta == null)) {
            if (auxiliar == null) auxiliar = this.primero;
            if (auxiliar.getValor().equals(desde)) {
                nodoDesde = auxiliar;
            } else if (auxiliar.getValor().equals(hasta)) {
                nodoHasta = auxiliar;
            }
            auxiliar = auxiliar.seguirEnlace(0);
        }

        if (nodoDesde == null || nodoHasta == null) {
            return false;
        }

        Nodo<T> desdeAnterior = nodoDesde.seguirEnlace(1);
        Nodo<T> desdeSiguiente = nodoDesde.seguirEnlace(0);

        Nodo<T> hastaAnterior = nodoHasta.seguirEnlace(1);
        Nodo<T> hastaSiguiente = nodoHasta.seguirEnlace(0);


        if (this.longitud > 2 && nodoHasta == desdeAnterior && hastaSiguiente == nodoDesde) {

            hastaAnterior.conectar(nodoDesde, 0);
            nodoDesde.conectar(hastaAnterior, 1);

            nodoDesde.conectar(nodoHasta, 0);
            nodoHasta.conectar(nodoDesde, 1);

            nodoHasta.conectar(desdeSiguiente, 0);
            desdeSiguiente.conectar(nodoHasta, 1);

            this.primero = nodoHasta;
            this.ultimo = nodoDesde;

        } else if (this.longitud > 2 && nodoHasta == desdeSiguiente && hastaAnterior == nodoDesde) {

            desdeAnterior.conectar(nodoHasta, 0);
            nodoHasta.conectar(desdeAnterior, 1);

            nodoHasta.conectar(nodoDesde, 0);
            nodoDesde.conectar(nodoHasta, 1);

            nodoDesde.conectar(hastaSiguiente, 0);
            hastaSiguiente.conectar(nodoDesde, 1);

            this.primero = nodoDesde;
            this.ultimo = nodoHasta;

        } else {

            if (this.longitud == 2) {

                nodoDesde.conectar(nodoHasta, 0);
                nodoHasta.conectar(nodoDesde, 1);

                nodoHasta.conectar(nodoDesde, 0);
                nodoDesde.conectar(nodoHasta, 1);

            } else {

                desdeAnterior.conectar(nodoHasta, 0);
                nodoHasta.conectar(desdeAnterior, 1);

                nodoHasta.conectar(desdeSiguiente, 0);
                desdeSiguiente.conectar(nodoHasta, 1);

                hastaAnterior.conectar(nodoDesde, 0);
                nodoDesde.conectar(hastaAnterior, 1);

                nodoDesde.conectar(hastaSiguiente, 0);
                hastaSiguiente.conectar(nodoDesde, 1);

            }

            if (this.primero == nodoDesde) {
                this.primero = nodoHasta;
            } else if (this.primero == nodoHasta) {
                this.primero = nodoDesde;
            }

            if (this.ultimo == nodoDesde) {
                this.ultimo = nodoHasta;
            } else if (this.ultimo == nodoHasta) {
                this.ultimo = nodoDesde;
            }

        }

        return true;

    }

    public void eliminar(T seleccionado) {

        Nodo<T> nodo = null;
        while (nodo != this.primero) {
            if (nodo == null) nodo = this.primero;
            if (nodo.getValor().equals(seleccionado)) {
                break;
            }
            nodo = nodo.seguirEnlace(0);
        }

        // No lo contiene la lista.
        if (nodo == null) {
            return;
        }

        Nodo<T> anteriorNodo = this.longitud >= 2 ? nodo.seguirEnlace(1) : null;
        Nodo<T> siguienteNodo = this.longitud >= 2 ? nodo.seguirEnlace(0) : null;
        if (anteriorNodo != null && siguienteNodo != null) {
            siguienteNodo.conectar(anteriorNodo, 1);
            anteriorNodo.conectar(siguienteNodo, 0);
        }

        if (nodo == this.ultimo) this.ultimo = anteriorNodo;
        if (nodo == this.primero) this.primero = siguienteNodo;

        this.actual = this.primero;
        this.longitud--;

    }

    public Nodo<T> getUltimo() {
        return ultimo;
    }

    public void setUltimo(Nodo<T> ultimo) {
        this.ultimo = ultimo;
    }

    public int getLongitud() {
        return longitud;
    }

    public void setLongitud(int longitud) {
        this.longitud = longitud;
    }

    @Override
    public Iterator<T> iterator() {
        return new NodoEstructuraIterador<>(this);
    }

    public T getUltimoValor() {
        return this.ultimo != null ? this.ultimo.getValor() : null;
    }

}
