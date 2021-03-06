package almacen.pedidos.views.InfoPedidos;


import almacen.pedidos.domain.Pedido;
import almacen.pedidos.util.AdaptadorListaCompra;
import almacen.pedidos.util.ElementoAdaptado;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ResumenPedidoController implements Initializable {

    @FXML
    private TableView tablaProductos;
    @FXML
    private TableColumn<ElementoAdaptado, Number> unidadesProductoColumn;
    @FXML
    private TableColumn<ElementoAdaptado, String> nombreColumn;
    @FXML
    private TableColumn<ElementoAdaptado, String> alimentoColumn;
    @FXML
    private TableColumn<ElementoAdaptado, Number> cantidadColumn;
    @FXML
    private TableColumn<ElementoAdaptado, String> unidadColumn;
    @FXML
    private TableColumn<ElementoAdaptado, Number> precioColumn;
    @FXML
    private Label tituloLabel;
    @FXML
    private Label estadoLabel;
    @FXML
    private Label precioLabel;


    private ObservableList<ElementoAdaptado> listaProductos;

    private Pedido pedido;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        unidadesProductoColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getUnidades()));

        nombreColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getProducto().getNombre()));

        alimentoColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getProducto().getAlimento().getNombre()));

        cantidadColumn.setCellValueFactory(p -> new SimpleDoubleProperty(p.getValue().getProducto().getCantidad()));

        unidadColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getProducto().getUnidades().toString()));

        precioColumn.setCellValueFactory(p -> new SimpleDoubleProperty(p.getValue().getProducto().getPrecio()));

    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
        this.tituloLabel.setText("Resumen de Pedido #" + pedido.getId());
        this.estadoLabel.setText(pedido.getEstado().toString());
        this.precioLabel.setText("" + pedido.getPrecio());
        refreshTable(pedido);
    }

    public void refreshTable(Pedido pedido) {
        List<ElementoAdaptado> lista = AdaptadorListaCompra.adaptarListaCompra(pedido.getLista());
        listaProductos = FXCollections.observableArrayList(lista);
        tablaProductos.setItems(listaProductos);
    }

}

