package ar.edu.unq.virtuaula.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.virtuaula.service.AccountService;
import ar.edu.unq.virtuaula.service.TeamService;
import ar.edu.unq.virtuaula.vo.TeamVO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TeamRestController {

	private final TeamService teamService;
	private final AccountService accountService;
	
    @GetMapping("/teams/{accountId}")
    @ApiResponse(code = 200, message = "Success", response = TeamVO.class, responseContainer = "List")
    @ApiOperation(value = "Get all teams by account id", notes = "Get all teams of a account")
    public List<TeamVO> findAllTeamsByLeader(@PathVariable("accountId") Long accountId) throws Exception {
        return teamService.findByLeaderAccount(accountService.findLeaderById(accountId));
    }
}
