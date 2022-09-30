package ar.com.eduducactionit.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import ar.com.eduducactionit.security.service.MyUserDetailsService;

public class JwtTokenFilter extends OncePerRequestFilter {
    private final static Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);
	
	@Autowired
	private JwtProvider jwtProvider;
	
	@Autowired
	private MyUserDetailsService userDetailService; 
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		try {
			String token = getToken(request);
			
			if(token != null && jwtProvider.validateToken(token)) {
				String username = jwtProvider.getNombreUsuarioFromToken(token);//EDUIT/GUESS
				UserDetails userDetails = this.userDetailService.loadUserByUsername(username);
				UsernamePasswordAuthenticationToken auth = 
						new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		}catch (Exception e) {
            logger.error("fail en el método doFilter " + e.getMessage());
		}
		
		filterChain.doFilter(request, response);//continuea evaluando los demas filtros 
	}

	private String getToken(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		if(header != null && header.startsWith("Bearer")) {
			return header.replace("Bearer", "");//
		}			
		return header;
	}
}
