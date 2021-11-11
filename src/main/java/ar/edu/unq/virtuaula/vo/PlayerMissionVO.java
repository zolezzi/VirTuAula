package ar.edu.unq.virtuaula.vo;

import java.util.List;

import lombok.Data;

@Data
public class PlayerMissionVO {

    private Long id;
    private String statement;
    private List<OptionMissionVO> options;
    private Double score;
    private Long answer;
    private Long missionTypeId;
    private String story;
}
