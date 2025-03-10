package edu.tsinghua.paratrooper.web.security;

import edu.tsinghua.paratrooper.data.repository.UserRepository;
import edu.tsinghua.paratrooper.util.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Value("${paratrooper.header}")
    private String tokenHeader;

    @Value("${paratrooper.tokenHead}")
    private String tokenHead;

    @Resource
    private JwtUtil jwtUtil;

    @Resource
    private AuthSecurityService authSecurityService;

    private static final long EXPIRE_TIME = 86400000;

    /**
     * Same contract as for {@code doFilter}, but guaranteed to be
     * just invoked once per request within a single request thread.
     * See {@link #shouldNotFilterAsyncDispatch()} for details.
     * <p>Provides HttpServletRequest and HttpServletResponse arguments instead of the
     * default ServletRequest and ServletResponse ones.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader(tokenHeader);
        String url = request.getRequestURI().substring(request.getContextPath().length());

        if (authHeader != null && authHeader.startsWith(tokenHead)) {
            final String authToken = authHeader.substring(tokenHead.length());
            Claims claims = jwtUtil.getClaimsFromToken(authToken);
            if (claims != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                String name = claims.get("name", String.class);
                int id = claims.get("id", Integer.class);
                long time = Long.valueOf(claims.get("time", String.class));
                UserDetails userDetails = authSecurityService.loadUserByUsername(name);
                if (url.contains("/api/v1/admin") && !name.equals("000001")) {
                    //do nothing
                } else if (name != null && userDetails != null && (new Date().getTime()-time)<EXPIRE_TIME) {
                    new AppContext(id);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(
                            request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
