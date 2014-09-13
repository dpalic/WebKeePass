DROP TABLE IF EXISTS `wkpSeqCrypt`;
CREATE TABLE `wkpSeqCrypt` (
  `TableName` char(64) NOT NULL default '',
  `Seq` int(11) unsigned NOT NULL default '0',
  PRIMARY KEY  (`TableName`),
  KEY `Seq` (`Seq`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
 

 
INSERT INTO `wkpSeqCrypt` (`TableName`,`Seq`) VALUES 
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
 ('Admins','ICMENU','Inventory Control',3,NULL),
 ('Admins','WKP-MyPasswords','Work With My Passwords',3,NULL),
 ('Users','WKP-MyPasswords','Work With My Passwords',3,NULL),
 ('Users','UserAdmin130','List Web Users',1,NULL),
 ('Users','UserAdmin110','Edit Web Users',1,NULL),
 ('Users','UserAdmin120','List Web User Groups',1,NULL),
 ('Users','UserAdmin100','Edit Web User Groups',1,NULL),
 ('Users','WKP-Users','Work With Web Users',1,NULL),
 ('Admins','WKP-Users','Work With Web Users',3,NULL);
 

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
 ('Admins','Admin Group','CCMenu','dd MMM yyyy',NULL,'20050130','20050130','20050130','',1,1,1,1,1,1,'HTTP','1'),
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

CREATE TABLE `wkpPasswordAKin` (
  `AKinID` int(11) NOT NULL,
  `z0` varchar(50) default NULL,
  `y1` varchar(50) default NULL,
  `x2` varchar(50) default NULL,
  `w3` varchar(50) default NULL,
  `v4` varchar(50) default NULL,
  `u5` varchar(50) default NULL,
  `t6` varchar(50) default NULL,
  `s7` varchar(50) default NULL,
  `t8` varchar(50) default NULL,
  `r9` varchar(50) default NULL,
  `PasswordID` int(11) NOT NULL,
  PRIMARY KEY  (`AKinID`,`PasswordID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;


 
