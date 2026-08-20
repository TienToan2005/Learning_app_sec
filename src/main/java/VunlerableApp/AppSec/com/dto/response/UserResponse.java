package VunlerableApp.AppSec.com.dto.response;

import VunlerableApp.AppSec.com.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String username;

    private String address;
    private LocalDate birthday;
    private UserRole role;

}
