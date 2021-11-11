package ar.edu.unq.virtuaula.model;

import ar.edu.unq.virtuaula.VirtuaulaApplicationTests;
import ar.edu.unq.virtuaula.builder.MissionBuilder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class MissionTest extends VirtuaulaApplicationTests {

    @Test
    public void addOptionWithNoOptionMissionHasOption() {
        Mission mission = MissionBuilder.missionWithStatement("Cuando mide el Obelisco").build();
        OptionMission optionMission = Mockito.mock(OptionMission.class);
        int sizeBeforeAdd = mission.getOptions().size();
        mission.addOption(optionMission);
        assertEquals(sizeBeforeAdd + 1, mission.getOptions().size());
    }

    @Test
    public void missionWithOptionMissionFindCorrectAnswer() {
        Long expected = Long.valueOf(4l);
        Mission mission = MissionBuilder.missionWithStatement("Cuando mide el Obelisco").build();
        OptionMission optionMission1 = Mockito.mock(OptionMission.class);
        OptionMission optionMission2 = Mockito.mock(OptionMission.class);
        OptionMission optionMission3 = Mockito.mock(OptionMission.class);
        OptionMission optionMission4 = Mockito.mock(OptionMission.class);
        Mockito.when(optionMission1.isCorrect()).thenReturn(false);
        Mockito.when(optionMission2.isCorrect()).thenReturn(false);
        Mockito.when(optionMission3.isCorrect()).thenReturn(false);
        Mockito.when(optionMission4.isCorrect()).thenReturn(true);
        Mockito.when(optionMission4.getId()).thenReturn(Long.valueOf(4l));
        mission.addOption(optionMission1);
        mission.addOption(optionMission2);
        mission.addOption(optionMission3);
        mission.addOption(optionMission4);
        assertEquals(expected, mission.findCorrectAnswer());
    }
}
