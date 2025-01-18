package wonho.jwtsecurity.global.exception;

import static org.springframework.http.HttpStatus.*;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExceptionCode {

    NOT_FOUND_ROLE(NOT_FOUND, "존재하지 않는 권한입니다."),
    DUPLICATED_USER(BAD_REQUEST, "이미 존재하는 아이디 입니다."),
    NOT_FOUND_USER(NOT_FOUND, "존재하지 않는 아이디입니다."),
    NOT_MATCH_PASSWORD(BAD_REQUEST, "비밀번호가 일치하지 않습니다."),

    NOT_FOUND_REFRESH_TOKEN(NOT_FOUND, "리프레시 토큰이 존재하지 않습니다."),
    INVALID_REFRESH_TOKEN(BAD_REQUEST, "리프레시 토큰이 유효하지 않습니다.");


    private final HttpStatus httpStatus;
    private final String message;
}
