package ar.edu.unq.virtuaula.builder;

import ar.edu.unq.virtuaula.model.MissionType;

public class MissionTypeBuilder {

	private final MissionType instance = new MissionType();
	 
	public static MissionTypeBuilder missionTypeWithName(String name) {
		MissionTypeBuilder missionTypeBuilder = new MissionTypeBuilder();
		missionTypeBuilder.instance.setName(name);
		return missionTypeBuilder;
	}
	
    public MissionType build() {
        return this.instance;
    }
}
