INSERT INTO users (id,f_name, l_name,description, hidden)
VALUES ( 1,'ola','norman', 'test',false);
INSERT INTO users (id,f_name, l_name,description, hidden)
VALUES ( 2,'petter','haha', 'test2',false);
INSERT INTO users (id,f_name, l_name,description, hidden)
VALUES ( 3,'sug',' meg', 'Dyktig',false);

INSERT INTO project (description, status, category, title, owner_id)
VALUES ('first','done', 'Musikk', 'cool project',2);
INSERT INTO project (description, status, category, title, owner_id)
VALUES ('second','pending', 'Film', 'bad project',2);

INSERT INTO project_tags (project_id,tags) VALUES (1,'Instrumental');
INSERT INTO project_tags (project_id,tags) VALUES (1,'Sounds');
INSERT INTO project_tags (project_id,tags) VALUES (2,'Movies');
INSERT INTO project_tags (project_id,tags) VALUES (2,'Action');

INSERT INTO user_projects_membership (user_id, project_id)
VALUES (1,2);


INSERT INTO application (motivation, status, project_id, user_id)
VALUES ('I want it that way', 'DENIED',1,1);
INSERT INTO application (motivation, status, project_id, user_id)

VALUES ('I want it that way', 'APPROVED',1,3);

INSERT INTO skill (title)
VALUES ('Java');

INSERT INTO skill (title)
VALUES ('Spring');

INSERT INTO skill (title)
VALUES ('Python');


INSERT INTO skill (title)
VALUES ('Kamera');

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




