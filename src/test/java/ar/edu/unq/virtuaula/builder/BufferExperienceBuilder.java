package ar.edu.unq.virtuaula.builder;

import ar.edu.unq.virtuaula.model.BufferExperience;
import ar.edu.unq.virtuaula.model.Level;

public class BufferExperienceBuilder {

    private final BufferExperience instance = new BufferExperience();

    public static BufferExperienceBuilder bufferExperienceWithName(String name) {
    	BufferExperienceBuilder bufferExperienceBuilder = new BufferExperienceBuilder();
    	bufferExperienceBuilder.instance.setName(name);
        return bufferExperienceBuilder;
    }
    
    public BufferExperienceBuilder withDescription(String description) {
        this.instance.setDescription(description);
        return this;
    }
    
    public BufferExperienceBuilder withLevel(Level level) {
        this.instance.setLevel(level);
        return this;
    }
    
    public BufferExperienceBuilder withOperator(String operator) {
        this.instance.setOperator(operator);
        return this;
    }
    
    public BufferExperienceBuilder withExperienceValue(Double value) {
        this.instance.setExperienceValue(value);
        return this;
    }
    
    public BufferExperience build() {
        return this.instance;
    }
}
