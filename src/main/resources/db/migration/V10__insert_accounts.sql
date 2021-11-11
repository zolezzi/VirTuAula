INSERT INTO account(username, first_name, last_name, email, account_type_id, account_type_role, experience, user_id, dni, level_id, life) VALUES ('charly','Carlos', 'Cardozo', 'carlos@gmail.com', 2, 'LeaderAccount', null, 1,35000111, null, 3);
INSERT INTO account(username, first_name, last_name, email, account_type_id, account_type_role, experience, user_id, dni, level_id, life) VALUES ('german','German', 'Cabrera', 'german@gmail.com', 1, 'PlayerAccount', 0, 2, 35000112, 1, 3);

INSERT INTO leaders_players(leader_id, player_id) VALUES(1,2);

INSERT INTO accounts_new_games(account_id, new_game_id) VALUES(1,1);
INSERT INTO accounts_new_games(account_id, new_game_id) VALUES(1,2);
INSERT INTO accounts_new_games(account_id, new_game_id) VALUES(2,1);
INSERT INTO accounts_new_games(account_id, new_game_id) VALUES(2,2);