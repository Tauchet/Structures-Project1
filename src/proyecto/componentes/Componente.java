package proyecto.componentes;

import javafx.scene.Node;

public abstract class Componente<T> {

    protected Node elemento;
    protected final T valor;

    public Componente(T valor) {
        this.valor = valor;
    }

    public T getValor() {
        return valor;
    }

    public Node getElemento() {
        return elemento;
    }

    public void setElemento(Node elemento) {
        this.elemento = elemento;
    }

}
