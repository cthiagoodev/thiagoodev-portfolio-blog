package br.com.thiagoodev.blogapi.infrastructure.auth;

import br.com.thiagoodev.blogapi.domain.exceptions.JwtAuthenticationException;
import br.com.thiagoodev.blogapi.infrastructure.data.models.UserModel;
import br.com.thiagoodev.blogapi.infrastructure.data.repositories.UsersRepository;
import br.com.thiagoodev.blogapi.infrastructure.security.JwtTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class UserAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;
    private final UsersRepository usersRepository;

    public UserAuthenticationFilter(
        JwtTokenService jwtTokenService,
        UsersRepository usersRepository
    ) {
        this.jwtTokenService = jwtTokenService;
        this.usersRepository = usersRepository;
    }

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        String token = recoveryToken(request);

        if(token == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token is missing or invalid.");
            return;
        }

        String subject = jwtTokenService.getSubjectFromToken(token);
        UserModel user = usersRepository.findByEmail(subject)
                .orElseThrow(JwtAuthenticationException::new);

        Authentication auth = new UsernamePasswordAuthenticationToken(
            user.getUsername(),
            null,
            user.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(auth);

        filterChain.doFilter(request, response);
    }

    private String recoveryToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) return null;
        return header.replace("Bearer ", "");
    }
}
