package org.zerok.club.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class ClubAuthMemberDTO extends User {

    private String email;
    private String name;
    private boolean fromSocial;

    public ClubAuthMemberDTO(String username, String password, Collection<? extends GrantedAuthority> authorities, String name, boolean fromSocial) {
        super(username, password, authorities);
        this.email = username;
        this.name = name;
        this.fromSocial = fromSocial;
    }
}
