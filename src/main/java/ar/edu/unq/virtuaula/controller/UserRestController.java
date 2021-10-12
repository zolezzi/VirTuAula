package ar.edu.unq.virtuaula.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.virtuaula.dto.AuthRequestDTO;
import ar.edu.unq.virtuaula.dto.JwtResponseDTO;
import ar.edu.unq.virtuaula.dto.UserDTO;
import ar.edu.unq.virtuaula.exception.UserRegisterException;
import ar.edu.unq.virtuaula.service.AuthenticationUserService;
import ar.edu.unq.virtuaula.service.JwtUserDetailsService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserRestController {
    
    private final AuthenticationUserService authenticationUserService;
    
    private final JwtUserDetailsService userService;

    @PostMapping(value = "/login")
    @ApiResponse(code = 200, message = "Successfully" , response = ResponseEntity.class)
    @ApiOperation(value = "Post login by user", notes = "Post login by user")
    public ResponseEntity<JwtResponseDTO> login(@RequestBody AuthRequestDTO authRequestDto) {
            return ResponseEntity.ok().body(authenticationUserService.login(authRequestDto));
    }
    
    @PostMapping(value = "/register")
    @ApiResponse(code = 200, message = "Successfully register" , response = UserDTO.class)
    @ApiOperation(value = "Post register a user", notes = "Post register a user")
    public UserDTO register(@RequestBody UserDTO userDto) throws UserRegisterException {
            return userService.register(userDto);
    }
}
