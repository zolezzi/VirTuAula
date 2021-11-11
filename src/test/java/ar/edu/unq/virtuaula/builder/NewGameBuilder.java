package ar.edu.unq.virtuaula.builder;

import ar.edu.unq.virtuaula.model.NewGame;
import ar.edu.unq.virtuaula.model.Campaign;

public class NewGameBuilder {

    private final NewGame instance = new NewGame();

    public static NewGameBuilder newGameWithName(String name) {
        NewGameBuilder newGameBuilder = new NewGameBuilder();
        newGameBuilder.instance.setName(name);
        return newGameBuilder;
    }

    public NewGameBuilder withCampaign(Campaign campaign) {
        this.instance.addCampaign(campaign);
        return this;
    }

    public NewGame build() {
        return this.instance;
    }

}
