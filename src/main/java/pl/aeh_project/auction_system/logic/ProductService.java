package pl.aeh_project.auction_system.logic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.aeh_project.auction_system.domain.entity.Product;
import pl.aeh_project.auction_system.domain.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
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

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
