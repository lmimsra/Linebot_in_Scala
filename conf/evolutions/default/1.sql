# --- !Ups

CREATE TABLE IF NOT EXISTS Test (
  id BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  t_name VARCHAR(200) NOT NULL,
  t_age BIGINT(20) NOT NULL
);

INSERT INTO Test VALUES (1,'あうちやねん', 23);
INSERT INTO Test VALUES (2, '何がやねん？', 43);
INSERT INTO Test VALUES (3, 'これがやねん？', 56) ;

INSERT INTO connection VALUES (1,'コンドー');
# --- !Downs

DROP TABLE Test;
