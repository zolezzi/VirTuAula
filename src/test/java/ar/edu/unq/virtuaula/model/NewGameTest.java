package ar.edu.unq.virtuaula.model;

import ar.edu.unq.virtuaula.VirtuaulaApplicationTests;
import ar.edu.unq.virtuaula.builder.NewGameBuilder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class NewGameTest extends VirtuaulaApplicationTests {

    @Test
    public void addCampaignWithNoCampaignsOnNewGameHasACampaign() {
        NewGame newGame = NewGameBuilder.newGameWithName("Matematicas").build();
        Campaign campaign = Mockito.mock(Campaign.class);
        int sizeBeforeAdd = newGame.getCampaigns().size();

        newGame.addCampaign(campaign);

        assertEquals(sizeBeforeAdd + 1, newGame.getCampaigns().size());
    }

}
