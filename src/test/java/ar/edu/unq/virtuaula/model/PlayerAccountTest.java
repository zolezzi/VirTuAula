package ar.edu.unq.virtuaula.model;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import ar.edu.unq.virtuaula.VirtuaulaApplicationTests;

public class PlayerAccountTest extends VirtuaulaApplicationTests{

    @Test
    public void testAddLeaderForPlayer() {
    	PlayerAccount player = (PlayerAccount) createOnePlayerAccount();
    	LeaderAccount leader = (LeaderAccount) createOneLeaderAccount();
    	player.addLeader(leader);
    	assertNotNull(player.getLeaders());
    }
	
}
