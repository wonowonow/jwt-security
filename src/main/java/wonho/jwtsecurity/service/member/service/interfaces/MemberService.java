package wonho.jwtsecurity.service.member.service.interfaces;

import wonho.jwtsecurity.service.member.dto.req.MemberCreateRequestDto;
import wonho.jwtsecurity.service.member.dto.req.MemberLoginRequestDto;
import wonho.jwtsecurity.service.member.dto.res.MemberResponseDto;
import wonho.jwtsecurity.service.member.dto.res.TokenResponseDto;

public interface MemberService {
    /**
 * Registers a new member in the system using the provided member creation request details.
 *
 * @param requestDto The data transfer object containing member registration information
 * @return A response DTO representing the newly created member's details
 * @throws IllegalArgumentException If the registration request contains invalid or incomplete data
 * @throws DuplicateMemberException If a member with the same identifier already exists
 */
MemberResponseDto signUp(MemberCreateRequestDto requestDto);

    /**
 * Authenticates a member and generates an authentication token.
 *
 * @param requestDto The login request containing member credentials
 * @return A token response containing authentication credentials for the logged-in member
 * @throws AuthenticationException If login credentials are invalid
 * @throws MemberNotFoundException If no member is found with the provided credentials
 */
TokenResponseDto sign(MemberLoginRequestDto requestDto);
}
