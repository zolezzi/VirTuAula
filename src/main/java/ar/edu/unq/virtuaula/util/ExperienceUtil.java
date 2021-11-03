package ar.edu.unq.virtuaula.util;

import org.springframework.stereotype.Component;

@Component
public class ExperienceUtil {

	public static boolean isChangeLevel(Double max, Double actualExperience) {
		return max < actualExperience;
	}

}
