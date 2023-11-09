CREATE TABLE `actor`
(
    `actor_id`    smallint unsigned NOT NULL AUTO_INCREMENT,
    `first_name`  varchar(45) NOT NULL,
    `last_name`   varchar(45) NOT NULL,
    `last_update` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`actor_id`),
    KEY           `idx_actor_last_name` (`last_name`)
) ENGINE=InnoDB AUTO_INCREMENT=202 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `address`
(
    `address_id`  smallint unsigned NOT NULL AUTO_INCREMENT,
    `address`     varchar(50) NOT NULL,
    `address2`    varchar(50)          DEFAULT NULL,
    `district`    varchar(20) NOT NULL,
    `city_id`     smallint unsigned NOT NULL,
    `postal_code` varchar(10)          DEFAULT NULL,
    `phone`       varchar(20) NOT NULL,
    `location`    geometry             DEFAULT NULL,
    `last_update` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`address_id`),
    KEY           `idx_fk_city_id` (`city_id`),
    CONSTRAINT `fk_address_city` FOREIGN KEY (`city_id`) REFERENCES `city` (`city_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=607 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `category`
(
    `category_id` tinyint unsigned NOT NULL AUTO_INCREMENT,
    `name`        varchar(25) NOT NULL,
    `last_update` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `city`
(
    `city_id`     smallint unsigned NOT NULL AUTO_INCREMENT,
    `city`        varchar(50) NOT NULL,
    `country_id`  smallint unsigned NOT NULL,
    `last_update` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`city_id`),
    KEY           `idx_fk_country_id` (`country_id`),
    CONSTRAINT `fk_city_country` FOREIGN KEY (`country_id`) REFERENCES `country` (`country_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=602 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `country`
(
    `country_id`  smallint unsigned NOT NULL AUTO_INCREMENT,
    `country`     varchar(50) NOT NULL,
    `last_update` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`country_id`)
) ENGINE=InnoDB AUTO_INCREMENT=111 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `customer`
(
    `customer_id` smallint unsigned NOT NULL AUTO_INCREMENT,
    `store_id`    tinyint unsigned NOT NULL,
    `first_name`  varchar(45) NOT NULL,
    `last_name`   varchar(45) NOT NULL,
    `email`       varchar(50) DEFAULT NULL,
    `address_id`  smallint unsigned NOT NULL,
    `active`      tinyint(1) NOT NULL DEFAULT '1',
    `create_date` datetime    NOT NULL,
    `last_update` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`customer_id`),
    KEY           `idx_fk_store_id` (`store_id`),
    KEY           `idx_fk_address_id` (`address_id`),
    KEY           `idx_last_name` (`last_name`),
    CONSTRAINT `fk_customer_address` FOREIGN KEY (`address_id`) REFERENCES `address` (`address_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT `fk_customer_store` FOREIGN KEY (`store_id`) REFERENCES `store` (`store_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=602 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `film`
(
    `film_id`              smallint unsigned NOT NULL AUTO_INCREMENT,
    `title`                varchar(128) NOT NULL,
    `description`          text,
    `release_year`         int                   DEFAULT NULL,
    `language_id`          tinyint unsigned DEFAULT NULL,
    `original_language_id` tinyint unsigned DEFAULT NULL,
    `rental_duration`      int          NOT NULL,
    `rental_rate`          int          NOT NULL,
    `length`               int                   DEFAULT NULL,
    `replacement_cost` double NOT NULL,
    `rating`               enum('G','NC17','PG','PG13','R') DEFAULT NULL,
    `special_features`     varchar(255)          DEFAULT NULL,
    `last_update`          timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`film_id`),
    KEY                    `idx_title` (`title`),
    KEY                    `idx_fk_language_id` (`language_id`),
    KEY                    `idx_fk_original_language_id` (`original_language_id`),
    CONSTRAINT `fk_film_language` FOREIGN KEY (`language_id`) REFERENCES `language` (`language_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT `fk_film_language_original` FOREIGN KEY (`original_language_id`) REFERENCES `language` (`language_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1004 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `film_actor`
(
    `actor_id`    smallint unsigned NOT NULL,
    `film_id`     smallint unsigned NOT NULL,
    `last_update` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`actor_id`, `film_id`),
    KEY           `idx_fk_film_id` (`film_id`),
    CONSTRAINT `fk_film_actor_actor` FOREIGN KEY (`actor_id`) REFERENCES `actor` (`actor_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT `fk_film_actor_film` FOREIGN KEY (`film_id`) REFERENCES `film` (`film_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `film_category`
(
    `film_id`     smallint unsigned NOT NULL,
    `category_id` tinyint unsigned NOT NULL,
    `last_update` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`film_id`, `category_id`),
    KEY           `fk_film_category_category` (`category_id`),
    CONSTRAINT `fk_film_category_category` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT `fk_film_category_film` FOREIGN KEY (`film_id`) REFERENCES `film` (`film_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `film_text`
(
    `film_id`     smallint     NOT NULL,
    `title`       varchar(255) NOT NULL,
    `description` text,
    PRIMARY KEY (`film_id`),
    FULLTEXT KEY `idx_title_description` (`title`,`description`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `inventory`
(
    `inventory_id` mediumint unsigned NOT NULL AUTO_INCREMENT,
    `film_id`      smallint unsigned NOT NULL,
    `store_id`     tinyint unsigned NOT NULL,
    `last_update`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`inventory_id`),
    KEY            `idx_fk_film_id` (`film_id`),
    KEY            `idx_store_id_film_id` (`store_id`,`film_id`),
    CONSTRAINT `fk_inventory_film` FOREIGN KEY (`film_id`) REFERENCES `film` (`film_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT `fk_inventory_store` FOREIGN KEY (`store_id`) REFERENCES `store` (`store_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4584 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `language`
(
    `language_id` tinyint unsigned NOT NULL AUTO_INCREMENT,
    `name`        varchar(255) NOT NULL,
    `last_update` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`language_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `payment`
(
    `payment_id`   bigint        NOT NULL AUTO_INCREMENT,
    `customer_id`  smallint unsigned NOT NULL,
    `staff_id`     tinyint unsigned NOT NULL,
    `rental_id`    int DEFAULT NULL,
    `amount`       decimal(5, 2) NOT NULL,
    `payment_date` datetime      NOT NULL,
    `last_update`  timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`payment_id`),
    KEY            `idx_fk_staff_id` (`staff_id`),
    KEY            `idx_fk_customer_id` (`customer_id`),
    KEY            `fk_payment_rental` (`rental_id`),
    CONSTRAINT `fk_payment_customer` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT `fk_payment_rental` FOREIGN KEY (`rental_id`) REFERENCES `rental` (`rental_id`) ON DELETE SET NULL ON UPDATE CASCADE,
    CONSTRAINT `fk_payment_staff` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`staff_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16051 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `rental`
(
    `rental_id`    int       NOT NULL AUTO_INCREMENT,
    `rental_date`  datetime  NOT NULL,
    `inventory_id` mediumint unsigned NOT NULL,
    `customer_id`  smallint unsigned NOT NULL,
    `return_date`  datetime           DEFAULT NULL,
    `staff_id`     tinyint unsigned NOT NULL,
    `last_update`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`rental_id`),
    UNIQUE KEY `rental_date` (`rental_date`,`inventory_id`,`customer_id`),
    KEY            `idx_fk_inventory_id` (`inventory_id`),
    KEY            `idx_fk_customer_id` (`customer_id`),
    KEY            `idx_fk_staff_id` (`staff_id`),
    CONSTRAINT `fk_rental_customer` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT `fk_rental_inventory` FOREIGN KEY (`inventory_id`) REFERENCES `inventory` (`inventory_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT `fk_rental_staff` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`staff_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16051 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `staff`
(
    `staff_id`    tinyint unsigned NOT NULL AUTO_INCREMENT,
    `first_name`  varchar(45) NOT NULL,
    `last_name`   varchar(45) NOT NULL,
    `address_id`  smallint unsigned NOT NULL,
    `picture`     blob,
    `email`       varchar(50)                                           DEFAULT NULL,
    `store_id`    tinyint unsigned NOT NULL,
    `active`      tinyint(1) NOT NULL DEFAULT '1',
    `username`    varchar(16) NOT NULL,
    `password`    varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
    `last_update` timestamp   NOT NULL                                  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`staff_id`),
    KEY           `idx_fk_store_id` (`store_id`),
    KEY           `idx_fk_address_id` (`address_id`),
    CONSTRAINT `fk_staff_address` FOREIGN KEY (`address_id`) REFERENCES `address` (`address_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT `fk_staff_store` FOREIGN KEY (`store_id`) REFERENCES `store` (`store_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `store`
(
    `store_id`         tinyint unsigned NOT NULL AUTO_INCREMENT,
    `manager_staff_id` tinyint unsigned DEFAULT NULL,
    `address_id`       smallint unsigned NOT NULL,
    `last_update`      timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`store_id`),
    UNIQUE KEY `idx_unique_manager` (`manager_staff_id`),
    KEY                `idx_fk_address_id` (`address_id`),
    CONSTRAINT `fk_store_address` FOREIGN KEY (`address_id`) REFERENCES `address` (`address_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT `fk_store_staff` FOREIGN KEY (`manager_staff_id`) REFERENCES `staff` (`staff_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
