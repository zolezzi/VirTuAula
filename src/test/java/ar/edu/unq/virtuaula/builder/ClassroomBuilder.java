package ar.edu.unq.virtuaula.builder;

import ar.edu.unq.virtuaula.model.Classroom;
import ar.edu.unq.virtuaula.model.Lesson;

public class ClassroomBuilder {

    private final Classroom instance = new Classroom();

    public static ClassroomBuilder classroomWithName(String name) {
        ClassroomBuilder classroomBuilder = new ClassroomBuilder();
        classroomBuilder.instance.setName(name);
        return classroomBuilder;
    }

    public ClassroomBuilder withLesson(Lesson lesson) {
        this.instance.addLesson(lesson);
        return this;
    }

    public Classroom build() {
        return this.instance;
    }

}
