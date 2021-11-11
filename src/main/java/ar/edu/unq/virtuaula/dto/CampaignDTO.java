package ar.edu.unq.virtuaula.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class CampaignDTO implements Serializable {

    private static final long serialVersionUID = -225949991242303086L;
    private Long id;
    private String name;
    private Long newGameId;
    private int maxNote;
    private List<MissionDTO> missions;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="America/Argentina/Buenos_Aires")
    private Date deliveryDate;

}
