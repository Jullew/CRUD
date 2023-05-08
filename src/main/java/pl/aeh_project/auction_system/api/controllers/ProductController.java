package pl.aeh_project.auction_system.api.controllers;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import pl.aeh_project.auction_system.api.dto.productDto.ModifiedProductDto;
import pl.aeh_project.auction_system.api.dto.productDto.BidProductDto;
import pl.aeh_project.auction_system.api.dto.productDto.AddProductDto;
import pl.aeh_project.auction_system.domain.entity.Product;
import pl.aeh_project.auction_system.logic.ProductService;
import pl.aeh_project.auction_system.logic.UserService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/product")
@RequiredArgsConstructor

/* Controller - klasa, która obsługuje zapytania wysyłane przez przeglądarkę do użytkownika */
public class ProductController {

    private final ProductService productService;

    private final UserService userService;


    /**
     * Pobieranie wszystkich produktów
     * WYMAGANE DANE:
     * brak;
     */
    @GetMapping("/getAll")
    public List<Product> getAll() {
        return productService.getAll();
    }

    /**
     * Pobieranie produktu
     * WYMAGANE DANE:
     * Long id;
     */
    /* Zmieniłbym na get/{id} */
    @GetMapping("/{id}")
    public Product get(@PathVariable("id") Long id) {
        return productService.get(id);
    }

    /**
     * Dodawanie produktu
     * WYMAGANE DANE w ProductDto:
     * String login;
     * String sessionKey;
     * String title;
     * String description;
     * BigDecimal price;
     * LocalDate endDate;
     */
    @PostMapping("/add")
    public void add(@RequestBody @NonNull AddProductDto product) {
        userService.loginVerification(product.getLogin(), product.getSessionKey());
        productService.saveProductDto(product);
    }

    /**
     * Modyfikacja produktu
     * WYMAGANE DANE w ModifyProductDto:
     *Long productId;
     *String login;
     *String sessionKey;
     *String title;
     *String description;
     *BigDecimal price;
     *LocalDate endDate;
     */
    @PutMapping("/modify")
    public void update(@RequestBody @NonNull ModifiedProductDto product) {
        userService.loginVerification(product.getLogin(), product.getSessionKey());
        productService.saveModifiedProductDto(product);
    }

    /**
     * Przebijanie oferty
     * WYMAGANE DANE w NewPriceDto:
     * String login;
     * String sessionKey;
     * Long productId;
     * BigDecimal newProductPrice;
     */
    /* zmieniłbym na /bid */
    @PostMapping("/setNewPrice")
    public void bid(@RequestBody @NonNull BidProductDto product) {
        userService.loginVerification(product.getLogin(), product.getSessionKey());
        productService.saveNewPriceDto(product);
    }

    /**
     * Usuwanie produktu
     * WYMAGANE DANE:
     * Long id;
     */
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        productService.delete(id);
    }
}
