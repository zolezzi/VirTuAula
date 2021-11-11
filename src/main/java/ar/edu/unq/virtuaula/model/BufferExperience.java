package ar.edu.unq.virtuaula.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import ar.edu.unq.virtuaula.util.OperatorUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper=false)
@DiscriminatorValue(value="BufferExperience")
public class BufferExperience extends Buffer {

	private static final long serialVersionUID = 491882710583547595L;

    
	private Double experienceValue;
	
	@Override
	public boolean isExperience() {
		return true;
	}

	@Override
	public boolean isLife() {
		return false;
	}

	@Override
	public void apply(PlayerAccount player, Double experienceGained) {
		if(experienceGained != null) {
			Double calculatedExperience = OperatorUtil.operatorWithDouble(getOperator(), experienceGained, this.experienceValue);
			player.setExperience(player.calculateExperience(player.getExperience(), calculatedExperience));
		}
	}

}
