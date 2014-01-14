# --- !Ups

CREATE TABLE User (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(50),
  PRIMARY KEY (id)
);

CREATE TABLE Board (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  title varchar(80),
  content varchar(255),
  user_id bigint(20),
  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES User (id)
);

INSERT INTO User (name) values ('semicolok');

INSERT INTO Board (title, content, user_id) values ('testTitle1', 'testContent1', 1);
INSERT INTO Board (title, content, user_id) values ('testTitle2', 'testContent2', 1);
INSERT INTO Board (title, content, user_id) values ('testTitle3', 'testContent3', 1);
INSERT INTO Board (title, content, user_id) values ('testTitle4', 'testContent4', 1);
INSERT INTO Board (title, content, user_id) values ('testTitle5', 'testContent5', 1);
INSERT INTO Board (title, content, user_id) values ('testTitle6', 'testContent6', 1);

# --- !Downs

DROP TABLE IF EXISTS User;

DROP TABLE IF EXISTS Board;