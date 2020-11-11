package proyecto.controlador;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import proyecto.Aplicacion;
import proyecto.componentes.Componente;
import proyecto.modelo.estructura.ListaEnlazadaSimple;
import proyecto.modelo.excepcion.CampoVacioExcepcion;
import proyecto.modelo.excepcion.NingunaSugerenciaExcepcion;

import java.util.ArrayList;

public abstract class BusquedaControlador<T> {

    @FXML
    private VBox lista;

    @FXML
    private TextField campoBusqueda;

    @FXML
    private FontAwesomeIconView buscarIcono;

    private ArrayList<Node> busqueda = null;

    @FXML
    void escucharCambioDeCampoBusqueda(KeyEvent event) {
        this.buscarIcono.setIcon(FontAwesomeIcon.SEARCH);
    }

    @FXML
    void buscar(ActionEvent event) {

        String valor = this.campoBusqueda.getText();

        boolean reiniciarBusqueda = true;

        if (this.buscarIcono.getGlyphName().equalsIgnoreCase("SEARCH") && valor != null && valor.length() >= 1) {
            reiniciarBusqueda = false;
            if (this.busqueda == null) this.busqueda = new ArrayList<>(this.lista.getChildren());
            this.buscarIcono.setIcon(FontAwesomeIcon.CLOSE);

            try {

                ListaEnlazadaSimple<T> sugerencias = buscarSugerencias(valor);
                for (Node elemento: this.busqueda) {
                    Componente<T> componente = (Componente<T>) elemento.getProperties().get("CONTROLADOR");
                    if (sugerencias.contiene(componente.getValor())) {
                        if (!this.lista.getChildren().contains(elemento)) {
                            this.lista.getChildren().add(elemento);
                        }
                    } else this.lista.getChildren().remove(elemento);
                }

            } catch (Throwable ex) {

                Aplicacion.getInstance().abrirDialogoError("¡No se ha encontrado algún resultado con esta búsqueda!");

            }
        }

        if (reiniciarBusqueda) {
            this.buscarIcono.setIcon(FontAwesomeIcon.SEARCH);
            this.campoBusqueda.setText("");
            this.lista.getChildren().clear();
            this.lista.getChildren().addAll(this.busqueda);
        }

    }

    public abstract ListaEnlazadaSimple<T> buscarSugerencias(String busqueda) throws NingunaSugerenciaExcepcion, CampoVacioExcepcion;

    public VBox getLista() {
        return lista;
    }

    public TextField getCampoBusqueda() {
        return campoBusqueda;
    }

    public FontAwesomeIconView getBuscarIcono() {
        return buscarIcono;
    }

    public void setBusqueda(ArrayList<Node> busqueda) {
        this.busqueda = busqueda;
    }

}
