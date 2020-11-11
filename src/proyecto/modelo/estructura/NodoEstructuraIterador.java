package proyecto.modelo.estructura;

import java.util.Iterator;

public class NodoEstructuraIterador<T> implements Iterator<T> {

    private final NodoEstructura<T> lista;
    private Nodo<T> actual;

    public NodoEstructuraIterador(NodoEstructura<T> lista) {
        this.lista = lista;
        this.actual = null;
    }

    @Override
    public boolean hasNext() {
        if (this.actual == null) {
            return lista.getPrimero() != null;
        } else {
            Nodo<T> siguienteNodo = this.actual.seguirEnlace(0);
            return siguienteNodo != null && siguienteNodo != this.lista.getPrimero();
        }
    }

    @Override
    public T next() {
        if (this.actual == null) {
            this.actual = this.lista.getPrimero();
        } else {
            this.actual = this.actual.seguirEnlace(0);
        }
        return this.actual.getValor();
    }

}
