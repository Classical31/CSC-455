use schedule;
select * from employee;
drop procedure if exists updateSalary;
drop procedure if exists createNewSchedule;
drop procedure if exists insertSchedule;
delimiter //

create procedure updateSalary (in ID varchar(15),in newSalary int)
begin
update salary set salary = newSalary where employeeID = ID;
end //

create procedure createNewSchedule(in ID varchar(15),curDat date)
begin 
insert into work_history(employeeID, weekof) values(ID,curDat);
end //
CREATE PROCEDURE insertSchedule(IN ID varchar(20),IN col VARCHAR(20),In ven varchar(20), In curDat date)
BEGIN
	set SQL_SAFE_UPDATES =0;
  SET @sql = CONCAT('update work_history set ', col, '=',ven,'  where employeeID= ',ID,' and  weekof=?;');
  set @myDate = curDat;
  PREPARE stmt FROM @sql;
  EXECUTE stmt using @myDate;
  DEALLOCATE PREPARE stmt;
  set SQL_SAFE_UPDATES=1;
END//

delimiter ;
