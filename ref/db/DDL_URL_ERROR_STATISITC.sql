USE test;

create table `url_error_statistic`
(
  `Id`  int(11) auto_increment,
  `NormalCount`	BIGINT(11) DEFAULT 0 COMMENT 'HTTP200 的数量',
  `ErrorCount`	BIGINT(11) DEFAULT 0 COMMENT 'HTTP200 的数量',
  `Url` 	VARCHAR(200) DEFAULT '',
  `TimeBox` VARCHAR(50),
  `TimeId` BigInt(64),
  `CreateTime` timestamp null,
  `UpdateTime` timestamp default CURRENT_TIMESTAMP not null,
  PRIMARY KEY (`Id`),
  KEY `INDEX_UPDATE` (`UpdateTime`) USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
