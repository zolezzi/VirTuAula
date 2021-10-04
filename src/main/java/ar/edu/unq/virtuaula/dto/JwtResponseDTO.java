package ar.edu.unq.virtuaula.dto;

import ar.edu.unq.virtuaula.vo.AccountVO;
import lombok.Data;

@Data
public class JwtResponseDTO {

    private String username;
    private String token;
    private AccountVO account;

    public JwtResponseDTO(String username, String token, AccountVO account) {
        this.username = username;
        this.token = token;
        this.account = account;
    }
}
