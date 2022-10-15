package com.bank.operation.jwt;

import com.bank.operation.utils.model.ResponseModel;
import com.bank.operation.utils.model.model.Response;
import com.bank.operation.utils.model.model.ResponseError;
import com.bank.operation.utils.model.transformer.ResponseAssembler;
import com.bank.operation.utils.model.transformer.ResponseErrorAssembler;
import com.bank.operation.utils.route.OpenRouteService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@RequiredArgsConstructor
@Component
public class JwtTokenVerifier extends OncePerRequestFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenVerifier.class);

    private final JwtTokenProvider jwtTokenProvider;
    private final ResponseModel responseModel;


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        try {
            authorizeRequest(request);
            filterChain.doFilter(request, response);
        } catch (ResponseStatusException e) {
            handleAuthException(response, e);
        } catch (RuntimeException e) {
            handleRunTImeException(response, e);
        }
    }

    private void handleAuthException(HttpServletResponse response, ResponseStatusException e) {
        LOGGER.warn(e.getMessage(), e);

        ResponseError responseError = ResponseErrorAssembler.toResponseError(e.getReason(), e.getStatus());
        Response<ResponseError> errorResponse = ResponseAssembler.toResponse(e.getStatus(), responseError);
        responseModel.writeResponse(response, errorResponse);
    }


    private void handleRunTImeException(HttpServletResponse response, RuntimeException e) {
        String errMessage = "A server error occurred. please retry later";
        LOGGER.error(e.getMessage(), e);

        ResponseError responseError = ResponseErrorAssembler.toResponseError(errMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        Response<ResponseError> errorResponse = ResponseAssembler.toResponse(HttpStatus.INTERNAL_SERVER_ERROR, responseError);
        responseModel.writeResponse(response, errorResponse);
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
