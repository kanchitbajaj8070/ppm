package com.example.ppm.web;

import com.example.ppm.domain.User;
import com.example.ppm.payload.JWTLoginSuccessResponse;
import com.example.ppm.payload.LoginRequest;
import com.example.ppm.security.JwtTokenProvider;
import com.example.ppm.service.UserService;
import com.example.ppm.service.ValidationService;
import com.example.ppm.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.ws.Response;
import java.util.Map;

import static com.example.ppm.security.SecurityConstants.TOKEN_PREFIX;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private ValidationService validationService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest,
                                              BindingResult result)
    {
        ResponseEntity<Map<String,String>> errorMap=validationService.performValidation(result);
        if( errorMap!=null)
            return errorMap;
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),loginRequest.getPassword()
                ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt=TOKEN_PREFIX+jwtTokenProvider.generateToken(authentication);
        return new ResponseEntity< JWTLoginSuccessResponse> (new JWTLoginSuccessResponse(true,jwt),HttpStatus.OK);

    }


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result)
    {
        userValidator.validate(user,result);
        ResponseEntity<Map<String,String>> errors=validationService.performValidation(result);
        if( errors!=null)
            return errors;
        User newUser=userService.saveUser(user);
        return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
    }

}
