package ar.edu.unq.virtuaula.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import ar.edu.unq.virtuaula.constants.CSVFormatConstants;
import ar.edu.unq.virtuaula.model.StudentAccount;

public class CSVUtil {

	public final static String TYPE = "text/csv";
	  static final String[] HEADERs = { CSVFormatConstants.COLUMN_FIRST_NAME, CSVFormatConstants.COLUMN_LAST_NAME, 
			  CSVFormatConstants.COLUMN_DNI, CSVFormatConstants.COLUMN_EMAIL };

	  public static boolean hasCSVFormat(MultipartFile file) {
	    return TYPE.equals(file.getContentType());
	  }
	  
	  public static List<StudentAccount> csvToStudents(InputStream inputStream) {
		  try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, 
				  CSVFormatConstants.TRANSFORMATION_FORMAT));
				  CSVParser csvParser = new CSVParser(fileReader,
						  CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

			  List<StudentAccount> students = new ArrayList<>();
			  Iterable<CSVRecord> csvRecords = csvParser.getRecords();

			  for (CSVRecord csvRecord : csvRecords) {
				  StudentAccount student = new StudentAccount();
				  student.setFirstName(csvRecord.get(CSVFormatConstants.COLUMN_FIRST_NAME));
				  student.setLastName(csvRecord.get(CSVFormatConstants.COLUMN_LAST_NAME));
				  student.setDni(Integer.parseInt(csvRecord.get(CSVFormatConstants.COLUMN_DNI)));
				  student.setEmail(csvRecord.get(CSVFormatConstants.COLUMN_EMAIL));
				  students.add(student);
			  }
			  
			  return students;
			   
		  } catch (IOException e) {
			  throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
		  }  
		 
	  }

}
