package ar.edu.unq.virtuaula.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import ar.edu.unq.virtuaula.VirtuaulaApplicationTests;
import ar.edu.unq.virtuaula.builder.CampaignBuilder;

public class CampaignTest extends VirtuaulaApplicationTests {

    @Test
    public void addMissionWithNoMissionOnACampaignHasAMission() {
        Campaign campaign = CampaignBuilder.campaignWithName("Ecuaciones").build();
        Mission mission = Mockito.mock(Mission.class);
        int sizeBeforeAdd = campaign.getMissions().size();
        campaign.addMission(mission);
        assertEquals(sizeBeforeAdd + 1, campaign.getMissions().size());
    }

    @Test
    public void progressWithOneMissionCompletedHas100Progress() {
        Campaign campaign = CampaignBuilder.campaignWithName("Ecuaciones").build();
        
        Mission mission1 = Mockito.mock(Mission.class);
        Mockito.when(mission1.getCorrectAnswer()).thenReturn(1l);
        Mockito.when(mission1.getScore()).thenReturn(100.0);
        
        Mission mission2 = Mockito.mock(Mission.class);
        Mockito.when(mission2.getCorrectAnswer()).thenReturn(2l);
        Mockito.when(mission2.getScore()).thenReturn(100.0);
        
        PlayerMission playerMission1 = Mockito.mock(PlayerMission.class);
        PlayerMission playerMission2 = Mockito.mock(PlayerMission.class);

        Mockito.when(playerMission1.getAnswer()).thenReturn(1l);
        Mockito.when(playerMission2.getAnswer()).thenReturn(1l);
        Mockito.when(playerMission1.getState()).thenReturn(State.COMPLETED);
        Mockito.when(playerMission2.getState()).thenReturn(State.COMPLETED);
        Mockito.when(playerMission1.getMission()).thenReturn(mission1);
        Mockito.when(playerMission2.getMission()).thenReturn(mission2);

        List<PlayerMission> missions = new ArrayList<>();
        missions.add(playerMission1);
        missions.add(playerMission2);
        assertEquals(100, campaign.progress(missions));
    }

    @Test
    public void progressWithTwoMissionsOneCompletedAndOtherUncompletedHas50Progress() {
        Campaign campaign = CampaignBuilder.campaignWithName("Ecuaciones").build();
        Mission mission1 = Mockito.mock(Mission.class);
        Mockito.when(mission1.getCorrectAnswer()).thenReturn(1l);
        Mockito.when(mission1.getScore()).thenReturn(100.0);
        
        Mission mission2 = Mockito.mock(Mission.class);
        Mockito.when(mission2.getCorrectAnswer()).thenReturn(2l);
        Mockito.when(mission2.getScore()).thenReturn(100.0);
        
        PlayerMission playerMission1 = Mockito.mock(PlayerMission.class);
        PlayerMission playerMission2 = Mockito.mock(PlayerMission.class);

        Mockito.when(playerMission1.getAnswer()).thenReturn(1l);
        Mockito.when(playerMission2.getAnswer()).thenReturn(1l);
        Mockito.when(playerMission1.getState()).thenReturn(State.COMPLETED);
        Mockito.when(playerMission2.getState()).thenReturn(State.UNCOMPLETED);
        Mockito.when(playerMission1.getMission()).thenReturn(mission1);
        Mockito.when(playerMission2.getMission()).thenReturn(mission2);

        List<PlayerMission> missions = new ArrayList<>();
        missions.add(playerMission1);
        missions.add(playerMission2);
        assertEquals(50, campaign.progress(missions));
    }

    @Test
    public void progressWithoutMissionsHas0Progress() {
        Campaign campaign = CampaignBuilder.campaignWithName("Ecuaciones").build();
        List<PlayerMission> missions = new ArrayList<>();
        assertEquals(0, campaign.progress(missions));
    }

    @Test
    public void qualifyWithOneMissionEvaluateAnswerCorrect() {
        Campaign campaign = CampaignBuilder.campaignWithNameAndMaxNote("Ecuaciones", 10).build();
        int expected = 10;
        Mission mission = Mockito.mock(Mission.class);
        Mockito.when(mission.getCorrectAnswer()).thenReturn(1l);
        Mockito.when(mission.getScore()).thenReturn(10.0);
        PlayerMission playerMission = Mockito.mock(PlayerMission.class);
        Mockito.when(playerMission.getAnswer()).thenReturn(1l);
        Mockito.when(playerMission.getState()).thenReturn(State.COMPLETED);
        Mockito.when(playerMission.getMission()).thenReturn(mission);
        campaign.addMission(mission);
        List<PlayerMission> missions = new ArrayList<>();
        missions.add(playerMission);
        assertEquals(expected, campaign.qualification(missions));
    }

    @Test
    public void qualifyWithTwoMissionEvaluateAnswerIncorrect() {
        Campaign campaign = CampaignBuilder.campaignWithNameAndMaxNote("Ecuaciones", 10).build();
        int expected = 5;
        Mission mission = Mockito.mock(Mission.class);
        Mission mission2 = Mockito.mock(Mission.class);
        Mockito.when(mission.getAnswer()).thenReturn(1l);
        Mockito.when(mission.getCorrectAnswer()).thenReturn(1l);
        Mockito.when(mission.getScore()).thenReturn(5.0);

        Mockito.when(mission2.getAnswer()).thenReturn(1l);
        Mockito.when(mission2.getCorrectAnswer()).thenReturn(2l);
        Mockito.when(mission2.getScore()).thenReturn(5.0);
        
        PlayerMission playerMission = Mockito.mock(PlayerMission.class);
        Mockito.when(playerMission.getAnswer()).thenReturn(1l);
        Mockito.when(playerMission.getState()).thenReturn(State.COMPLETED);
        Mockito.when(playerMission.getMission()).thenReturn(mission);
        
        PlayerMission playerMission2 = Mockito.mock(PlayerMission.class);
        Mockito.when(playerMission2.getAnswer()).thenReturn(1l);
        Mockito.when(playerMission2.getState()).thenReturn(State.COMPLETED);
        Mockito.when(playerMission2.getMission()).thenReturn(mission2);

        List<PlayerMission> missions = new ArrayList<>();
        missions.add(playerMission);
        missions.add(playerMission2);

        campaign.addMission(mission);
        campaign.addMission(mission2);

        assertEquals(expected, campaign.qualification(missions));
    }

    @Test
    public void qualifyWithUncompleteMissionHas0Qualification() {
        Campaign campaign = CampaignBuilder.campaignWithNameAndMaxNote("Ecuaciones", 10).build();
        int expected = 0;
        Mission mission = Mockito.mock(Mission.class);
        Mockito.when(mission.getAnswer()).thenReturn(null);
        Mockito.when(mission.getCorrectAnswer()).thenReturn(1l);
        Mockito.when(mission.getScore()).thenReturn(10.0);

        PlayerMission playerMission = Mockito.mock(PlayerMission.class);
        Mockito.when(playerMission.getAnswer()).thenReturn(1l);
        Mockito.when(playerMission.getState()).thenReturn(State.UNCOMPLETED);
        Mockito.when(playerMission.getMission()).thenReturn(mission);
        
        campaign.addMission(mission);
        List<PlayerMission> missions = new ArrayList<>();
        missions.add(playerMission);

        assertEquals(expected, campaign.qualification(missions));
    }

    @Test
    public void qualifyWithoutMissionEvaluateAnswerCorrect() {
        Campaign campaign = CampaignBuilder.campaignWithNameAndMaxNote("Ecuaciones", 10).build();
        int expected = 0;
        List<PlayerMission> missions = new ArrayList<>();
        assertEquals(expected, campaign.qualification(missions));
    }

    @Test
    public void qualifyWithQualificationMoreThanMaxNoteThrowsException() {
        Campaign campaign = CampaignBuilder.campaignWithNameAndMaxNote("Ecuaciones", 10).build();

        Mission mission = Mockito.mock(Mission.class);
        Mockito.when(mission.getAnswer()).thenReturn(1l);
        Mockito.when(mission.getCorrectAnswer()).thenReturn(1l);
        Mockito.when(mission.getScore()).thenReturn(100.0);
        
        PlayerMission playerMission = Mockito.mock(PlayerMission.class);
        Mockito.when(playerMission.getAnswer()).thenReturn(1l);
        Mockito.when(playerMission.getState()).thenReturn(State.COMPLETED);
        Mockito.when(playerMission.getMission()).thenReturn(mission);
        campaign.addMission(mission);
        List<PlayerMission> missions = new ArrayList<>();
        missions.add(playerMission);
        IllegalArgumentException assertThrows = assertThrows(IllegalArgumentException.class, () -> {
        	campaign.qualification(missions);
        });

        assertTrue(assertThrows.getMessage().contains("The qualification exceed the max note."));
    }

}
