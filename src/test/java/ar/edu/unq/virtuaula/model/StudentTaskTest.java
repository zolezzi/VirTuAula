package ar.edu.unq.virtuaula.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ar.edu.unq.virtuaula.VirtuaulaApplicationTests;
import ar.edu.unq.virtuaula.builder.StudentTaskBuilder;

public class StudentTaskTest extends VirtuaulaApplicationTests{

    @Test
    public void taskUncompleteThenCompleteNowHasCompleteState() {
    	StudentTask task = StudentTaskBuilder.createStudentTask().uncompleted().build();
        task.complete();
        assertEquals(State.COMPLETED, task.getState());
    }

    @Test
    public void taskCompleteThenUncompleteNowHasUncompleteState() {
    	StudentTask task = StudentTaskBuilder.createStudentTask().completed().build();
        task.uncomplete();
        assertEquals(State.UNCOMPLETED, task.getState());
    }
}
