package VunlerableApp.AppSec.com.service;

import VunlerableApp.AppSec.com.dto.request.ProductRequest;
import VunlerableApp.AppSec.com.dto.response.ProductResponse;
import VunlerableApp.AppSec.com.enums.ErrorCode;
import VunlerableApp.AppSec.com.exception.AppException;
import VunlerableApp.AppSec.com.model.Product;
import VunlerableApp.AppSec.com.respository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    public ProductResponse createProduct(ProductRequest request){
        if (request.name().equals("Hack")) {
            throw new AppException(ErrorCode.PRODUCT_NAME_INVALID);
        }
        Product product = new Product();
        product.setName(request.name());

        Product saved = productRepository.save(product);

        return new ProductResponse(saved.getId(), saved.getName());
    }
    public List<ProductResponse> getAllProducts(){
        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(product -> new ProductResponse(
                        product.getId(),
                        product.getName()
                ))
                .collect(Collectors.toList());
    }
    public ProductResponse getProductById(Long id){
        Product product = productRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        return new ProductResponse(product.getId(),product.getName());
    }
    public ProductResponse updateProduct(Long id, ProductRequest request){
        Product product = productRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        product.setName(request.name());

        Product saved = productRepository.save(product);
        return new ProductResponse(saved.getId(), saved.getName());
    }
    public void deleteProduct(Long id){
        Product product = productRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        productRepository.delete(product);
    }
}
