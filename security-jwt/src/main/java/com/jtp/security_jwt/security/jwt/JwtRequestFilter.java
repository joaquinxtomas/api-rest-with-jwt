package com.jtp.security_jwt.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtRequestFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);
    public static final String BEARER = "Bearer";
    public static final String HEADER_STRING = "Authorization";

    @Autowired
    private JWTUtility jwtTokenUtil;

    @Autowired
    private com.jtp.security_jwt.service.UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        logger.info("Request received with headers: ");
        request.getHeaderNames().asIterator().forEachRemaining(headerName ->
                logger.info(headerName+ ": " + request.getHeader(headerName)));

        String header = request.getHeader(HEADER_STRING);
        String username = null;
        String authToken = null;
        if (header != null && header.trim().startsWith(BEARER)) {
            authToken = header.replaceFirst(BEARER, "").trim();
            logger.info("El token que llego con headers es: " + authToken);

            try {
                username = jwtTokenUtil.extractUsername(authToken);
                logger.info("El username es: " + username);
            }catch(IllegalArgumentException e) {
                logger.error("An error ocurred while fetching username from token ", e);
            } catch (ExpiredJwtException e) {
                logger.warn("The token has expired ", e);
            } catch(SignatureException e) {
                logger.error("Authentication failed. Username or password not valid.");
            }

        }else {
            logger.warn("Couldn't find bearer string, header will be ignored.");
        }

        if(username != null && SecurityContextHolder.getContext().getAuthentication()==null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if(jwtTokenUtil.validateJwtToken(authToken,userDetails)) {
                UsernamePasswordAuthenticationToken authentication = jwtTokenUtil.getAuthenticationToken(authToken, SecurityContextHolder.getContext().getAuthentication(),userDetails);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                logger.info("authenticated user " + username + ", setting security context");
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}

