package pl.aeh_project.auction_system.api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RequestMapping;
import pl.aeh_project.auction_system.domain.entity.Product;
import pl.aeh_project.auction_system.domain.entity.User;
import pl.aeh_project.auction_system.exceptions.EndOfAuctionException;
import pl.aeh_project.auction_system.exceptions.NoProductException;
import pl.aeh_project.auction_system.exceptions.UnloggedUserException;
import pl.aeh_project.auction_system.exceptions.WrongNewPriceException;
import pl.aeh_project.auction_system.logic.ProductService;
import pl.aeh_project.auction_system.logic.UserService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/product")
@RequiredArgsConstructor

/* Controller - klasa, która obsługuje zapytania wysyłane przez przeglądarkę do użytkownika */
public class ProductController {

    @Autowired
    private final ProductService productService;

    @Autowired
    private final UserService userService;


    /* Pobierz wszystkie produkty */
    @GetMapping("/getAll")
    public List<Product> getAll() {
        return productService.getAll();
    }

    /* Pobierz produkt po id */
    @GetMapping("/{id}")
    public Product getById(@PathVariable("id") Long id) {
        Optional<Product> product = productService.getById(id);
        if(product.isEmpty()){
            throw new NoProductException();
        }
        return product.get();
    }

    /* Dodaj produkt */
    @PostMapping("/add")
    public void add(@RequestBody List<Product> products) {
        productService.saveAll(products);
    }

    /* Modyfikuj produkt */
    @PutMapping("/modify")
    public void update(@RequestBody Product updatedProduct) {
        Optional<Product> product = productService.getById(updatedProduct.getProductId());
        if (product.isPresent()) {
            productService.save(updatedProduct);
        } else {
            throw new IllegalArgumentException("Product is null");
        }
    }

    /* Przebijanie oferty */
    @PutMapping("/setNewPrice")
    public void setNewPrice(@RequestBody Product updatedProduct, BigDecimal newPrice, User user) {
        if(userService.checkSession(user.getLogin(), user.getSessionKey()).isEmpty()){
            throw new UnloggedUserException();
        }
        if(newPrice.compareTo(updatedProduct.getPrice()) <= 0){
            throw new WrongNewPriceException();
        }
        if(updatedProduct.getEndDate().compareTo(LocalDate.now()) < 0){
            throw new EndOfAuctionException();
        }
        Optional<Product> product = productService.getById(updatedProduct.getProductId());
        if (product.isEmpty()) {
            throw new NoProductException();
        }
        updatedProduct.setPrice(newPrice);
        productService.save(updatedProduct);
    }

    /* Usuń produkt */
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        productService.delete(id);
    }
}
