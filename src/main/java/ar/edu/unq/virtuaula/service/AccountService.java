package ar.edu.unq.virtuaula.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.edu.unq.virtuaula.exception.AccountNotFoundException;
import ar.edu.unq.virtuaula.exception.TeacherNotFoundException;
import ar.edu.unq.virtuaula.model.Account;
import ar.edu.unq.virtuaula.model.TeacherAccount;
import ar.edu.unq.virtuaula.repository.AccountRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {
	
	private final AccountRepository accountRepository;

	public TeacherAccount findTeacherById(Long accountId) throws TeacherNotFoundException {
		TeacherAccount teacher = null;
		try {
			teacher = (TeacherAccount) accountRepository.findById(accountId).get();
		}catch (Exception e) {
			throw new TeacherNotFoundException("Error not found account with id: " + accountId, e);
		}
		return teacher;
	}
	
	public Account findById(Long accountId) throws AccountNotFoundException {
		Account account = null;
		try {
			account =  accountRepository.findById(accountId).get();
		}catch (Exception e) {
			throw new AccountNotFoundException("Error not found account with id: " + accountId, e);
		}
		return account;
	}
}
