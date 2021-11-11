package ar.edu.unq.virtuaula.service;

import static java.util.stream.Collectors.toList;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.edu.unq.virtuaula.dto.NewGameDTO;
import ar.edu.unq.virtuaula.dto.LevelDTO;
import ar.edu.unq.virtuaula.exception.TeamNotFoundException;
import ar.edu.unq.virtuaula.model.NewGame;
import ar.edu.unq.virtuaula.model.PlayerAccount;
import ar.edu.unq.virtuaula.model.LeaderAccount;
import ar.edu.unq.virtuaula.model.Team;
import ar.edu.unq.virtuaula.repository.TeamRepository;
import ar.edu.unq.virtuaula.util.CalculatedProgressUtil;
import ar.edu.unq.virtuaula.util.MapperUtil;
import ar.edu.unq.virtuaula.vo.PlayerAccountVO;
import ar.edu.unq.virtuaula.vo.TeamVO;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final CalculatedProgressUtil progressUtil;
    private final MapperUtil mapperUtil;
    
	public List<TeamVO> findByLeaderAccount(LeaderAccount leader) throws TeamNotFoundException {
		List<Team> teams = teamRepository.findByLeader(leader);
        if (teams.isEmpty()) {
            throw new TeamNotFoundException("Error not found team for leader id: " + leader.getId());
        }
        return transformToTeamVO(teams);
    }

	public void createTeam(NewGame newGame, LeaderAccount leaderAccount, List<PlayerAccount> players) {
		Team team = new Team();
		team.setName(newGame.getName() + "with leader: " + leaderAccount.getFirstName());
		team.setNewGame(newGame);
		team.setLeader(leaderAccount);
		team.setPlayers(players);
		teamRepository.save(team);
	}
	

	private List<TeamVO> transformToTeamVO(List<Team> teams) {
		return teams.stream().map(team -> {
            TeamVO teamVO = new TeamVO();
            teamVO.setId(team.getId());
            teamVO.setName(team.getName());
            teamVO.setNewGame(mapperUtil.getMapper().map(team.getNewGame(), NewGameDTO.class));
            teamVO.setPlayerAccounts(transformToPlayerVO(team.getNewGame(), team.getPlayers()));
            return teamVO;
        }).collect(toList());
	}

	private List<PlayerAccountVO> transformToPlayerVO(NewGame newGame, List<PlayerAccount> players) {
		return players.stream().map(player -> {
			PlayerAccountVO playerVO = new PlayerAccountVO();
			playerVO.setFirstName(player.getFirstName());
			playerVO.setUsername(player.getUsername());
			playerVO.setExperience(player.getExperience());
			playerVO.setProgress(progressUtil.getProgress(newGame, player.getId()));
			playerVO.setLevel(mapperUtil.getMapper().map(player.getLevel(), LevelDTO.class));
            return playerVO;
        }).collect(toList());
	}
}
