SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

INSERT INTO `departments` (`id`, `name`, `description`) VALUES
(1,	'Managers',	'Managers'),
(2,	'Workers',	'Workers');

INSERT INTO `employees` (`id`, `first_name`, `second_name`, `birth_date`, `hire_date`, `salary`, `jobtitle_id`, `department_id`) VALUES
(1,	'Jorge',	'Smith',	'2018-12-03',	'2018-12-03',	100000.00000,	2,	1),
(2,	'Alex',	'Merge',	'2018-12-03',	'2018-12-03',	50000.00000,	3,	1),
(3,	'Johny',	'Zurich',	'2018-12-03',	'2018-12-03',	12000.00000,	1,	2),
(4,	'Serge',	'Hire',	'2018-12-03',	'2018-12-03',	15000.00000,	1,	2);

INSERT INTO `jobtitles` (`id`, `name`) VALUES
(1,	'engineer'),
(2,	'boss'),
(3,	'manager');