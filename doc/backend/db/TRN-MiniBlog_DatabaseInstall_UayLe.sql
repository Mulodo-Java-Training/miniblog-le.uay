-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema MiniBlog
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema MiniBlog
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `MiniBlog` DEFAULT CHARACTER SET utf8 ;
USE `MiniBlog` ;

-- -----------------------------------------------------
-- Table `MiniBlog`.`User`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `MiniBlog`.`User` ;

CREATE TABLE IF NOT EXISTS `MiniBlog`.`User` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(30) NOT NULL,
  `password` VARCHAR(200) NOT NULL,
  `lastname` VARCHAR(45) NOT NULL,
  `firstname` VARCHAR(45) NOT NULL,
  `email` VARCHAR(100) NULL DEFAULT NULL,
  `create_at` DATETIME NULL DEFAULT NULL,
  `modified_at` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`id`, `username`, `lastname`, `firstname`))
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `MiniBlog`.`Post`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `MiniBlog`.`Post` ;

CREATE TABLE IF NOT EXISTS `MiniBlog`.`Post` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(150) NOT NULL,
  `content` LONGTEXT NULL DEFAULT NULL,
  `created_at` DATETIME NULL DEFAULT NULL,
  `modified_at` DATETIME NULL DEFAULT NULL,
  `status` VARCHAR(15) NOT NULL,
  `user_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`, `title`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_Post_User1_idx` (`user_id` ASC))
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `MiniBlog`.`Comment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `MiniBlog`.`Comment` ;

CREATE TABLE IF NOT EXISTS `MiniBlog`.`Comment` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `content` VARCHAR(500) NOT NULL,
  `created_at` DATETIME NOT NULL,
  `modifed_at` DATETIME NOT NULL,
  `status` VARCHAR(20) NULL DEFAULT NULL,
  `post_id` INT(11) NOT NULL,
  `user_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`, `post_id`, `user_id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_Comment_Post_idx` (`post_id` ASC),
  INDEX `fk_Comment_User1_idx` (`user_id` ASC))
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `MiniBlog`.`Token`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `MiniBlog`.`Token` ;

CREATE TABLE IF NOT EXISTS `MiniBlog`.`Token` (
  `id` VARCHAR(200) NOT NULL,
  `created_at` DATETIME NOT NULL,
  `expired_at` DATETIME NOT NULL,
  `user_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`, `user_id`),
  INDEX `fk_Token_User1_idx` (`user_id` ASC))
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
