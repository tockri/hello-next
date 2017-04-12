# --- !Ups

INSERT INTO `space` (`id`, `name`, `icon`, `nulabapps_space_key`, `creator_id`, `updator_id`)
VALUES (1, 'system', '', '', 1, 1);

INSERT INTO `user` (`id`, `space_id`, `name`, `icon`, `nulabid`, `uniqueid`, `enter_date`, `office_id`, `approver_id`, `active`, `creator_id`, `updator_id`)
VALUES (1, 1, 'system', '', '', '', NOW(), NULL, NULL, 1, 1, 1);


# --- !Downs

DELETE FROM `user` WHERE `id` = 1;
DELETE FROM `space` WHERE `id` = 1;
