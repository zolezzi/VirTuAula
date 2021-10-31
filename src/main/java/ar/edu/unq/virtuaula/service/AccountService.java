package ar.edu.unq.virtuaula.service;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ar.edu.unq.virtuaula.dto.AccountDTO;
import ar.edu.unq.virtuaula.dto.AccountTypeDTO;
import ar.edu.unq.virtuaula.dto.PrivilegeDTO;
import ar.edu.unq.virtuaula.exception.AccountNotFoundException;
import ar.edu.unq.virtuaula.exception.StudentAccountNotFoundException;
import ar.edu.unq.virtuaula.exception.TeacherNotFoundException;
import ar.edu.unq.virtuaula.message.ResponseMessage;
import ar.edu.unq.virtuaula.model.Account;
import ar.edu.unq.virtuaula.model.AccountType;
import ar.edu.unq.virtuaula.model.Privilege;
import ar.edu.unq.virtuaula.model.StudentAccount;
import ar.edu.unq.virtuaula.model.TeacherAccount;
import ar.edu.unq.virtuaula.model.User;
import ar.edu.unq.virtuaula.repository.AccountRepository;
import ar.edu.unq.virtuaula.repository.AccountTypeRepository;
import ar.edu.unq.virtuaula.util.CSVUtil;
import ar.edu.unq.virtuaula.util.MapperUtil;
import ar.edu.unq.virtuaula.vo.AccountVO;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountTypeRepository accountTypeRepository;
    private final JwtUserDetailsService userService;
    private final MapperUtil mapperUtil;
    private final CSVUtil csvUtil;
    private static final String ACCOUNT_TYPE_TEACHER = "TEACHER";
    private static final String ACCOUNT_TYPE_STUDENT = "STUDENT";
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

	public ResponseMessage uploadFileStudents(TeacherAccount teacherAccount, MultipartFile file) {
		String message = "";
		if (csvUtil.hasCSVFormat(file)) {
			try {
				AccountType accountType = accountTypeRepository.findByName(ACCOUNT_TYPE_STUDENT);
				List<StudentAccount> students = csvUtil.csvToStudents(file.getInputStream());
				if(!students.isEmpty()) {
					students = validateStudentsAndSetTeacherAndAccountType(students, accountType, teacherAccount);
					students = accountRepository.saveAll(students);
					teacherAccount.getStudents().addAll(students);
					teacherAccount = accountRepository.save(teacherAccount);
					List<Integer> dnis = students.stream().map(student-> student.getDni()).collect(Collectors.toList());
					createUserForStudents(teacherAccount.getStudentsByDNIs(dnis));
			        message = "Uploaded the file successfully: " + file.getOriginalFilename();
				}else {
					message = "I do not know loaded any lines from the file: " + file.getOriginalFilename();
				}

			} catch (Exception e) {
		        message = "Could not upload the file: " + file.getOriginalFilename() + "!";
			}
		}
		return new ResponseMessage(message);
	}
    
    private List<StudentAccount> validateStudentsAndSetTeacherAndAccountType(List<StudentAccount> students,
			AccountType accountType, TeacherAccount teacherAccount) {
    	return students.stream()
    			.filter(student -> !existsAccount(student))
    			.map(student -> {
        			student.setAccountType(accountType);
        			student.addTeacher(teacherAccount);
        			return student;
        			})
    			.collect(Collectors.toList());
	}

	private void createUserForStudents(List<StudentAccount> students) {
    	List<User> users = students.stream().map(student -> {
    		User user = new User();
    		user.setPassword(String.valueOf(student.getDni()));
    		user.setAccount(student);
    		user.setEmail(student.getEmail());
    		user.setUsername(String.valueOf(student.getDni()));
    		return user;
    	}).collect(Collectors.toList());
    	userService.saveAllUsers(users);
	} 

	private Boolean existsAccount(StudentAccount student) {
		return accountRepository.findByDni(student.getDni()).isPresent();
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
