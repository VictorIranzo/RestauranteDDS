package restaurante.business.modelo.Patron_Estado;

import restaurante.domain.PedidoRestaurante;
import restaurante.domain.Reclamacion;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;
@Entity
@DiscriminatorValue("Entregado")
public class EstadoEntregado extends EstadoPedido {

    public EstadoEntregado() {
        super("Pedido recibido.");
    }

    @Override
    public void confirmarPedido(PedidoRestaurante pedido) throws Exception {
        throw new Exception("El pedido ya ha sido recibido");
    }

    @Override
    public void cancelarPedido(PedidoRestaurante pedido) throws Exception {
        throw new Exception("El pedido ya ha sido recibido");
    }

    @Override
    public Reclamacion reclamarRetraso(PedidoRestaurante pedido) throws Exception {
        Date horaRecibido = pedido.getHoraRecibido();
        long diferenciaEnMinutos = (horaRecibido.getTime() - pedido.getHoraConfirmacion().getTime()) / 60000;
        if(diferenciaEnMinutos>30.0){
            return new Reclamacion(horaRecibido,pedido);
        }
        else{
            throw new Exception("No puede reclamar si no hay una diferencia de 30 minutos entre su pedido y la recepción de éste.");
        }
    }
}
