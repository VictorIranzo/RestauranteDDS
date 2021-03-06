package almacen.pedidos.domain;

import almacen.pedidos.util.ListaCompraIterator;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Entity
@PrimaryKeyJoinColumn(name = "ID")
public class ListaCompuesto extends ListaCompra {
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<ListaCompra> hijos;

    public ListaCompuesto() {}

    public ListaCompuesto(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        hijos = new ArrayList<>();
    }

    @Override
    public double getPrecio() {
        double precio = 0;
        for (ListaCompra elemento: hijos) {
            precio += elemento.getPrecio();
        }
        return precio;
    }

    @Override
    public void add(ListaCompra p) {
        hijos.add(p);
    }

    @Override
    public void remove(ListaCompra p) {
        hijos.remove(p);
    }

    @Override
    public Iterator<ListaCompra> createIterator() {
        return new ListaCompraIterator(hijos.iterator());
    }

    private void setHijos(List<ListaCompra> hijos) {
        this.hijos = hijos;
    }

    private List<ListaCompra> clonarHijos() throws CloneNotSupportedException {
        List<ListaCompra> clon = new ArrayList<>();
        for(ListaCompra elem : hijos){
            clon.add(elem.clone());
        }
        return clon;
    }

    public ListaCompra clone() throws CloneNotSupportedException {
        ListaCompuesto listaCompuesto = new ListaCompuesto(this.nombre, this.descripcion);
        listaCompuesto.setHijos(clonarHijos());
        return listaCompuesto;
    }

    public String toString() {
        return "Lista: " + nombre + " - " + descripcion + " - " + getPrecio() +"€";
    }
}
