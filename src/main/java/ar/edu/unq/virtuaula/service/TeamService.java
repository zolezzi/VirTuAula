package ar.edu.unq.virtuaula.service;

import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.edu.unq.virtuaula.dto.TeamDTO;
import ar.edu.unq.virtuaula.exception.TeamNotFoundException;
import ar.edu.unq.virtuaula.model.Classroom;
import ar.edu.unq.virtuaula.model.StudentAccount;
import ar.edu.unq.virtuaula.model.TeacherAccount;
import ar.edu.unq.virtuaula.model.Team;
import ar.edu.unq.virtuaula.repository.TeamRepository;
import ar.edu.unq.virtuaula.util.MapperUtil;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final MapperUtil mapperUtil;
    
	public List<TeamDTO> findByTeacherAccount(TeacherAccount teacher) throws TeamNotFoundException {
		List<Team> teams = teamRepository.findByTeacher(teacher);
        if (teams.isEmpty()) {
            throw new TeamNotFoundException("Error not found team for teacher id: " + teacher.getId());
        }
        return Arrays.asList(mapperUtil.getMapper().map(teams, TeamDTO[].class));
    }

	public void createTeam(Classroom classroom, TeacherAccount teacherAccount, List<StudentAccount> students) {
		Team team = new Team();
		team.setName(classroom.getName() + "with teacher: " + teacherAccount.getFirstName());
		team.setClassroom(classroom);
		team.setTeacher(teacherAccount);
		team.setStudents(students);
		teamRepository.save(team);
	}
}
