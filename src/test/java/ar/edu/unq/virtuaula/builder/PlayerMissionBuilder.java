package ar.edu.unq.virtuaula.builder;

import ar.edu.unq.virtuaula.model.Campaign;
import ar.edu.unq.virtuaula.model.PlayerAccount;
import ar.edu.unq.virtuaula.model.PlayerMission;
import ar.edu.unq.virtuaula.model.Mission;

public class PlayerMissionBuilder {

    private final PlayerMission instance = new PlayerMission();

    public static PlayerMissionBuilder createPlayerMission() {
        PlayerMissionBuilder missionBuilder = new PlayerMissionBuilder();
        return missionBuilder;
    }
    
    public PlayerMissionBuilder withMission(Mission mission) {
        this.instance.setMission(mission);
        return this;
    }
    
    public PlayerMissionBuilder withCampaign(Campaign campaign) {
        this.instance.setCampaign(campaign);
        return this;
    }
    
    public PlayerMissionBuilder withPlayerAccount(PlayerAccount playerAccount) {
        this.instance.setPlayerAccount(playerAccount);
        return this;
    }

    public PlayerMissionBuilder completed() {
        this.instance.complete();
        return this;
    }

    public PlayerMissionBuilder uncompleted() {
        this.instance.uncomplete();
        return this;
    }

    public PlayerMission build() {
        return this.instance;
    }
}
