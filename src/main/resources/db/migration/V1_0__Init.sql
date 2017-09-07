DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `password` VARCHAR(64) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  CONSTRAINT unique_email_constraint UNIQUE (email),
  PRIMARY KEY (`id`));
