package ar.edu.unq.virtuaula.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ar.edu.unq.virtuaula.dto.AccountDTO;
import ar.edu.unq.virtuaula.dto.LevelDTO;
import ar.edu.unq.virtuaula.exception.TeacherNotFoundException;
import ar.edu.unq.virtuaula.message.ResponseMessage;
import ar.edu.unq.virtuaula.service.AccountService;
import ar.edu.unq.virtuaula.service.JwtUserDetailsService;
import ar.edu.unq.virtuaula.vo.AccountVO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final JwtUserDetailsService userService;

    @PostMapping("/account/create/{userId}")
    @ApiResponse(code = 200, message = "Successfully create account", response = AccountDTO.class)
    @ApiOperation(value = "Create account by user id", notes = "Create account by user id")
    public AccountVO createAccount(@PathVariable("userId") Long userId, @RequestBody AccountDTO account) throws Exception {
        return accountService.createAccountTeacher(userService.findById(userId), account);
    }
    
    @GetMapping("/account/experience/{accountId}")
    @ApiResponse(code = 200, message = "Sucessfully get experience", response = Double.class)
    @ApiOperation(value = "Get experience by account id", notes = "Get experience by account id")
    public Double getExperience(@PathVariable("accountId") Long accountId) throws Exception {
        return accountService.getExperience(accountId);
    }
    
    @PostMapping("/account/upload-file-students/{accountId}")
	@ApiOperation(value="Import list students in CSV", response=ResponseEntity.class)
    public ResponseEntity<ResponseMessage> uploadFile(@PathVariable("accountId") Long accountId, @RequestParam("file") MultipartFile file) throws TeacherNotFoundException {
		return ResponseEntity.ok().body(accountService.uploadFileStudents(accountService.findTeacherById(accountId), file));
	}
    
    @GetMapping("/account/level/{accountId}")
    @ApiResponse(code = 200, message = "Sucessfully get level", response = LevelDTO.class)
    @ApiOperation(value = "Get level by account id", notes = "Get level by account id")
    public LevelDTO getLevel(@PathVariable("accountId") Long accountId) throws Exception {
        return accountService.getLevel(accountId);
    }
}
