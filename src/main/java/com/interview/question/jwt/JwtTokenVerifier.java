package com.interview.question.jwt;

import com.interview.question.utils.model.ResponseModel;
import com.interview.question.utils.route.OpenRouteService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
@Component
public class JwtTokenVerifier extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        try {
            authorizeRequest(request);
            filterChain.doFilter(request, response);
        } catch (ResponseStatusException e) {
            ResponseModel.getResponseBody(response, e.getStatus(), e.getMessage());
        } catch (RuntimeException e) {
            ResponseModel.getResponseBody(response, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private void authorizeRequest(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        if (token != null && jwtTokenProvider.validateToken(token)) {
            var auth = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return OpenRouteService.isRouteOpen(request.getServletPath(), request.getMethod());
    }
}
