package org.zerok.club.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.zerok.club.dto.ClubAuthMemberDTO;
import org.zerok.club.entity.ClubMember;
import org.zerok.club.repository.ClubMemberRepository;

import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClubUserDetailsService implements UserDetailsService {

    private final ClubMemberRepository clubMemberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       ClubMember clubMember = clubMemberRepository.findByEmailAndFromSocial(username, false)
                .orElseThrow(() -> new UsernameNotFoundException("user is not exist"));

        ClubAuthMemberDTO clubAuthMemberDTO = new ClubAuthMemberDTO(
                clubMember.getEmail(),
                clubMember.getPassword(),
                clubMember.getRoleSet().stream()
                        .map(r -> new SimpleGrantedAuthority("ROLE_" + r.name()))
                        .collect(Collectors.toSet()),
                clubMember.getName(),
                clubMember.isFromSocial());

        return clubAuthMemberDTO;
    }
}
