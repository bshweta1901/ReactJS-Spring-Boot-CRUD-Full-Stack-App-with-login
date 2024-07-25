package com.springboot.assignment.auth.controller;



import com.springboot.assignment.auth.LoginResponseDto;
import com.springboot.assignment.auth.exception.APIException;
import com.springboot.assignment.auth.service.AuthService;
import com.springboot.assignment.model_structure.entity.User;
import com.springboot.assignment.model_structure.payload.JWTAuthResponse;
import com.springboot.assignment.model_structure.payload.LoginDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;;
    private  PasswordEncoder encoder;

    public AuthController(AuthService authService,
                          PasswordEncoder encoder) {
        this.authService = authService;
        this.encoder = encoder;
    }

    // Build Login REST API
    @PostMapping(value = {"/login"})
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto) throws Exception {
        User user = authService.getUserByUsername(loginDto.getUsernameOrEmail());
        if (user.getIsDeactivate() != null && user.getIsDeactivate().equalsIgnoreCase("Y")) {
            throw new APIException(HttpStatus.BAD_REQUEST, "IUser is Deactivate!");
        }
        if (user != null && loginDto.getUsernameOrEmail() != null
                && !encoder.matches(loginDto.getPassword() , user.getPassword())) {
            throw new APIException(HttpStatus.BAD_REQUEST," Password wrong!!! ");

        }
        LoginResponseDto token = authService.login(loginDto);
        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token.getToken());

        return ResponseEntity.ok(jwtAuthResponse);

    }

}