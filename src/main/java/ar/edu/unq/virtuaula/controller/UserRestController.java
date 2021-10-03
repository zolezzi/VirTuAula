package ar.edu.unq.virtuaula.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.virtuaula.dto.AuthRequestDTO;
import ar.edu.unq.virtuaula.dto.JwtResponseDTO;
import ar.edu.unq.virtuaula.dto.UserDTO;
import ar.edu.unq.virtuaula.service.AuthenticationUserService;
import ar.edu.unq.virtuaula.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserRestController {
    
    private final AuthenticationUserService authenticationUserService;
    private final UserService userService;
    
    @PostMapping(value = "/login")
    public ResponseEntity<JwtResponseDTO> login(@RequestBody AuthRequestDTO authRequestDto) {
            return ResponseEntity.ok().body(authenticationUserService.login(authRequestDto));
    }
   
    @GetMapping("/users/{username}")
    public ResponseEntity<UserDTO> get(@PathVariable("username") String username) {
        return ResponseEntity.ok().body(userService.getUserByUsername(username));
    }
}
