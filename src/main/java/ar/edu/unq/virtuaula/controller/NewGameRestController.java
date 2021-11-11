package ar.edu.unq.virtuaula.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.virtuaula.dto.NewGameDTO;
import ar.edu.unq.virtuaula.dto.CampaignDTO;
import ar.edu.unq.virtuaula.exception.AccountNotFoundException;
import ar.edu.unq.virtuaula.message.ResponseMessage;
import ar.edu.unq.virtuaula.service.AccountService;
import ar.edu.unq.virtuaula.service.NewGameService;
import ar.edu.unq.virtuaula.vo.NewGameVO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NewGameRestController {

    private final NewGameService newGameService;
    private final AccountService accountService;

    @GetMapping("/new-games/{accountId}")
    @ApiResponse(code = 200, message = "Success", response = NewGameDTO.class, responseContainer = "List")
    @ApiOperation(value = "Get all new games by account id", notes = "Get all new games of a account")
    public List<NewGameDTO> findByAccountId(@PathVariable("accountId") Long accountId) throws AccountNotFoundException {
        return newGameService.findByAccount(accountService.findById(accountId));
    }

    @PostMapping("/new-games/create/{accountId}")
    @ApiResponse(code = 200, message = "Successfully create new game", response = CampaignDTO.class)
    @ApiOperation(value = "Post create new game for leader by account id", notes = "Post create new game for a leader")
    public NewGameDTO create(@PathVariable("accountId") Long accountId, @RequestBody NewGameVO newGameVO) throws Exception {
        return newGameService.create(accountService.findLeaderById(accountId), newGameVO.getNewGame(), newGameVO.getPlayers());
    }
    
    @PostMapping("/new-games/assign/{newGameId}/{accountId}")
    @ApiResponse(code = 200, message = "Successfully create new game", response = ResponseMessage.class)
    @ApiOperation(value = "Post create new game for leader by account id", notes = "Post create new game for a leader")
    public ResponseMessage assign(@PathVariable("accountId") Long accountId, @PathVariable("newGameId") Long newGameId, @RequestBody List<Long> ids) throws Exception {
        return newGameService.assign(accountService.findLeaderById(accountId), newGameId, ids);
    }
}
