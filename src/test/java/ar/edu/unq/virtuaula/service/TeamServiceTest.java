package ar.edu.unq.virtuaula.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.unq.virtuaula.VirtuaulaApplicationTests;
import ar.edu.unq.virtuaula.exception.TeamNotFoundException;
import ar.edu.unq.virtuaula.model.LeaderAccount;
import ar.edu.unq.virtuaula.model.Team;
import ar.edu.unq.virtuaula.vo.TeamVO;

public class TeamServiceTest extends VirtuaulaApplicationTests{

    @Autowired
    private TeamService teamService;
    
    @Test
    public void whenLeaderConsultedByATeamThatDoesNotBelongToHimThenReturnExceptioNotFoundTeam() {
    	createOneTeam();
    	LeaderAccount leader = (LeaderAccount) createOneLeaderAccount();
        Exception exception = assertThrows(TeamNotFoundException.class, () -> {
        	teamService.findByLeaderAccount(leader);
        });
        String expectedMessage = "Error not found team for leader id: 3";
        assertEquals(expectedMessage, exception.getMessage());
    }
    
    @Test
    public void  whenLeaderConsultedByATeamThenReturnTeamAssociate() throws TeamNotFoundException {
    	int expected = 1;
    	Team team = createOneTeam();
    	LeaderAccount leader = team.getLeader(); 
        List<TeamVO> result = teamService.findByLeaderAccount(leader);
        assertEquals(expected, result.size());
    }
    
}
