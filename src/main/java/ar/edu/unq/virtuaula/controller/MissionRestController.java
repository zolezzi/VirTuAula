package ar.edu.unq.virtuaula.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.virtuaula.dto.MissionDTO;
import ar.edu.unq.virtuaula.service.AccountService;
import ar.edu.unq.virtuaula.service.CampaignService;
import ar.edu.unq.virtuaula.service.MissionService;
import ar.edu.unq.virtuaula.vo.PlayerMissionVO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MissionRestController {

    private final MissionService missionService;
    private final CampaignService campaignService;
    private final AccountService accountService;

    @GetMapping("/missions/players/{campaignId}/{accountId}")
    @ApiResponse(code = 200, message = "Success", response = PlayerMissionVO.class, responseContainer = "List")
    @ApiOperation(value = "Get all mission players by campaign id", notes = "Get all missions of a campaign")
    public List<PlayerMissionVO> getAllByCampaignIdPlayerId(@PathVariable("campaignId") Long campaignId, @PathVariable("accountId") Long accountId) {
        return missionService.getAllMissionByCampaignForPlayer(campaignService.findById(campaignId), accountId);
    }

    @GetMapping("/missions/{campaignId}/{accountId}")
    @ApiResponse(code = 200, message = "Success", response = MissionDTO.class, responseContainer = "List")
    @ApiOperation(value = "Get all missions leader by campaign id and account id", notes = "Get all missions of a campaign and leader")
    public List<MissionDTO> getAllByCampaignId(@PathVariable("campaignId") Long campaignId, @PathVariable("accountId") Long accountId) throws Exception {
        return missionService.getAllMissionByCampaign(campaignService.findById(campaignId), accountService.findLeaderById(accountId));
    }
}
