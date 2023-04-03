package pl.aeh_project.auction_system.logic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.aeh_project.auction_system.domain.entity.Product;
import pl.aeh_project.auction_system.domain.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

/* Klasa service to klasa, która oferuje logikę biznesową, która jest wykorzystywana w klasach controller */
public class ProductService {

    private final ProductRepository productRepository;


    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Optional<Product> getById(Long id) {
        return productRepository.findById(id);
    }

    public void saveAll(List<Product> products) {
        productRepository.saveAll(products);
    }

    public void save(Product product) {
        productRepository.save(product);
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
