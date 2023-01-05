package nl.plinio.backend.endpoints.auth.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import nl.plinio.backend.helper.JwtHelper;
import org.springframework.http.HttpCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtHelper jwtHelper;
    private final CookieGenerator cookieGenerator;
    private final CustomUserDetailsService customUserDetailsService;

    public JwtAuthenticationFilter(
            JwtHelper jwtHelper,
            CookieGenerator cookieGenerator,
            CustomUserDetailsService customUserDetailsService
    ) {
        this.jwtHelper = jwtHelper;
        this.cookieGenerator = cookieGenerator;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String jwt = jwtHelper.retrieveJwt(request);

        if (StringUtils.hasText(jwt) && jwtHelper.isValidJwt(jwt)) {
            Jwt decodedJwt;

            try {
                decodedJwt = jwtHelper.decode(jwt);
            } catch (Exception ex) {
                HttpCookie httpCookie = cookieGenerator.generate("", 0);
                Cookie cookie = new Cookie(httpCookie.getName(), httpCookie.getValue());
                response.addCookie(cookie);
                response.setStatus(401);
                filterChain.doFilter(request, response);
                log.error(ex.getMessage());
                return;
            }

            try {
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(decodedJwt.getSubject());
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception ex) {
                log.error(ex.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }
}
