package ar.edu.unq.virtuaula.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.unq.virtuaula.model.LeaderAccount;
import ar.edu.unq.virtuaula.model.NewGame;
import ar.edu.unq.virtuaula.model.Team;

public interface TeamRepository extends JpaRepository<Team, Long>{

	public List<Team> findByLeader(LeaderAccount leaderAccount);
        public Team findByNewGame(NewGame newGame);
}
