package VunlerableApp.AppSec.com.controller;

import VunlerableApp.AppSec.com.dto.request.ProductRequest;
import VunlerableApp.AppSec.com.dto.response.ApiResponse;
import VunlerableApp.AppSec.com.dto.response.ProductResponse;
import VunlerableApp.AppSec.com.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ApiResponse<ProductResponse> createProduct(@RequestBody  @Valid ProductRequest request){
        return ApiResponse.<ProductResponse>builder()
                .message("Tạo sản phẩm thành công")
                .data(productService.createProduct(request))
                .build();
    }
    @GetMapping
    public ApiResponse<List<ProductResponse>> getAllProducts(){
        return ApiResponse.<List<ProductResponse>>builder()
                .data(productService.getAllProducts())
                .build();
    }
    @GetMapping("/{id}")
    public ApiResponse<ProductResponse> getProductById(@PathVariable Long id){
        return ApiResponse.<ProductResponse>builder()
                .data(productService.getProductById(id))
                .build();
    }
    @PutMapping("/{id}")
    public ApiResponse<ProductResponse> updateProduct(@PathVariable Long id, @RequestBody @Valid ProductRequest request){
        return ApiResponse.<ProductResponse>builder()
                .message("Thay đổi sản phẩm thành công")
                .data(productService.updateProduct(id, request))
                .build();
    }
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ApiResponse.<Void>builder()
                .message("Xoá sản phẩm thành công")
                .build();
    }

}
