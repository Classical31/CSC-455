README

Automated Scheduling System

	A scheduling system that helps managers and employees by automating the schedule process
Automated scheduling system with fully functional features and ability to store data in a database. The software has a built in log in and log out ability and has multiple functions for both employees and venues. Functions include:
	
	Employee
Ability to trade shifts with other employees
    (Checks for the requestee and manager approval before swap is made)
Ability to request off
    (Checks for the approval of a manager)
Search Employees based on ID
View the currently logged in employee's schedule and past work history.

	Venue
Search, update, add, and remove venues based on IDs

	Blacklisting
Blacklist specific employees and venues from being paired together in the scheduler.

	Manager
Has the ability to approve of employees requesting time off and employees trying to trade shifts
View employee ratings that reflect their performance, as well as showing their deviation from the
average rating across all employees
Can update an employee's salary

	Functions are divided into different menus. 
File, Employee, Venue, and Manager.

	File
Contains ability to View Schedule, Export to Excel, and Log Out.
    
	Manager
This menu is only visible by logged in users who are Managers. This menu houses all functions that employees will not have access to, such as updating employees or venues, viewing employee's ratings and adding / viewing employees blacklisted from a particular venue. In the Manager menu, the button Create schedule will create a schedule for the next week, as a schedule will have already been made for the current week. When the schedule is created it will be stored in the work_history table within the database.
    
    Employeee
Contains functions that all employees have access to, such as requesting time off, requesting shift trades with other employees, and viewing their own current and past schedules.
    
    Venue
Contains functions that are visible by both employees and managers. Houses the ability to search for particular venues.
