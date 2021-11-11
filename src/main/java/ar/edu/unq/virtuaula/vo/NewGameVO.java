package ar.edu.unq.virtuaula.vo;

import ar.edu.unq.virtuaula.dto.NewGameDTO;
import java.util.List;
import lombok.Data;

@Data
public class NewGameVO {
    
    private NewGameDTO newGame;
    private List<Long> players;
}
