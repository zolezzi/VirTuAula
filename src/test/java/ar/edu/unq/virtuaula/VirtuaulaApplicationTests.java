package ar.edu.unq.virtuaula;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import ar.edu.unq.virtuaula.builder.AccountTypeBuilder;
import ar.edu.unq.virtuaula.builder.BufferExperienceBuilder;
import ar.edu.unq.virtuaula.builder.BufferLifeBuilder;
import ar.edu.unq.virtuaula.builder.NewGameBuilder;
import ar.edu.unq.virtuaula.builder.CampaignBuilder;
import ar.edu.unq.virtuaula.builder.LevelBuilder;
import ar.edu.unq.virtuaula.builder.OptionMissionBuilder;
import ar.edu.unq.virtuaula.builder.PlayerAccountBuilder;
import ar.edu.unq.virtuaula.builder.PlayerMissionBuilder;
import ar.edu.unq.virtuaula.builder.MissionBuilder;
import ar.edu.unq.virtuaula.builder.MissionTypeBuilder;
import ar.edu.unq.virtuaula.builder.LeaderAccountBuilder;
import ar.edu.unq.virtuaula.builder.TeamBuilder;
import ar.edu.unq.virtuaula.builder.UserBuilder;
import ar.edu.unq.virtuaula.model.Account;
import ar.edu.unq.virtuaula.model.AccountType;
import ar.edu.unq.virtuaula.model.Buffer;
import ar.edu.unq.virtuaula.model.BufferExperience;
import ar.edu.unq.virtuaula.model.BufferLife;
import ar.edu.unq.virtuaula.model.NewGame;
import ar.edu.unq.virtuaula.model.Campaign;
import ar.edu.unq.virtuaula.model.Level;
import ar.edu.unq.virtuaula.model.OptionMission;
import ar.edu.unq.virtuaula.model.PlayerAccount;
import ar.edu.unq.virtuaula.model.PlayerMission;
import ar.edu.unq.virtuaula.model.Mission;
import ar.edu.unq.virtuaula.model.MissionType;
import ar.edu.unq.virtuaula.model.LeaderAccount;
import ar.edu.unq.virtuaula.model.Team;
import ar.edu.unq.virtuaula.model.User;
import ar.edu.unq.virtuaula.repository.AccountRepository;
import ar.edu.unq.virtuaula.repository.NewGameRepository;
import ar.edu.unq.virtuaula.repository.LevelRepository;
import ar.edu.unq.virtuaula.repository.PlayerMissionRepository;
import ar.edu.unq.virtuaula.repository.MissionTypeRepository;
import ar.edu.unq.virtuaula.repository.TeamRepository;
import ar.edu.unq.virtuaula.repository.UserRepository;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class VirtuaulaApplicationTests {

    @Autowired
    protected NewGameRepository newGameRepository;

    @Autowired
    protected AccountRepository accountRepository;

    @Autowired
    protected PlayerMissionRepository playerMissionRepository;
    
    @Autowired
    protected MissionTypeRepository missionTypeRepository;
    
    @Autowired
    protected UserRepository userRepository;
    
    @Autowired
    protected LevelRepository levelRepository;
    
    @Autowired
    protected TeamRepository teamRepository;

    protected NewGame createOneNewGame() {
    	MissionType missionType = MissionTypeBuilder.missionTypeWithName("Multiple choise").build();
        Mission mission = MissionBuilder.missionWithStatement("Cuanto vale x para x = x * 2 + 1").withCorrectAnswer(1l).withAnswer(1l).withScore(100d)
        		.withMissionType(missionType)
        		.build();
		Date date = getDate();
		Campaign campaign = CampaignBuilder.campaignWithName("Ecuaciones").withMission(mission).withMaxNote(1000).withDeliveryDate(date).build();
		NewGame newGame = NewGameBuilder.newGameWithName("Matematicas").withCampaign(campaign).build();
		return createNewGame(newGame);
    }
    
    protected NewGame createOneNewGameWithCampaignScore(Double score) {
    	MissionType missionType = MissionTypeBuilder.missionTypeWithName("Multiple choise").build();
        Mission mission = MissionBuilder.missionWithStatement("Cuanto vale x para x = x * 2 + 1").withCorrectAnswer(1l).withAnswer(1l).withScore(score)
        		.withMissionType(missionType)
        		.build();
		Date date = getDate();
		Campaign campaign = CampaignBuilder.campaignWithName("Ecuaciones").withMission(mission).withMaxNote(3000).withDeliveryDate(date).build();
		NewGame newGame = NewGameBuilder.newGameWithName("Matematicas").withCampaign(campaign).build();
		return createNewGame(newGame);
    }
    
    protected NewGame createOneNewGameWithoutDateExpired(Date date){
    	MissionType missionType = MissionTypeBuilder.missionTypeWithName("Multiple choise").build();
        Mission mission = MissionBuilder.missionWithStatement("Cuanto vale x para x = x * 2 + 1").withCorrectAnswer(1l).withAnswer(1l).withScore(100d)
        		.withMissionType(missionType)
        		.build();
		Campaign campaign = CampaignBuilder.campaignWithName("Ecuaciones").withMission(mission).withMaxNote(1000).withDeliveryDate(date).build();
		NewGame newGame = NewGameBuilder.newGameWithName("Matematicas").withCampaign(campaign).build();
		return createNewGame(newGame);
    }
    
    protected MissionType  createOneMissionType() {
    	MissionType missionType = MissionTypeBuilder.missionTypeWithName("Multiple choise").build();
        return createMissionType(missionType);
    }
    
    protected NewGame createOneNewGameWithoutCampaign() {
        NewGame newGame = NewGameBuilder.newGameWithName("Matematicas").build();
        return createNewGame(newGame);
    }

    protected NewGame createOneNewGameWithTwoMissions() {
        Mission mission1 = MissionBuilder.missionWithStatement("Cuanto vale x para x = x * 2 + 1").withCorrectAnswer(1l).build();
        Mission mission2 = MissionBuilder.missionWithStatement("Cuanto vale x para x = x * 2 + 1").withCorrectAnswer(1l).build();
        Date date = getDate();
        Campaign campaign = CampaignBuilder.campaignWithName("Ecuaciones").withMission(mission1).withMission(mission2).withDeliveryDate(date).build();
        NewGame newGame = NewGameBuilder.newGameWithName("Matematicas").withCampaign(campaign).build();
        return createNewGame(newGame);
    }

    protected NewGame createOneNewGameWithTwoMissionsAndTwoOptionMissions() {
        MissionType missionType = new MissionType();
        missionType.setName("Multiple choice");
    	OptionMission option1 = OptionMissionBuilder.missionWithReponseValue("2").withIsCorrect(true).build();
    	OptionMission option2 = OptionMissionBuilder.missionWithReponseValue("1").withIsCorrect(false).build();
    	OptionMission option3 = OptionMissionBuilder.missionWithReponseValue("2").withIsCorrect(true).build();
    	OptionMission option4 = OptionMissionBuilder.missionWithReponseValue("1").withIsCorrect(false).build();
        Mission mission1 = MissionBuilder.missionWithStatement("Cuanto vale x para x = x * 2 + 1").withCorrectAnswer(1l).withOptionMission(option1).withOptionMission(option2).withMissionType(missionType).build();
        Mission mission2 = MissionBuilder.missionWithStatement("Cuanto vale x para x = x * 2 + 1").withCorrectAnswer(1l).withOptionMission(option3).withOptionMission(option4).withMissionType(missionType).build();
        Campaign campaign = CampaignBuilder.campaignWithName("Ecuaciones").withMission(mission1).withMission(mission2).build();
        NewGame newGame = NewGameBuilder.newGameWithName("Matematicas").withCampaign(campaign).build();
        return createNewGame(newGame);
    }

    protected NewGame createNewGame(NewGame newGame) {
        return newGameRepository.save(newGame);
    }

    protected LeaderAccount createLeaderAccount(LeaderAccount account) {
        return accountRepository.save(account);
    }
    
    protected PlayerAccount createPlayerAccount(PlayerAccount account) {
        return accountRepository.save(account);
    }
    
    protected User createOneUserWithLeaderAccount() {
        return createOneLeaderAccount().getUser();
    }

    protected User createUser(User user) {
        return userRepository.save(user);
    }

    protected MissionType createMissionType(MissionType missionType) {
        return missionTypeRepository.save(missionType);
    }
    
    protected Level createLevel(Level level) {
    	return levelRepository.save(level);
    }
    
    protected Team createTeam(Team team) {
    	return teamRepository.save(team);
    }
    
    protected Team createOneTeam(){
    	List<PlayerAccount> players = new ArrayList<>();
    	NewGame newGame = createOneNewGame();
    	PlayerAccount player = (PlayerAccount) createOnePlayerAccount();
    	LeaderAccount leader = (LeaderAccount) createOneLeaderAccountWithNewGameAndPlayer(newGame, player);
    	players.add(player);
    	Team team = TeamBuilder.teamWithName("Team de equipo de matemaica")
    			.withNewGame(newGame)
    			.withLeader(leader)
    			.withPlayers(players)
    			.build();
    	return createTeam(team);
    }
    
    protected User createOneUser() {
        User user = UserBuilder.userWithUsernameAndPassword("charly2", "1234567n")
                .withEmail("charlie@gmail.com")
                .build();
        return createUser(user);
    }
    
	
	protected Level createLevelProfesional() {
		Level level = LevelBuilder.levelWithName("Profesional").withDescription("Nivel profesional").withImagePath("/images/image.png")
    			.withNameNextLevel(null)
    			.withNumberLevel(2)
    			.withMinValue(0d)
    			.withMaxValue(2000d)
    			.build();
		return createLevel(level);
	}
	
	protected Level createLevelBeginner() {
		Level level = LevelBuilder.levelWithName("Beginner").withDescription("Nivel principiante").withImagePath("/images/image.png")
    			.withNameNextLevel("Profesional")
    			.withNumberLevel(1)
    			.withMinValue(0d)
    			.withMaxValue(2000d)
    			.build();
		return createLevel(level);
	}
	
	protected Level createLevelWithTwoBuffer(){
		BufferExperience bufferExprience = BufferExperienceBuilder.bufferExperienceWithName("Buffer experience")
				.withDescription("Buffer suma experiencia")
				.withExperienceValue(10d)
				.withOperator("+")
				.build();
		
		BufferLife bufferlife = BufferLifeBuilder.bufferLifeWithName("Buffer life")
				.withDescription("Buffer suma life")
				.withLifeValue(1)
				.withOperator("+")
				.build();
		List<Buffer> buffers = new ArrayList<>();
		buffers.add(bufferExprience);
		buffers.add(bufferlife);
		
		Level level = LevelBuilder.levelWithName("Beginner").withDescription("Nivel principiante").withImagePath("/images/image.png")
    			.withNameNextLevel("Profesional")
    			.withNumberLevel(1)
    			.withMinValue(0d)
    			.withMaxValue(2000d)
    			.withBuffers(buffers)
    			.build();
		return createLevel(level);
	}

    protected Account createOneLeaderAccount() {
        AccountType accountType = AccountTypeBuilder.accountTypeWithUsername("LEADER").build();
        User user = UserBuilder.userWithUsernameAndPassword("charlie", "03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4").build();
        LeaderAccount account = LeaderAccountBuilder.accountWithUsername("charlie")
                .accountWithFisrtName("Charlie")
                .accountWithLastName("Cardozo")
                .accountWithEmail("charlie@gmail.com")
                .withAccountType(accountType)
                .withUser(user)
                .build();
        account = createLeaderAccount(account);
        return account;
    }
    
    protected Account createOnePlayerAccount() {
    	AccountType accountType= AccountTypeBuilder.accountTypeWithUsername("PLAYER").build();
		BufferExperience bufferExprience = BufferExperienceBuilder.bufferExperienceWithName("Buffer experience")
				.withDescription("Buffer suma experiencia")
				.withExperienceValue(10d)
				.withOperator("+")
				.build();
		
		BufferLife bufferlife = BufferLifeBuilder.bufferLifeWithName("Buffer life")
				.withDescription("Buffer suma life")
				.withLifeValue(1)
				.withOperator("+")
				.build();
		List<Buffer> buffers = new ArrayList<>();
		buffers.add(bufferExprience);
		buffers.add(bufferlife);
    	Level level = LevelBuilder.levelWithName("Principiante").withDescription("Nivel inicial").withImagePath("/images/image.png")
    			.withNameNextLevel("Aficionado")
    			.withNumberLevel(1)
    			.withMinValue(0d)
    			.withMaxValue(1000d)
    			.withBuffers(buffers)
    			.build();
    	User user = UserBuilder.userWithUsernameAndPassword("charlie2", "03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4").build();
    	PlayerAccount account = PlayerAccountBuilder.accountWithUsername("charlie2")
        		.accountWithFisrtName("Charlie2")
        		.accountWithLastName("Cardozo2")
        		.accountWithEmail("charlie2@gmail.com")
        		.withAccountType(accountType)
        		.withLevel(level)
        		.withUser(user)
        		.withLife(3)
        		.build();
        account = createPlayerAccount(account);
        return account;
    }
    
    protected Account createOnePlayerMissionWithCampaignAndMissionAndPlayerAccount(Campaign campaign, Mission mission, PlayerAccount playerAccount) {
    	PlayerMission PlayerMission = PlayerMissionBuilder.createPlayerMission()
    			.withCampaign(campaign)
    			.withMission(mission)
    			.withPlayerAccount(playerAccount)
    			.completed()
    			.build();
    	PlayerMission = createPlayerMission(PlayerMission);
        return PlayerMission.getPlayerAccount();
    }
    
    protected Account createOnePlayerMissionUncompletedWithCampaignAndMissionAndPlayerAccount(Campaign campaign, Mission mission, PlayerAccount playerAccount) {
    	PlayerMission PlayerMission = PlayerMissionBuilder.createPlayerMission()
    			.withCampaign(campaign)
    			.withMission(mission)
    			.withPlayerAccount(playerAccount)
    			.uncompleted()
    			.build();
    	PlayerMission = createPlayerMission(PlayerMission);
        return PlayerMission.getPlayerAccount();
    }
    
    private PlayerMission createPlayerMission(PlayerMission playerMission) {
		return playerMissionRepository.save(playerMission);
	}

	protected Account createOneLeaderAccountWithNewGame(NewGame newGame) {
    	List<NewGame> newGames = new ArrayList<>();
    	newGames.add(newGame);
    	AccountType accountType= AccountTypeBuilder.accountTypeWithUsername("LEADER").build();
    	User user = UserBuilder.userWithUsernameAndPassword("charlie", "1234").build();
    	LeaderAccount account = LeaderAccountBuilder.accountWithUsername("charlie")
        		.accountWithFisrtName("Charlie")
        		.accountWithLastName("Cardozo")
        		.accountWithEmail("charlie@gmail.com")
        		.withAccountType(accountType)
        		.withUser(user)
        		.withNewGames(newGames)
        		.build();
        account = createLeaderAccount(account);
        return account;
    }
	
	protected Account createOnePlayerAccountWithNewGame(NewGame newGame) {
    	List<NewGame> newGames = new ArrayList<>();
    	newGames.add(newGame);
    	AccountType accountType= AccountTypeBuilder.accountTypeWithUsername("LEADER").build();
    	User user = UserBuilder.userWithUsernameAndPassword("charlie", "1234").build();
    	PlayerAccount account = PlayerAccountBuilder.accountWithUsername("charlie")
        		.accountWithFisrtName("Charlie")
        		.accountWithLastName("Cardozo")
        		.accountWithEmail("charlie@gmail.com")
        		.withAccountType(accountType)
        		.withUser(user)
        		.withNewGames(newGames)
        		.build();
        account = createPlayerAccount(account);
        return account;
    }
	
	protected Account createOneLeaderAccountWithNewGameAndPlayer(NewGame newGame, PlayerAccount player) {
    	List<NewGame> newGames = new ArrayList<>();
    	newGames.add(newGame);
    	AccountType accountType= AccountTypeBuilder.accountTypeWithUsername("LEADER").build();
    	User user = UserBuilder.userWithUsernameAndPassword("charlie", "1234").build();
    	LeaderAccount account = LeaderAccountBuilder.accountWithUsername("charlie")
        		.accountWithFisrtName("Charlie")
        		.accountWithLastName("Cardozo")
        		.accountWithEmail("charlie@gmail.com")
        		.withAccountType(accountType)
        		.withUser(user)
        		.addPlayer(player)
        		.withNewGames(newGames)
        		.build();
        account = createLeaderAccount(account);
        return account;
    }
	
	private Date getDate() {
		Date date = null;
		try {
			String dateString = "2021-12-31 23:59:59";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			date = sdf.parse(dateString + " UTC");
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

}
