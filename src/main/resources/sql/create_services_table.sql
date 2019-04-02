DROP TABLE IF EXISTS services;
CREATE TABLE services (
  id int(11) NOT NULL AUTO_INCREMENT,
  service_name varchar(255) DEFAULT NULL,
  popularity int(11) default 1,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
