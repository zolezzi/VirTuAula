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
    public void taskWithOptionTaskFindCorrectAnswer() {
    	Long expected = Long.valueOf(4l);
        Task task = TaskBuilder.taskWithStatement("Cuando mide el Obelisco").build();
        OptionTask optionTask1 = Mockito.mock(OptionTask.class);
        OptionTask optionTask2 = Mockito.mock(OptionTask.class);
        OptionTask optionTask3 = Mockito.mock(OptionTask.class);
        OptionTask optionTask4 = Mockito.mock(OptionTask.class);
        Mockito.when(optionTask1.isCorrect()).thenReturn(false);
        Mockito.when(optionTask2.isCorrect()).thenReturn(false);
        Mockito.when(optionTask3.isCorrect()).thenReturn(false);
        Mockito.when(optionTask4.isCorrect()).thenReturn(true);
        Mockito.when(optionTask4.getId()).thenReturn(Long.valueOf(4l));
        task.addOption(optionTask1);
        task.addOption(optionTask2);
        task.addOption(optionTask3);
        task.addOption(optionTask4);
        assertEquals(expected, task.findCorrectAnswer());
    }
}
