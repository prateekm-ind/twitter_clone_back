package com.example.twitter.clone.jwt;

import com.example.twitter.clone.exception.TwitterAppGeneralException;
import com.example.twitter.clone.service.UserDetailsServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.persistence.Column;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private static final String HEADER_STRING = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    JwtProvider jwtProvider;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String requestHeader= request.getHeader(HEADER_STRING);
        if(requestHeader == null || !requestHeader.startsWith(TOKEN_PREFIX)){
            chain.doFilter(request,response);
            return;
        }

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken= getAuthenticationToken(request);

        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        chain.doFilter(request,response);
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(HttpServletRequest request) {
        String bearerString= request.getHeader(HEADER_STRING);
        Boolean isTokenValid= false;
        String token=bearerString.substring(7);
        //System.out.println("JWT-TOKEN==>"+token);

        String username= jwtProvider.extractUsernameFromJWT(token);

        if(username!=null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails= this.userDetailsService.loadUserByUsername(username);
            try {
                isTokenValid= jwtProvider.validateToken(token, userDetails);
                if(isTokenValid){
                    return new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
                }
            }
            catch (ExpiredJwtException e){
                throw new TwitterAppGeneralException("JWT token Expired. Please login again.");
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }
        return null;
    }
}
