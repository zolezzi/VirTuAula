package ar.edu.unq.virtuaula.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
public class NewGame implements Serializable {

    private static final long serialVersionUID = 768575911005782319L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "new_game_id")
    @JsonIgnoreProperties("newGame")
    private List<Campaign> campaigns = new ArrayList<>();

    @ManyToMany(mappedBy = "newGames")
    private List<Account> accounts = new ArrayList<>();

    public void addCampaign(Campaign campaign) {
        this.campaigns.add(campaign);
    }

	public boolean containsCampaign(Long campaignId) {
		return this.getCampaigns().stream().map(campaign -> campaign.getId())
		.collect(Collectors.toList())
		.contains(campaignId);
	}

}
