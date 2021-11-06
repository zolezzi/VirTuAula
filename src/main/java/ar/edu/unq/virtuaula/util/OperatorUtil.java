package ar.edu.unq.virtuaula.util;

public class OperatorUtil {

	private static final String OPERATOR_SUM = "+";
	private static final String OPERATOR_MULTIPLIER = "*";

	
	public static Integer operatorWithInteger(String operator, Integer value1, Integer value2) {
	    Integer result;
		switch (operator) {
        case OPERATOR_SUM:
            result = value1 + value2; 
            break;
        case OPERATOR_MULTIPLIER:
            result = value1 * value2;
            break;
        default:
            result = value1 - value2;
            break;
		} 
		
		return result;	
	}
	
	public static Double operatorWithDouble(String operator, Double value1, Double value2) {
	    Double result;
		switch (operator) {
        case OPERATOR_SUM:
            result = value1 + value2; 
            break;
        case OPERATOR_MULTIPLIER:
            result = value1 * value2;
            break;
        default:
            result = value1 - value2;
            break;
		}
		return result;
	}
}
