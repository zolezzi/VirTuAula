package ar.edu.unq.virtuaula.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.edu.unq.virtuaula.dto.AccountDTO;
import ar.edu.unq.virtuaula.exception.AccountNotFoundException;
import ar.edu.unq.virtuaula.exception.StudentAccountNotFoundException;
import ar.edu.unq.virtuaula.exception.TeacherNotFoundException;
import ar.edu.unq.virtuaula.model.Account;
import ar.edu.unq.virtuaula.model.StudentAccount;
import ar.edu.unq.virtuaula.model.TeacherAccount;
import ar.edu.unq.virtuaula.model.User;
import ar.edu.unq.virtuaula.repository.AccountRepository;
import ar.edu.unq.virtuaula.repository.AccountTypeRepository;
import ar.edu.unq.virtuaula.util.MapperUtil;
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

	public AccountDTO createAccountTeacher(User user, AccountDTO account) {
		TeacherAccount newAccount = mapperUtil.getMapper().map(account, TeacherAccount.class);
		newAccount.setUser(user);
		newAccount.setUsername(user.getUsername());
		newAccount.setAccountType(accountTypeRepository.findByName(ACCOUNT_TYPE_TEACHER));
		newAccount = accountRepository.save(newAccount);
		return  mapperUtil.getMapper().map(newAccount, AccountDTO.class);
	}

	public StudentAccount findStudentById(Long accountId) throws StudentAccountNotFoundException {
		return (StudentAccount) accountRepository.findById(accountId)
				.orElseThrow(() -> new StudentAccountNotFoundException("Error not found account with id: " + accountId));
	}

    public Double getExperience(Long accountId) throws StudentAccountNotFoundException {
    	StudentAccount account = findStudentById(accountId);
        return account.getExperience();
    }
}
