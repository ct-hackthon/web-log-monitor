USE Test;

create table `spout_history`
(
  `Id`  int(11) auto_increment,
  `Text` varchar(5000) default null,
  `CreateTime` timestamp null,
  `UpdateTime` timestamp default CURRENT_TIMESTAMP not null,
  PRIMARY KEY (`Id`),
  KEY `INDEX_UPDATE` (`UpdateTime`) USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
