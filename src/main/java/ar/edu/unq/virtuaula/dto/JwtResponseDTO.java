package ar.edu.unq.virtuaula.dto;

import lombok.Data;

@Data
public class JwtResponseDTO {

    private String username;
    private String token;

    public JwtResponseDTO(String username, String token) {
        this.username = username;
        this.token = token;
    }
}
