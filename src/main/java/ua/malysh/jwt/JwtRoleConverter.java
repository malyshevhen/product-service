package ua.malysh.jwt;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.Collection;

public class JwtRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection convert(Jwt jwt) {

        @SuppressWarnings("unchecked")
        var roles = (ArrayList<String>) jwt.getClaims().get("roles");

        if (roles == null || roles.isEmpty()) {
            return new ArrayList<>();
        }

        return roles.stream().map(SimpleGrantedAuthority::new).toList();
    }
}