package ar.edu.unq.virtuaula.vo;

import ar.edu.unq.virtuaula.dto.AccountTypeDTO;
import lombok.Data;

@Data
public class AccountVO {

    private Long accountId;
    private AccountTypeDTO accountType;

}
