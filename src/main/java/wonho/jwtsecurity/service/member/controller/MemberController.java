package wonho.jwtsecurity.service.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import wonho.jwtsecurity.service.member.dto.req.MemberCreateRequestDto;
import wonho.jwtsecurity.service.member.dto.req.MemberLoginRequestDto;
import wonho.jwtsecurity.service.member.dto.req.RefreshTokenRequestDto;
import wonho.jwtsecurity.service.member.dto.res.MemberResponseDto;
import wonho.jwtsecurity.service.member.dto.res.AllTokenResponseDto;
import wonho.jwtsecurity.service.member.service.interfaces.MemberService;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDto> signUp(
            @RequestBody final MemberCreateRequestDto memberCreateRequestDto
    ) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(memberService.signUp(memberCreateRequestDto));
    }

    @PostMapping("/sign")
    public ResponseEntity<AllTokenResponseDto> sign(
            @RequestBody final MemberLoginRequestDto memberLoginRequestDto
    ) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(memberService.sign(memberLoginRequestDto));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AllTokenResponseDto> refreshToken(
            @RequestBody final RefreshTokenRequestDto requestDto
    ) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(memberService.refreshToken(requestDto.refreshToken()));
    }
}
