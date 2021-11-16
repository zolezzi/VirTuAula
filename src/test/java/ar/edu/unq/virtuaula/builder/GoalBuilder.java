package ar.edu.unq.virtuaula.builder;

import ar.edu.unq.virtuaula.model.Goal;
import ar.edu.unq.virtuaula.model.Level;

public class GoalBuilder {
    private final Goal instance = new Goal();

    public static GoalBuilder goalWithName(String name) {
    	GoalBuilder goalBuilder = new GoalBuilder();
    	goalBuilder.instance.setName(name);
        return goalBuilder;
    }
    
    public GoalBuilder withDescription(String description) {
        this.instance.setDescription(description);
        return this;
    }
    
    public GoalBuilder withLevel(Level level) {
        this.instance.setLevel(level);
        return this;
    }

    public Goal build() {
        return this.instance;
    }
}
