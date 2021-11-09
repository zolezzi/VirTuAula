package ar.edu.unq.virtuaula.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.unq.virtuaula.VirtuaulaApplicationTests;
import ar.edu.unq.virtuaula.exception.TeamNotFoundException;
import ar.edu.unq.virtuaula.model.TeacherAccount;
import ar.edu.unq.virtuaula.model.Team;
import ar.edu.unq.virtuaula.vo.TeamVO;

public class TeamServiceTest extends VirtuaulaApplicationTests{

    @Autowired
    private TeamService teamService;
    
    @Test
    public void whenTeacherConsultedByATeamThatDoesNotBelongToHimThenReturnExceptioNotFoundTeam() {
    	createOneTeam();
    	TeacherAccount teacher = (TeacherAccount) createOneTeacherAccount();
        Exception exception = assertThrows(TeamNotFoundException.class, () -> {
        	teamService.findByTeacherAccount(teacher);
        });
        String expectedMessage = "Error not found team for teacher id: 3";
        assertEquals(expectedMessage, exception.getMessage());
    }
    
    @Test
    public void  whenTeacherConsultedByATeamThenReturnTeamAssociate() throws TeamNotFoundException {
    	int expected = 1;
    	Team team = createOneTeam();
    	TeacherAccount teacher = team.getTeacher(); 
        List<TeamVO> result = teamService.findByTeacherAccount(teacher);
        assertEquals(expected, result.size());
    }
    
}
