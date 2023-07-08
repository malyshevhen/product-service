// package ua.malysh.jwt;

// import java.util.ArrayList;
// import java.util.Collection;
// import java.util.stream.Collectors;

// import org.springframework.core.convert.converter.Converter;
// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.oauth2.jwt.Jwt;

// public class JwtRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

//     @Override
//     public Collection<GrantedAuthority> convert(Jwt jwt) {

//         @SuppressWarnings("unchecked")
//         final var roles = (ArrayList<String>) jwt.getClaims().get("roles");

//         return roles
//                 .stream()
//                 .map(SimpleGrantedAuthority::new)
//                 .collect(Collectors.toList());
//     }
// }