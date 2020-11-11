package proyecto.modelo.estructura;

import java.util.Iterator;

public class ListaEnlazadaSimple<T> extends NodoEstructura<T> implements Iterable<T> {

	private Nodo<T> ultimo;
	private int longitud;
	
	public ListaEnlazadaSimple() {
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
		if (estaVacia()) {
			this.primero = nuevoNodo;
		}
		else {
			this.ultimo.conectar(nuevoNodo, 0);
		}
		this.longitud++;
		this.ultimo = nuevoNodo;
		this.actual = nuevoNodo;
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

	public boolean contiene(T elemento) {
		Nodo<T> auxiliar = this.primero;
		while (auxiliar != null) {
			if (auxiliar.getValor().equals(elemento)) {
				return true;
			}
			auxiliar = auxiliar.seguirEnlace(0);
		}
		return false;
	}

	public Nodo<T> getPrimero() {
		return primero;
	}

	public void setPrimero(Nodo<T> primero) {
		this.primero = primero;
	}

	public Nodo<T> getUltimo() {
		return ultimo;
	}

	public void setUltimo(Nodo<T> ultimo) {
		this.ultimo = ultimo;
	}

	public Nodo<T> getActual() {
		return actual;
	}

	public void setActual(Nodo<T> actual) {
		this.actual = actual;
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


}
