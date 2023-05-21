package pl.aeh_project.auction_system.logic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.aeh_project.auction_system.api.dto.productDto.ModifiedProductDto;
import pl.aeh_project.auction_system.api.dto.productDto.BidProductDto;
import pl.aeh_project.auction_system.api.dto.productDto.AddProductDto;
import pl.aeh_project.auction_system.api.dto.productDto.ProductDto;
import pl.aeh_project.auction_system.domain.entity.Product;
import pl.aeh_project.auction_system.domain.entity.User;
import pl.aeh_project.auction_system.domain.repository.ProductRepository;
import pl.aeh_project.auction_system.domain.repository.UserRepository;
import pl.aeh_project.auction_system.exceptions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
/* Klasa service to klasa, która oferuje logikę biznesową, która jest wykorzystywana w klasach controller */
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;


    /* Pobieranie wszystkich produktów w postaci listy */
    public List<ProductDto> getAll() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productsDto = new LinkedList<>();
        for(var product : products){
            Optional<User> optionalUser = userRepository.findUserByUserId(product.getCustomerId());
            if(optionalUser.isEmpty()){
                productsDto.add(
                        new ProductDto(
                                product.getProductId(),
                                product.getUserId(),
                                product.getTitle(),
                                product.getDescription(),
                                product.getPrice(),
                                product.getCustomerId(),
                                "",
                                "",
                                product.getEndDate()
                                )
                );
            }else{
                User user = optionalUser.get();
                productsDto.add(
                        new ProductDto(
                                product.getProductId(),
                                product.getUserId(),
                                product.getTitle(),
                                product.getDescription(),
                                product.getPrice(),
                                product.getCustomerId(),
                                user.getFirstName(),
                                user.getLastName(),
                                product.getEndDate()
                                )
                );
            }
        }
        return productsDto;
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
            throw new NoProductException("There is no product with such id");
        }
        return optionalProduct.get();
    }

    /* ------------------------------------ */

    /* Pobieranie wszystkich produktów danego użytkownika */
    public List<Product> getAllProductsByUserId(Long id){
        return productRepository.findAllByUserId(id);
    }

    /* ------------------------------------ */

    /* Pobieranie wylicytowanych przez użytkownika produktów */
    public List<Product> getPurchasedProducts(Long id){
        return productRepository.findAllByCustomerId(id)
                .stream()
                .filter(product -> product.getEndDate().isBefore(LocalDate.now()))
                .toList();
    }

    /* ------------------------------------ */

    /* Pobieranie licytowanych przez użytkownika produktów */
    public List<Product> getAuctionedProducts(Long id){
        return productRepository.findAllByCustomerId(id)
                .stream()
                .filter(product -> product.getEndDate().isAfter(LocalDate.now()))
                .toList();
    }

    /* ------------------------------------ */

    /* Dodawanie produktu */
    public void saveProductDto(AddProductDto productDTO) {
        Product product = convertProductDtoToEntity(productDTO);
        product.setCustomerId(0L);
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

    /* Modyfikowanie produktu po przebiciu ceny */
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
        if(product.getCustomerId() != null && product.getCustomerId().equals(user.getUserId())){
            throw new DoubleBiddingException("You are bidding on yourself");
        }
        if(product.getUserId().equals(user.getUserId())){
            throw new OwnAuctionBidding("You are bidding on your own auction");
        }
        if(product.getEndDate().isBefore(LocalDate.now())){
            throw new EndOfAuctionException("Auction time has passed");
        }

        if (product.getPrice().compareTo(bidProductDto.getNewProductPrice()) >= 0) {
            throw new WrongNewPriceException("New price is lower than current price");
        }
    }

    /* ------------------------------------ */

    /* Usuwanie produktu po id */
    public void delete(Long id) {
        checkingConditionsDuringProductRemoval(id);
        productRepository.deleteById(id);
    }

    /* Metoda pomocnicza do metody delete */
    /* Sprawdzenie, czy użytkownik, który jest właścicielem produktu jest zalogowany */
    private void checkingConditionsDuringProductRemoval(Long id){
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isEmpty()){
            throw new NoProductException("There is no product with such id");
        }
        Product product = optionalProduct.get();
        Optional<User> optionalUser = userRepository.findUserByUserId(product.getUserId());
        if(optionalUser.isEmpty()){
            throw new NoUserException("There is no user with such id");
        }
        User user = optionalUser.get();
        if(userRepository.findUserByUserIdAndSessionKeyAndSessionEndIsAfter(user.getUserId(), user.getSessionKey(), LocalDateTime.now()).isEmpty()){
            throw new UnloggedUserException("You are logged out");
        }
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
