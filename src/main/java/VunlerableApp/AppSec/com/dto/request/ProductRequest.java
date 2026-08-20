package VunlerableApp.AppSec.com.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProductRequest(
        @NotBlank(message = "Tên sản phẩm không được để trống")
        @Size(min = 2, max = 100, message = "Tên sản phẩm phải từ 2 đến 100 ký tự")
        String name
) { }
