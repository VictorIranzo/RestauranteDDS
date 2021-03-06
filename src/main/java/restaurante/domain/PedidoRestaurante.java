package restaurante.domain;

import restaurante.business.modelo.Patron_Estado.EstadoPedido;
import restaurante.business.modelo.Patron_Estado.EstadoPorConfirmar;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class PedidoRestaurante {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_fk")
    private Usuario usuario;

    @OneToOne(cascade =  CascadeType.ALL)
    @JoinColumn(name="estado_id")
    private EstadoPedido estado;

    private Date horaConfirmacion;
    private Date horaRecibido;
    private boolean estaCocinado;

    @OneToMany (mappedBy = "pedidoRestaurante",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Plato> platosPedido;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="reclamacion_id")
    private Reclamacion reclamacion;

    public PedidoRestaurante(Usuario usuario) {
        this.setUsuario(usuario);
        this.estado = new EstadoPorConfirmar();
        this.platosPedido = new ArrayList<Plato>();
        this.horaConfirmacion = null;
        this.horaRecibido = null;
        this.estaCocinado = false;
    }

    public PedidoRestaurante() {
        this.estado = new EstadoPorConfirmar();
        this.platosPedido = new ArrayList<Plato>();
        this.estaCocinado = false;
    }

    public  void confirmarPedido() throws Exception {
        if(platosPedido.size()<1) throw new Exception("Un pedido debe contener almenos 1 plato");
        estado.confirmarPedido(this);
    }

    public  void cancelarPedido() throws Exception {
        estado.cancelarPedido(this);
    }

    public Reclamacion reclamarRetraso() throws Exception {
        Reclamacion result = estado.reclamarRetraso(this);
        setReclamacion(result);
        return result;
    }

    public long getId() {
        return id;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public Date getHoraConfirmacion() {
        return horaConfirmacion;
    }

    public void setHoraConfirmacion(Date horaConfirmacion) {
        this.horaConfirmacion = horaConfirmacion;
    }

    public Date getHoraRecibido() {
        return horaRecibido;
    }

    public List<Plato> getPlatosPedido() {
        return platosPedido;
    }

    public void addPlatoPedido(Plato platoPedido) {
        this.platosPedido.add(platoPedido);
        platoPedido.setPedidoRestaurante(this);
    }

    public void deletePlatoPedido(Plato platoPedido){ this.platosPedido.remove(platoPedido);}

    public Reclamacion getReclamacion() {
        return reclamacion;
    }

    public void setReclamacion(Reclamacion reclamacion) {
        this.reclamacion = reclamacion;
    }

    public void setHoraRecibido(Date horaRecibido) {
        this.horaRecibido = horaRecibido;
    }

    @Override
    public String toString(){
        String s = new String();
        for (Plato plato:platosPedido) {
            s+= plato.getDescripcion() + '\n';
        }
        return s;
    }

    public double getPrecio(){
        double result = 0;
        for (Plato plato:platosPedido) {
           result+= plato.getPrecio();
        }
        return result;
    }

    public double getCalorias(){
        double result = 0;
        for (Plato plato:platosPedido) {
            result+= plato.getCalorias();
        }
        return result;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        usuario.addPedidoUsuario(this);
    }

    public boolean isCocinado() {
        return estaCocinado;
    }

    public void setEstaCocinado(boolean estaCocinado) {
        this.estaCocinado = estaCocinado;
    }
}
