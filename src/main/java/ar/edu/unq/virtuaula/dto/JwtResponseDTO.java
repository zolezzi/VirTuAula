package ar.edu.unq.virtuaula.dto;

import lombok.Data;

@Data
public class JwtResponseDTO {

    private String username;
    private String token;
    private AccountDTO account;

    public JwtResponseDTO(String username, String token, AccountDTO account) {
        this.username = username;
        this.token = token;
        this.account = account;
    }
}
