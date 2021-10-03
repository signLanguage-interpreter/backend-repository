package signLanguage.web.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import signLanguage.web.domain.common.CommonConst;
import signLanguage.web.domain.entity.Member;
import signLanguage.web.auth.PrincipalDetails;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Date;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {

            ObjectMapper objectMapper = new ObjectMapper();
            Member member = objectMapper.readValue(request.getInputStream(), Member.class);

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(member.getUsername(), member.getPassword());

            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            if(authentication == null){
                return null;
            }
            PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
            // Session Saved
            return authentication;

        } catch (Exception e) {
            e.printStackTrace();
        }
        // 인증 실패
        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        PrincipalDetails principal = (PrincipalDetails) authResult.getPrincipal();
        String authorities = principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        //create token
        String jwtToken = JWT.create()
                .withSubject("authentication")
                .withExpiresAt(new Date(System.currentTimeMillis()+(60000*10)))
                .withClaim("id", principal.getMember().getId())
                .withClaim("auth", authorities)
                .withClaim("username", principal.getUsername())
                .sign(Algorithm.HMAC512(CommonConst.PRIVATE_KEY));

        //토큰을 jwt를 헤더에 달아줌.
        response.addHeader("Authorization", "Bearer "+jwtToken);

        //solve token
        String auth = JWT.require(Algorithm.HMAC512(CommonConst.PRIVATE_KEY)).build().verify(jwtToken).getClaim("auth").asString();

        //role
        response.addHeader("auth", auth);
    }
}
