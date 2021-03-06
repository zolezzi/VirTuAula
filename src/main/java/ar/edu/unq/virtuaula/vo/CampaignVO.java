package ar.edu.unq.virtuaula.vo;

import ar.edu.unq.virtuaula.model.State;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class CampaignVO {

    private Long id;
    private String name;
    private int progress;
    private Long newGameId;
    private Double note;
    private State state;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="America/Argentina/Buenos_Aires")
    private Date deliveryDate;
}
