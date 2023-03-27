CREATE DATABASE  IF NOT EXISTS `hero_shema`;
USE `hero_shema`;

DROP TABLE IF EXISTS `tb_hero`;
CREATE TABLE `tb_hero` (
  `id` int NOT NULL,
  `name` varchar(200) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `tb_hero_seq`;
CREATE TABLE `tb_hero_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
