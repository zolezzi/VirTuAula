package ar.edu.unq.virtuaula.util;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import ar.edu.unq.virtuaula.dto.AccountDTO;
import ar.edu.unq.virtuaula.dto.NewGameDTO;
import ar.edu.unq.virtuaula.dto.OptionMissionDTO;
import ar.edu.unq.virtuaula.dto.MissionDTO;
import ar.edu.unq.virtuaula.model.NewGame;
import ar.edu.unq.virtuaula.model.OptionMission;
import ar.edu.unq.virtuaula.model.Mission;
import ar.edu.unq.virtuaula.model.LeaderAccount;
import ar.edu.unq.virtuaula.vo.MissionVO;

@Component
public class MapperUtil {

    private final ModelMapper modelMapper;

    public MapperUtil() {
        this.modelMapper = new ModelMapper();
        this.configure();
    }

    public ModelMapper getMapper() {
        return this.modelMapper;
    }

    private void configure() {
        // NewGameDTO config
        this.modelMapper.typeMap(NewGame.class, NewGameDTO.class).addMappings(mapper -> {
            mapper.map(NewGame::getId, NewGameDTO::setId);
            mapper.map(NewGame::getName, NewGameDTO::setName);
        });
        // MissionDTO config
        this.modelMapper.typeMap(Mission.class, MissionDTO.class).addMappings(mapper -> {
            mapper.map(Mission::getId, MissionDTO::setId);
            mapper.map(Mission::getStatement, MissionDTO::setStatement);
            mapper.map(Mission::getAnswer, MissionDTO::setAnswer);
            mapper.map(Mission::getCorrectAnswer, MissionDTO::setCorrectAnswer);
        });

        // OptionMissionDTO config
        this.modelMapper.typeMap(OptionMission.class, OptionMissionDTO.class).addMappings(mapper -> {
            mapper.map(OptionMission::getId, OptionMissionDTO::setId);
            mapper.map(OptionMission::isCorrect, OptionMissionDTO::setIsCorrect);
            mapper.map(OptionMission::getResponseValue, OptionMissionDTO::setResponseValue);
        });

        // Mission config
        this.modelMapper.typeMap(MissionDTO.class, Mission.class).addMappings(mapper -> {
            mapper.map(MissionDTO::getId, Mission::setId);
            mapper.map(MissionDTO::getStatement, Mission::setStatement);
            mapper.map(MissionDTO::getAnswer, Mission::setAnswer);
            mapper.map(MissionDTO::getCorrectAnswer, Mission::setCorrectAnswer);

        });

        // MissionVO config
        this.modelMapper.typeMap(Mission.class, MissionVO.class).addMappings(mapper -> {
            mapper.map(Mission::getId, MissionVO::setId);
            mapper.map(Mission::getAnswer, MissionVO::setAnswerId);
        });

        // NewGameDTO config
        this.modelMapper.typeMap(LeaderAccount.class, AccountDTO.class).addMappings(mapper -> {
            mapper.map(LeaderAccount::getId, AccountDTO::setAccountId);
            mapper.map(LeaderAccount::getFirstName, AccountDTO::setFirstName);
            mapper.map(LeaderAccount::getLastName, AccountDTO::setLastName);
            mapper.map(LeaderAccount::getEmail, AccountDTO::setEmail);
            mapper.map(LeaderAccount::getDni, AccountDTO::setDni);
        });
    }
}
