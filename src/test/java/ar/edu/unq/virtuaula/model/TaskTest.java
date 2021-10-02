package ar.edu.unq.virtuaula.model;

import ar.edu.unq.virtuaula.VirtuaulaApplicationTests;
import ar.edu.unq.virtuaula.builder.TaskBuilder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class TaskTest extends VirtuaulaApplicationTests {

    @Test
    public void addOptionWithNoOptionTaskHasOption() {
        Task task = TaskBuilder.taskWithStatement("Cuando mide el Obelisco").build();
        OptionTask optionTask = Mockito.mock(OptionTask.class);
        int sizeBeforeAdd = task.getOptions().size();

        task.addOption(optionTask);

        assertEquals(sizeBeforeAdd + 1, task.getOptions().size());
    }

    @Test
    public void taskUncompleteThenCompleteNowHasCompleteState() {
        Task task = TaskBuilder.taskWithStatement("Cuando mide el Obelisco").uncompleted().build();

        task.complete();

        assertEquals(State.COMPLETED, task.getState());
    }

    @Test
    public void taskCompleteThenUncompleteNowHasUncompleteState() {
        Task task = TaskBuilder.taskWithStatement("Cuando mide el Obelisco").completed().build();

        task.uncomplete();

        assertEquals(State.UNCOMPLETED, task.getState());
    }
}
