package ar.edu.unq.virtuaula.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
public class Campaign implements Serializable {

    private static final long serialVersionUID = 768575911005782319L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int maxNote;
    
    private Date deliveryDate;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "campaign_id")
    @JsonIgnoreProperties("campaign")
    private List<Mission> missions = new ArrayList<>();

    public void addMission(Mission mission) {
        this.missions.add(mission);
    }

    public int progress(List<PlayerMission> missions) {
        int completed = (int) missions.stream().filter(mission -> State.COMPLETED.equals(mission.getState())).count();
        return missions.isEmpty() ? 0 : completed * 100 / missions.size();
    }

    public double qualification(List<PlayerMission> missions) {
        double completed = (double) missions.stream()
                .filter(playerMission -> State.COMPLETED.equals(playerMission.getState()) && playerMission.getMission().getCorrectAnswer().equals(playerMission.getAnswer()))
                .mapToDouble(playerMission -> playerMission.getMission().getScore())
                .sum();
        return missions.isEmpty() ? 0 : validate(completed);
    }

    private double validate(double completed) {
        Assert.isTrue(this.maxNote >= completed, "The qualification exceed the max note.");
        return completed;
    }
}
