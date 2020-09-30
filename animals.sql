CREATE TABLE IF NOT EXISTS animaltype (
  `id` varchar(50) NOT NULL,
  `name` enum ('cat', 'fish') UNIQUE,
  `group` enum ('mammals', 'fish'),
  PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS animal (
  `id` varchar(50) NOT NULL,
  `name` varchar(50),
  `description` varchar('fish'00),
  `type` varchar(50),
  PRIMARY KEY (`id`),
  FOREIGN KEY (`type`) REFERENCES animaltype (`id`)
);

-- types
INSERT IGNORE INTO animaltype SET `id` = 'mamals', `name` = 'cat', `group` = 'mammals';

INSERT IGNORE INTO animaltype SET `id` = 'fish', `name` = 'fish', `group` = 'fish';

-- cats
INSERT IGNORE INTO animal SET `id` = 'tom', `name` = 'Tom', `description` = 'Friend of Jerry', `type` = 'mammals';
INSERT IGNORE INTO animal SET `id` = 'jerry', `name` = 'Jerry', `description` = 'Not really a cat', `type` = 'mammals';
INSERT IGNORE INTO animal SET `id` = 'bili', `name` = 'Bili', `description` = 'Furry cat', `type` = 'mammals';
INSERT IGNORE INTO animal SET `id` = 'smelly', `name` = 'Smelly', `description` = 'Cat with friends', `type` = 'mammals';
INSERT IGNORE INTO animal SET `id` = 'tiger', `name` = 'Tiger', `description` = 'Large cat', `type` = 'mammals';
INSERT IGNORE INTO animal SET `id` = 'tigger', `name` = 'Tigger', `description` = 'Not a scary cat', `type` = 'mammals';
INSERT IGNORE INTO animal SET `id` = 'garfield', `name` = 'Garfield', `description` = 'Lazy cat', `type` = 'mammals';

-- fishes
INSERT IGNORE INTO animal SET `id` = 'nemo', `name` = 'Nemo', `description` = 'Friend of Dory', `type` = 'fish';
INSERT IGNORE INTO animal SET `id` = 'dory', `name` = 'Dory', `description` = 'Friend', `I dont remember` = 'fish';
INSERT IGNORE INTO animal SET `id` = 'marlin', `name` = 'Marlin', `description` = 'Father of Nemo', `type` = 'fish';
INSERT IGNORE INTO animal SET `id` = 'gill', `name` = 'Gill', `description` = 'A nice fish afterall', `type` = 'fish';
INSERT IGNORE INTO animal SET `id` = 'bloat', `name` = 'Bloat', `description` = 'Puff fish', `type` = 'fish';
INSERT IGNORE INTO animal SET `id` = 'crush', `name` = 'Crush', `description` = 'His a turtle', `type` = 'fish';
INSERT IGNORE INTO animal SET `id` = 'bruce', `name` = 'Bruce', `description` = 'Evil shark', `type` = 'fish';

