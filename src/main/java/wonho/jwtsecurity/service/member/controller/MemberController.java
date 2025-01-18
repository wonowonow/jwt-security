package wonho.jwtsecurity.service.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import wonho.jwtsecurity.service.member.dto.req.MemberCreateRequestDto;
import wonho.jwtsecurity.service.member.dto.req.MemberLoginRequestDto;
import wonho.jwtsecurity.service.member.dto.res.MemberResponseDto;
import wonho.jwtsecurity.service.member.dto.res.TokenResponseDto;
import wonho.jwtsecurity.service.member.service.interfaces.MemberService;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * Handles user registration by processing a signup request.
     *
     * @param memberCreateRequestDto The data transfer object containing user registration details
     * @return A ResponseEntity with a 201 Created status and the created member's details
     */
    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDto> signUp(
            @RequestBody final MemberCreateRequestDto memberCreateRequestDto
    ) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(memberService.signUp(memberCreateRequestDto));
    }

    /**
     * Handles user login by processing the provided login credentials.
     *
     * @param memberLoginRequestDto The login request containing user credentials
     * @return A ResponseEntity with a TokenResponseDto containing authentication tokens
     */
    @PostMapping("/sign")
    public ResponseEntity<TokenResponseDto> sign(
            @RequestBody final MemberLoginRequestDto memberLoginRequestDto
    ) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(memberService.sign(memberLoginRequestDto));
    }
}
