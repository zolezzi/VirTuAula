package ar.edu.unq.virtuaula.builder;

import ar.edu.unq.virtuaula.model.OptionTask;

public class OptionTaskBuilder {

    private final OptionTask instance = new OptionTask();

    public static OptionTaskBuilder taskWithReponseValue(String reponseValue) {
        OptionTaskBuilder optionTaskBuilder = new OptionTaskBuilder();
        optionTaskBuilder.instance.setResponseValue(reponseValue);
        return optionTaskBuilder;
    }

    public OptionTaskBuilder withIsCorrect(boolean isCorrect) {
        this.instance.setCorrect(isCorrect);
        return this;
    }

    public OptionTask build() {
        return this.instance;
    }
}
