DROP TABLE IF EXISTS service_specific_question;
CREATE TABLE service_specific_question {
  id INTEGER NOT NULL AUTO_INCREMENT,
  title varchar(255),
  type varchar(255),
  choice varchar(1000) DEFAULT NULL,
  primary key(id)
  foregin key
}ENGINE=InnoDB DEFAULT CHARSET=latin1;