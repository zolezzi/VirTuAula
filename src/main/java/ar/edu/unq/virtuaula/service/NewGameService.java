package ar.edu.unq.virtuaula.service;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.edu.unq.virtuaula.dto.NewGameDTO;
import ar.edu.unq.virtuaula.exception.NewGameNotFoundException;
import ar.edu.unq.virtuaula.message.ResponseMessage;
import ar.edu.unq.virtuaula.model.Account;
import ar.edu.unq.virtuaula.model.NewGame;
import ar.edu.unq.virtuaula.model.Campaign;
import ar.edu.unq.virtuaula.model.PlayerAccount;
import ar.edu.unq.virtuaula.model.PlayerMission;
import ar.edu.unq.virtuaula.model.Mission;
import ar.edu.unq.virtuaula.model.LeaderAccount;
import ar.edu.unq.virtuaula.repository.NewGameRepository;
import ar.edu.unq.virtuaula.repository.PlayerMissionRepository;
import ar.edu.unq.virtuaula.util.CalculatedProgressUtil;
import ar.edu.unq.virtuaula.util.MapperUtil;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class NewGameService {

    private final NewGameRepository newGameRepository;
    private final AccountService accountService;
    private final TeamService teamService;
    private final PlayerMissionRepository playerMissionRepositoryRepository;
    private final MapperUtil mapperUtil;
    private final CalculatedProgressUtil progressUtil;

    public List<NewGameDTO> getAll() {
        List<NewGame> newGames = newGameRepository.findAll();
        return transformToNewGameDTO(newGames);
    }

	public List<NewGameDTO> findByAccount(Account account) {
        List<NewGame> newGames = account.getNewGames();
        return transformToDTO(newGames, account.getId());
    }

    public NewGame findById(Long id) {
        return newGameRepository.findById(id).get();
    }

	public NewGameDTO create(LeaderAccount leaderAccount, NewGameDTO newGameDTO, List<Long> ids) {
		final NewGame newGame = mapperUtil.getMapper().map(newGameDTO, NewGame.class);
		List<PlayerAccount> players = accountService.findAllPlayerByIds(ids);
		//Agregar validacion de player valido para un leader
		NewGame newGameBD = newGameRepository.save(newGame);
		leaderAccount.getNewGames().add(newGameBD);
		players.stream().forEach(player -> player.getNewGames().add(newGameBD));
		teamService.createTeam(newGameBD, leaderAccount, players);
        return mapperUtil.getMapper().map(newGameBD, NewGameDTO.class);
	}

	public ResponseMessage assign(LeaderAccount leaderAccount, Long newGameId, List<Long> ids) throws NewGameNotFoundException {
		NewGame newGameBD = newGameRepository.findById(newGameId)
				.orElseThrow(() -> new NewGameNotFoundException("Error not found new game id: " + newGameId));
		List<PlayerAccount> players = accountService.findAllPlayerByIds(ids);
                teamService.addToTeam(newGameBD, leaderAccount, players);
		return createAllPlayerMission(newGameBD, players);
	}
	
    private ResponseMessage createAllPlayerMission(NewGame newGameBD, List<PlayerAccount> players) {
        players.forEach(player -> player.addNewGame(newGameBD));
    	newGameBD.getCampaigns().stream()
		.forEach(campaign -> createPlayerMissionAllByPlayer(campaign, players));
		return new ResponseMessage("the assignment to the new game was successful");
	}

	private void createPlayerMissionAllByPlayer(Campaign campaign, List<PlayerAccount> players) {
		players.stream()
		.forEach(player -> createPlayerMissionByCampaignAndPlayer(campaign, player));
	}
	
	private void createPlayerMissionByCampaignAndPlayer(Campaign newCampaign, PlayerAccount player) {
		List<PlayerMission> listPlayerMissions = new ArrayList<>();
		for(Mission mission : newCampaign.getMissions()) {
			PlayerMission playerMission = new PlayerMission();
			playerMission.uncomplete();
			playerMission.setMission(mission);
			playerMission.setCampaign(newCampaign);
			playerMission.setPlayerAccount(player);
			listPlayerMissions.add(playerMission);
		}
		playerMissionRepositoryRepository.saveAll(listPlayerMissions);
	}

	private List<NewGameDTO> transformToNewGameDTO(List<NewGame> newGames) {
        return newGames.stream().map(newGame -> {
            NewGameDTO newGameDTO = mapperUtil.getMapper().map(newGame, NewGameDTO.class);
            return newGameDTO;
        }).collect(toList());
	}

    private List<NewGameDTO> transformToDTO(List<NewGame> newGames, Long accountId) {
        return newGames.stream().map(newGame -> {
            NewGameDTO newGameDTO = mapperUtil.getMapper().map(newGame, NewGameDTO.class);
            newGameDTO.setProgress(progressUtil.getProgress(newGame, accountId));
            return newGameDTO;
        }).collect(toList());
    }

}
