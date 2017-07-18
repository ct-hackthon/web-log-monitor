    
    
    
    
USE test;

create table `http_code_statistic`
(
  `Id`  int(11) auto_increment,
  `Count`	BIGINT(11) DEFAULT 0 ,
  `TimeBox` VARCHAR(50),
  `TimeId` BigInt(64),
  `Code`VARCHAR(50),
  `CreateTime` timestamp null,
  `UpdateTime` timestamp default CURRENT_TIMESTAMP not null,
  PRIMARY KEY (`Id`),
  KEY `INDEX_UPDATE` (`UpdateTime`) USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

    