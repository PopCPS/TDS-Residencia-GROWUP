package com.example.exemplo;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Service
public class CustomBasicAuthenticationFilter extends OncePerRequestFilter {

    private static final int BASIC_LENGTH = 6;
    private static final String EXEMPLO_USERNAME = "alice";
    private static final String EXEMPLO_PASSWORD = "123";
    String[] basicAuthsSplit;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Basic dXN1YXJpbzoxMjM0NTY=
        var headerAuthorization = request.getHeader("Authorization");

        if (headerAuthorization == null || !headerAuthorization.startsWith("Basic ")) {
            filterChain.doFilter(request, response);
            return;
        }
        // dXN1YXJpbzoxMjM0NTY=
        var basicToken = headerAuthorization.substring(BASIC_LENGTH);
        byte[] basicTokenDecoded = Base64.getDecoder().decode(basicToken);

        // alice:123
        String basicTokenValue = new String(basicTokenDecoded);

        // ["alice", "123"]
        basicAuthsSplit = basicTokenValue.split(":");


        if (basicAuthsSplit[0].equals(EXEMPLO_USERNAME)
                && basicAuthsSplit[1].equals(EXEMPLO_PASSWORD)) {
            var authToken = new UsernamePasswordAuthenticationToken(basicAuthsSplit[0], null, null);
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        filterChain.doFilter(request, response);
    }
    public String[] getBasicAuthsSplit(){
        return basicAuthsSplit;
    }
}