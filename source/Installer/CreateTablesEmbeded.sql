
CREATE TABLE wkpSeqCrypt (
  TableName varchar(50),
  Seq int  
);
 

 
INSERT INTO wkpSeqCrypt (TableName,Seq) VALUES 
 ('wkpGroups',13),
 ('wkpPasswordCrypt',4),
 ('wkpCryptHistory',9);
  


CREATE TABLE jrBUnits (
  BUnit varchar(50) NOT NULL PRIMARY KEY ,
  BUnitDesc varchar(50),
  LastUpdate varchar(50)  
);

 
INSERT INTO jrBUnits (BUnit,BUnitDesc,LastUpdate) VALUES 
 ('1','WebKeePass Password Safe','20070101');
 

 
CREATE TABLE jrGroupAccess (
  GroupID varchar(50),
  MenuItem varchar(50),
  GroupItemDescription varchar(50),
  ItemAccessLevel int,
  LastChangeDate varchar(20)  
);

 
INSERT INTO jrGroupAccess (GroupID,MenuItem,GroupItemDescription,ItemAccessLevel,LastChangeDate) VALUES 
 ('Users','WKP-MyPasswords','Work With My Passwords',3,NULL),
 ('Users','UserAdmin130','List Web Users',1,NULL),
 ('Users','UserAdmin110','Edit Web Users',1,NULL),
 ('Users','UserAdmin120','List Web User Groups',1,NULL),
 ('Users','UserAdmin100','Edit Web User Groups',1,NULL),
 ('Users','WKP-Users','Work With Web Users',1,NULL);
 

 
CREATE TABLE jrUserGroups (
  GroupID varchar(50) NOT NULL PRIMARY KEY,
  GroupDescription varchar(50),
  MenuXML varchar(50),
  DateFormat varchar(50),
  CreateDate varchar(50),
  ActiveDate varchar(50),
  InActiveDate varchar(50),
  LastChangeDate varchar(50),
  Notes varchar(500),
  ActiveGroup smallint, 
  Administrator smallint, 
  DeskTopTheme smallint, 
  TipAccess smallint, 
  CopyAccess smallint, 
  PrintAccess smallint, 
  AccessMethod varchar(10),
  BUnit varchar(50)  
);

 
INSERT INTO jrUserGroups (GroupID,GroupDescription,MenuXML,DateFormat,CreateDate,ActiveDate,InActiveDate,LastChangeDate,Notes,ActiveGroup,Administrator,DeskTopTheme,TipAccess,CopyAccess,PrintAccess,AccessMethod,BUnit) VALUES 
 ('Admins','Admin Group','CCMenu','dd MMM yyyy',NULL,'20070101','20070101','20070101','',1,1,1,1,1,1,'HTTP','1'),
 ('Users','Users Group','CCMenu','dd MMM yyyy',NULL,'20070101','20070101','20070101',NULL,1,0,1,1,1,1,'HTTP',NULL);
 

 
CREATE TABLE jrUsers (
  UserID varchar(50) NOT NULL PRIMARY KEY,
  GroupID varchar(50),
  UserDescription varchar(50),
  Name varchar(50),
  Address1 varchar(50),
  Address2 varchar(50),
  Address3 varchar(50),
  Address4 varchar(50),
  Phone1 varchar(50),
  Phone2 varchar(50),
  Fax varchar(50),
  Email varchar(50),
  WebSite varchar(50),
  Notes long varchar,
  CreateDate varchar(50),
  ActiveDate varchar(50),
  InActiveDate varchar(50),
  LastChangeDate varchar(50),
  ActiveUser smallint, 
  BackGroundColorRed int, 
  BackGroundColorBlue int, 
  BackGroundColorGreen int, 
  TitleBarColorRed int, 
  TitleBarColorBlue int, 
  TitleBarColorGreen int, 
  TitleBarFontColorRed int, 
  TitleBarFontColorBlue int, 
  TitleBarFontColorGreen int, 
  WindowTitleColorRed int, 
  WindowTitleColorBlue int, 
  WindowTitleColorGreen int, 
  WindowTitleFontColorRed int, 
  WindowTitleFontColorBlue int, 
  WindowTitleFontColorGreen int, 
  CursorColorRed int, 
  CursorColorBlue int, 
  CursorColorGreen int, 
  HeaderFontName varchar(50),
  HeaderFontSize int, 
  HeaderFontItalic smallint, 
  HeaderFontBold smallint, 
  RegularFontName varchar(50),
  RegularFontSize int, 
  RegularFontItalic smallint, 
  RegularFontBold smallint, 
  CopyAccess smallint, 
  PrintAccess smallint, 
  Password varchar(50)  
);

 
 
CREATE TABLE wkpCryptHistory (
  HistoryID int  NOT NULL PRIMARY KEY,
  PasswordID int ,
  a0 varchar(50),
  b9 varchar(50),
  c8 varchar(50),
  d7 varchar(50),
  e6 varchar(50),
  f5 varchar(50),
  g4 varchar(50),
  h3 varchar(50),
  i2 varchar(50),
  j1 varchar(50),
  k0 varchar(50),
  l9 varchar(50),
  m8 varchar(50),
  n7 varchar(50),
  o6 varchar(50),
  p5 varchar(50),
  q4 varchar(50),
  r3 varchar(50),
  s2 varchar(50),
  t1 varchar(50),
  Notes blob,
  Image blob,
  KeeperID int
);

 
 
CREATE TABLE wkpGroups (
  KeeperID int NOT NULL PRIMARY KEY,
  KeeperDesc varchar(50),
  keeperIcon varchar(50),
  CreateDate varchar(50),
  LastUpdate varchar(50),
  j1 varchar(50),
  PrntID int 
);



 
CREATE TABLE wkpPasswordCrypt (
  PasswordID int NOT NULL PRIMARY KEY,
  a0 varchar(50),
  b9 varchar(50),
  c8 varchar(50),
  d7 varchar(50),
  e6 varchar(50),
  f5 varchar(50),
  g4 varchar(50),
  h3 varchar(50),
  i2 varchar(50),
  j1 varchar(50),
  k0 varchar(50),
  l9 varchar(50),
  m8 varchar(50),
  n7 varchar(50),
  o6 varchar(50),
  p5 varchar(50),
  q4 varchar(50),
  r3 varchar(50),
  s2 varchar(50),
  t1 varchar(50),
  Notes blob,
  Image blob,
  KeeperID int 
);

CREATE TABLE  wkpPasswordAKin  (AKinID  int NOT NULL PRIMARY KEY,
   z0  varchar(50) default NULL,
   y1  varchar(50) default NULL,
   x2  varchar(50) default NULL,  
   w3  varchar(50) default NULL, 
   v4  varchar(50) default NULL, 
   u5  varchar(50) default NULL,
   t6  varchar(50) default NULL,  
   s7  varchar(50) default NULL,
   t8  varchar(50) default NULL, 
   r9  varchar(50) default NULL,
   PasswordID int); 