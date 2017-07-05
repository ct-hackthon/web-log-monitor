create table `http_error_statistic`
(
  `Id`  int(11) auto_increment,
  `NormalCount`	BIGINT(11) DEFAULT 0 COMMENT 'HTTP200 的数量',
  `ErrorCount` 	BIGINT(11) DEFAULT 0 COMMENT '非HTTP200 的数量',
  `TimeBox` VARCHAR(50),
  `CreateTime` timestamp null,
  `UpdateTime` timestamp default CURRENT_TIMESTAMP not null,
  PRIMARY KEY (`Id`),
  KEY `INDEX_UPDATE` (`UpdateTime`) USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
