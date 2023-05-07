package pl.aeh_project.auction_system.api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RequestMapping;
import pl.aeh_project.auction_system.api.dto.ModifyProductDTO;
import pl.aeh_project.auction_system.api.dto.NewPriceDTO;
import pl.aeh_project.auction_system.api.dto.ProductDTO;
import pl.aeh_project.auction_system.domain.entity.Product;
import pl.aeh_project.auction_system.domain.entity.User;
import pl.aeh_project.auction_system.exceptions.*;
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
            throw new NoProductException("There is no such product");
        }
        return product.get();
    }

    /* Dodaj produkt */
    @PostMapping("/add")
    public void add(@RequestBody ProductDTO productDTO) {
        productService.save(productService.convertDTOToEntity(productDTO));
    }

    /* Modyfikuj produkt */
    @PutMapping("/modify")
    public void update(@RequestBody ModifyProductDTO modifyProductDTO) {
        Optional<Product> product = productService.getById(modifyProductDTO.getProductId());
        if(product.isEmpty()){
            throw new NoProductException("There is no such product");
        }

        productService.save(productService.convertDTOToEntity(
                new ProductDTO(
                modifyProductDTO.getUserId(),
                modifyProductDTO.getTitle(),
                modifyProductDTO.getDescription(),
                modifyProductDTO.getPrice(),
                modifyProductDTO.getEndDate()
        )
        ));
    }

    /* Przebijanie oferty */
    @PostMapping("/setNewPrice")
    public void setNewPrice(@RequestBody NewPriceDTO newPriceDTO) {
        Optional<User> optionalUser = userService.checkSession(newPriceDTO.getLogin(), newPriceDTO.getSessionKey());
        Optional<Product> productOptional = productService.getById(newPriceDTO.getProductId());

        if(optionalUser.isEmpty()){
            throw new UnloggedUserException("You are not logged in");
        }
        if(productOptional.isEmpty()){
            throw new NoProductException("There is no such product");
        }

        User user = optionalUser.get();
        Product product = productOptional.get();

        if(product.getCustomerId().equals(user.getUserId())){
            throw new DoubleBiddingException("User beats the offer after himself");
        }

        if(product.getEndDate().isBefore(LocalDate.now())){
            throw new EndOfAuctionException("Auction time has passed");
        }
        if (product.getPrice().compareTo(newPriceDTO.getNewProductPrice()) >= 0) {
            throw new WrongNewPriceException("New price is lower than current price");
        }

        product.setPrice(newPriceDTO.getNewProductPrice());
        product.setCustomerId(user.getUserId());
        productService.save(product);
    }

    /* Usuń produkt */
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        productService.delete(id);
    }
}
