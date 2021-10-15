package ar.edu.unq.virtuaula.builder;

import ar.edu.unq.virtuaula.model.Lesson;
import ar.edu.unq.virtuaula.model.StudentAccount;
import ar.edu.unq.virtuaula.model.StudentTask;
import ar.edu.unq.virtuaula.model.Task;

public class StudentTaskBuilder {

    private final StudentTask instance = new StudentTask();

    public static StudentTaskBuilder createStudentTask() {
        StudentTaskBuilder taskBuilder = new StudentTaskBuilder();
        return taskBuilder;
    }
    
    public StudentTaskBuilder withTask(Task task) {
        this.instance.setTask(task);
        return this;
    }
    
    public StudentTaskBuilder withLesson(Lesson lesson) {
        this.instance.setLesson(lesson);
        return this;
    }
    
    public StudentTaskBuilder withStudentAccount(StudentAccount studentAccount) {
        this.instance.setStudentAccount(studentAccount);
        return this;
    }

    public StudentTaskBuilder completed() {
        this.instance.complete();
        return this;
    }

    public StudentTaskBuilder uncompleted() {
        this.instance.uncomplete();
        return this;
    }

    public StudentTask build() {
        return this.instance;
    }
}
