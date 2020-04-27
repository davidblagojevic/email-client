package rs.projekatOSA2019_maven.controller;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.projekatOSA2019_maven.dto.LoginDto;
import rs.projekatOSA2019_maven.repository.UserRepository;
import rs.projekatOSA2019_maven.security.jwt.JWTFilter;
import rs.projekatOSA2019_maven.security.jwt.TokenProvider;

@RestController
@RequestMapping("/api")
public class LoginController {

	private final TokenProvider tokenProvider;

	private final AuthenticationManagerBuilder authenticationManagerBuilder;

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	public LoginController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder, UserRepository userRepository, PasswordEncoder passwordEncoder) {
	      this.tokenProvider = tokenProvider;
	      this.authenticationManagerBuilder = authenticationManagerBuilder;
	      this.userRepository = userRepository;
	      this.passwordEncoder = passwordEncoder;
	   }
	
	@PostMapping("/authenticate")
	public ResponseEntity<JWTToken> authorize(@Valid @RequestBody LoginDto loginDto) {
		System.out.println(loginDto.getUsername() + " vucic s peder " +  loginDto.getPassword());

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				loginDto.getUsername(), loginDto.getPassword());

		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		boolean rememberMe = (loginDto.isRememberMe() == null) ? false : loginDto.isRememberMe();
		String jwt = tokenProvider.createToken(authentication, rememberMe);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

		return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
	}	
	/**
	 * Object to return as body in JWT Authentication.
	 */
	static class JWTToken {
		private String idToken;
		
		public JWTToken(String idToken) {
			this.idToken = idToken;
		}

		@JsonProperty("id_token")
		public String getIdToken() {
			return idToken;
		}

		public void setIdToken(String idToken) {
			this.idToken = idToken;
		}
		
	}
}
