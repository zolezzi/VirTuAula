package ar.edu.unq.virtuaula.service;

import static java.util.stream.Collectors.toList;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.hash.Hashing;

import ar.edu.unq.virtuaula.dto.AccountDTO;
import ar.edu.unq.virtuaula.dto.AccountTypeDTO;
import ar.edu.unq.virtuaula.dto.BufferDTO;
import ar.edu.unq.virtuaula.dto.GoalDTO;
import ar.edu.unq.virtuaula.dto.LevelDTO;
import ar.edu.unq.virtuaula.dto.PrivilegeDTO;
import ar.edu.unq.virtuaula.exception.AccountNotFoundException;
import ar.edu.unq.virtuaula.exception.LeaderAccountNotFoundException;
import ar.edu.unq.virtuaula.exception.PlayerAccountNotFoundException;
import ar.edu.unq.virtuaula.message.ResponseMessage;
import ar.edu.unq.virtuaula.model.Account;
import ar.edu.unq.virtuaula.model.AccountType;
import ar.edu.unq.virtuaula.model.LeaderAccount;
import ar.edu.unq.virtuaula.model.Level;
import ar.edu.unq.virtuaula.model.PlayerAccount;
import ar.edu.unq.virtuaula.model.Privilege;
import ar.edu.unq.virtuaula.model.User;
import ar.edu.unq.virtuaula.repository.AccountRepository;
import ar.edu.unq.virtuaula.repository.AccountTypeRepository;
import ar.edu.unq.virtuaula.repository.UserRepository;
import ar.edu.unq.virtuaula.util.CSVUtil;
import ar.edu.unq.virtuaula.util.MapperUtil;
import ar.edu.unq.virtuaula.vo.AccountVO;
import ar.edu.unq.virtuaula.vo.PlayerAccountVO;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountTypeRepository accountTypeRepository;
    private final LevelService levelService;
    private final UserRepository userRepository;
    private final MapperUtil mapperUtil;
    private final CSVUtil csvUtil;
    private static final String ACCOUNT_TYPE_LEADER = "LEADER";
    private static final String ACCOUNT_TYPE_PLAYER = "PLAYER";

    public LeaderAccount findLeaderById(Long accountId) throws LeaderAccountNotFoundException {
        return (LeaderAccount) accountRepository.findById(accountId)
                .orElseThrow(() -> new LeaderAccountNotFoundException("Error not found leader account with id: " + accountId));
    }

    public Account findById(Long accountId) throws AccountNotFoundException {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Error not found account with id: " + accountId));
    }

    public AccountVO createAccountLeader(User user, AccountDTO account) {
        LeaderAccount newAccount = mapperUtil.getMapper().map(account, LeaderAccount.class);
        newAccount.setUser(user);
        newAccount.setUsername(user.getUsername());
        newAccount.setFirstName(account.getFirstName());
        newAccount.setLastName(account.getLastName());
        newAccount.setDni(account.getDni());
        newAccount.setEmail(user.getEmail());
        newAccount.setAccountType(accountTypeRepository.findByName(ACCOUNT_TYPE_LEADER));
        newAccount = accountRepository.save(newAccount);
        return createAccountVo(newAccount);
    }

    public PlayerAccount findPlayerById(Long accountId) throws PlayerAccountNotFoundException {
        return (PlayerAccount) accountRepository.findById(accountId)
                .orElseThrow(() -> new PlayerAccountNotFoundException("Error not found player account with id: " + accountId));
    }

    public Double getExperience(Long accountId) throws PlayerAccountNotFoundException {
        PlayerAccount account = findPlayerById(accountId);
        return account.getExperience();
    }

    public ResponseMessage uploadFilePlayers(LeaderAccount leaderAccount, MultipartFile file) {
        String message = "";
        if (csvUtil.hasCSVFormat(file)) {
            try {
                AccountType accountType = accountTypeRepository.findByName(ACCOUNT_TYPE_PLAYER);
                List<PlayerAccount> players = csvUtil.csvToPlayers(file.getInputStream());
                if (!players.isEmpty()) {
                	players = validatePlayersAndSetLeaderAndAccountType(players, accountType, leaderAccount);
                	players = accountRepository.saveAll(players);
                    leaderAccount.getPlayers().addAll(players);
                    leaderAccount = accountRepository.save(leaderAccount);
                    List<Integer> dnis = players.stream().map(player -> player.getDni()).collect(Collectors.toList());
                    createUserForPlayers(leaderAccount.getPlayersByDNIs(dnis));
                    message = "Uploaded the file successfully: " + file.getOriginalFilename();
                } else {
                    message = "Please review file, i do not know loaded any lines from the file: " + file.getOriginalFilename();
                }

            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            }
        }
        return new ResponseMessage(message);
    }

	public List<PlayerAccount> findAllPlayerByIds(List<Long> playerIds) {
		return accountRepository.findAllPlayerByIds(playerIds);
	}

	public LevelDTO getLevel(Long accountId) throws PlayerAccountNotFoundException {
		PlayerAccount account = findPlayerById(accountId);
		return  mapperUtil.getMapper().map(account.getLevel(), LevelDTO.class);
	}

	public List<PlayerAccountVO> findAllPlayersByLeader(LeaderAccount leader) {
		return transformToPlayerVO(leader.getPlayers());
	}
	

	public List<BufferDTO> getBuffers(Long accountId) throws PlayerAccountNotFoundException {
		PlayerAccount account = findPlayerById(accountId);
		return Arrays.asList(mapperUtil.getMapper().map(account.getLevel().getBuffers(), BufferDTO[].class));
	}

	public List<GoalDTO> getGoals(Long accountId) throws PlayerAccountNotFoundException {
		PlayerAccount account = findPlayerById(accountId);
		return Arrays.asList(mapperUtil.getMapper().map(account.getLevel().getGoals(), GoalDTO[].class));
	}
	
	private List<PlayerAccountVO> transformToPlayerVO(List<PlayerAccount> players) {
		return players.stream().map(player -> {
			PlayerAccountVO playerVO = new PlayerAccountVO();
                        playerVO.setId(player.getId());
			playerVO.setFirstName(player.getFirstName());
			playerVO.setUsername(player.getUsername());
			playerVO.setExperience(player.getExperience());
			playerVO.setLevel(mapperUtil.getMapper().map(player.getLevel(), LevelDTO.class));
            return playerVO;
        }).collect(toList());
	}

    private List<PlayerAccount> validatePlayersAndSetLeaderAndAccountType(List<PlayerAccount> players,
            AccountType accountType, LeaderAccount leaderAccount) {
    	Level Level = levelService.getInitialLevel();
        return players.stream()
                .filter(player -> !existsAccount(player))
                .map(player -> {
                	player.setAccountType(accountType);
                	player.addLeader(leaderAccount);
                	player.setLevel(Level);
                    return player;
                })
                .collect(Collectors.toList());
    }

    private void createUserForPlayers(List<PlayerAccount> players) {
    	players.forEach(player -> {
            User user = new User();
            String password = Hashing.sha256()
                    .hashString(player.getDni().toString(), StandardCharsets.UTF_8)
                    .toString();
            user.setPassword(password);
            user.setAccount(player);
            user.setEmail(player.getEmail());
            user.setUsername(String.valueOf(player.getDni()));
            User userSaved = userRepository.save(user);
            player.setUser(userSaved);
            accountRepository.save(player);
        });
    }

    private Boolean existsAccount(PlayerAccount player) {
        return accountRepository.findByDni(player.getDni()).isPresent();
    }

    private AccountVO createAccountVo(LeaderAccount account) {
        AccountVO accountVO = new AccountVO();
        accountVO.setAccountId(account.getId());
        AccountTypeDTO accountTypeDTO = new AccountTypeDTO();
        accountTypeDTO.setName(account.getAccountType().getName());
        List<PrivilegeDTO> privileges = createPrivileges(account.getAccountType().getPrivileges());
        accountTypeDTO.setPrivileges(privileges);
        accountVO.setAccountType(accountTypeDTO);
        return accountVO;
    }

    private List<PrivilegeDTO> createPrivileges(List<Privilege> privileges) {
        return privileges.stream().map(privilege -> createPrivilegeDTO(privilege)).collect(toList());
    }

    private PrivilegeDTO createPrivilegeDTO(Privilege privilege) {
        PrivilegeDTO privilegeDTO = new PrivilegeDTO();
        privilegeDTO.setId(privilege.getId());
        privilegeDTO.setName(privilege.getName());
        return privilegeDTO;
    }

}