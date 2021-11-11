package ar.edu.unq.virtuaula.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
public class Mission implements Serializable {

    private static final long serialVersionUID = 768575911005782319L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String statement;

    private double score;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "campaign_id", referencedColumnName = "id")
    @JsonIgnoreProperties("missions")
    private Campaign campaign;
    
    private Long answer;

    private Long correctAnswer;
    
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "mission_type_id", referencedColumnName = "id")
    @JsonIgnoreProperties("missions")
    private MissionType missionType;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    @JsonIgnoreProperties("mission")
    private List<OptionMission> options = new ArrayList<>();

    public void addOption(OptionMission option) {
        this.options.add(option);
    }

    public Long findCorrectAnswer() {
        return this.getOptions().stream().filter(optionMission -> optionMission.isCorrect()).findFirst().get().getId();
    }

}
