package VunlerableApp.AppSec.com.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record RegisterRequest(
        @NotBlank
        String username,
        @NotBlank
        @Size(min = 8)
        String password,
        String address,
        LocalDate birthday
) {
}
