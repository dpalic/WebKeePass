
CREATE TABLE wkpSeqCrypt (
  TableName varchar(250) NOT NULL PRIMARY KEY,
  Seq int  
);
 

 
INSERT INTO wkpSeqCrypt (TableName,Seq) VALUES  ('wkpGroups',13);
INSERT INTO wkpSeqCrypt (TableName,Seq) VALUES  ('wkpPasswordCrypt',4);
INSERT INTO wkpSeqCrypt (TableName,Seq) VALUES  ('wkpCryptHistory',9);
  


CREATE TABLE jrBUnits (
  BUnit varchar(250) NOT NULL PRIMARY KEY ,
  BUnitDesc varchar(250),
  LastUpdate varchar(250)  
);

 
INSERT INTO jrBUnits (BUnit,BUnitDesc,LastUpdate) VALUES  ('1','WebKeePass Password Safe','20070101');
 

CREATE TABLE jrGroupAccess (
  GroupID varchar(250),
  MenuItem varchar(250),
  GroupItemDescription varchar(250),
  ItemAccessLevel integer,
  LastChangeDate varchar(20)  
);

 
INSERT INTO jrGroupAccess (GroupID,MenuItem,GroupItemDescription,ItemAccessLevel,LastChangeDate) VALUES 
 ('Users','WKP-MyPasswords','Work With My Passwords',3,NULL);
INSERT INTO jrGroupAccess (GroupID,MenuItem,GroupItemDescription,ItemAccessLevel,LastChangeDate) VALUES
 ('Users','UserAdmin130','List Web Users',1,NULL);
INSERT INTO jrGroupAccess (GroupID,MenuItem,GroupItemDescription,ItemAccessLevel,LastChangeDate) VALUES
 ('Users','UserAdmin110','Edit Web Users',1,NULL);
INSERT INTO jrGroupAccess (GroupID,MenuItem,GroupItemDescription,ItemAccessLevel,LastChangeDate) VALUES
 ('Users','UserAdmin120','List Web User Groups',1,NULL);
INSERT INTO jrGroupAccess (GroupID,MenuItem,GroupItemDescription,ItemAccessLevel,LastChangeDate) VALUES
 ('Users','UserAdmin100','Edit Web User Groups',1,NULL);
INSERT INTO jrGroupAccess (GroupID,MenuItem,GroupItemDescription,ItemAccessLevel,LastChangeDate) VALUES
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
  TimeOut int,
  Language varchar(100),
  Notes varchar(500),
  ActiveGroup integer, 
  Administrator integer, 
  DeskTopTheme integer, 
  TipAccess integer, 
  CopyAccess integer, 
  PrintAccess integer, 
  AccessMethod varchar(250),
  BUnit varchar(250)  
);

 

 
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
  Notes varchar(2000),
  CreateDate varchar(250),
  ActiveDate varchar(250),
  InActiveDate varchar(250),
  LastChangeDate varchar(250),
  ActiveUser integer, 
  BackGroundColorRed integer, 
  BackGroundColorBlue integer, 
  BackGroundColorGreen integer, 
  TitleBarColorRed integer, 
  TitleBarColorBlue integer, 
  TitleBarColorGreen integer, 
  TitleBarFontColorRed integer, 
  TitleBarFontColorBlue integer, 
  TitleBarFontColorGreen integer, 
  WindowTitleColorRed integer, 
  WindowTitleColorBlue integer, 
  WindowTitleColorGreen integer, 
  WindowTitleFontColorRed integer, 
  WindowTitleFontColorBlue integer, 
  WindowTitleFontColorGreen integer, 
  CursorColorRed integer, 
  CursorColorBlue integer, 
  CursorColorGreen integer, 
  HeaderFontName varchar(250),
  HeaderFontSize integer, 
  HeaderFontItalic integer, 
  HeaderFontBold integer, 
  RegularFontName varchar(250),
  RegularFontSize integer, 
  RegularFontItalic integer, 
  RegularFontBold integer, 
  CopyAccess integer, 
  PrintAccess integer, 
  Password varchar(250)  
);

 
 
CREATE TABLE wkpCryptHistory (
  HistoryID int  NOT NULL PRIMARY KEY,
  PasswordID  varchar(250),
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
			 VALUES (25, 'Home', 'MNode1.gif','20070101','20070101','[Share-Group/]' ,0);
INSERT INTO wkpGroups (KeeperID, KeeperDesc,keeperIcon,CreateDate,LastUpdate,j1,PrntID)  
			 VALUES (26, 'Other', 'MNode1.gif','20070101','20070101','[Share-Group/]' ,0);

 
CREATE TABLE wkpPasswordCrypt (
  PasswordID  varchar(250),
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
   z0  varchar(250),
   y1  varchar(250),
   x2  varchar(250),  
   w3  varchar(250), 
   v4  varchar(250), 
   u5  varchar(250),
   t6  varchar(250),  
   s7  varchar(250),
   t8  varchar(250), 
   r9  varchar(250),
   PasswordID varchar(250)
); 


CREATE TABLE wkpBundle (
  BundleID int  NOT NULL PRIMARY KEY,
  aa varchar(250),
  bb varchar(250),
  cc varchar(250),
  dd varchar(250),
  ee varchar(250),
  ff varchar(250),
  gg varchar(250),
  hh varchar(250)
 );
 
 
 
CREATE TABLE wkpAccess (
  AccessID int  NOT NULL PRIMARY KEY,
  aa varchar(250),
  bb varchar(250),
  cc varchar(250),
  dd varchar(250),
  ee varchar(250),
  ff varchar(250),
  gg varchar(250),
  hh varchar(250)
 );



CREATE TABLE documents (
   DocumentID  int NOT NULL PRIMARY KEY,
   DocumentKey  varchar(250),
   DocumentDesc  varchar(250),
   DocumentFile  varchar(250),
   DocumentPath  varchar(250),
   DateAdded  varchar(50),
   UserID  varchar(50)
);  
