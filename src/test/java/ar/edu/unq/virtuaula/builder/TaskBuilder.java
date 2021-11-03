package ar.edu.unq.virtuaula.builder;

import ar.edu.unq.virtuaula.model.OptionTask;
import ar.edu.unq.virtuaula.model.Task;
import ar.edu.unq.virtuaula.model.TaskType;

public class TaskBuilder {

    private final Task instance = new Task();

    public static TaskBuilder taskWithStatement(String statement) {
        TaskBuilder taskBuilder = new TaskBuilder();
        taskBuilder.instance.setStatement(statement);
        return taskBuilder;
    }

    public TaskBuilder withCorrectAnswer(Long correctAnswer) {
        this.instance.setCorrectAnswer(correctAnswer);
        return this;
    }

    public TaskBuilder withAnswer(Long answer) {
        this.instance.setAnswer(answer);
        return this;
    }

    public TaskBuilder withOptionTask(OptionTask optionTask) {
        this.instance.addOption(optionTask);
        return this;
    }
    
    public TaskBuilder withScore(Double score) {
        this.instance.setScore(score);;
        return this;
    }
    
    public TaskBuilder withTaskType(TaskType taskType) {
        this.instance.setTaskType(taskType);
        return this;
    }

    public Task build() {
        return this.instance;
    }
}
