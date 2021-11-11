package ar.edu.unq.virtuaula.builder;

import ar.edu.unq.virtuaula.model.OptionMission;
import ar.edu.unq.virtuaula.model.Mission;
import ar.edu.unq.virtuaula.model.MissionType;

public class MissionBuilder {

    private final Mission instance = new Mission();

    public static MissionBuilder missionWithStatement(String statement) {
        MissionBuilder missionBuilder = new MissionBuilder();
        missionBuilder.instance.setStatement(statement);
        return missionBuilder;
    }

    public MissionBuilder withCorrectAnswer(Long correctAnswer) {
        this.instance.setCorrectAnswer(correctAnswer);
        return this;
    }

    public MissionBuilder withAnswer(Long answer) {
        this.instance.setAnswer(answer);
        return this;
    }

    public MissionBuilder withOptionMission(OptionMission optionMission) {
        this.instance.addOption(optionMission);
        return this;
    }
    
    public MissionBuilder withScore(Double score) {
        this.instance.setScore(score);
        return this;
    }
    
    public MissionBuilder withMissionType(MissionType missionType) {
        this.instance.setMissionType(missionType);
        return this;
    }

    public Mission build() {
        return this.instance;
    }
}
