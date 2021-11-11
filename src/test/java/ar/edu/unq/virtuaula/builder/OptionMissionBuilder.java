package ar.edu.unq.virtuaula.builder;

import ar.edu.unq.virtuaula.model.OptionMission;

public class OptionMissionBuilder {

    private final OptionMission instance = new OptionMission();

    public static OptionMissionBuilder missionWithReponseValue(String reponseValue) {
        OptionMissionBuilder optionMissionBuilder = new OptionMissionBuilder();
        optionMissionBuilder.instance.setResponseValue(reponseValue);
        return optionMissionBuilder;
    }

    public OptionMissionBuilder withIsCorrect(boolean isCorrect) {
        this.instance.setCorrect(isCorrect);
        return this;
    }

    public OptionMission build() {
        return this.instance;
    }
}
