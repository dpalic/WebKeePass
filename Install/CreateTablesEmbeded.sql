
CREATE TABLE wkpSeqCrypt (
  TableName varchar(250),
  Seq int  
);
 

 
INSERT INTO wkpSeqCrypt (TableName,Seq) VALUES 
 ('wkpGroups',13),
 ('wkpPasswordCrypt',4),
 ('wkpCryptHistory',9);
  


CREATE TABLE jrBUnits (
  BUnit varchar(250) NOT NULL PRIMARY KEY ,
  BUnitDesc varchar(250),
  LastUpdate varchar(250)  
);

 
INSERT INTO jrBUnits (BUnit,BUnitDesc,LastUpdate) VALUES 
 ('1','WebKeePass Password Safe','20070101');
 

 
CREATE TABLE jrGroupAccess (
  GroupID varchar(250),
  MenuItem varchar(250),
  GroupItemDescription varchar(250),
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
  GroupID varchar(250) NOT NULL PRIMARY KEY,
  GroupDescription varchar(250),
  MenuXML varchar(250),
  DateFormat varchar(250),
  CreateDate varchar(250),
  ActiveDate varchar(250),
  InActiveDate varchar(250),
  LastChangeDate varchar(250),
  Notes varchar(500),
  ActiveGroup smallint, 
  Administrator smallint, 
  DeskTopTheme smallint, 
  TipAccess smallint, 
  CopyAccess smallint, 
  PrintAccess smallint, 
  AccessMethod varchar(250),
  BUnit varchar(250)  
);

 
INSERT INTO jrUserGroups (GroupID,GroupDescription,MenuXML,DateFormat,CreateDate,ActiveDate,InActiveDate,LastChangeDate,Notes,ActiveGroup,Administrator,DeskTopTheme,TipAccess,CopyAccess,PrintAccess,AccessMethod,BUnit) VALUES 
 ('Admins','Admin Group','CCMenu','dd MMM yyyy',NULL,'20070101','20070101','20070101','',1,1,1,1,1,1,'HTTP','1'),
 ('Users','Users Group','CCMenu','dd MMM yyyy',NULL,'20070101','20070101','20070101',NULL,1,0,1,1,1,1,'HTTP',NULL);
 

 
CREATE TABLE jrUsers (
  UserID varchar(250) NOT NULL PRIMARY KEY,
  GroupID varchar(250),
  UserDescription varchar(250),
  Name varchar(250),
  Address1 varchar(250),
  Address2 varchar(250),
  Address3 varchar(250),
  Address4 varchar(250),
  Phone1 varchar(250),
  Phone2 varchar(250),
  Fax varchar(250),
  Email varchar(250),
  WebSite varchar(250),
  Notes long varchar,
  CreateDate varchar(250),
  ActiveDate varchar(250),
  InActiveDate varchar(250),
  LastChangeDate varchar(250),
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
  HeaderFontName varchar(250),
  HeaderFontSize int, 
  HeaderFontItalic smallint, 
  HeaderFontBold smallint, 
  RegularFontName varchar(250),
  RegularFontSize int, 
  RegularFontItalic smallint, 
  RegularFontBold smallint, 
  CopyAccess smallint, 
  PrintAccess smallint, 
  Password varchar(250)  
);

 
 
CREATE TABLE wkpCryptHistory (
  HistoryID int  NOT NULL PRIMARY KEY,
  PasswordID int ,
  a0 varchar(250),
  b9 varchar(250),
  c8 varchar(250),
  d7 varchar(250),
  e6 varchar(250),
  f5 varchar(250),
  g4 varchar(250),
  h3 varchar(250),
  i2 varchar(250),
  j1 varchar(250),
  k0 varchar(250),
  l9 varchar(250),
  m8 varchar(250),
  n7 varchar(250),
  o6 varchar(250),
  p5 varchar(250),
  q4 varchar(250),
  r3 varchar(250),
  s2 varchar(250),
  t1 varchar(250),
  Notes blob,
  Image blob,
  KeeperID int
);

 
 
CREATE TABLE wkpGroups (
  KeeperID int NOT NULL PRIMARY KEY,
  KeeperDesc varchar(250),
  keeperIcon varchar(250),
  CreateDate varchar(250),
  LastUpdate varchar(250),
  j1 varchar(250),
  PrntID int 
);


INSERT INTO wkpGroups (KeeperID, KeeperDesc,keeperIcon,CreateDate,LastUpdate,j1,PrntID)  
			 VALUES (21, 'General', 'MNode1.gif','20070101','20070101','[Share-Group/]' ,0);
INSERT INTO wkpGroups (KeeperID, KeeperDesc,keeperIcon,CreateDate,LastUpdate,j1,PrntID)  
			 VALUES (22, 'Internet', 'MNode1.gif','20070101','20070101','[Share-Group/]' ,0);
INSERT INTO wkpGroups (KeeperID, KeeperDesc,keeperIcon,CreateDate,LastUpdate,j1,PrntID)  
			 VALUES (23, 'Email', 'MNode1.gif','20070101','20070101','[Share-Group/]' ,0);
INSERT INTO wkpGroups (KeeperID, KeeperDesc,keeperIcon,CreateDate,LastUpdate,j1,PrntID)  
			 VALUES (24, 'Home', 'MNode1.gif','20070101','20070101','[Share-Group/]' ,0);
INSERT INTO wkpGroups (KeeperID, KeeperDesc,keeperIcon,CreateDate,LastUpdate,j1,PrntID)  
			 VALUES (25, 'Home', 'MNode1.gif','20070101','20070101','[Share-Group/]' ,0);
INSERT INTO wkpGroups (KeeperID, KeeperDesc,keeperIcon,CreateDate,LastUpdate,j1,PrntID)  
			 VALUES (26, 'Other', 'MNode1.gif','20070101','20070101','[Share-Group/]' ,0);

 
CREATE TABLE wkpPasswordCrypt (
  PasswordID int NOT NULL PRIMARY KEY,
  a0 varchar(250),
  b9 varchar(250),
  c8 varchar(250),
  d7 varchar(250),
  e6 varchar(250),
  f5 varchar(250),
  g4 varchar(250),
  h3 varchar(250),
  i2 varchar(250),
  j1 varchar(250),
  k0 varchar(250),
  l9 varchar(250),
  m8 varchar(250),
  n7 varchar(250),
  o6 varchar(250),
  p5 varchar(250),
  q4 varchar(250),
  r3 varchar(250),
  s2 varchar(250),
  t1 varchar(250),
  Notes blob,
  Image blob,
  KeeperID int 
);

CREATE TABLE  wkpPasswordAKin  (AKinID  int NOT NULL PRIMARY KEY,
   z0  varchar(250) default NULL,
   y1  varchar(250) default NULL,
   x2  varchar(250) default NULL,  
   w3  varchar(250) default NULL, 
   v4  varchar(250) default NULL, 
   u5  varchar(250) default NULL,
   t6  varchar(250) default NULL,  
   s7  varchar(250) default NULL,
   t8  varchar(250) default NULL, 
   r9  varchar(250) default NULL,
   PasswordID int); 
