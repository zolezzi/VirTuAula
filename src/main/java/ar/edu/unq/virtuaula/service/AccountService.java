package ar.edu.unq.virtuaula.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.edu.unq.virtuaula.exception.ExceptionTeacherNotFound;
import ar.edu.unq.virtuaula.model.TeacherAccount;
import ar.edu.unq.virtuaula.repository.AccountRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {
	
	private final AccountRepository accountRepository;

	public TeacherAccount findTeacherById(Long accountId) throws ExceptionTeacherNotFound {
		TeacherAccount teacher = null;
		try {
			teacher = (TeacherAccount) accountRepository.findById(accountId).get();
		}catch (Exception e) {
			throw new ExceptionTeacherNotFound("Error not found account with id: " + accountId, e);
		}
		return teacher;
	}
}
