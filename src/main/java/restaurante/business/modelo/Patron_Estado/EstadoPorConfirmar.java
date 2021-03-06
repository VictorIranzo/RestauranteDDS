package restaurante.business.modelo.Patron_Estado;

import almacen.business.controllers.AlimentoController;
import domain.Alimento;
import restaurante.domain.PedidoRestaurante;
import restaurante.domain.Reclamacion;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@DiscriminatorValue("PorConfirmar")
public class EstadoPorConfirmar extends EstadoPedido {

    public EstadoPorConfirmar() {
        super("Pedido todavía realizándose.");
    }

    @Override
    public void confirmarPedido(PedidoRestaurante pedido) throws Exception {
        AlimentoController alimentoController = AlimentoController.getInstance();

        List<Alimento> alimentosDelPedido = new ArrayList<Alimento>();
        pedido.getPlatosPedido().forEach(plato-> plato.getAlimentosPlato().forEach(alimentoPlato -> alimentosDelPedido.add(alimentoPlato)));

        alimentoController.consumirSiSePuedeAlimentos(alimentosDelPedido);

        pedido.setEstado(new EstadoPendiente());
        pedido.setHoraConfirmacion(new Date()); //La hora de confirmación es la actual
    }

    @Override
    public void cancelarPedido(PedidoRestaurante pedido) throws Exception {
        pedido.setEstado(new EstadoCancelado());
    }

    @Override
    public Reclamacion reclamarRetraso(PedidoRestaurante pedido) throws Exception {
        throw new Exception("El pedido todavía no se ha realizado.");
    }
}
