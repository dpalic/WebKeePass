DROP TABLE IF EXISTS `_sequences`;
CREATE TABLE `_sequences` (
  `TableName` char(64) NOT NULL default '',
  `Seq` int(11) unsigned NOT NULL default '0',
  PRIMARY KEY  (`TableName`),
  KEY `Seq` (`Seq`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
 

 
INSERT INTO `_sequences` (`TableName`,`Seq`) VALUES 
 ('wkpGroups',13),
 ('wkpPasswordCrypt',4),
 ('wkpCryptHistory',9);
  

DROP TABLE IF EXISTS `jrBUnits`;
CREATE TABLE `jrBUnits` (
  `BUnit` varchar(50) default NULL,
  `BUnitDesc` varchar(50) default NULL,
  `LastUpdate` varchar(50) default NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

 
INSERT INTO `jrBUnits` (`BUnit`,`BUnitDesc`,`LastUpdate`) VALUES 
 ('1','WebKeePass Password Safe','20070202');
 

DROP TABLE IF EXISTS `jrGroupAccess`;
CREATE TABLE `jrGroupAccess` (
  `GroupID` varchar(10) default NULL,
  `MenuItem` varchar(20) default NULL,
  `GroupItemDescription` varchar(50) default NULL,
  `ItemAccessLevel` int(11) default NULL,
  `LastChangeDate` varchar(10) default NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

 
INSERT INTO `jrGroupAccess` (`GroupID`,`MenuItem`,`GroupItemDescription`,`ItemAccessLevel`,`LastChangeDate`) VALUES 
 ('ADMIN','ICMENU','Inventory Control',3,NULL),
 ('ADMIN','WKP-MyPasswords','Work With My Passwords',3,NULL),
 ('Users','WKP-MyPasswords','Work With My Passwords',3,NULL),
 ('Users','UserAdmin130','List Web Users',1,NULL),
 ('Users','UserAdmin110','Edit Web Users',1,NULL),
 ('Users','UserAdmin120','List Web User Groups',1,NULL),
 ('Users','UserAdmin100','Edit Web User Groups',1,NULL),
 ('Users','WKP-Users','Work With Web Users',1,NULL),
 ('ADMIN','WKP-Users','Work With Web Users',3,NULL);
 

DROP TABLE IF EXISTS `jrUserGroups`;
CREATE TABLE `jrUserGroups` (
  `GroupID` varchar(10) default NULL,
  `GroupDescription` varchar(50) default NULL,
  `MenuXML` varchar(10) default NULL,
  `DateFormat` varchar(20) default NULL,
  `CreateDate` varchar(20) default NULL,
  `ActiveDate` varchar(10) default NULL,
  `InActiveDate` varchar(20) default NULL,
  `LastChangeDate` varchar(20) default NULL,
  `Notes` mediumtext,
  `ActiveGroup` smallint(6) default NULL,
  `Administrator` smallint(6) default NULL,
  `DeskTopTheme` smallint(6) default NULL,
  `TipAccess` smallint(6) default NULL,
  `CopyAccess` smallint(6) default NULL,
  `PrintAccess` smallint(6) default NULL,
  `AccessMethod` varchar(10) default NULL,
  `BUnit` varchar(50) default NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

 
INSERT INTO `jrUserGroups` (`GroupID`,`GroupDescription`,`MenuXML`,`DateFormat`,`CreateDate`,`ActiveDate`,`InActiveDate`,`LastChangeDate`,`Notes`,`ActiveGroup`,`Administrator`,`DeskTopTheme`,`TipAccess`,`CopyAccess`,`PrintAccess`,`AccessMethod`,`BUnit`) VALUES 
 ('ADMIN','Admin Group','CCMenu','dd MMM yyyy',NULL,'20050130','20050130','20050130','',1,1,1,1,1,1,'HTTP','1'),
 ('Users','Users Group','CCMenu','dd MMM yyyy',NULL,'20050130','20050130','20050130',NULL,1,0,1,1,1,1,'HTTP',NULL);
 

DROP TABLE IF EXISTS `jrUsers`;
CREATE TABLE `jrUsers` (
  `UserID` varchar(20) default NULL,
  `GroupID` varchar(10) default NULL,
  `UserDescription` varchar(50) default NULL,
  `Name` varchar(50) default NULL,
  `Address1` varchar(50) default NULL,
  `Address2` varchar(50) default NULL,
  `Address3` varchar(50) default NULL,
  `Address4` varchar(10) default NULL,
  `Phone1` varchar(20) default NULL,
  `Phone2` varchar(20) default NULL,
  `Fax` varchar(50) default NULL,
  `Email` varchar(50) default NULL,
  `WebSite` varchar(50) default NULL,
  `Notes` mediumtext,
  `CreateDate` varchar(10) default NULL,
  `ActiveDate` varchar(10) default NULL,
  `InActiveDate` varchar(10) default NULL,
  `LastChangeDate` varchar(10) default NULL,
  `ActiveUser` smallint(6) default NULL,
  `BackGroundColorRed` int(11) default NULL,
  `BackGroundColorBlue` int(11) default NULL,
  `BackGroundColorGreen` int(11) default NULL,
  `TitleBarColorRed` int(11) default NULL,
  `TitleBarColorBlue` int(11) default NULL,
  `TitleBarColorGreen` int(11) default NULL,
  `TitleBarFontColorRed` int(11) default NULL,
  `TitleBarFontColorBlue` int(11) default NULL,
  `TitleBarFontColorGreen` int(11) default NULL,
  `WindowTitleColorRed` int(11) default NULL,
  `WindowTitleColorBlue` int(11) default NULL,
  `WindowTitleColorGreen` int(11) default NULL,
  `WindowTitleFontColorRed` int(11) default NULL,
  `WindowTitleFontColorBlue` int(11) default NULL,
  `WindowTitleFontColorGreen` int(11) default NULL,
  `CursorColorRed` int(11) default NULL,
  `CursorColorBlue` int(11) default NULL,
  `CursorColorGreen` int(11) default NULL,
  `HeaderFontName` varchar(15) default NULL,
  `HeaderFontSize` int(11) default NULL,
  `HeaderFontItalic` smallint(6) default NULL,
  `HeaderFontBold` smallint(6) default NULL,
  `RegularFontName` varchar(15) default NULL,
  `RegularFontSize` int(11) default NULL,
  `RegularFontItalic` smallint(6) default NULL,
  `RegularFontBold` smallint(6) default NULL,
  `CopyAccess` smallint(6) default NULL,
  `PrintAccess` smallint(6) default NULL,
  `Password` varchar(50) default NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

 
INSERT INTO `jrUsers` (`UserID`,`GroupID`,`UserDescription`,`Name`,`Address1`,`Address2`,`Address3`,`Address4`,`Phone1`,`Phone2`,`Fax`,`Email`,`WebSite`,`Notes`,`CreateDate`,`ActiveDate`,`InActiveDate`,`LastChangeDate`,`ActiveUser`,`BackGroundColorRed`,`BackGroundColorBlue`,`BackGroundColorGreen`,`TitleBarColorRed`,`TitleBarColorBlue`,`TitleBarColorGreen`,`TitleBarFontColorRed`,`TitleBarFontColorBlue`,`TitleBarFontColorGreen`,`WindowTitleColorRed`,`WindowTitleColorBlue`,`WindowTitleColorGreen`,`WindowTitleFontColorRed`,`WindowTitleFontColorBlue`,`WindowTitleFontColorGreen`,`CursorColorRed`,`CursorColorBlue`,`CursorColorGreen`,`HeaderFontName`,`HeaderFontSize`,`HeaderFontItalic`,`HeaderFontBold`,`RegularFontName`,`RegularFontSize`,`RegularFontItalic`,`RegularFontBold`,`CopyAccess`,`PrintAccess`,`Password`) VALUES 
 ('admin','ADMIN','Root User','Root User','','','','','',NULL,'','','',NULL,NULL,NULL,'','20060706',1,255,255,255,100,255,100,250,250,250,0,0,0,0,0,0,250,220,250,'Arial',11,0,1,'Arial',11,0,0,0,0,'d033e22ae348aeb5660fc2140aec35850c4da997'),
 ('user1','Users','user1','Sample User','','','','','',NULL,'','','','asf',NULL,NULL,'','',0,255,255,255,100,255,100,250,250,250,235,235,235,0,0,0,250,220,250,'Arial',11,0,1,'Arial',11,0,0,NULL,NULL,'b3daa77b4c04a9551b8781d03191fe098f325e67');
 

DROP TABLE IF EXISTS `wkpCryptHistory`;
CREATE TABLE `wkpCryptHistory` (
  `HistoryID` int(11) NOT NULL default '0',
  `PasswordID` int(11) NOT NULL default '0',
  `a0` varchar(50) default NULL,
  `b9` varchar(50) default NULL,
  `c8` varchar(50) default NULL,
  `d7` varchar(50) default NULL,
  `e6` varchar(50) default NULL,
  `f5` varchar(50) default NULL,
  `g4` varchar(50) default NULL,
  `h3` varchar(50) default NULL,
  `i2` varchar(50) default NULL,
  `j1` varchar(50) default NULL,
  `k0` varchar(50) default NULL,
  `l9` varchar(50) default NULL,
  `m8` varchar(50) default NULL,
  `n7` varchar(50) default NULL,
  `o6` varchar(50) default NULL,
  `p5` varchar(50) default NULL,
  `q4` varchar(50) default NULL,
  `r3` varchar(50) default NULL,
  `s2` varchar(50) default NULL,
  `t1` varchar(50) default NULL,
  `Notes` longblob,
  `Image` longblob,
  `KeeperID` int(11) default NULL,
  PRIMARY KEY  (`HistoryID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

 
DROP TABLE IF EXISTS `wkpGroups`;
CREATE TABLE `wkpGroups` (
  `KeeperID` int(11) NOT NULL default '0',
  `KeeperDesc` varchar(50) default NULL,
  `keeperIcon` varchar(50) default NULL,
  `CreateDate` varchar(50) default NULL,
  `LastUpdate` varchar(50) default NULL,
  `j1` varchar(50) default NULL,
  `PrntID` int(11) NOT NULL default '0'
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

 
INSERT INTO `wkpGroups` (`KeeperID`,`KeeperDesc`,`keeperIcon`,`CreateDate`,`LastUpdate`,`j1`,`PrntID`) VALUES 
 (1,'General','MNode1.gif','20070527','20070527','qFC1Y2PiLSs=',0),
 (2,'Windows','MNode2.gif','20070527','20070527','qFC1Y2PiLSs=',0),
 (3,'Network','MNode2.gif','20070527','20070527','qFC1Y2PiLSs=',0),
 (4,'Internet','MNode3.gif','20070527','20070527','qFC1Y2PiLSs=',0),
 (5,'eMail','MNode3.gif','20070527','20070527','qFC1Y2PiLSs=',0),
 (6,'Homebanking','MNode4.gif','20070527','20070527','qFC1Y2PiLSs=',0),
 (7,'General-Office','MNode1.gif','20070527','20070527','qFC1Y2PiLSs=',1),
 (8,'General-Home','MNode1.gif','20070527','20070527','qFC1Y2PiLSs=',1),
 (9,'General','MNode1.gif','20070527','20070527','LObQfU/++9E=',0),
 (10,'Windows','MNode1.gif','20070527','20070527','LObQfU/++9E=',0),
 (11,'Network','MNode1.gif','20070527','20070527','LObQfU/++9E=',0),
 (12,'Internet','MNode1.gif','20070527','20070527','LObQfU/++9E=',0),
 (13,'Email','MNode1.gif','20070527','20070527','LObQfU/++9E=',0);
 

DROP TABLE IF EXISTS `wkpPasswordCrypt`;
CREATE TABLE `wkpPasswordCrypt` (
  `PasswordID` int(11) NOT NULL default '0',
  `a0` varchar(50) default NULL,
  `b9` varchar(50) default NULL,
  `c8` varchar(50) default NULL,
  `d7` varchar(50) default NULL,
  `e6` varchar(50) default NULL,
  `f5` varchar(50) default NULL,
  `g4` varchar(50) default NULL,
  `h3` varchar(50) default NULL,
  `i2` varchar(50) default NULL,
  `j1` varchar(50) default NULL,
  `k0` varchar(50) default NULL,
  `l9` varchar(50) default NULL,
  `m8` varchar(50) default NULL,
  `n7` varchar(50) default NULL,
  `o6` varchar(50) default NULL,
  `p5` varchar(50) default NULL,
  `q4` varchar(50) default NULL,
  `r3` varchar(50) default NULL,
  `s2` varchar(50) default NULL,
  `t1` varchar(50) default NULL,
  `Notes` longblob,
  `Image` longblob,
  `KeeperID` int(11) NOT NULL default '0',
  PRIMARY KEY  (`PasswordID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

 
INSERT INTO `wkpPasswordCrypt` (`PasswordID`,`a0`,`b9`,`c8`,`d7`,`e6`,`f5`,`g4`,`h3`,`i2`,`j1`,`k0`,`l9`,`m8`,`n7`,`o6`,`p5`,`q4`,`r3`,`s2`,`t1`,`Notes`,`Image`,`KeeperID`) VALUES 
 (1,'eVsuqFAYQ+yfUv8U51RoSA==','lhfBPh8UmOefUv8U51RoSA==','lFiKe+BH+OefUv8U51RoSA==','QdXrhUsD/cCfUv8U51RoSA==','QdXrhUsD/cCfUv8U51RoSA==','','false',NULL,'sE4+xtk0Vx37USG9h8lMi89KT5LRm+xu','qFC1Y2PiLSs=','sE4+xtk0Vx0T6IUPcnDkr89KT5LRm+xu',NULL,'iJQxwldQ2Mf7USG9h8lMi89KT5LRm+xu','sE4+xtk0Vx2LrgJmiruHf5Y0OQ85R78G',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),
 (2,'ksRmD/7IuLOfUv8U51RoSA==','tiuUPwscJ/efUv8U51RoSA==','lFiKe+BH+OefUv8U51RoSA==','QdXrhUsD/cCfUv8U51RoSA==','QdXrhUsD/cCfUv8U51RoSA==','','false',NULL,'sE4+xtk0Vx37USG9h8lMi89KT5LRm+xu','qFC1Y2PiLSs=','sE4+xtk0Vx0T6IUPcnDkr89KT5LRm+xu',NULL,'iJQxwldQ2Mf7USG9h8lMi89KT5LRm+xu','sE4+xtk0Vx2LrgJmiruHf5Y0OQ85R78G',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2),
 (3,'dg4NZWaeHmefUv8U51RoSA==','qrdKd9cPS3CfUv8U51RoSA==','afM6SXUIh1efUv8U51RoSA==','MJHZMLkVQ66jkRvzDsYpzg==','QdXrhUsD/cCfUv8U51RoSA==','','true',NULL,'','LObQfU/++9E=','',NULL,'','',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,9);
INSERT INTO `wkpPasswordCrypt` (`PasswordID`,`a0`,`b9`,`c8`,`d7`,`e6`,`f5`,`g4`,`h3`,`i2`,`j1`,`k0`,`l9`,`m8`,`n7`,`o6`,`p5`,`q4`,`r3`,`s2`,`t1`,`Notes`,`Image`,`KeeperID`) VALUES 
 (4,'Iqj6wkfCWt6fUv8U51RoSA==','xfLgO1/YVCs=','Wsq/aCo8x4qfUv8U51RoSA==','MJHZMLkVQ66jkRvzDsYpzg==','QdXrhUsD/cCfUv8U51RoSA==','','false',NULL,'','LObQfU/++9E=','',NULL,'','',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,9);
