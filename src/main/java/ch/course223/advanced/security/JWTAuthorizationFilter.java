package ch.course223.advanced.security;

import ch.course223.advanced.domainmodels.user.UserDetailsImpl;
import ch.course223.advanced.domainmodels.user.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

class JWTAuthorizationFilter extends OncePerRequestFilter {

	private UserService userService;
	
	private PropertyReader propertyReader;

	JWTAuthorizationFilter(UserService userService,
			PropertyReader propertyReader) {
		this.userService = userService;
		this.propertyReader = propertyReader;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		String header = req.getHeader(propertyReader.getStringProperty("jwt.header-string"));
		if (header != null && header.startsWith(propertyReader.getStringProperty("jwt.token-prefix"))) {
			SecurityContextHolder.getContext().setAuthentication(getAuthentication(req, header));
		}
		chain.doFilter(req, res);
	}

	private Authentication getAuthentication(HttpServletRequest req, String header) {
		String subject;

		try {
			subject = Jwts.parser()
					.setSigningKey(propertyReader.getStringProperty("jwt.secret").getBytes())
					.parseClaimsJws(header.replace(propertyReader.getStringProperty("jwt.token-prefix"), "")).getBody()
					.getSubject();
		} catch (ExpiredJwtException ex) {
			return null;
		}
		
		if (subject != null) {
			UserDetailsImpl userDetails = new UserDetailsImpl(userService.findById(subject));
			return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		}
		return null;
	}

}