CREATE TABLE `users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(64) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`));
