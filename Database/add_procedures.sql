use schedule;

drop procedure if exists GetTable;

drop procedure if exists GetEmployeeInfo;
drop procedure if exists AddEmployee;
drop procedure if exists UpdateEmployee;
drop procedure if exists UpdateManager;
drop procedure if exists SearchEmployee;
drop procedure if exists SearchEmployeeID;
drop procedure if exists RemoveEmployee;
drop procedure if exists viewEmployee;

drop procedure if exists GetVenueInfo;
drop procedure if exists AddVenue;
drop procedure if exists UpdateVenue;
drop procedure if exists SearchVenue;
drop procedure if exists SearchVenueID;
drop procedure if exists RemoveVenue;
drop procedure if exists compareSch;

drop procedure if exists AddBlacklisted;
drop procedure if exists SearchBlacklistedEmployee;
drop procedure if exists SearchBlacklistedVenue;
drop procedure if exists RemoveBlacklisted;

drop procedure if exists AddScheduled;
drop procedure if exists SearchScheduled;
drop procedure if exists SearchScheduledEmployee;
drop procedure if exists RemoveScheduled;

drop procedure if exists updateSalary;
drop procedure if exists createNewSchedule;
drop procedure if exists insertSchedule;
drop procedure if exists swapDays;
drop procedure if exists differenceInRatings;
drop function if exists difference;

delimiter //


create procedure compareSch(myDate date, in emp1 varchar(15), in emp2 varchar(15))
begin
set @s1 = 'select * from (select * from work_history where weekof=? and employeeID=?) as t union (select * from work_history where weekof=? and employeeID=?)' ;
prepare stmt from @s1;
set @myDate1 = myDate;
set @emp01 = emp1;
set @emp02 = emp2;

execute stmt using @myDate1, @emp01, @myDate1, @emp02;

deallocate prepare stmt;
end//

create function difference(x int, y int) returns int
begin
Declare z int;
set z = x-y;
return z;
end//



create procedure differenceInRatings()
begin 
declare myAvg int default 0;
declare n int default 0;
declare i int default 0;
set i=0;
drop temporary table if exists differenceRatingTable;
create temporary table differenceRatingTable( employeeID varchar(15),rating int);

select count(*) from venue_emp_rating into n;
select avg(rating) from venue_emp_rating  into myAvg;
while i<n do

	 select employeeID,rating into @ID,@rate From venue_emp_rating Limit i,1;
     set @Rating = difference(@rate,myAvg);
     insert into differenceRatingTable values(@ID,@Rating);
	  set i = i+1;
END while;
select * from differenceRatingTable;

end //



create procedure GetTable(
	in t_name varchar(15)
)
begin
	select * from t_name;
end//

/*Employee Procedures*/
create procedure GetEmployeeInfo(
	in f_name varchar(15),
	in l_name varchar(15),
	in t_column varchar(15)
)
begin
	select t_column from employee
		where f_name = fName
		and l_name = lName;
end//

create procedure AddEmployee(
	in e_id varchar(10),
	in fName varchar(15),
	in lName varchar(15),
	in password varchar(15),
	in phone varchar(15),
	in email varchar(25),
	in isManager boolean
)
begin
	insert into employee
		values(
			e_id, fName, lName, password, phone, email, isManager
		);
end//


/* Sets the variable values dependent on the ID of the swap request in the swap_request table
select employeeID, employee2, weekOfRequest, dayOf, dayOf2 into @emp1ID, @emp2ID, @weekOf, @day1, @day2 from swap_request where ID = 9;
*/
create procedure swapDays(
	in e_id1 varchar(15), in e_id2 varchar(15), in day1 varchar(15), in day2 varchar(15),
	in week1 date)

begin 

	set @s = concat('select ',day1, ' INTO @venue1 from work_history where employeeID=',e_id1,' and weekof=?');
	set @myDate = week1;
	prepare stmt from @s;


	execute stmt using @mydate;
	deallocate prepare stmt;


	set @s1=concat('select ',day2, ' INTO @venue2 from work_history where employeeID=',e_id2,' and weekof=?');
	set @myDate = week1;
	prepare stmt from @s1;
	execute stmt using @mydate;
	deallocate prepare stmt;

	set @s2=concat('update work_history set ',day1,'="' ,@venue2,'" where employeeID=',e_id1,' and weekof=?');
	prepare stmt from @s2;
	execute stmt using @mydate;



	set @s3=concat('update work_history set ',day2,'="' ,@venue1,'" where employeeID= ',e_id2,' and weekof=?');
	prepare stmt from @s3;
	execute stmt using @mydate;
	deallocate prepare stmt;

end//



create procedure UpdateEmployee(
	in e_column varchar(10),
	in e_value varchar(25),
	in e_id varchar(10)
)
begin
	update employee set e_column = e_value
		where e_id = employeeID;
end//

create procedure UpdateManager(
	in is_man boolean,
	in e_id varchar(10)
)
begin
	update employee set isManager = is_man
		where e_id = employeeID;
end//

create procedure SearchEmployee(
	in f_name varchar(15),
	in l_name varchar(15)
)
begin
	select * from employee
		where f_name = fName
		and l_name = lName;
end//

create procedure SearchEmployeeID(
	in eID varchar(10)
)
begin
	select * from employee
		where employeeID = eID;
end//

create procedure RemoveEmployee(
	in eID varchar(10)
)
begin
	delete from employee
		where employeeID = eID;
end//

/*Venue Procedures*/
create procedure GetVenueInfo(
	in v_name varchar(10),
	in t_column varchar(10)
)
begin
	select t_column from venue
		where v_name = name;
end//

create procedure AddVenue(
	in venueID varchar(10),
	in name varchar(20),
	in tableNum integer,
	in address varchar(50)
)
begin
	insert into venue
		values(
			venueID, name, tableNum, address
		);
end//

create procedure UpdateVenue(
	in v_column varchar(10),
	in v_value varchar(50),
	in v_id varchar(10)
)
begin
	update venue set v_column = v_value
		where v_id = venueID;
end//

create procedure SearchVenue(
	in v_name varchar(20)
)
begin
	select * from venue
		where v_name = name;
end//

create procedure SearchVenueID(
	in vID varchar(20)
)
begin
	select * from venue
		where venueID = vID;
end//

create procedure RemoveVenue(
	in vID varchar(10)
)
begin
	delete from venue
		where venueID = vID;
end//

/*Blacklisted Procedures*/
create procedure AddBlacklisted(
	in e_id varchar(10),
	in v_id varchar(10)
)
begin
	insert into blacklist values(
		e_id, v_id
	);
end//

create procedure SearchBlacklistedEmployee(
	in e_id varchar(10)
)
begin
	select venueID from blacklist
		where e_id = employeeID;
end//

create procedure SearchBlacklistedVenue(
	in v_id varchar(10)
)
begin
	select employeeID from blacklist
		where v_id = venueID;
end//

create procedure RemoveBlacklisted(
	in eID varchar(10),
    in vID varchar(10)
)
begin
	delete from blacklist
		where employeeID = eID
        and venueID = vID;
end//

/*Scheduled functions*/
CREATE PROCEDURE AddScheduled (
	in eID varchar(10),
    in vID varchar(10)
)
BEGIN
	insert into scheduled values(
		eID, vID
	);
END//

CREATE PROCEDURE SearchScheduledEmployee (
	in eID varchar(10)
)
BEGIN
	select venueID from scheduled
		where eId = employeeID;
END//

CREATE PROCEDURE RemoveScheduled (
	in eID varchar(10),
    in vID varchar(10)
)
BEGIN
	delete from scheduled
		where eID = employeeID
		and vID = VenueID;
END//

create procedure updateSalary (
	in ID varchar(15),
	in newSalary int)
begin
	update salary set salary = newSalary where employeeID = ID;
end //

create procedure createNewSchedule(
	in ID varchar(15),
	curDat date)
begin 
	insert into work_history(employeeID, weekof) values(ID,curDat);
end //


CREATE PROCEDURE insertSchedule(
	IN ID varchar(20),
	IN col VARCHAR(20),
	In ven varchar(20), 
	In curDat date)
BEGIN
	set SQL_SAFE_UPDATES =0;
 	SET @sql = CONCAT('update work_history set ', col, '=',ven,'  where employeeID= ',ID,' and  weekof=?;');
  	set @myDate = curDat;
  	PREPARE stmt FROM @sql;
  	EXECUTE stmt using @myDate;
  	DEALLOCATE PREPARE stmt;
  	set SQL_SAFE_UPDATES=1;
END//

create view employeeView as 
	select employeeID, fName,lName,phone, email from employee;

delimiter ;