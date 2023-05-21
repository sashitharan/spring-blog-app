package com.springboot.blog.springbootblogrestapi.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {


    private JWTTokenProvider jwtTokenProvider;
    private UserDetailsService userDetailsService;

    public JWTAuthenticationFilter(JWTTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //get jwt token from request
        String token = getTokenFromRequest(request);

        //Validate the token

        //check null or empty
        if(StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)){
            //get username from token
            String username = jwtTokenProvider.getUsername(token);

            //load the user associated with the token
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // pass all the user details to the authentication token object
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );

            //pass request to the authentication token
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            //add to security context holder
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        }

        filterChain.doFilter(request,response);

    }

    private String getTokenFromRequest(HttpServletRequest request){
//        HttpServletRequest contains the headers and from that header we wil take the token

        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken)&& bearerToken.startsWith("Bearer ")){
            //get jwt token
            return bearerToken.substring(7,bearerToken.length());
        }
        return null;
    }
}
