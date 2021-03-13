CREATE TABLE `parents` (
  `id` bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `first_name` varchar(256) NOT NULL,
  `last_name` varchar(256) NOT NULL,
  `identification_type` varchar(3) NOT NULL,
  `identification_value` varchar(128) NOT NULL,
  `email` varchar(256) NOT NULL,
  `password` text NOT NULL
);

ALTER TABLE
  `parents`
ADD
  UNIQUE `identification` (`identification_type`, `identification_value`);