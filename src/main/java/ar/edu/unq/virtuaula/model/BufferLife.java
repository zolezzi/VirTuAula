package ar.edu.unq.virtuaula.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import ar.edu.unq.virtuaula.util.OperatorUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper=false)
@DiscriminatorValue(value="BufferLife")
public class BufferLife extends Buffer{
	
	private static final long serialVersionUID = 3770732449788670745L;

	private Integer lifeValue;
	
	@Override
	public boolean isExperience() {
		return false;
	}

	@Override
	public boolean isLife() {
		return true;
	}

	@Override
	public void apply(StudentAccount student, Double lifeGained) {
		Integer calculatedLife = OperatorUtil.operatorWithInteger(getOperator(), lifeGained.intValue(), this.lifeValue);
		student.setLife(student.lifeSum(student.getLife(), calculatedLife));
	}

}
