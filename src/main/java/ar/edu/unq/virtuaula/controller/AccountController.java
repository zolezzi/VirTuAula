package ar.edu.unq.virtuaula.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.virtuaula.dto.AccountDTO;
import ar.edu.unq.virtuaula.service.AccountService;
import ar.edu.unq.virtuaula.service.JwtUserDetailsService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final JwtUserDetailsService userService;

    @PostMapping("/account/create/{userId}")
    @ApiResponse(code = 200, message = "Successfully create account", response = AccountDTO.class)
    @ApiOperation(value = "Create account by user id", notes = "Create account by user id")
    public AccountDTO createAccount(@PathVariable("userId") Long userId, @RequestBody AccountDTO account) throws Exception {
        return accountService.createAccountTeacher(userService.findById(userId), account);
    }
    
    @GetMapping("/account/experience/{accountId}")
    @ApiResponse(code = 200, message = "Sucessfully get experience", response = Double.class)
    @ApiOperation(value = "Get experience by account id", notes = "Get experience by account id")
    public Double getExperience(@PathVariable("accountId") Long accountId) throws Exception {
        return accountService.getExperience(accountId);
    }
}
