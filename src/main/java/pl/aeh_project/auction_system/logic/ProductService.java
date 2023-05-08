package pl.aeh_project.auction_system.logic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.aeh_project.auction_system.api.dto.productDto.ModifiedProductDto;
import pl.aeh_project.auction_system.api.dto.productDto.BidProductDto;
import pl.aeh_project.auction_system.api.dto.productDto.AddProductDto;
import pl.aeh_project.auction_system.domain.entity.Product;
import pl.aeh_project.auction_system.domain.entity.User;
import pl.aeh_project.auction_system.domain.repository.ProductRepository;
import pl.aeh_project.auction_system.domain.repository.UserRepository;
import pl.aeh_project.auction_system.exceptions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
/* Klasa service to klasa, która oferuje logikę biznesową, która jest wykorzystywana w klasach controller */
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;


    /* Pobieranie wszystkich produktów w postaci listy */
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    /* ------------------------------------ */

    /* Pobieranie produktu po id */
    public Product get(Long id) {
        return convertIdToProductEntity(id);
    }

    /* Metoda pomocnicza dla metody get */
    /* Konwertowanie id do product */
    private Product convertIdToProductEntity(Long id){
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isEmpty()){
            throw new NoProductException("There is no such product");
        }
        return optionalProduct.get();
    }

    /* ------------------------------------ */

    /* Dodawanie produktu */
    public void saveProductDto(AddProductDto productDTO) {
        Product product = convertProductDtoToEntity(productDTO);
        productRepository.save(product);
    }

    /* Metoda pomocnicza dla metody saveProductDto */
    /* Konwertowanie convertProductDto do product */
    private Product convertProductDtoToEntity(AddProductDto productDTO){
        User user = getUser(productDTO.getLogin());

        Product product = new Product();

        product.setUserId(user.getUserId());
        product.setTitle(productDTO.getTitle());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setEndDate(productDTO.getEndDate());

        return product;
    }

    /* ------------------------------------ */

    /* Modyfikowanie produktu */
    public void saveModifiedProductDto(ModifiedProductDto modifiedProductDTO){
        Product product = convertModifiedProductDtoToEntity(modifiedProductDTO);
        productRepository.save(product);
    }

    /* Metoda pomocnicza dla metody saveModifiedProductDto */
    /* Konwertowanie modifiedProductDto do product */
    private Product convertModifiedProductDtoToEntity(ModifiedProductDto modifyProductDTO) {
        User user = getUser(modifyProductDTO.getLogin());

        Product product = new Product();

        product.setProductId(modifyProductDTO.getProductId());
        product.setUserId(user.getUserId());
        product.setTitle(modifyProductDTO.getTitle());
        product.setDescription(modifyProductDTO.getDescription());
        product.setPrice(modifyProductDTO.getPrice());
        product.setEndDate(modifyProductDTO.getEndDate());

        return product;

    }

    /* ------------------------------------ */

    public void saveNewPriceDto(BidProductDto newPriceDTO){
        Product product = convertNewPriceDto(newPriceDTO);
        productRepository.save(product);
    }

    /* Metoda pomocnicza dla metody saveNewPriceDto */
    /* Konwertowanie newPriceDTO do product */
    private Product convertNewPriceDto(BidProductDto bidProductDto) {
        User user = getUser(bidProductDto.getLogin());

        Product product = get(bidProductDto.getProductId());

        checkProductConditions(product, user, bidProductDto);

        product.setPrice(bidProductDto.getNewProductPrice());
        product.setCustomerId(user.getUserId());

        return product;
    }

    /* Metoda pomocnicza dla metody convertNewPriceDto */
    /* Sprawdzanie warunków */
    private void checkProductConditions(Product product, User user, BidProductDto bidProductDto) {
        if(product.getCustomerId().equals(user.getUserId())){
            throw new DoubleBiddingException("User beats the offer after himself");
        }
        if(product.getEndDate().isBefore(LocalDate.now())){
            throw new EndOfAuctionException("Auction time has passed");
        }

        if (product.getPrice().compareTo(bidProductDto.getNewProductPrice()) >= 0) {
            throw new WrongNewPriceException("New price is lower than current price");
        }
    }

    /* ------------------------------------ */

    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    /* ------------------------------------ */

    /* Metoda pomocnicza */
    /* Konwertuje login do user */
    private User getUser(String login){
        Optional<User> optionalUser = userRepository.findByLogin(login);
        if(optionalUser.isEmpty()){
            throw new NoUserException("There is no user with such login");
        }
        return optionalUser.get();
    }


}
