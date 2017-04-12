# --- !Ups
-- MySQL Workbench Forward Engineering

-- -----------------------------------------------------
-- Table `space`
-- -----------------------------------------------------
CREATE TABLE `space` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(40) NOT NULL,
  `icon` VARCHAR(200) NULL,
  `nulabapps_space_key` VARCHAR(45) NULL,
  `creator_id` INT NOT NULL,
  `created` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updator_id` INT NOT NULL,
  `updated` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`))
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `office`
-- -----------------------------------------------------
CREATE TABLE `office` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `space_id` INT NOT NULL,
  `name` VARCHAR(40) NOT NULL,
  `latitude` DOUBLE NULL,
  `longitude` DOUBLE NULL,
  `creator_id` INT NOT NULL,
  `created` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updator_id` INT NOT NULL,
  `updated` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `fk_office_space_idx` (`space_id` ASC),
  INDEX `latitude_idx` (`latitude` ASC),
  INDEX `longitude_idx` (`longitude` ASC),
  CONSTRAINT `fk_office_space`
  FOREIGN KEY (`space_id`)
  REFERENCES `space` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `user`
-- -----------------------------------------------------
CREATE TABLE `user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `space_id` INT NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  `icon` VARCHAR(200) NULL,
  `nulabid` VARCHAR(100) NOT NULL,
  `uniqueid` VARCHAR(45) UNIQUE NOT NULL,
  `enter_date` DATE NOT NULL,
  `office_id` INT NULL,
  `approver_id` INT NULL,
  `role` TINYINT(1) NOT NULL DEFAULT 0,
  `active` TINYINT(1) NOT NULL DEFAULT 1,
  `creator_id` INT NOT NULL,
  `created` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updator_id` INT NOT NULL,
  `updated` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `fk_user_space1_idx` (`space_id` ASC),
  INDEX `fk_user_office1_idx` (`office_id` ASC),
  INDEX `fk_user_user1_idx` (`approver_id` ASC),
  INDEX `nulabid_idx` (`nulabid` ASC),
  INDEX `enter_date_idx` (`enter_date` ASC),
  INDEX `active_idx` (`active` ASC),
  INDEX `role_idx` (`role` ASC),
  CONSTRAINT `fk_user_space1`
  FOREIGN KEY (`space_id`)
  REFERENCES `space` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_office1`
  FOREIGN KEY (`office_id`)
  REFERENCES `office` (`id`)
    ON DELETE SET NULL
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_user1`
  FOREIGN KEY (`approver_id`)
  REFERENCES `user` (`id`)
    ON DELETE SET NULL
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `official_holiday`
-- -----------------------------------------------------
CREATE TABLE `official_holiday` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `space_id` INT NOT NULL,
  `date` DATE NOT NULL,
  `name` VARCHAR(40) NOT NULL,
  `creator_id` INT NOT NULL,
  `created` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updator_id` INT NOT NULL,
  `updated` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `fk_official_holiday_space1_idx` (`space_id` ASC),
  INDEX `date_idx` (`date` ASC),
  CONSTRAINT `fk_official_holiday_space1`
  FOREIGN KEY (`space_id`)
  REFERENCES `space` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `attendance`
-- -----------------------------------------------------
CREATE TABLE `attendance` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `office_id` INT NOT NULL,
  `start_time` DATETIME NOT NULL,
  `end_time` DATETIME NULL,
  `creator_id` INT NOT NULL,
  `created` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updator_id` INT NOT NULL,
  `updated` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `fk_attendance_user1_idx` (`user_id` ASC),
  INDEX `fk_attendance_office1_idx` (`office_id` ASC),
  INDEX `start_time_idx` (`start_time` ASC),
  INDEX `end_time_idx` (`end_time` ASC),
  CONSTRAINT `fk_attendance_user1`
  FOREIGN KEY (`user_id`)
  REFERENCES `user` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_attendance_office1`
  FOREIGN KEY (`office_id`)
  REFERENCES `office` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `given_holiday`
-- -----------------------------------------------------
CREATE TABLE `given_holiday` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `days` FLOAT NOT NULL,
  `created` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `expire` DATETIME NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_given_holiday_user1_idx` (`user_id` ASC),
  INDEX `created_idx` (`created` ASC),
  INDEX `expire_idx` (`expire` ASC),
  CONSTRAINT `fk_given_holiday_user1`
  FOREIGN KEY (`user_id`)
  REFERENCES `user` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `holiday_application`
-- -----------------------------------------------------
CREATE TABLE `holiday_application` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `date` DATE NOT NULL,
  `amount` FLOAT NOT NULL,
  `status` TINYINT(1) NOT NULL DEFAULT 0,
  `paid` TINYINT(1) NOT NULL DEFAULT 0,
  `applier_id` INT NOT NULL,
  `approver_id` INT NOT NULL,
  `reason` VARCHAR(200) NOT NULL,
  `comment` VARCHAR(200) NULL,
  `creator_id` INT NOT NULL,
  `created` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updator_id` INT NOT NULL,
  `updated` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `fk_extrawork_application_user1_idx` (`applier_id` ASC),
  INDEX `fk_extrawork_application_user2_idx` (`approver_id` ASC),
  INDEX `date_idx` (`date` ASC),
  INDEX `status_idx` (`status` ASC),
  CONSTRAINT `fk_extrawork_application_user10`
  FOREIGN KEY (`applier_id`)
  REFERENCES `user` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_extrawork_application_user20`
  FOREIGN KEY (`approver_id`)
  REFERENCES `user` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `attendance_application`
-- -----------------------------------------------------
CREATE TABLE `attendance_application` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `start_time` DATETIME NOT NULL,
  `end_time` DATETIME NOT NULL,
  `attendance_id` INT NOT NULL,
  `status` TINYINT(1) NOT NULL DEFAULT 0,
  `applier_id` INT NOT NULL,
  `approver_id` INT NOT NULL,
  `reason` VARCHAR(200) NOT NULL,
  `comment` VARCHAR(200) NULL,
  `creator_id` INT NOT NULL,
  `created` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updator_id` INT NOT NULL,
  `updated` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `fk_attendance_application_attendance1_idx` (`attendance_id` ASC),
  INDEX `fk_attendance_application_user1_idx` (`applier_id` ASC),
  INDEX `fk_attendance_application_user2_idx` (`approver_id` ASC),
  INDEX `start_time_idx` (`start_time` ASC),
  INDEX `status_idx` (`status` ASC),
  CONSTRAINT `fk_attendance_application_attendance1`
  FOREIGN KEY (`attendance_id`)
  REFERENCES `attendance` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_attendance_application_user1`
  FOREIGN KEY (`applier_id`)
  REFERENCES `user` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_attendance_application_user2`
  FOREIGN KEY (`approver_id`)
  REFERENCES `user` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `rest`
-- -----------------------------------------------------
CREATE TABLE `rest` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `attendance_id` INT NOT NULL,
  `start_time` DATETIME NOT NULL,
  `end_time` DATETIME NULL,
  `creator_id` INT NOT NULL,
  `created` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updator_id` INT NOT NULL,
  `updated` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `fk_rest_attendance1_idx` (`attendance_id` ASC),
  INDEX `start_time_idx` (`start_time` ASC),
  CONSTRAINT `fk_rest_attendance1`
  FOREIGN KEY (`attendance_id`)
  REFERENCES `attendance` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `extrawork_application`
-- -----------------------------------------------------
CREATE TABLE `extrawork_application` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `start_time` DATETIME NOT NULL,
  `end_time` DATETIME NOT NULL,
  `status` TINYINT(1) NOT NULL DEFAULT 0,
  `applier_id` INT NOT NULL,
  `approver_id` INT NOT NULL,
  `reason` VARCHAR(200) NOT NULL,
  `comment` VARCHAR(200) NULL,
  `creator_id` INT NOT NULL,
  `created` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updator_id` INT NOT NULL,
  `updated` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `fk_extrawork_application_user1_idx` (`applier_id` ASC),
  INDEX `fk_extrawork_application_user2_idx` (`approver_id` ASC),
  INDEX `start_time_idx` (`start_time` ASC),
  INDEX `status_idx` (`status` ASC),
  CONSTRAINT `fk_extrawork_application_user1`
  FOREIGN KEY (`applier_id`)
  REFERENCES `user` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_extrawork_application_user2`
  FOREIGN KEY (`approver_id`)
  REFERENCES `user` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `typetalk`
-- -----------------------------------------------------
CREATE TABLE `typetalk` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `space_id` INT NOT NULL,
  `notify_topic_id` INT NOT NULL,
  `typetalk_token` VARCHAR(200) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_typetalk_space1_idx` (`space_id` ASC),
  CONSTRAINT `fk_typetalk_space1`
  FOREIGN KEY (`space_id`)
  REFERENCES `space` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;

--
-- system user
--

# --- !Downs
SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `typetalk`;
DROP TABLE IF EXISTS `extrawork_application`;
DROP TABLE IF EXISTS `rest`;
DROP TABLE IF EXISTS `attendance_application`;
DROP TABLE IF EXISTS `holiday_application`;
DROP TABLE IF EXISTS `given_holiday`;
DROP TABLE IF EXISTS `attendance`;
DROP TABLE IF EXISTS `official_holiday`;
DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `office`;
DROP TABLE IF EXISTS `space`;
SET FOREIGN_KEY_CHECKS=1;