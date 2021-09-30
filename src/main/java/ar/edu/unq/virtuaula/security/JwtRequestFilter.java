package ar.edu.unq.virtuaula.security;

import ar.edu.unq.virtuaula.service.JwtUserDetailsService;
import ar.edu.unq.virtuaula.util.JwtTokenUtil;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        JwtUser jwtUser = initialize(authorizationHeader);

        authenticate(jwtUser, request);
        
        chain.doFilter(request, response);
    }

    private void authenticate(JwtUser jwtUser, HttpServletRequest request) throws UsernameNotFoundException {
        if (jwtUser.getUsername() != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(jwtUser.getUsername());
            generateAuth(jwtUser, userDetails, request);
        }
    }

    private void generateAuth(JwtUser jwtUser, UserDetails userDetails, HttpServletRequest request) {
        if (jwtTokenUtil.validateToken(jwtUser.getJwt(), userDetails)) {
            
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            usernamePasswordAuthenticationToken
                    .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
    }
    
    private JwtUser initialize(String authorizationHeader) {
        JwtUser jwtUser = new JwtUser();
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwtUser.setJwt(authorizationHeader.substring(7));
            jwtUser.setUsername(jwtTokenUtil.getUsernameFromToken(jwtUser.getJwt()));
        }
        return jwtUser;
    }
}
