INSERT INTO users (f_name, l_name,description, hidden)
VALUES ( 'ola','norman', 'test',false);
INSERT INTO users (f_name, l_name,description, hidden)
VALUES ( 'petter','haha', 'test2',false);

INSERT INTO project (description, status, category, title, owner_id)
VALUES ('first','done', 'MUSIC', 'cool project',2);
INSERT INTO project (description, status, category, title, owner_id)
VALUES ('second','pending', 'FILM', 'bad project',2);

INSERT INTO user_projects_membership (user_id, project_id)
VALUES (1,2);

INSERT INTO application (motivation, status, project_id, user_id)
VALUES ('I want it that way', 'pending',1,1);
INSERT INTO skill (id, title)
VALUES (1, 'Java');

INSERT INTO skill (id, title)
VALUES (2, 'Spring');

INSERT INTO user_skill (user_id, skill_id)
VALUES (1, 2);

INSERT INTO project_skill (project_id, skill_id)
VALUES (1, 1);

INSERT INTO project_skill (project_id, skill_id)
VALUES (1, 2);

INSERT INTO comment (message, project_id, user_id)
VALUES ('Hallloaooalooooo', 1, 1);

INSERT INTO comment (message, project_id, user_id, replied_to_id)
VALUES ('Hei!', 1, 2, 1);
