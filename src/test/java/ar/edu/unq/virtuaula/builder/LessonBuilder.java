package ar.edu.unq.virtuaula.builder;

import java.util.Date;

import ar.edu.unq.virtuaula.model.Lesson;
import ar.edu.unq.virtuaula.model.Task;

public class LessonBuilder {

    private final Lesson instance = new Lesson();

    public static LessonBuilder lessonWithName(String name) {
        LessonBuilder lessonBuilder = new LessonBuilder();
        lessonBuilder.instance.setName(name);
        return lessonBuilder;
    }

    public static LessonBuilder lessonWithNameAndMaxNote(String name, int note) {
        LessonBuilder lessonBuilder = new LessonBuilder();
        lessonBuilder.instance.setName(name);
        lessonBuilder.instance.setMaxNote(note);
        return lessonBuilder;
    }

    public LessonBuilder withTask(Task task) {
        this.instance.addTask(task);
        return this;
    }
    
    public LessonBuilder withMaxNote(int note) {
        this.instance.setMaxNote(note);
        return this;
    }
    
    public LessonBuilder withDeliveryDate(Date date) {
        this.instance.setDeliveryDate(date);
        return this;
    }

    public Lesson build() {
        return this.instance;
    }
}
