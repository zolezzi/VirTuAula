package ar.edu.unq.virtuaula.builder;

import ar.edu.unq.virtuaula.model.Lesson;
import ar.edu.unq.virtuaula.model.Task;

public class LessonBuilder {

    private final Lesson instance = new Lesson();
    
    public static LessonBuilder lessonWithName(String name) {
        LessonBuilder lessonBuilder = new LessonBuilder();
        lessonBuilder.instance.setName(name);
        return lessonBuilder;
    }
    
    public LessonBuilder withTask(Task task) {
        this.instance.addTask(task);
        return this;
    }
    
    public Lesson build() {
        return this.instance;
    }
}
