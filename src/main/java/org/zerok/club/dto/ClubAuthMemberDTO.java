package org.zerok.club.dto;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
public class ClubAuthMemberDTO extends User implements OAuth2User {

    private String email;
    private String name;
    private boolean fromSocial;
    private Map<String, Object> attr;

    public ClubAuthMemberDTO(String username,
                             String password,
                             Collection<? extends GrantedAuthority> authorities,
                             String name,
                             boolean fromSocial) {
        super(username, password, authorities);
        this.email = username;
        this.name = name;
        this.fromSocial = fromSocial;
    }

    public ClubAuthMemberDTO(String username,
                             String password,
                             Collection<? extends GrantedAuthority> authorities,
                             String name,
                             boolean fromSocial,
                             Map<String, Object> attr) {
        this(username, password, authorities, name, fromSocial);
        this.attr = attr;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attr;
    }
}
