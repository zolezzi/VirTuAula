package ar.edu.unq.virtuaula.builder;

import ar.edu.unq.virtuaula.model.BufferLife;
import ar.edu.unq.virtuaula.model.Level;

public class BufferLifeBuilder {

    private final BufferLife instance = new BufferLife();

    public static BufferLifeBuilder bufferLifeWithName(String name) {
    	BufferLifeBuilder bufferLifeBuilder = new BufferLifeBuilder();
    	bufferLifeBuilder.instance.setName(name);
        return bufferLifeBuilder;
    }
    
    public BufferLifeBuilder withDescription(String description) {
        this.instance.setDescription(description);
        return this;
    }
    
    public BufferLifeBuilder withLevel(Level level) {
        this.instance.setLevel(level);
        return this;
    }
    
    public BufferLifeBuilder withOperator(String operator) {
        this.instance.setOperator(operator);
        return this;
    }
    
    public BufferLifeBuilder withLifeValue(Integer value) {
        this.instance.setLifeValue(value);
        return this;
    }
    
    public BufferLife build() {
        return this.instance;
    }
}
