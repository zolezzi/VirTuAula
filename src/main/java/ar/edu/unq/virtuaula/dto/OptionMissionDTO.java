package ar.edu.unq.virtuaula.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class OptionMissionDTO implements Serializable {

    private static final long serialVersionUID = -2444114603403962407L;
    private Long id;
    private String responseValue;
    private Boolean isCorrect;
}
