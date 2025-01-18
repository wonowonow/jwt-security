package wonho.jwtsecurity.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException {

    private final HttpStatus status;
    private final String message;

    public BusinessException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.status = exceptionCode.getHttpStatus();
        this.message = exceptionCode.getMessage();
    }
}
