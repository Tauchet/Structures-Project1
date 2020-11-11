package proyecto.modelo.estructura;

public class NodoEstructura<T> {

    protected Nodo<T> actual, primero;

    public Nodo<T> getActual() {
        return actual;
    }

    public void setActual(Nodo<T> actual) {
        this.actual = actual;
    }

    public Nodo<T> getPrimero() {
        return primero;
    }

    public void setPrimero(Nodo<T> primero) {
        this.primero = primero;
    }

    public T getPrimerValor() {
        return this.primero != null ? this.primero.getValor() : null;
    }

}
