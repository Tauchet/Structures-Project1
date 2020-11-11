package proyecto.modelo.excepcion;

public class ValorMinimoExcepcion extends ModeloExcepcion {

    public ValorMinimoExcepcion(String nombreCampo, int minimo) {
        super("¡El valor ingresado en el campo " + nombreCampo + " debe ser mayor o igual a " + minimo + "!");
    }

}
