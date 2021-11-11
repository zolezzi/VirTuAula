package ar.edu.unq.virtuaula.builder;

import java.util.Date;

import ar.edu.unq.virtuaula.model.Campaign;
import ar.edu.unq.virtuaula.model.Mission;

public class CampaignBuilder {

    private final Campaign instance = new Campaign();

    public static CampaignBuilder campaignWithName(String name) {
        CampaignBuilder campaignBuilder = new CampaignBuilder();
        campaignBuilder.instance.setName(name);
        return campaignBuilder;
    }

    public static CampaignBuilder campaignWithNameAndMaxNote(String name, int note) {
        CampaignBuilder campaignBuilder = new CampaignBuilder();
        campaignBuilder.instance.setName(name);
        campaignBuilder.instance.setMaxNote(note);
        return campaignBuilder;
    }

    public CampaignBuilder withMission(Mission mission) {
        this.instance.addMission(mission);
        return this;
    }
    
    public CampaignBuilder withMaxNote(int note) {
        this.instance.setMaxNote(note);
        return this;
    }
    
    public CampaignBuilder withDeliveryDate(Date date) {
        this.instance.setDeliveryDate(date);
        return this;
    }

    public Campaign build() {
        return this.instance;
    }
}
