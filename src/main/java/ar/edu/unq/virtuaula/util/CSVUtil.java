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
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import ar.edu.unq.virtuaula.constants.CSVFormatConstants;
import ar.edu.unq.virtuaula.model.PlayerAccount;

@Component
public class CSVUtil {

	public final static String TYPE = "text/csv";
	  static final String[] HEADERs = { CSVFormatConstants.COLUMN_FIRST_NAME, CSVFormatConstants.COLUMN_LAST_NAME, 
			  CSVFormatConstants.COLUMN_DNI, CSVFormatConstants.COLUMN_EMAIL };

	  public boolean hasCSVFormat(MultipartFile file) {
	    return TYPE.equals(file.getContentType());
	  }
	  
	  public List<PlayerAccount> csvToPlayers(InputStream inputStream) {
		  try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, 
				  CSVFormatConstants.TRANSFORMATION_FORMAT));
				  CSVParser csvParser = new CSVParser(fileReader,
						  CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

			  List<PlayerAccount> players = new ArrayList<>();
			  Iterable<CSVRecord> csvRecords = csvParser.getRecords();

			  for (CSVRecord csvRecord : csvRecords) {
				  PlayerAccount player = new PlayerAccount();
				  player.setFirstName(csvRecord.get(CSVFormatConstants.COLUMN_FIRST_NAME));
				  player.setLastName(csvRecord.get(CSVFormatConstants.COLUMN_LAST_NAME));
				  player.setUsername(csvRecord.get(CSVFormatConstants.COLUMN_DNI));
				  player.setDni(Integer.parseInt(csvRecord.get(CSVFormatConstants.COLUMN_DNI)));
				  player.setEmail(csvRecord.get(CSVFormatConstants.COLUMN_EMAIL));
				  player.setExperience(Double.valueOf(0.0d));
				  player.setLife(3);
				  players.add(player);
			  }
			  
			  return players;
			   
		  } catch (IOException e) {
			  throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
		  }  
		 
	  }

}
