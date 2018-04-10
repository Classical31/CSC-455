use schedule;
create table venue_emp_rating(venueID varchar(40), employeeID varchar(12),rating varchar(12), primary key(venueID,employeeID, rating), foreign key(employeeID)
 references employee(employeeID) on delete cascade, foreign key(venueID) references venue(venueID) on delete  cascade);


create table salary(employeeID varchar(12),salary integer(12),primary key(employeeID,salary),foreign key(employeeID)references employee(employeeID) on delete cascade);
create table work_history(employeeID varchar(12), foreign key(employeeID) references employee(employeeID) on delete cascade,weekof date, primary key(employeeID,weekof),
mon varchar(20),tues varchar(20),wed varchar(20),thurs varchar(20),fri varchar(20),sat varchar(20),sun varchar(20), foreign key(mon,tues,wed,thurs,fri,sat,sun) references venue(venueID) on delete cascade);

create table request_off(employeeID varchar(12), date_needed_off date, primary key(employeeID, date_needed_off), foreign key (employeeID) references employee(employeeID) on delete cascade,
isApproved boolean, approv_manager varchar(12), foreign key (approv_manager) references employee(employeeID)on delete cascade);

create table swap_request(employeeID varchar(12), foreign key(employeeID) references employee(employeeID) on delete cascade, dateOfRequest date, dateSwap date,
location varchar(12),foreign key(location) references venue(venueID) on delete cascade, employee2 varchar(12) references employee(employeeID) on delete cascade,
employee2Approval boolean, managerApr boolean, approv_manager varchar(12), foreign key(approv_manager) references employee(employeeID) on delete cascade);


insert into work_history values('17128','2018-08-08','LA','EC','RB','RA','EX','RB','DD');
insert into work_history values('17038','2018-08-08','VI','TR','BD','EC','KB','OP','KB');
insert into work_history values('17094','2018-08-08','OP','FS','JP','VB','HB','SH','BD');
insert into work_history values('817086','2018-08-08','HR','HR','VB','DU','TR','DD','EC');
insert into work_history values('17005','2018-08-08','VB','TR','DD','RB','HR','BO','DD');
insert into work_history values('1713','2018-08-08','LA','BD','BD','LA','EX','SH','EC');
insert into work_history values('13881','2018-08-08','HR','BD','LA','RA','EX','VB','BD');
insert into work_history values('17057','2018-08-08','DD','BO','VI','TR','VB','SH','RA');



insert into salary values('17108',60780);
insert into salary values('17134',38082);
insert into salary values('817003',18400);
insert into salary values('17064',66283);
insert into salary values('817003',18858);
insert into salary values('917063',32030);
insert into salary values('17137',19771);
insert into salary values('817042',56734);


insert into venue_emp_rating values('OP','17129','10');
insert into venue_emp_rating values('HB','817042','6');
insert into venue_emp_rating values('LA','17005','9');
insert into venue_emp_rating values('BD','13881','7');
insert into venue_emp_rating values('HR','17038','5');
insert into venue_emp_rating values('JP','17142','5');
insert into venue_emp_rating values('RB','17094','8');
insert into venue_emp_rating values('OP','17100','6');

insert into request_off values('17130','2018-4-7',True,'17146');
insert into request_off values('17129','2018-3-9',True,'17005');
insert into request_off values('13881','2018-10-8',False,null);
insert into request_off values('917061','2018-9-11',False,null);
insert into request_off values('17094','2018-10-6',True,'17131');
insert into request_off values('17128','2018-3-9',False,null);
insert into request_off values('17134','2018-3-7',False,null);
insert into request_off values('817003','2018-4-9',True,'17035');


insert into swap_request values('17121','2018-4-6','2018-2-11','KB','17143',False,False,null);
insert into swap_request values('17000','2018-11-12','2018-2-12','BO','817003',False,False,null);
insert into swap_request values('17142','2018-8-11','2018-3-5','HR','13881',True,True,'17014');
insert into swap_request values('17038','2018-6-2','2018-5-7','SH','17057',True,False,null);
insert into swap_request values('13881','2018-5-2','2018-3-6','KB','17057',True,True,'17022');
insert into swap_request values('17129','2018-4-7','2018-2-10','BD','17088',False,True,'917061');
insert into swap_request values('17128','2018-10-11','2018-12-5','SH','917061',True,True,'917063');
insert into swap_request values('17128','2018-11-9','2018-9-7','RA','3433020220',True,False,null);
