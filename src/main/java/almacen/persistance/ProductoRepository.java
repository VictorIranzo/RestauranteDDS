package almacen.persistance;

import almacen.domain.Producto;
import domain.Alimento;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends CrudRepository<Producto, Long> {
    Iterable<Producto> findAllByAlimento(Alimento alimento);
}
