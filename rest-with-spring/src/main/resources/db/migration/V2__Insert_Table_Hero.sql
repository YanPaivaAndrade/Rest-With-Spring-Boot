LOCK TABLES `tb_hero` WRITE;
INSERT INTO `tb_hero` VALUES (1,'testeee22'),(2,'kkkkkk'),(152,'HeroFake'),(202,'HeroFake'),(252,'HeroFake'),(602,'okoko'),(652,'deku');
UNLOCK TABLES;

LOCK TABLES `tb_hero_seq` WRITE;
INSERT INTO `tb_hero_seq` VALUES (751);
UNLOCK TABLES;
