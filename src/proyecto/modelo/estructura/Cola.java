package proyecto.modelo.estructura;

import java.util.Iterator;

public class Cola<T> extends NodoEstructura<T> implements Iterable<T> {
	
	private Nodo<T> ultimo;
	private int longitud;
	
	public Cola() {
		this.primero = null;
		this.ultimo = null;
		this.longitud = 0;
	}
	
	public void encolar(T elemento) {
		Nodo<T> nuevoNodo = new Nodo<>(elemento);
		if (estaVacia()) {
			primero = nuevoNodo;
		} else {
			ultimo.conectar(nuevoNodo, 0);
		}
		longitud++;
		ultimo = nuevoNodo;
	}

	public void encolar(int posicion, T elemento) {

		if (posicion < 0) {
			encolar(elemento);
			return;
		}

		Nodo<T> nuevoNodo = new Nodo<>(elemento);
		if (estaVacia()) {
			primero = nuevoNodo;
			ultimo = nuevoNodo;
		} else {

			Nodo<T> auxiliar = null;

			while (posicion > 0) {

				if (auxiliar == null) {
					auxiliar = this.primero;
					if (auxiliar == null) {
						break;
					}
				}

				posicion--;
				if (posicion > 0) {
					if (auxiliar.estaConectado(0)) {
						auxiliar = auxiliar.seguirEnlace(0);
					} else {
						posicion = -1;
					}
				}

			}

			if (auxiliar == null) {
				// Es el primer valor.
				nuevoNodo.conectar(this.primero, 0);
				this.primero = nuevoNodo;
			} else {

				nuevoNodo.conectar(auxiliar.seguirEnlace(0), 0);
				auxiliar.conectar(nuevoNodo, 0);

				if (this.ultimo == auxiliar) {
					// Es el último valor
					this.ultimo = nuevoNodo;
				}
			}

		}


		longitud++;
	}

	public boolean desencolar(T seleccionado) {

		Nodo<T> nodoEncontrado = this.primero;
		Nodo<T> nodoAnterior = null;
		boolean encontrado = false;

		while (nodoEncontrado != null && !encontrado) {

			if (nodoEncontrado.getValor().equals(seleccionado)) {
				encontrado = true;
			}

			if (!encontrado) {
				nodoAnterior = nodoEncontrado;
				nodoEncontrado = nodoEncontrado.seguirEnlace(0);
			}

		}

		if (nodoEncontrado != null) {

			Nodo<T> siguienteNodo = nodoEncontrado.seguirEnlace(0);
			if (nodoAnterior == null) {
				// Convertir en primer valor, el segundo.
				this.primero = siguienteNodo;
			} else {
				nodoAnterior.conectar(siguienteNodo, 0);
				if (this.ultimo == nodoEncontrado) {
					// El anterior se convierte en primero.
					this.ultimo = nodoAnterior;
					nodoAnterior.desconectar(0);
				}
			}

			nodoEncontrado.desconectar(0);

			this.longitud--;
			return true;
		} else return false;

	}
	
	public boolean estaVacia() {
        return primero == null;
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

	public T getUltimoValor() {
		return this.ultimo != null ? this.ultimo.getValor() : null;
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

	@Override
	public String toString() {
		return "Cola [primero=" + primero + ", ultimo=" + ultimo + ", longitud=" + longitud + "]";
	}



}