package proyecto.modelo.estructura;

import java.util.ArrayList;

public class Nodo<T> {

    private final T valor;
    private final ArrayList<Nodo<T>> enlaces;

    public Nodo(T valor) {
        this.valor = valor;
        this.enlaces = new ArrayList<>();
    }

    public T getValor() {
        return this.valor;
    }

    //Metodo de conectar nodo
    public void conectar(Nodo<T> destino, int indice) {
        if (indice < enlaces.size()) {
            enlaces.set(indice, destino);
        } else {
            int n = indice - enlaces.size();

            for (int i = 0; i < n; i++) {
                enlaces.add(null);
            }

            enlaces.add(destino);
        }
    }

    //Metodo de desconectar nodo
    public void desconectar(int indice) {
        if (estaConectado(indice)) {
            enlaces.set(indice, null);
        }
    }

    //Verificar conexion del nodo
    public boolean estaConectado(int indice) {
        if (enlaces.size() > indice && enlaces.get(indice) != null) {
            return true;
        } else {
            return false;
        }
    }

    //Seguir enlace
    public Nodo<T> seguirEnlace(int indice) {
        if (estaConectado(indice)) {
            return enlaces.get(indice);
        } else {
            return null;
        }

    }

    @Override
    public String toString() {
        StringBuilder resultado = new StringBuilder("Nodo$" + hashCode() + " [valor=" + valor);
        for (int i = 0; i < enlaces.size(); i++) {
            resultado.append(resultado.toString().contains("enlaces=") ? "," : ", enlaces=").append("(").append(i).append("->$").append(enlaces.get(i) != null ? enlaces.get(i).hashCode() : "NaN").append(")");
        }
        return resultado + "]";
    }

}
