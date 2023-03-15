//package pl.projektaeh.systemaukcji;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@RestController
//@RequestMapping("/movies")
//public class ProductController {
//    @Autowired
//    ProductRepository productRepository;
//
//    @GetMapping("")
//    public List<Product> getAll() {
//        return productRepository.getAll();
//    }
//
//    @GetMapping("/{id}")
//    public Product getById(@PathVariable("id") int id) {
//        return productRepository.getById(id);
//    }
//
//    @PostMapping("")
//    public int add(@RequestBody List<Product> movies) {
//        return productRepository.save(movies);
//    }
//
//    @PutMapping("/{id}")
//    public int update(@PathVariable("id") int id, @RequestBody Product updatedProduct) {
//        Product product = productRepository.getById(id);
//
//        if (product != null) {
//            product.setName(updatedMovie.getName());
//            product.setRating(updatedMovie.getRating());
//
//            productRepository.update(product);
//
//            return 1;
//        } else {
//            return -1;
//        }
//    }
//
//    @PatchMapping("/{id}")
//    public int partiallyUpdate(@PathVariable("id") int id, @RequestBody Movie updatedMovie) {
//        Product product = productRepository.getById(id);
//
//        if (product != null) {
//            if (product.getName() != null) product.setName(updatedMovie.getName());
//            if (updatedMovie.getRating() > 0) product.setRating(updatedMovie.getRating());
//
//            productRepository.update(product);
//
//            return 1;
//        } else {
//            return -1;
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    public int delete(@PathVariable("id") int id) {
//        return productRepository.delete(id);
//    }
//}
