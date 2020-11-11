package proyecto.modelo;

import proyecto.modelo.excepcion.CampoVacioExcepcion;
import proyecto.modelo.excepcion.ValorIncorrectoException;
import proyecto.modelo.excepcion.ValorMinimoExcepcion;

public class Validacion {

    public static <T> T entrada(String nombreCampo, T valor) throws CampoVacioExcepcion {
        if (valor == null || (valor instanceof String && ((String) valor).length() == 0)) {
            throw new CampoVacioExcepcion(nombreCampo);
        }
        return valor;
    }

    public static int entrarNumero(String nombreCampo, String valor) throws ValorIncorrectoException, CampoVacioExcepcion {

        entrada(nombreCampo, valor);

        try {
            return Integer.parseInt(valor);
        } catch (NumberFormatException ex) {
            throw new ValorIncorrectoException(nombreCampo, valor);
        }

    }

    public static void minimoValor(String nombreCampo, int valor, int minimoValor) throws ValorMinimoExcepcion {
        if (valor < minimoValor) {
            throw new ValorMinimoExcepcion(nombreCampo, minimoValor);
        }
    }
}
