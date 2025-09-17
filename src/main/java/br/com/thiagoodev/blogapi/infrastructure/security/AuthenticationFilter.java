package br.com.thiagoodev.blogapi.infrastructure.security;

import br.com.thiagoodev.blogapi.domain.entities.User;
import br.com.thiagoodev.blogapi.domain.exceptions.JwtAuthenticationException;
import br.com.thiagoodev.blogapi.infrastructure.config.SecurityConfig;
import br.com.thiagoodev.blogapi.infrastructure.persistence.repositories.UsersRepository;
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
import java.util.List;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UsersRepository usersRepository;

    public AuthenticationFilter(
        JwtService jwtService,
        UsersRepository usersRepository
    ) {
        this.jwtService = jwtService;
        this.usersRepository = usersRepository;
    }

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {
        if(this.notAuthenticate(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = this.getToken(request);
        String subject = this.jwtService.getSubject(token);

        if(subject == null) throw new JwtAuthenticationException();

        User user = this.usersRepository.findByEmail(subject)
                .filter(User::isEnabled)
                .orElseThrow(JwtAuthenticationException::new);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
            user.getEmail(),
            null,
            user.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    private boolean notAuthenticate(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return List.of(SecurityConfig.PUBLIC_MATCHERS).contains(requestURI);
    }

    private String getToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if(header == null) return null;
        return header.replace("Bearer ", "");
    }
}
