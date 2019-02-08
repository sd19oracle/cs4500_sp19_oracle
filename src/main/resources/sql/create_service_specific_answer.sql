DROP TABLE IF EXISTS service_specific_answer;

CREATE TABLE service_specific_answer(
  id integer not null auto_increment,
  answer varchar(255),
  service_specific_question_id integer not null,
  user integer not null,
  primary key (id),
  foreign key (service_specific_question_id) references service_specific_question (id),
  foreign key (user) references users (id)
) engine=InnoDB