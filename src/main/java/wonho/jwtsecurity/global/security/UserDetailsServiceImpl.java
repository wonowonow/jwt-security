package wonho.jwtsecurity.global.security;

import java.util.stream.Collector;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import wonho.jwtsecurity.service.member.domain.Member;
import wonho.jwtsecurity.service.member.domain.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("해당 유저는 존재하지 않습니다.")
        );

        return new UserDetailsImpl(
                member.getUsername(),
                member.getPassword(),
                member.getUserRoles().stream()
                        .map(userRole -> new SimpleGrantedAuthority(userRole.getAuthority().getAuthority().getAuthority()))
                        .collect(Collectors.toList())
        );
    }
}
