package VunlerableApp.AppSec.com.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private int code = 1000;

    @Builder.Default
    private String message = "Success";
    private T data;

}
