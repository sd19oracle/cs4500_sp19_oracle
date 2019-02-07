DROP TABLE IF EXISTS service_specific_question;
CREATE TABLE service_specific_question {
  id INTEGER NOT NULL AUTO_INCREMENT,
  title varchar(255) NOT NULL,
  type varchar(255) NOT NULL,
  choice varchar(1000) DEFAULT NULL,
  service integer NOT NULL,
  primary key(id)
  foreign key(service) references services (id)
}ENGINE=InnoDB DEFAULT CHARSET=latin1;