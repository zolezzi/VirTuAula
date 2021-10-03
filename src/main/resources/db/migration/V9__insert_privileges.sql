INSERT INTO privilege(name) VALUES ('ADD');
INSERT INTO privilege(name) VALUES ('WRITE');
INSERT INTO privilege(name) VALUES ('VIEW');

INSERT INTO account_types_privileges (account_type_id, privilege_id ) VALUES (1, 1);
INSERT INTO account_types_privileges (account_type_id, privilege_id ) VALUES (1, 2);
INSERT INTO account_types_privileges (account_type_id, privilege_id ) VALUES (1, 3);

INSERT INTO account_types_privileges (account_type_id, privilege_id ) VALUES (2, 3);