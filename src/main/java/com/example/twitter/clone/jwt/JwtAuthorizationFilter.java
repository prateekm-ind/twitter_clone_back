package com.example.twitter.clone.jwt;

import com.example.twitter.clone.exception.CustomExpiredJwtExceptionHandler;
import com.example.twitter.clone.exception.TwitterAppRuntimeException;
import com.example.twitter.clone.service.UserDetailsServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private static final String HEADER_STRING = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtProvider jwtProvider;

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)  {
        String requestHeader= request.getHeader(HEADER_STRING);
        if(requestHeader == null || !requestHeader.startsWith(TOKEN_PREFIX)){
            try {
                chain.doFilter(request,response);
            } catch (IOException e) {
                throw new TwitterAppRuntimeException("Spring security filter not initiated. IO-Exception");
            } catch (ServletException e) {
               throw new TwitterAppRuntimeException("Spring security filter not initiated. Servlet-Exception");
            }
            return;
        }

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken= getAuthenticationToken(request);

        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        chain.doFilter(request,response);
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(HttpServletRequest request) {
        String bearerString = request.getHeader(HEADER_STRING);
        Boolean isTokenValid = false;
        String token = bearerString.substring(7);
        //System.out.println("JWT-TOKEN==>"+token);

        String username = jwtProvider.extractUsernameFromJWT(token);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            isTokenValid = jwtProvider.validateToken(token, userDetails);
            if (isTokenValid) {
                return new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
            }
            return null;
        }
        return null;
    }
}
