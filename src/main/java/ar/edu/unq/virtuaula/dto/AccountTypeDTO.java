package ar.edu.unq.virtuaula.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class AccountTypeDTO implements Serializable {

    private static final long serialVersionUID = 8236841855555236353L;
    private String name;
    private List<PrivilegeDTO> privileges;
}
