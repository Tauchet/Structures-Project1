package proyecto.modelo.excepcion;

public class ValorIncorrectoException extends ModeloExcepcion {

    public ValorIncorrectoException(String campoNombre, String valor) {
        super("¡El valor " + valor + " ingresado en el campo " + campoNombre + " no es el correcto!");
    }

}
