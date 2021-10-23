package ar.edu.unq.virtuaula.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.edu.unq.virtuaula.dto.AccountDTO;
import ar.edu.unq.virtuaula.dto.AccountTypeDTO;
import ar.edu.unq.virtuaula.dto.PrivilegeDTO;
import ar.edu.unq.virtuaula.exception.AccountNotFoundException;
import ar.edu.unq.virtuaula.exception.StudentAccountNotFoundException;
import ar.edu.unq.virtuaula.exception.TeacherNotFoundException;
import ar.edu.unq.virtuaula.model.Account;
import ar.edu.unq.virtuaula.model.Privilege;
import ar.edu.unq.virtuaula.model.StudentAccount;
import ar.edu.unq.virtuaula.model.TeacherAccount;
import ar.edu.unq.virtuaula.model.User;
import ar.edu.unq.virtuaula.repository.AccountRepository;
import ar.edu.unq.virtuaula.repository.AccountTypeRepository;
import ar.edu.unq.virtuaula.util.MapperUtil;
import ar.edu.unq.virtuaula.vo.AccountVO;
import java.util.List;
import static java.util.stream.Collectors.toList;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountTypeRepository accountTypeRepository;
    private final MapperUtil mapperUtil;
    private static final String ACCOUNT_TYPE_TEACHER = "TEACHER";
	public TeacherAccount findTeacherById(Long accountId) throws TeacherNotFoundException {
		return (TeacherAccount) accountRepository.findById(accountId)
				.orElseThrow(() -> new TeacherNotFoundException("Error not found account with id: " + accountId));
	}
	
	public Account findById(Long accountId) throws AccountNotFoundException {
		return accountRepository.findById(accountId)
				.orElseThrow(() -> new AccountNotFoundException("Error not found account with id: " + accountId));
	}

    public AccountVO createAccountTeacher(User user, AccountDTO account) {
        TeacherAccount newAccount = mapperUtil.getMapper().map(account, TeacherAccount.class);
        newAccount.setUser(user);
        newAccount.setUsername(user.getUsername());
        newAccount.setFirstName(account.getFirstName());
        newAccount.setLastName(account.getLastName());
        newAccount.setDni(account.getDni());
        newAccount.setEmail(user.getEmail());
        newAccount.setAccountType(accountTypeRepository.findByName(ACCOUNT_TYPE_TEACHER));
        newAccount = accountRepository.save(newAccount);
        return createAccountVo(newAccount);
    }

	public StudentAccount findStudentById(Long accountId) throws StudentAccountNotFoundException {
		return (StudentAccount) accountRepository.findById(accountId)
				.orElseThrow(() -> new StudentAccountNotFoundException("Error not found account with id: " + accountId));
	}

    public Double getExperience(Long accountId) throws StudentAccountNotFoundException {
    	StudentAccount account = findStudentById(accountId);
        return account.getExperience();
    }

    private AccountVO createAccountVo(TeacherAccount account) {
        AccountVO accountVO = new AccountVO();
        accountVO.setAccountId(account.getId());
        AccountTypeDTO accountTypeDTO = new AccountTypeDTO();
        accountTypeDTO.setName(account.getAccountType().getName());
        List<PrivilegeDTO> privileges = createPrivileges(account.getAccountType().getPrivileges());
        accountTypeDTO.setPrivileges(privileges);
        accountVO.setAccountType(accountTypeDTO);
        return accountVO;
    }

    private List<PrivilegeDTO> createPrivileges(List<Privilege> privileges) {
        return privileges.stream().map(privilege -> createPrivilegeDTO(privilege)).collect(toList());
    }

    private PrivilegeDTO createPrivilegeDTO(Privilege privilege) {
        PrivilegeDTO privilegeDTO = new PrivilegeDTO();
        privilegeDTO.setId(privilege.getId());
        privilegeDTO.setName(privilege.getName());
        return privilegeDTO;
    }
}
