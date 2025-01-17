package wonho.jwtsecurity.sevice.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import wonho.jwtsecurity.sevice.member.dto.req.MemberCreateRequestDto;
import wonho.jwtsecurity.sevice.member.dto.res.MemberResponseDto;
import wonho.jwtsecurity.sevice.member.service.interfaces.MemberService;

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
}
