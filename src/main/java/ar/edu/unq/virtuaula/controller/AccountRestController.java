package ar.edu.unq.virtuaula.controller;

import java.util.List;

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
import ar.edu.unq.virtuaula.dto.BufferDTO;
import ar.edu.unq.virtuaula.dto.GoalDTO;
import ar.edu.unq.virtuaula.dto.LevelDTO;
import ar.edu.unq.virtuaula.exception.LeaderAccountNotFoundException;
import ar.edu.unq.virtuaula.message.ResponseMessage;
import ar.edu.unq.virtuaula.service.AccountService;
import ar.edu.unq.virtuaula.service.JwtUserDetailsService;
import ar.edu.unq.virtuaula.vo.AccountVO;
import ar.edu.unq.virtuaula.vo.PlayerAccountVO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AccountRestController {

    private final AccountService accountService;
    private final JwtUserDetailsService userService;

    @PostMapping("/account/create/{userId}")
    @ApiResponse(code = 200, message = "Successfully create account", response = AccountDTO.class)
    @ApiOperation(value = "Create account by user id", notes = "Create account by user id")
    public AccountVO createAccount(@PathVariable("userId") Long userId, @RequestBody AccountDTO account) throws Exception {
        return accountService.createAccountLeader(userService.findById(userId), account);
    }
    
    @GetMapping("/account/experience/{accountId}")
    @ApiResponse(code = 200, message = "Sucessfully get experience", response = Double.class)
    @ApiOperation(value = "Get experience by account id", notes = "Get experience by account id")
    public Double getExperience(@PathVariable("accountId") Long accountId) throws Exception {
        return accountService.getExperience(accountId);
    }
    
    @PostMapping("/account/upload-file-players/{accountId}")
	@ApiOperation(value="Import list players in CSV", response=ResponseEntity.class)
    public ResponseEntity<ResponseMessage> uploadFile(@PathVariable("accountId") Long accountId, @RequestParam("file") MultipartFile file) throws LeaderAccountNotFoundException {
		return ResponseEntity.ok().body(accountService.uploadFilePlayers(accountService.findLeaderById(accountId), file));
	}
    
    @GetMapping("/account/level/{accountId}")
    @ApiResponse(code = 200, message = "Sucessfully get level", response = LevelDTO.class)
    @ApiOperation(value = "Get level by account id", notes = "Get level by account id")
    public LevelDTO getLevel(@PathVariable("accountId") Long accountId) throws Exception {
        return accountService.getLevel(accountId);
    }
    
    @GetMapping("/account/players/{accountId}")
    @ApiResponse(code = 200, message = "Success", response = PlayerAccountVO.class, responseContainer = "List")
    @ApiOperation(value = "Get all players by leader", notes = "Get all players of a leader account")
    public List<PlayerAccountVO> findAllPlayersByAccountId(@PathVariable("accountId") Long accountId) throws LeaderAccountNotFoundException {
        return accountService.findAllPlayersByLeader(accountService.findLeaderById(accountId));
    }
    
    @GetMapping("/account/buffers/{accountId}")
    @ApiResponse(code = 200, message = "Sucessfully get buffers", response = LevelDTO.class)
    @ApiOperation(value = "Get buffers by account id", notes = "Get buffers by account id")
    public List<BufferDTO> getBuffers(@PathVariable("accountId") Long accountId) throws Exception {
        return accountService.getBuffers(accountId);
    }
    
    @GetMapping("/account/goals/{accountId}")
    @ApiResponse(code = 200, message = "Sucessfully get goals", response = LevelDTO.class)
    @ApiOperation(value = "Get goals by account id", notes = "Get goals by account id")
    public List<GoalDTO> getGoals(@PathVariable("accountId") Long accountId) throws Exception {
        return accountService.getGoals(accountId);
    }
    
    @GetMapping("/account/life/{accountId}")
    @ApiResponse(code = 200, message = "Sucessfully get life", response = Integer.class)
    @ApiOperation(value = "Get life by account id", notes = "Get life by account id")
    public Integer getLife(@PathVariable("accountId") Long accountId) throws Exception {
        return accountService.getLife(accountId);
    }
}
