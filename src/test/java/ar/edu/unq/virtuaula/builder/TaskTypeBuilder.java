package ar.edu.unq.virtuaula.builder;

import ar.edu.unq.virtuaula.model.TaskType;

public class TaskTypeBuilder {

	private final TaskType instance = new TaskType();
	 
	public static TaskTypeBuilder taskTypeWithName(String name) {
		TaskTypeBuilder taskTypeBuilder = new TaskTypeBuilder();
		taskTypeBuilder.instance.setName(name);
		return taskTypeBuilder;
	}
	
    public TaskType build() {
        return this.instance;
    }
}
