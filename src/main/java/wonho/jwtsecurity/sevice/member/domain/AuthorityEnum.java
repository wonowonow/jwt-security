package wonho.jwtsecurity.sevice.member.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthorityEnum {

    ROLE_USER("ROLE_USER");

    private final String authority;
}
