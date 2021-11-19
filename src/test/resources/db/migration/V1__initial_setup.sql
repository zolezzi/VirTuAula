drop table if exists teams_players;
drop table if exists accounts_new_games;
drop table if exists leaders_players;
drop table if exists account_types_privileges;
drop table if exists player_missions;
drop table if exists team;
drop table if exists buffer;
drop table if exists goal;
drop table if exists level;
drop table if exists account;
drop table if exists user;
drop table if exists privilege;
drop table if exists account_type;
drop table if exists mission_type;
drop table if exists option_mission;
drop table if exists mission;
drop table if exists campaign;
drop table if exists new_game;
create table new_game(id bigint primary key AUTO_INCREMENT, name varchar(255) not null, description varchar(255));
create table campaign(id bigint primary key AUTO_INCREMENT, name varchar(255) not null, max_note int(10), new_game_id bigint, delivery_date DATETIME, constraint fk_campaign_new_game FOREIGN KEY (new_game_id) REFERENCES new_game(id));
create table mission_type(id bigint primary key AUTO_INCREMENT, name varchar(255) not null);
create table mission(id bigint primary key AUTO_INCREMENT, statement varchar(255) not null, answer bigint, correct_answer bigint, score int(10), campaign_id bigint, mission_type_id bigint, constraint fk_mission_campaign FOREIGN KEY (campaign_id) REFERENCES campaign(id), constraint fk_mission_type FOREIGN KEY (mission_type_id) REFERENCES mission_type(id));
create table option_mission(id bigint primary key AUTO_INCREMENT, response_value varchar(255) not null, mission_id bigint, is_correct boolean not null default 0, constraint fk_option_mission FOREIGN KEY (mission_id) REFERENCES mission(id));
create table account_type(id bigint primary key AUTO_INCREMENT, name varchar(255) not null);
create table privilege(id bigint primary key AUTO_INCREMENT, name varchar(255) not null);
create table user(id bigint primary key AUTO_INCREMENT, username varchar(255), password varchar(255), email varchar(255));
create table level(id bigint primary key AUTO_INCREMENT, name varchar(255), description varchar(255), image_path varchar(255), min_value int(20), max_value int(20), number_level int(10), name_next_level varchar(255));
create table buffer (id bigint primary key AUTO_INCREMENT, name varchar(255), description varchar(255), operator varchar(10), buffer_type varchar(255), experience_value int(20), life_value int(10), level_id bigint, constraint fk_buffer_level FOREIGN KEY (level_id) REFERENCES level(id));
create table goal (id bigint primary key AUTO_INCREMENT, name varchar(255), description varchar(255), level_id bigint, constraint fk_goal_level FOREIGN KEY (level_id) REFERENCES level(id));
create table account(id bigint primary key AUTO_INCREMENT, username varchar(255), first_name varchar(255), last_name varchar(255), email varchar(255), account_type_id bigint, account_type_role varchar(255), experience int(10), user_id bigint, dni int(8), level_id bigint, life int(10), constraint fk_account_type FOREIGN KEY (account_type_id) REFERENCES account_type(id), constraint fk_account_user FOREIGN KEY (user_id) REFERENCES user(id), constraint fk_account_level FOREIGN KEY (level_id) REFERENCES level(id));
create table account_types_privileges(id bigint primary key AUTO_INCREMENT, account_type_id bigint, privilege_id bigint, constraint fk_account_type_privilege FOREIGN KEY (account_type_id) REFERENCES account_type(id), constraint fk_privilege_account_type FOREIGN KEY (privilege_id) REFERENCES privilege(id));
create table leaders_players(id bigint primary key AUTO_INCREMENT, leader_id bigint, player_id bigint, constraint fk_leader FOREIGN KEY (leader_id) REFERENCES account(id), constraint fk_player FOREIGN KEY (player_id) REFERENCES account(id));
create table accounts_new_games(id bigint primary key AUTO_INCREMENT, account_id bigint, new_game_id bigint, constraint fk_account_new_game FOREIGN KEY (account_id) REFERENCES account(id), constraint fk_new_game_account FOREIGN KEY (new_game_id) REFERENCES new_game(id));
create table player_mission(id bigint primary key AUTO_INCREMENT, answer bigint, state varchar(15) default 'UNCOMPLETED', campaign_id bigint, account_id bigint, mission_id bigint, story varchar(255), comment varchar(255), constraint fk_player_mission_campaign FOREIGN KEY (campaign_id) REFERENCES campaign(id), constraint fk_player_mission_account FOREIGN KEY (account_id) REFERENCES account(id), constraint fk_player_mission FOREIGN KEY (mission_id) REFERENCES mission(id));
create table team(id bigint primary key AUTO_INCREMENT, name varchar(255), new_game_id bigint, leader_id bigint, constraint fk_new_game_team FOREIGN KEY (new_game_id) REFERENCES new_game(id), constraint fk_leader_team FOREIGN KEY (leader_id) REFERENCES account(id));
create table teams_players(id bigint primary key AUTO_INCREMENT, team_id bigint, player_id bigint, constraint fk_team FOREIGN KEY (team_id) REFERENCES team(id), constraint fk_player_team FOREIGN KEY (player_id) REFERENCES account(id));