package com.example.rehabilitationandintegration.filter;

import com.example.rehabilitationandintegration.utility.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String bearerToken = request.getHeader("Authorization");
        String jwt = null;
        String username = null;

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            jwt = bearerToken.substring(7);
            username = jwtTokenUtil.extractUsername(jwt);
        } else {
            log.debug("Authorization header missing or does not start with 'Bearer '");
        }


        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (jwtTokenUtil.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                log.warn("Invalid JWT token for user: {}", username);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token");
                return;
            }
        } else {
            log.debug("No valid JWT found, skipping authentication");
        }

        filterChain.doFilter(request, response);
    }

//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String username = null;
//        String jwt = null;
//
//        String bearerToken = request.getHeader("Authorization");
//        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
//            jwt = bearerToken.substring(7);
//            username = jwtTokenUtil.extractUsername(jwt);
//            log.debug("Extracted username: {}", username);
//        } else {
//            log.debug("Authorization header is missing or does not start with 'Bearer '");
//        }
//
//
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
//
//            if (jwtTokenUtil.validateToken(jwt, userDetails)) {
//                log.debug("JWT is valid, setting authentication for user: {}", username); // ЛОГИРОВАНИЕ
//
//
//                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
//                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//
//                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//            }
//            else {
//                log.debug("JWT is invalid for user: {}", username);
//            }
//
//        }
//
//        filterChain.doFilter(request, response);
//    }


//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        log.debug("JwtRequestFilter triggered for request: {}", request.getRequestURI());
//
//        try {
//            String bearerToken = request.getHeader("Authorization");
//            String jwt = null;
//            String username = null;
//
//            if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
//                jwt = bearerToken.substring(7);
//                username = jwtTokenUtil.extractUsername(jwt);
//                log.debug("Extracted username from JWT: {}", username);
//            } else {
//                log.debug("Authorization header missing or does not start with 'Bearer '");
//            }
//
//            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
//                log.debug("Loaded user details for: {}", username);
//
//                if (jwtTokenUtil.validateToken(jwt, userDetails)) {
//                    log.debug("JWT is valid, setting authentication");
//
//                    UsernamePasswordAuthenticationToken authToken =
//                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//
//                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                    SecurityContextHolder.getContext().setAuthentication(authToken);
//                    log.debug("Authentication set in SecurityContext for user: {}", username);
//                } else {
//                    log.debug("Invalid JWT token for user: {}", username);
//                }
//            } else {
//                log.debug("No valid JWT found, skipping authentication");
//            }
//
//            filterChain.doFilter(request, response);
//        } catch (Exception e) {
//            log.error("Error in JwtRequestFilter: {}", e.getMessage(), e);
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: " + e.getMessage());
//        }
//    }

}
