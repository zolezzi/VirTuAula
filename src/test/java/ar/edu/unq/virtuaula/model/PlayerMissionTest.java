package ar.edu.unq.virtuaula.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ar.edu.unq.virtuaula.VirtuaulaApplicationTests;
import ar.edu.unq.virtuaula.builder.PlayerMissionBuilder;

public class PlayerMissionTest extends VirtuaulaApplicationTests{

    @Test
    public void whenMissionUncompleteThenCompleteNowHasCompleteState() {
    	PlayerMission mission = PlayerMissionBuilder.createPlayerMission().uncompleted().build();
    	mission.complete();
        assertEquals(State.COMPLETED, mission.getState());
    }

    @Test
    public void whenMissionCompleteThenUncompleteNowHasUncompleteState() {
    	PlayerMission mission = PlayerMissionBuilder.createPlayerMission().completed().build();
    	mission.uncomplete();
        assertEquals(State.UNCOMPLETED, mission.getState());
    }
}
