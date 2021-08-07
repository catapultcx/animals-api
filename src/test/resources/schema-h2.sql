SET SCHEMA PUBLIC;

DROP TABLE IF EXISTS animals;

CREATE TABLE animals(
    `id` UUID DEFAULT RANDOM_UUID() NOT NULL PRIMARY KEY,
    `name` VARCHAR(255),
    `description` VARCHAR(255),
    `group_name` INTEGER
                    );
