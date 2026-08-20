package VunlerableApp.AppSec.com.enums;

import lombok.Getter;

@Getter
public enum ErrorCode {
    PRODUCT_NOT_FOUND(2001, "Sản phẩm không tồn tại"),
    PRODUCT_NAME_INVALID(2002, "Tên sản phẩm không hợp lệ"),
    UNAUTHORIZED(2003, "Bạn không có quyền!"),
    USER_NOT_FOUND(2004, "User không tồn tại"),
    USER_ALREADY_EXISTS(2005,"User đã tồn tại"),
    WRONG_PASSWORD(2006,"Mật khẩu sai!"),
    INVALID_TOKEN(2007,"INVALID_TOKEN"),
    TOKEN_REVOKED(2008,"TOKEN_REVOKED"),
    TOKEN_EXPIRED(2009,"TOKEN_EXPIRED"),

    UNCATEGORIZED(9999, "Lỗi không xác định");

    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
