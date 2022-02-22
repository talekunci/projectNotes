package ua.goit.notes.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.stereotype.Repository;
import ua.goit.notes.users.UserDto;
import ua.goit.notes.users.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS;

@Repository
public class JwtTokenRepository implements CsrfTokenRepository {

    @Getter
    private final String secret;
    private final UserServiceImpl service;

    @Autowired
    public JwtTokenRepository(UserServiceImpl service) {
        this.secret = "secret";
        this.service = service;
    }

    @Override
    public CsrfToken generateToken(HttpServletRequest request) {
//        System.out.println(request.getUserPrincipal());
//        return new DefaultCsrfToken("x-csrf-token", "_csrf", "token");
        Principal userPrincipal = request.getUserPrincipal();
        UserDto user = service.getByName(userPrincipal.getName());

        String id = user.getId().toString();
        Date now = new Date();
        Date exp = Date.from(LocalDateTime.now().plusMinutes(30)
                .atZone(ZoneId.systemDefault()).toInstant());

        String token = "";
        try {
            token = Jwts.builder()
                    .setId(id)
                    .setIssuedAt(now)
                    .setNotBefore(now)
                    .setExpiration(exp)
                    .signWith(SignatureAlgorithm.HS256, secret)
                    .compact();
        } catch (JwtException e) {
            e.printStackTrace();
            //ignore
        }

        return new DefaultCsrfToken("x-csrf-token", "_csrf", token);
    }

    @Override
    public void saveToken(CsrfToken token, HttpServletRequest request, HttpServletResponse response) {

        if (Objects.nonNull(token)) {
            if (!response.getHeaderNames().contains(ACCESS_CONTROL_EXPOSE_HEADERS))
                response.addHeader(ACCESS_CONTROL_EXPOSE_HEADERS, token.getHeaderName());

            if (response.getHeaderNames().contains(token.getHeaderName()))
                response.setHeader(token.getHeaderName(), token.getToken());
            else
                response.addHeader(token.getHeaderName(), token.getToken());
        }
    }

    @Override
    public CsrfToken loadToken(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute(CsrfToken.class.getName());
    }

    public void clearToken(HttpServletResponse response) {
        if (response.getHeaderNames().contains("x-csrf-token"))
            response.setHeader("x-csrf-token", "");
    }
}
