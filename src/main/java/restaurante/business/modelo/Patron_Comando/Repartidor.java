package restaurante.business.modelo.Patron_Comando;

import domain.Persona;
import restaurante.business.modelo.Patron_Estado.EstadoEnCamino;
import restaurante.business.modelo.Patron_Estado.EstadoEntregado;
import restaurante.domain.PedidoRestaurante;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Repartidor extends Persona {
    private boolean disponible;

    @OneToOne(cascade = CascadeType.ALL)
    private PedidoRestaurante pedidoAtendiendo;

    public Repartidor() {
    }

    public Repartidor(String nombre, int dni, String contraseña) {
        super(nombre,dni, contraseña);
        this.disponible=true;
        EmisorOrdenes.getEmisorOrdenes().registrarRepartidor(this);
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public void realizarEnvio(PedidoRestaurante pedido){
        pedido.setEstado(new EstadoEnCamino());
        this.pedidoAtendiendo = pedido;
    }

    public void finalizarEnvio(){
        if(pedidoAtendiendo != null) this.pedidoAtendiendo.setEstado(new EstadoEntregado());
        this.pedidoAtendiendo = null;
        this.disponible=true;
        EmisorOrdenes.getEmisorOrdenes().registrarRepartidor(this);
    }

    public PedidoRestaurante getPedidoAtendiendo() {
        return pedidoAtendiendo;
    }

    public void setPedidoAtendiendo(PedidoRestaurante pedidoAtendiendo) {
        this.pedidoAtendiendo = pedidoAtendiendo;
    }
}
