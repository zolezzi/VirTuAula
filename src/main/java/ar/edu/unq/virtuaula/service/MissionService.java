package ar.edu.unq.virtuaula.service;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.edu.unq.virtuaula.dto.MissionDTO;
import ar.edu.unq.virtuaula.exception.CampaignNotFoundException;
import ar.edu.unq.virtuaula.model.Campaign;
import ar.edu.unq.virtuaula.model.OptionMission;
import ar.edu.unq.virtuaula.model.PlayerMission;
import ar.edu.unq.virtuaula.model.Mission;
import ar.edu.unq.virtuaula.model.LeaderAccount;
import ar.edu.unq.virtuaula.repository.PlayerMissionRepository;
import ar.edu.unq.virtuaula.repository.MissionRepository;
import ar.edu.unq.virtuaula.util.MapperUtil;
import ar.edu.unq.virtuaula.vo.OptionMissionVO;
import ar.edu.unq.virtuaula.vo.PlayerMissionVO;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MissionService {

    private final MissionRepository missionRepository;
    private final PlayerMissionRepository playerMissionRepository;
    private final MapperUtil mapperUtil;

    public List<MissionDTO> getAllMissionByCampaign(Campaign campaign, LeaderAccount leaderAccount) throws CampaignNotFoundException {
        if (!leaderAccount.containsCampaign(campaign)) {
            throw new CampaignNotFoundException("Not found campaign id: " + campaign.getId() + " for leader account id: " + leaderAccount.getId());
        }
        List<Mission> missions = missionRepository.findByCampaign(campaign);
        return Arrays.asList(mapperUtil.getMapper().map(missions, MissionDTO[].class));
    }

    public List<PlayerMissionVO> getAllMissionByCampaignForPlayer(Campaign campaign, Long playerId) {
        List<Mission> missions = missionRepository.findByCampaign(campaign);
        return transformToVO(missions, playerId);
    }

    private List<PlayerMissionVO> transformToVO(List<Mission> missions, Long playerId) {
        return missions.stream().map(mission -> {
            Optional<PlayerMission> playerMission = playerMissionRepository.findByMissionIdAndPlayerId(mission.getId(), playerId);
            PlayerMissionVO missionVO = new PlayerMissionVO();
            missionVO.setId(mission.getId());
            missionVO.setStatement(mission.getStatement());
            missionVO.setScore(mission.getScore());
            missionVO.setMissionTypeId(mission.getMissionType().getId());
            if (playerMission.isPresent()) {
                missionVO.setAnswer(playerMission.get().getAnswer());
                missionVO.setStory(playerMission.get().getStory());
                missionVO.setState(playerMission.get().getState().name());
            } else {
                missionVO.setAnswer(mission.getAnswer());
            }
            missionVO.setOptions(transformOptionMissionToVO(mission.getOptions()));
            return missionVO;
        }).collect(toList());
    }

    private List<OptionMissionVO> transformOptionMissionToVO(List<OptionMission> options) {
        return options.stream().map(option -> {
            OptionMissionVO optionVO = new OptionMissionVO();
            optionVO.setId(option.getId());
            optionVO.setResponseValue(option.getResponseValue());
            return optionVO;
        }).collect(toList());
    }
}
