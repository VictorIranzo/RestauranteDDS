package restaurante.business.modelo.Patron_Estado;

import restaurante.domain.PedidoRestaurante;
import restaurante.domain.Reclamacion;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@DiscriminatorValue("Cocinandose")
public class EstadoCocinandose extends EstadoPedido {

    public EstadoCocinandose() {
        super("Pedido elaborándose en Cocina.");
    }

    @Override
    public void confirmarPedido(PedidoRestaurante pedido) throws Exception {
        throw new Exception("El pedido ya ha sido confirmado.");
    }

    @Override
    public void cancelarPedido(PedidoRestaurante pedido) throws Exception {
        throw new Exception("El pedido ya está cocinándose y no se puede cancelar.");
    }

    @Override
    public Reclamacion reclamarRetraso(PedidoRestaurante pedido) throws Exception {
        Date horaActual = new Date();
        long diferenciaEnMinutos = (horaActual.getTime() - pedido.getHoraConfirmacion().getTime()) / 60000;
        if(diferenciaEnMinutos>30.0){
            return new Reclamacion(horaActual,pedido);
        }
        else{
            throw new Exception("No puede reclamar hasta que no pasen 30 minutos desde la confirmación de su pedido.");
        }
    }
}
