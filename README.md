README

Automated Scheduling System

    An scheduling system that helps managers and employees by automating the schedule process
and storing all past and future schedules in a database. The software has a built in log in and log out ability 
and has multiple functions for both employees and venues. Functions include:
    Employee:
        Ability to trade shifts with other employees
            (Checks for the requestee and manager approval before swap is made)
        Ability to request off
            (Checks for the approval of a manager)
        Search Employees based on ID
        View the currently logged in employee's schedule and past work history.
    Venue:
        Search, update, add, and remove venues based on IDs
    Blacklisting:
        Blacklist specific employees and venues from being paired together in the scheduler.
    Manager:
        Has the ability to approve of employees requesting time off and employees trying to trade shifts
        View employee ratings that reflect their performance, as well as showing their deviation from the
        average rating across all employees
        Can update an employee's salary

Functions are divided into different menus. 
    
    The Manager menu is only visible by logged in users who are Managers.
This menu houses all functions that employees will not have access to, such as updating employees or venues,
viewing employee's ratings and adding / viewing employees blacklisted from a particular venue.
    The Employeee menu houses functions that all employees have access to, such as requesting time off,
requesting shift trades with other employees, and viewing their own current and past schedules.
    Venues is visible by both employees and managers and only houses the ability to search for particular venues.

In the Manager menu, the button Create schedule will create a schedule for the next week, as a 
schedule will have already been made for the current week. When the schedule is created it will be stored in the
work_history table within the database.

There is also an export to Excel button that will convert the current or future schedule into an Excel spreadsheet
for portability.


Location of certain features within the database for easy lookup:

    Primary Keys  create_schedule_database.sql line 26
    Foreign Key create_schedule_database.sql in line 87
        
    Queries
    
    Natural Join Database.java line 440
    Union & Nested Query & stored procedures add_procedures.sql line 42
    Groupby & Avg Viewer.java line 894
    Insert & prepared statement database.java line 1017

    View add_procedures.java line361
    Trigger create_schedule.sql line 120
    Transaction viewer.line268
    Function add_procedure.sql line 55
