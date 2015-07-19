-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema miniblog
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema miniblog
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `miniblog` DEFAULT CHARACTER SET utf8 ;
USE `miniblog` ;

-- -----------------------------------------------------
-- Table `miniblog`.`User`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `miniblog`.`User` ;

CREATE TABLE IF NOT EXISTS `miniblog`.`User` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `created_at` DATETIME NOT NULL,
  `email` VARCHAR(100) NULL DEFAULT NULL,
  `firstname` VARCHAR(45) NOT NULL,
  `lastname` VARCHAR(45) NOT NULL,
  `modified_at` DATETIME NOT NULL,
  `password` VARCHAR(72) NOT NULL,
  `status` INT(11) NOT NULL,
  `username` VARCHAR(40) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 377
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `miniblog`.`Post`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `miniblog`.`Post` ;

CREATE TABLE IF NOT EXISTS `miniblog`.`Post` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `content` VARCHAR(5000) NOT NULL,
  `created_at` DATETIME NOT NULL,
  `modified_at` DATETIME NOT NULL,
  `status` TINYINT(4) NOT NULL,
  `title` VARCHAR(150) NOT NULL,
  `author_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK260CC07934CE09` (`author_id` ASC),
  CONSTRAINT `FK260CC07934CE09`
    FOREIGN KEY (`author_id`)
    REFERENCES `miniblog`.`User` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 554
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `miniblog`.`Comment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `miniblog`.`Comment` ;

CREATE TABLE IF NOT EXISTS `miniblog`.`Comment` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `content` VARCHAR(500) NOT NULL,
  `created_at` DATETIME NOT NULL,
  `modified_at` DATETIME NOT NULL,
  `post_id` INT(11) NOT NULL,
  `user_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK9BDE863F187BDBC9` (`user_id` ASC),
  INDEX `FK9BDE863F9ED2FA9` (`post_id` ASC),
  CONSTRAINT `FK9BDE863F9ED2FA9`
    FOREIGN KEY (`post_id`)
    REFERENCES `miniblog`.`Post` (`id`),
  CONSTRAINT `FK9BDE863F187BDBC9`
    FOREIGN KEY (`user_id`)
    REFERENCES `miniblog`.`User` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 567
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `miniblog`.`Token`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `miniblog`.`Token` ;

CREATE TABLE IF NOT EXISTS `miniblog`.`Token` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `access_key` VARCHAR(72) NOT NULL,
  `created_at` DATETIME NOT NULL,
  `expired_at` DATETIME NOT NULL,
  `user_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK4D3C9D9187BDBC9` (`user_id` ASC),
  CONSTRAINT `FK4D3C9D9187BDBC9`
    FOREIGN KEY (`user_id`)
    REFERENCES `miniblog`.`User` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 522
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
