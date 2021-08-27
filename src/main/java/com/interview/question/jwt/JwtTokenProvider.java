package com.interview.question.jwt;

import com.interview.question.bank.service.AccountService;
import com.interview.question.jwt.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Objects;

@Component
@AllArgsConstructor
public class JwtTokenProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenProvider.class);
    private final AccountService accountService;
    private final JwtConfig jwtConfig;
    private final SecretKey secretKeyForSigning;


    public String createJwt(String accountNumber) {
        var claims = Jwts.claims().setSubject(accountNumber);
        var now = new Date();
        var validity = new Date(now.getTime() + jwtConfig.getExpireLength());

        return Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(validity)
                .signWith(secretKeyForSigning, SignatureAlgorithm.HS512).compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = accountService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return decode(token).getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (Objects.isNull(bearerToken))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "wrong credentials");
        return (bearerToken.startsWith("Bearer ")) ? bearerToken.replace("Bearer ", "") : bearerToken;
    }

    public boolean validateToken(String token) {
        try {
            decode(token).getSubject();
            return !(decode(token).getExpiration().before(new Date()));
        } catch (JwtException e) {
            LOGGER.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "wrong credentials");
        }
    }

    public Claims decode(String token) {
        var claimsJws = Jwts.parserBuilder().setSigningKey(secretKeyForSigning).build().parseClaimsJws(token);
        return claimsJws.getBody();
    }
}
