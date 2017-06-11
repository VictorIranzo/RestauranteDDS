package almacen;

import almacen.model.Producto;
import almacen.model.ProductoAlmacen;
import almacen.model.UnidadesCantidad;
import almacen.persistance.ProductoAlmacenService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Alimento;
import persistance.AlimentoService;
import persistance.AppContext;


import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EditarProductoController implements Initializable {

    @FXML
    private TextField nombreText;

    @FXML
    private ComboBox<Alimento> alimentoCombo;

    @FXML
    private TextField precioText;

    @FXML
    private TextField cantidadText;

    @FXML
    private ComboBox<UnidadesCantidad> unidadesCombo;

    @FXML
    private TextField stockText;

    @FXML
    private Button cancelarButton;

    @FXML
    private Button aceptarButton;

    @FXML
    private Label tituloLabel;

    private ProductoAlmacen productoAlmacen;

    private boolean editarProducto = false;


    private ObservableList<Alimento> alimentoObservableList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AlimentoService alimentoService = (AlimentoService) AppContext.getBean("alimentoService");
        ArrayList<Alimento> alimentosList = new ArrayList<>();
        alimentoService.findAll().forEach(a -> alimentosList.add(a));
        alimentoObservableList = FXCollections.observableList(alimentosList);
        alimentoCombo.setItems(alimentoObservableList);

        ArrayList<UnidadesCantidad> unidadesCantidadList = new ArrayList<>();
        unidadesCantidadList.add(UnidadesCantidad.KG);
        unidadesCantidadList.add(UnidadesCantidad.LITRO);
        unidadesCantidadList.add(UnidadesCantidad.Unidades);
        unidadesCombo.setItems(FXCollections.observableArrayList(unidadesCantidadList));

        unidadesCombo.getSelectionModel().select(0);
        DecimalFormat format = new DecimalFormat( "#.0" );
        precioText.setTextFormatter( new TextFormatter<>(c ->
        {
            if ( c.getControlNewText().isEmpty() )
            {
                return c;
            }

            ParsePosition parsePosition = new ParsePosition( 0 );
            Object object = format.parse( c.getControlNewText(), parsePosition );

            if ( object == null || parsePosition.getIndex() < c.getControlNewText().length() )
            {
                return null;
            }
            else
            {
                return c;
            }
        }));

        cantidadText.setTextFormatter( new TextFormatter<>(c ->
        {
            if ( c.getControlNewText().isEmpty() )
            {
                return c;
            }

            ParsePosition parsePosition = new ParsePosition( 0 );
            Object object = format.parse( c.getControlNewText(), parsePosition );

            if ( object == null || parsePosition.getIndex() < c.getControlNewText().length() )
            {
                return null;
            }
            else
            {
                return c;
            }
        }));

        stockText.setTextFormatter( new TextFormatter<>(c ->
        {
            if ( c.getControlNewText().isEmpty() )
            {
                return c;
            }

            ParsePosition parsePosition = new ParsePosition( 0 );
            Object object = format.parse( c.getControlNewText(), parsePosition );

            if ( object == null || parsePosition.getIndex() < c.getControlNewText().length() )
            {
                return null;
            }
            else
            {
                return c;
            }
        }));

        precioText.setText("0.0");
        cantidadText.setText("0");
        cantidadText.setText("0");
    }

    public void setProductoAlmacen(ProductoAlmacen productoAlmacen) {
        this.productoAlmacen = productoAlmacen;
        editarProducto = true;

        tituloLabel.setText("Editar Producto");
        nombreText.setText(productoAlmacen.getProducto().getNombre());
        alimentoCombo.getSelectionModel().select(productoAlmacen.getProducto().getAlimento());
        precioText.setText("" + productoAlmacen.getProducto().getPrecio());
        cantidadText.setText("" + productoAlmacen.getProducto().getCantidad());
        unidadesCombo.getSelectionModel().select(productoAlmacen.getProducto().getUnidades());
        stockText.setText("" + productoAlmacen.getStock());

    }

    @FXML
    public void OnClickAceptarButton() {
        if(noHayCamposVacios()) {
            ProductoAlmacenService almacenService = (ProductoAlmacenService) AppContext.getBean("productoAlmacenService");

            if (editarProducto) {
                productoAlmacen.getProducto().setNombre(precioText.getText());
                productoAlmacen.getProducto().setAlimento(alimentoCombo.getSelectionModel().getSelectedItem());
                productoAlmacen.getProducto().setCantidad(Double.parseDouble(cantidadText.getText()));

                almacenService.update(productoAlmacen);
            }
            else {
                productoAlmacen = new ProductoAlmacen(new Producto(
                        nombreText.getText(),
                        alimentoCombo.getSelectionModel().getSelectedItem(),
                        Double.parseDouble(precioText.getText()),
                        Double.parseDouble(cantidadText.getText()),
                        unidadesCombo.getSelectionModel().getSelectedItem()),
                        (int) Double.parseDouble(stockText.getText())
                );
                almacenService.add(productoAlmacen);
            }
            Stage stage = (Stage) cancelarButton.getScene().getWindow();
            stage.close();
        }

        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hay campos vacios");
            alert.setHeaderText("Hay Campos Vacios");
            alert.setContentText("Debe rellenar todos los campos para continuar.");
            alert.showAndWait();
        }
    }

    private boolean noHayCamposVacios() {
        return !nombreText.getText().isEmpty() && !precioText.getText().isEmpty() && !cantidadText.getText().isEmpty() && !stockText.getText().isEmpty();
    }

    @FXML
    public void OnCancelarClickButton() {
        Stage stage = (Stage) cancelarButton.getScene().getWindow();

        stage.close();
    }
}