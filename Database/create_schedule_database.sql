use schedule;

/*Drop tables*/
drop table if exists scheduled;
drop table if exists blacklist;


drop table if exists venue_emp_rating;
drop table if exists salary;

drop table if exists request_off;
drop table if exists swap_request;
drop table if exists work_history;
drop table if exists venue;
drop table if exists employee;

/*Create tables*/
create table employee (
	employeeID varchar(10),
	fName varchar(15),
	lName varchar(15),
	password varchar(15),
	phone varchar(15),
	email varchar(25),
	isManager boolean,
	primary key(employeeID)
);

create table venue (
	venueID varchar(10),
	name varchar(20),
	tableNum integer,
	address varchar(50),
	primary key(venueID)
);

create table blacklist (
	employeeID varchar(10),
	venueID varchar(10),
	primary key(employeeID, venueID),
	foreign key(employeeID) references employee(employeeID) 
		on delete cascade 
		on update cascade,
	foreign key(venueID) references venue(venueID)
		on delete cascade
		on update cascade
);

create table scheduled (
	employeeID varchar(10),
    venueID varchar(10),
    primary key(employeeID, venueID),
    foreign key(employeeID) references employee(employeeID)
		on delete cascade
        on update cascade,
	foreign key(venueID) references venue(venueID)
		on delete cascade
		on update cascade
);
create table venue_emp_rating(
venueID varchar(40), employeeID varchar(12),rating int, primary key(venueID,employeeID, rating), 
foreign key(employeeID)
 references employee(employeeID) on delete cascade, foreign key(venueID) 
references venue(venueID) on delete  cascade);



create table salary(
    employeeID varchar(12),
    salary integer(12),
    primary key(employeeID),
    foreign key(employeeID) references employee(employeeID) on delete cascade);



create table work_history(
    employeeID varchar(12), 
    weekof date, 
    primary key(employeeID,weekof),
    mon varchar(10),
    tues varchar(10),
    wed varchar(10),
    thurs varchar(10),
    fri varchar(10),
    sat varchar(10),
    sun varchar(10),
    foreign key(employeeID) references employee(employeeID) on delete cascade, 
    foreign key(mon) references venue(venueID) on delete cascade,
    foreign key(tues) references venue(venueID) on delete cascade,
    foreign key(wed) references venue(venueID) on delete cascade,
    foreign key(thurs) references venue(venueID) on delete cascade,
    foreign key(fri) references venue(venueID) on delete cascade,
    foreign key(sat) references venue(venueID) on delete cascade,
    foreign key(sun) references venue(venueID) on delete cascade);

create index weekof on work_history(weekof);

create table request_off(
    employeeID varchar(12), 
    date_needed_off date, 
    primary key(employeeID, date_needed_off), 
    foreign key (employeeID) references employee(employeeID) on delete cascade,
    isApproved boolean, 
    approv_manager varchar(12), 
	ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    foreign key (approv_manager) references employee(employeeID)on delete cascade);

create table swap_request(
ID int NOT NULL AUTO_INCREMENT, Primary Key(ID),
employeeID varchar(12), foreign key(employeeID) references employee(employeeID) on delete cascade, 
weekOfRequest date, foreign key(weekOfRequest) references work_history(weekof), dayOf varchar(15),

 employee2 varchar(12) references employee(employeeID) on delete cascade, dayOf2 varchar(15),

employee2Approval boolean, managerApr boolean, approv_manager varchar(12),
ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 foreign key(approv_manager) references employee(employeeID) on delete cascade);
 
 
drop trigger if exists salarymaker;
delimiter //
CREATE trigger salarymaker after insert on employee
for each row
begin
insert into salary values(NEW.EmployeeID,6000);
end//

delimiter ;
 
 drop trigger if exists emp2check;
 DELIMITER ///
CREATE trigger emp2check before update on swap_request FOR EACH ROW
BEGIN
  
	If NEW.managerApr <> OLD.managerApr THEN
		If Old.Employee2 is null THEN
			SIGNAL SQLSTATE '45000' SET message_text= 'Other employee hasn''t approved the swap';
		End if;
         END IF;
END
///
DELIMITER ;