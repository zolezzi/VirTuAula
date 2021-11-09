package ar.edu.unq.virtuaula.builder;

import java.util.List;

import ar.edu.unq.virtuaula.model.Buffer;
import ar.edu.unq.virtuaula.model.Level;

public class LevelBuilder {

    private final Level instance = new Level();

    public static LevelBuilder levelWithName(String name) {
    	LevelBuilder levelBuilder = new LevelBuilder();
    	levelBuilder.instance.setName(name);
        return levelBuilder;
    }
    
    public LevelBuilder withDescription(String description) {
        this.instance.setDescription(description);
        return this;
    }
    
    public LevelBuilder withNumberLevel(Integer levelNumber) {
        this.instance.setNumberLevel(levelNumber);
        return this;
    }
    
    public LevelBuilder withImagePath(String imagePath) {
        this.instance.setImagePath(imagePath);
        return this;
    }
    
    public LevelBuilder withNameNextLevel(String nameNextLevel) {
        this.instance.setNameNextLevel(nameNextLevel);
        return this;
    }
    
    public LevelBuilder withMinValue(Double minValue) {
        this.instance.setMinValue(minValue);
        return this;
    }
    
    public LevelBuilder withMaxValue(Double maxValue) {
        this.instance.setMaxValue(maxValue);
        return this;
    }
    
    public LevelBuilder withBuffers(List<Buffer> buffers) {
    	 this.instance.setBuffers(buffers);
    	 return this;
    }
    
    public Level build() {
        return this.instance;
    }
}
