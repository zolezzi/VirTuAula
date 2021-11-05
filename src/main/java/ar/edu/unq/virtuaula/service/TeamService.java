package ar.edu.unq.virtuaula.service;

import static java.util.stream.Collectors.toList;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.edu.unq.virtuaula.dto.ClassroomDTO;
import ar.edu.unq.virtuaula.dto.LevelDTO;
import ar.edu.unq.virtuaula.exception.TeamNotFoundException;
import ar.edu.unq.virtuaula.model.Classroom;
import ar.edu.unq.virtuaula.model.StudentAccount;
import ar.edu.unq.virtuaula.model.TeacherAccount;
import ar.edu.unq.virtuaula.model.Team;
import ar.edu.unq.virtuaula.repository.TeamRepository;
import ar.edu.unq.virtuaula.util.CalculatedProgressUtil;
import ar.edu.unq.virtuaula.util.MapperUtil;
import ar.edu.unq.virtuaula.vo.StudentAccountVO;
import ar.edu.unq.virtuaula.vo.TeamVO;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final CalculatedProgressUtil progressUtil;
    private final MapperUtil mapperUtil;
    
	public List<TeamVO> findByTeacherAccount(TeacherAccount teacher) throws TeamNotFoundException {
		List<Team> teams = teamRepository.findByTeacher(teacher);
        if (teams.isEmpty()) {
            throw new TeamNotFoundException("Error not found team for teacher id: " + teacher.getId());
        }
        return transformToTeamVO(teams);
    }

	public void createTeam(Classroom classroom, TeacherAccount teacherAccount, List<StudentAccount> students) {
		Team team = new Team();
		team.setName(classroom.getName() + "with teacher: " + teacherAccount.getFirstName());
		team.setClassroom(classroom);
		team.setTeacher(teacherAccount);
		team.setStudents(students);
		teamRepository.save(team);
	}
	

	private List<TeamVO> transformToTeamVO(List<Team> teams) {
		return teams.stream().map(team -> {
            TeamVO teamVO = new TeamVO();
            teamVO.setId(team.getId());
            teamVO.setName(team.getName());
            teamVO.setClassroom(mapperUtil.getMapper().map(team.getClassroom(), ClassroomDTO.class));
            teamVO.setStudentAccounts(transformToStudentVO(team.getClassroom(), team.getStudents()));
            return teamVO;
        }).collect(toList());
	}

	private List<StudentAccountVO> transformToStudentVO(Classroom classroom, List<StudentAccount> students) {
		return students.stream().map(student -> {
			StudentAccountVO studentVO = new StudentAccountVO();
			studentVO.setFirstName(student.getFirstName());
			studentVO.setUsername(student.getUsername());
			studentVO.setExperience(student.getExperience());
			studentVO.setProgress(progressUtil.getProgress(classroom, student.getId()));
			studentVO.setLevel(mapperUtil.getMapper().map(student.getLevel(), LevelDTO.class));
            return studentVO;
        }).collect(toList());
	}
}
