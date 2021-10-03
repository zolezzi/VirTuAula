package ar.edu.unq.virtuaula.security;

import lombok.Data;

@Data
public class JwtUser {

    private String username;
    private String jwt;
}
