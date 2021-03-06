package restaurante.domain;

import domain.Persona;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Usuario extends Persona {
    private String direccion;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PedidoRestaurante> pedidosDelUsuario;

    public Usuario() {}

    public Usuario(String nombre, int dni, String direccion, String contraseña) {
        super(nombre, dni, contraseña);
        this.direccion = direccion;
        pedidosDelUsuario = new ArrayList<>();
    }

    public List<PedidoRestaurante> getPedidosDelUsuario() {
        return pedidosDelUsuario;
    }

    public void setPedidosDelUsuario(List<PedidoRestaurante> pedidosDelUsuario) {
        this.pedidosDelUsuario = pedidosDelUsuario;
    }

    public void addPedidoUsuario(PedidoRestaurante pedido){
        this.pedidosDelUsuario.add(pedido);
    }

    public String getDireccion() {
        return direccion;
    }
}
