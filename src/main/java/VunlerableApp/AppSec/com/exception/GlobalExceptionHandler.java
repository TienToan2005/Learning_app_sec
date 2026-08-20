package VunlerableApp.AppSec.com.exception;

import VunlerableApp.AppSec.com.dto.response.ApiResponse;
import VunlerableApp.AppSec.com.enums.ErrorCode;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = AppException.class)
    public ApiResponse<Object> AppExceptionHandler(AppException exception){
        ErrorCode errorCode = exception.getErrorCode();
        return ApiResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
    }
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ApiResponse<Object> handlingValidation(MethodArgumentNotValidException exception){
        String message = exception.getFieldError().getDefaultMessage();
        return ApiResponse.builder()
                .code(1001)
                .message(message)
                .build();
    }
    @ExceptionHandler(value = RuntimeException.class)
    public ApiResponse<Object> RuntimeExceptionHandler(RuntimeException exception){
        return ApiResponse.builder()
                .code(9999)
                .message(exception.getMessage())
                .build();
    }
}
