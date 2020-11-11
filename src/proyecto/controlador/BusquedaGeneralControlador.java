package proyecto.controlador;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import proyecto.Aplicacion;
import proyecto.componentes.Componente;
import proyecto.componentes.proceso.ProcesoComponente;
import proyecto.modelo.Actividad;
import proyecto.modelo.BusquedaResultado;
import proyecto.modelo.Tarea;
import proyecto.modelo.estructura.ListaEnlazadaSimple;
import proyecto.vistas.TipoDeVista;

import java.util.ArrayList;

public class BusquedaGeneralControlador {

    @FXML
    private VBox lista;

    @FXML
    private Label nombre;

    @FXML
    private Label id;

    @FXML
    private FontAwesomeIconView buscarIcono;

    @FXML
    private TextField campoBusqueda;

    private BaseControlador baseControlador;

    @FXML
    void buscar(ActionEvent event) {
        String valor = this.campoBusqueda.getText();

        boolean reiniciarBusqueda = true;

        if (this.buscarIcono.getGlyphName().equalsIgnoreCase("SEARCH") && valor != null && valor.length() >= 1) {
            reiniciarBusqueda = false;

            this.buscarIcono.setIcon(FontAwesomeIcon.CLOSE);

            try {

                this.lista.getChildren().clear();

                ListaEnlazadaSimple<BusquedaResultado> sugerencias = Aplicacion.getInstance().getContenedor().buscarAvanzadamente(valor);
                for (BusquedaResultado resultado: sugerencias) {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/proyecto/componentes/GeneralComponente.fxml"));
                    BorderPane elemento = loader.load();
                    this.lista.getChildren().add(elemento);

                    HBox opciones = (HBox) elemento.lookup("#opciones");

                    if (resultado.getDireccion() != null) {
                        ((Label) elemento.lookup("#direccion")).setText(resultado.getDireccion());
                    } else opciones.getChildren().remove(elemento.lookup("#direccion"));

                    if (resultado.getEntidad() != null) {
                        ((Label) elemento.lookup("#entidad")).setText(resultado.getEntidad());
                    } else opciones.getChildren().remove(elemento.lookup("#entidad"));

                    ((Label) elemento.lookup("#descripcion")).setText(resultado.getDescripcion());

                    elemento.addEventHandler(MouseEvent.MOUSE_RELEASED, event1 -> {

                        TipoDeVista vista = TipoDeVista.PROCESO;
                        if (resultado.getValor() instanceof Actividad) {
                            vista = TipoDeVista.ACTIVIDAD;
                        } else if (resultado.getValor() instanceof Tarea) {
                            vista = TipoDeVista.TAREA_FORMULARIO;
                        }

                        if (!baseControlador.getBotonInicio().getStyleClass().contains("active")) {
                            baseControlador.getBotonBusqueda().getStyleClass().remove("active");
                            baseControlador.getBotonInicio().getStyleClass().add("active");
                            Aplicacion.getInstance().getInterfaz().abrir(TipoDeVista.GENERAL, null);
                        }
                        Aplicacion.getInstance().getInterfaz().abrir(vista, resultado.getValor());


                    });

                }

            } catch (Throwable ex) {
                Aplicacion.getInstance().abrirDialogoError(ex.getMessage());
            }

        }

        if (reiniciarBusqueda) {
            this.buscarIcono.setIcon(FontAwesomeIcon.SEARCH);
            this.campoBusqueda.setText("");
            this.lista.getChildren().clear();
        }
    }

    @FXML
    void escucharCambioDeCampoBusqueda(KeyEvent event) {
        buscarIcono.setIcon(FontAwesomeIcon.SEARCH);
    }

    public VBox getLista() {
        return lista;
    }

    public BaseControlador getBaseControlador() {
        return baseControlador;
    }

    public void setBaseControlador(BaseControlador baseControlador) {
        this.baseControlador = baseControlador;
    }

}
