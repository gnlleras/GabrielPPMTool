package io.baufest.ppmtool.web;

import static io.baufest.ppmtool.security.SecurityConstants.TOKEN_PREFIX;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.baufest.ppmtool.domain.User;
import io.baufest.ppmtool.payload.JWTLoginSucessReponse;
import io.baufest.ppmtool.payload.LoginRequest;
import io.baufest.ppmtool.security.JwtTokenProvider;
import io.baufest.ppmtool.services.MapValidationErrorService;
import io.baufest.ppmtool.services.UserService;
import io.baufest.ppmtool.validator.UserValidator;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private MapValidationErrorService mapValidationErrorService;
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserValidator userValidator;
	
	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	@PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null) return errorMap;

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = TOKEN_PREFIX +  tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JWTLoginSucessReponse(true, jwt));
    }
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result){
		
		userValidator.validate(user, result);
		
		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
		
		if(errorMap != null)return errorMap;
		
		User newUser = userService.saveUser(user);
		
		return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
	}

}
