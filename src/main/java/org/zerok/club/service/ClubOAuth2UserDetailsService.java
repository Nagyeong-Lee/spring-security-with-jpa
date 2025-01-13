package org.zerok.club.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.zerok.club.dto.ClubAuthMemberDTO;
import org.zerok.club.entity.ClubMember;
import org.zerok.club.entity.ClubMemberRole;
import org.zerok.club.repository.ClubMemberRepository;

import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClubOAuth2UserDetailsService extends DefaultOAuth2UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final ClubMemberRepository clubMemberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("userRequest : {}", userRequest.toString());

        String clientName = userRequest.getClientRegistration().getClientName();
        OAuth2User oAuth2User = super.loadUser(userRequest); //user 정보가 리턴됨

        String email = "";
        if ("Google".equals(clientName)) {
            email = oAuth2User.getAttribute("email");
        }

        ClubMember clubMember = saveSocailMember(email);
        ClubAuthMemberDTO clubAuthMemberDTO = new ClubAuthMemberDTO(
                clubMember.getEmail(),
                clubMember.getPassword(),
                clubMember.getRoleSet().stream()
                        .map(r -> new SimpleGrantedAuthority("ROLE_" + r.name()))
                        .collect(Collectors.toSet()),
                clubMember.getName(),
                clubMember.isFromSocial(),
                oAuth2User.getAttributes() //구글에서 반환받은 user 정보들
        );

        return clubAuthMemberDTO;
    }

    private ClubMember saveSocailMember(String email) {
        Optional<ClubMember> result = clubMemberRepository.findByEmailAndFromSocial(email, true);
        if (result.isPresent()) {
            return result.get();
        }

        ClubMember clubMember = ClubMember.builder()
                .email(email)
                .password(bCryptPasswordEncoder.encode("1111"))
                .fromSocial(true)
                .build();
        clubMember.addMemberRole(ClubMemberRole.USER);

        clubMemberRepository.save(clubMember);
        return clubMember;
    }
}
