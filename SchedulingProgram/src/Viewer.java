import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;


import javax.swing.*;

/**
 * Viewer class that implements JButton objects into a 2d Object array viewable
 * on a JTable. Objects are clickable to display information related to the
 * object in question. JButtons have their own instance so data can be
 * manipulated to display individualized data.
 * 
 *
 *
 */
public class Viewer extends JFrame implements ActionListener {
	private static JMenuItem addEmployee, removeEmployee, updateEmployee, searchEmployee, addVenue, removeVenue,
			updateVenue, searchVenue, saveFile, addBlacklisted, searchBlacklistedEmployee,
			createSchedule,updateSalary,requestOff,requestSwitch,viewRequestOff,approveRequestOff,approveSwap,viewSwap,viewCurrentSchedule,viewAllSchedules,logout,viewAverageRating,
			checkRatingDifference,compareSchedule, viewFullSchedule;
	
	JTextField whatToUpdateField, updateField, updateIDField; // = new JTextField(25);
	static JTextField addID, addFName, addLName, addPassword, addPhone, addEmail, addVenAddress, addVenName, addVenTables,
			addEID, addVID;
	static JMenuBar menuBar;
	static JMenu empMenu, venMenu, fileMenu, blackListMenu,managerMenu, manEmpMenu, manVenMenu, manRatingMenu, empRequestMenu,empScheduleMenu,manBlackListMenu;
	static DefaultTableModel tableModel, model;
	static Scheduler generateSchedule = new Scheduler();
	static ArrayList<Event> scheduler;
	static Object[][] data;
	static String inputID;
	static Employee employee;
	static ArrayList<Employee> empList;
	static Boolean isManager2;
	static String employeeIDLoggedIn;
	static JPanel myPanel;

	static Object[] empInfo, venInfo, blackInfo;

	static JTable table;

	/**
	 * 
	 * Constructor that creates the JMenu and adds the proper menu selection based on if they are a manager or not
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	public Viewer(Boolean isManager,String loggedInID) {
		// FORM TITLE
		super("Logged In:" + loggedInID);
		
		employeeIDLoggedIn = loggedInID;
		isManager2 = isManager;
		menuBar = new JMenuBar();
		empMenu = new JMenu("Employees");
		venMenu = new JMenu("Venues");
		fileMenu = new JMenu("File");
		blackListMenu = new JMenu("Blacklist");
		managerMenu = new JMenu("Manager");
		manEmpMenu = new JMenu("Employee Functions");
		manVenMenu = new JMenu("Venue Functions");
		manRatingMenu = new JMenu("Employee Ratings");
		manBlackListMenu = new JMenu("Blacklist Functions");
		
		empRequestMenu = new JMenu("Requests");
		empScheduleMenu = new JMenu("Schedule");
		
		saveFile = new JMenuItem("Export to Excel");
		logout = new JMenuItem("Log out");
		
		myPanel = new JPanel();
		

		// Employee Menu Items
		viewFullSchedule = new JMenuItem("View Full Schedule");
		requestOff = new JMenuItem("Request Off");
		requestSwitch = new JMenuItem("Request a Trade Shift");
		viewRequestOff = new JMenuItem("View Request Off");
		approveSwap = new JMenuItem("Approve Traded Shifts");
		approveRequestOff = new JMenuItem("Approve Request Off");
		viewSwap = new JMenuItem("View Traded Shifts");
		viewCurrentSchedule = new JMenuItem("View Current Schedule");
		viewAllSchedules = new JMenuItem("View Past Schedules");
		compareSchedule = new JMenuItem("Compare Schedules");

		// Venue Menu Items


		// Blacklist Menu Items
		addBlacklisted = new JMenuItem("Add Blacklisted Employee");
		searchBlacklistedEmployee = new JMenuItem("View Blacklisted Employees");

		// Add File Menu Items to the File Menu
		fileMenu.add(viewFullSchedule);
		fileMenu.add(saveFile);
		fileMenu.add(logout);

		//Manager functions
		createSchedule = new JMenuItem("Create Schedule");
		updateSalary = new JMenuItem("Update Salary");
		viewAverageRating  = new JMenuItem("View Average Rating");
		checkRatingDifference = new JMenuItem("View Difference in Ratings");
		
		// Manager Menu Items
		
		addEmployee = new JMenuItem("Add Employee");
		removeEmployee = new JMenuItem("Remove Employee");
		updateEmployee = new JMenuItem("Update Employee");
		searchEmployee = new JMenuItem("Search Employee");
		
		addVenue = new JMenuItem("Add Venue");
		removeVenue = new JMenuItem("Remove Venue");
		updateVenue = new JMenuItem("Update Venue");
		searchVenue = new JMenuItem("Search Venue");
		
		// Add Employee Menu Items to the Employee Menu
		empMenu.add(searchEmployee);
		empRequestMenu.add(requestOff);
		empRequestMenu.add(requestSwitch);
		empRequestMenu.add(viewRequestOff);
		empRequestMenu.add(approveSwap);
		empRequestMenu.add(viewSwap);
		empRequestMenu.add(approveRequestOff);
		
		empScheduleMenu.add(viewCurrentSchedule);
		empScheduleMenu.add(viewAllSchedules);
		empScheduleMenu.add(compareSchedule);

		//Manager Menu Items
		
		blackListMenu.add(addBlacklisted);
		blackListMenu.add(searchBlacklistedEmployee);

		managerMenu.add(createSchedule);
		manEmpMenu.add(updateSalary);
		
		manRatingMenu.add(viewAverageRating);
		manRatingMenu.add(checkRatingDifference);
		
		manEmpMenu.add(approveRequestOff);
		
		manBlackListMenu.add(blackListMenu);
		
		managerMenu.add(manEmpMenu);
		managerMenu.add(manVenMenu);
		managerMenu.add(manRatingMenu);
		managerMenu.add(blackListMenu);
		
		// Add Venue Menu Items to the Employee Menu
		venMenu.add(searchVenue);
		empMenu.add(empScheduleMenu);
		empMenu.add(empRequestMenu);
		menuBar.add(fileMenu);
		menuBar.add(empMenu);
		menuBar.add(venMenu);
		
		if (isManager){
			menuBar.add(managerMenu);
			
			manVenMenu.add(updateVenue);
			manVenMenu.add(addVenue);
			manVenMenu.add(removeVenue);
			
			manEmpMenu.add(updateEmployee);
			manEmpMenu.add(addEmployee);
			manEmpMenu.add(removeEmployee);
			
			
			
		}
		saveFile.addActionListener(this);
		logout.addActionListener(this);
		addEmployee.addActionListener(this);
		removeEmployee.addActionListener(this);
		updateEmployee.addActionListener(this);
		searchEmployee.addActionListener(this);
		requestOff.addActionListener(this);
		requestSwitch.addActionListener(this);
		viewFullSchedule.addActionListener(this);

		addVenue.addActionListener(this);
		removeVenue.addActionListener(this);
		updateVenue.addActionListener(this);
		searchVenue.addActionListener(this);

		addBlacklisted.addActionListener(this);
		updateSalary.addActionListener(this);
		searchBlacklistedEmployee.addActionListener(this);
		createSchedule.addActionListener(this);
		viewRequestOff.addActionListener(this);
		approveRequestOff.addActionListener(this);
		approveSwap.addActionListener(this);
		viewSwap.addActionListener(this);
		viewCurrentSchedule.addActionListener(this);
		viewAllSchedules.addActionListener(this);
		viewAverageRating.addActionListener(this);
		checkRatingDifference.addActionListener(this);
		compareSchedule.addActionListener(this);
		setJMenuBar(menuBar);
		
		
	}
	public void putTableInFrame(JTable myTable2,JPanel myPanel){
		myPanel.removeAll();
		myTable2.setVisible(true);
		myTable2.setSize(500,500);
		myPanel.add(new JScrollPane(myTable2));
		
		myPanel.setVisible(true);
		myPanel.setSize(900, 900);
		myPanel.revalidate();
		
		add(myPanel,BorderLayout.CENTER);
		repaint();
		revalidate();
	}
	
	//Gets the  date of next Sunday in the month
	public static LocalDate getNextWeekStart(){
		LocalDate today =LocalDate.now();
		LocalDate sunday = today;
		 while (sunday.getDayOfWeek() != DayOfWeek.SUNDAY) {
		      sunday = sunday.plusDays(1);
		    }
		 return sunday;
	}
	
	//Gets the Date of the current week's Sunday
	public static LocalDate getWeekStart(){
		LocalDate today =LocalDate.now();
		LocalDate sunday = today;
		 while (sunday.getDayOfWeek() != DayOfWeek.SUNDAY) {
		      sunday = sunday.minusDays(1);
		    }
		 return sunday;
	}
	

	public  JPanel doSchedule() throws Exception{
		TableColumn columnModel;
		
		myPanel.removeAll();
		Object[] colDays = { "Name", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
		ArrayList<ArrayList<Event>> schedulerList = new ArrayList<ArrayList<Event>>();

		empList = new ArrayList<Employee>();

		// Get employee data and stores into a list. Sets look and feel to
		// SeaGlass. This can be changed if we don't like it.
		try {
			empList = SchedulerDatabaseUtils.getEmployees();
			

		} catch (Exception e) {
			// If SeaGlass is not available, you can set the GUI to another look
			// and feel.
		}
		
		data = new Object[empList.size()][colDays.length];
		// Creates an Event List called schedulerList and calls generateWeek from the Scheduler class that makes a list of matched venues and employees.
		schedulerList = generateSchedule.generateWeek(schedulerList,colDays);
		
		
		
		// Creates the schedule by calling fillSchedule from the Scheduler class
		data = generateSchedule.fillSchedule(empList, colDays, schedulerList, data);
		LocalDate weekof = getNextWeekStart();
		
		String[] days = {"","sun","mon","tues","wed","thurs","fri","sat"};
		try{
			SchedulerDatabaseUtils.connect();
			
			(SchedulerDatabaseUtils.getConnection()).setAutoCommit(false);
		for(int i =0; i<data.length;i++){
			for(int j=0;j<data[i].length;j++){
				Employee id =  (Employee) data[i][0];
				
				if (j==0){
					SchedulerDatabaseUtils.createScheduleInDB(id.getId(), weekof);
					
				}
				else{

					String myString = "'"+data[i][j]+"'";
					if (myString.equals("''")){
						
						myString = "'#'";
					}

					SchedulerDatabaseUtils.insertIntoSchedule(id.getId(), days[j], myString, weekof);
				}
				
			}
		}
		}
		catch(Exception e){
			System.out.println(e.toString());
			SchedulerDatabaseUtils.getConnection().rollback();
		}
		SchedulerDatabaseUtils.getConnection().commit();
		SchedulerDatabaseUtils.close();
		
		model = new DefaultTableModel(data, colDays);
		table = new JTable(model);
		// create new table with an overriden tooltip
		HashMap<String, Venue> venueDict = new HashMap<String, Venue>();
		try {
			for (Venue venue : SchedulerDatabaseUtils.getVenues()) {
				venueDict.put(venue.getID(), venue);
			}
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		table = new JTable(data, colDays) {

			// Implement table cell tool tips.
			public String getToolTipText(MouseEvent e) {
				java.awt.Point p = e.getPoint();
				String tip = null;
				int row = rowAtPoint(p);
				int column = columnAtPoint(p);

				try {
					// comment row, exclude heading
					if (row > 0 && column > 0) {
						String venueId = data[row][column].toString();
						Venue venue = venueDict.get(venueId);
						tip = "<html>" + venue.getName() + "<br>Tables: " + venue.getTables() + "</html>";
					}
				} catch (RuntimeException e1) {
					// catch null pointer exception if mouse is over an empty
					// line
				}

				return tip;
			}
		};

		// Table resizing
		table.setAutoResizeMode(table.AUTO_RESIZE_OFF);
		columnModel = table.getColumnModel().getColumn(0);
		columnModel.setPreferredWidth(180);

		for (int i = 1; i < colDays.length; i++) {
			table.setRowHeight(20);
			columnModel = table.getColumnModel().getColumn(i);
			columnModel.setPreferredWidth(150);
		}

		// Sets a particular Column as JButtons
		table.getColumnModel().getColumn(0).setCellRenderer(new ButtonRenderer());

		// SET CUSTOM EDITOR TO TEAMS COLUMN

		table.getColumnModel().getColumn(0).setCellEditor(new ButtonEditor(new JTextField()));

		// SCROLLPANE,SET SZE,SET CLOSE OPERATION
		JScrollPane pane = new JScrollPane(table);

		// Adds the JTable with the Scroll pane to a Panel so Menus can be
		// displayed on the Frame
		myPanel.setLayout(new BorderLayout());
		myPanel.add(pane, BorderLayout.CENTER);

		table.repaint();
		// Frame settings
		setSize(1280, 720);
		setLocation(800, 300);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);

		// Adding different Swing components to the Frame
		
		//add(panel, BorderLayout.CENTER);
		return myPanel;
	}
	


	/**
	 * Overrides actionPerformed to allow easier action listeners for all menu
	 * items.
	 * 
	 * @param menuItem
	 *            Menu Item that is passed and retrieves a specific function
	 *            call if the Menu Item equals a some JMenuItem.
	 */
	@Override
	public void actionPerformed(ActionEvent menuItem) {
		Venue venue = null;
		Employee employee = null;

		// Actions for File Menu Items


		if (menuItem.getSource().equals(logout)){
			Login lg = new Login();
			lg.setVisible(true);
			dispose();
		}
		if (menuItem.getSource().equals(saveFile)) {
			String fileName = JOptionPane.showInputDialog("File name:");
			ExcelExporter ee = new ExcelExporter(fileName);
			ee.addSheet("Schedule", ExcelExporter.scheduleHeaders, data);
			ee.finish();
			JOptionPane.showMessageDialog(null, "File " + fileName + " successfully saved!");
		}

		// Actions for Employee Menu Items
		
		if(menuItem.getSource().equals(viewSwap)){
			
			JTable myTable2 = new JTable();
			String mySQLString = "";
			try {
				if (isManager2){
					mySQLString = "select * from swap_request";
				}
				else{
					mySQLString="select * from swap_request where employeeID="+"'"+employeeIDLoggedIn +"'";
				}
				
				//System.out.println(myTable2.getSelectedRows().toString());
				SchedulerDatabaseUtils.fillAllTables(myTable2,mySQLString);
				putTableInFrame(myTable2,myPanel);
				
			
		}
			catch(Exception e){
				e.printStackTrace();
			}
			finally{
		
			}
		}
		if(menuItem.getSource().equals(checkRatingDifference)){
			JTable myTable2 = new JTable();
			try{
				
				SchedulerDatabaseUtils.fillAllTables(myTable2, "call differenceInRatings()");
				putTableInFrame(myTable2,myPanel);
				
			}catch(Exception e){
					
				}
			
		}
		if(menuItem.getSource().equals(compareSchedule)){
			JTable myTable2 = new JTable();
			
			JTextField requestDate = new JTextField(15);
			JTextField compareID = new JTextField(15);

			Object[] message={
					"Enter the weekof you want to compare", requestDate,
					"Enter the ID you want to compare to", compareID
			};
			
			JOptionPane.showConfirmDialog(null,message);
			
			SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd");
			java.util.Date date;
			try {
				date = format.parse(requestDate.getText());
				java.sql.Date sqlRequestDate = new java.sql.Date(date.getTime());
				System.out.println(sqlRequestDate);
				String myString ="call compareSch('"+sqlRequestDate+"','"+compareID.getText()+"','"+employeeIDLoggedIn+"')";
				//System.out.println(myString);
				SchedulerDatabaseUtils.fillAllTables(myTable2,myString);
				putTableInFrame(myTable2,myPanel);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}
		
		if(menuItem.getSource().equals(viewFullSchedule)){
			JTable myTable2 = new JTable();
			String mySQLString = "";
			
			mySQLString="select * from work_history where weekof = '" + getWeekStart() +"';";
			
			try {
				SchedulerDatabaseUtils.fillAllTables(myTable2, mySQLString);
				putTableInFrame(myTable2,myPanel);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(menuItem.getSource().equals(viewRequestOff)){
			
			
			JTable myTable2 = new JTable();
			String mySQLString = "";
			try {
				if (isManager2){
					mySQLString = "select * from request_off";
				}
				else{
					mySQLString="select * from request_off where employeeID="+"'"+employeeIDLoggedIn +"'";
				}
				
				//System.out.println(myTable2.getSelectedRows().toString());
				SchedulerDatabaseUtils.fillAllTables(myTable2,mySQLString);
				putTableInFrame(myTable2,myPanel);
				
				
				//revalidate();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(menuItem.getSource().equals(viewCurrentSchedule)){
			LocalDate weekStart =getWeekStart();
			JTable myTable2 = new JTable();
			try {
				SchedulerDatabaseUtils.fillAllTables(myTable2, "Select * from work_history where EmployeeID='" + employeeIDLoggedIn+"' and weekof='" + weekStart+"';");
				putTableInFrame(myTable2,myPanel);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(menuItem.getSource().equals(viewAllSchedules)){
			LocalDate weekStart =getWeekStart();
			JTable myTable2 = new JTable();
			myPanel.removeAll();
			try {
				SchedulerDatabaseUtils.fillAllTables(myTable2, "Select * from work_history where EmployeeID='" + employeeIDLoggedIn+"';");
				putTableInFrame(myTable2,myPanel);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(menuItem.getSource().equals(requestSwitch)){
			//JTextField requestID = new JTextField(15);
			JTextField date_needed = new JTextField(15);
			String[] daysOfTheWeek = {"mon","tues","wed","thurs","fri","sat","sun"};
			@SuppressWarnings("unchecked")
			JComboBox<String> dayOfWeek = new JComboBox(daysOfTheWeek);
			JComboBox<String> dayOfWeek2 = new JComboBox(daysOfTheWeek);
			
			JTextField venueID = new JTextField(15);
			JTextField employee2ID = new JTextField(15);
			
			Object[] message ={
					"Enter the week Of the needed swap", date_needed,
					"Day of the Week your shift is you want to switch",dayOfWeek,
					"employee you want to switch with",employee2ID,
					"Day of the week their shift is ",dayOfWeek2
			};
			
			int option = JOptionPane.showConfirmDialog(null,
			message, "request swap", JOptionPane.OK_CANCEL_OPTION);
			
			if(option == JOptionPane.OK_OPTION){
				try {
					SchedulerDatabaseUtils.addRequestSwap(employeeIDLoggedIn, date_needed.getText(), dayOfWeek.getSelectedItem().toString(), employee2ID.getText(), dayOfWeek2.getSelectedItem().toString());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else{
				
			}
		}
		if (menuItem.getSource().equals(approveSwap)){
			JTextField requestID = new JTextField();
			Object[] message={
					"Enter the ID of the Swap:",requestID
			};
			int option = JOptionPane.showConfirmDialog(null,
					message, "request swap", JOptionPane.YES_NO_CANCEL_OPTION);
			
			if(option == JOptionPane.OK_OPTION){
				try {
					SchedulerDatabaseUtils.approveSwapRequest(Integer.parseInt(requestID.getText()), employeeIDLoggedIn, isManager2, true);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(option == JOptionPane.NO_OPTION){
				try{
					SchedulerDatabaseUtils.approveSwapRequest(Integer.parseInt(requestID.getText()), employeeIDLoggedIn, isManager2, false);
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
			else{
				
			}
		}
		
		if(menuItem.getSource().equals(approveRequestOff)){
			JTextField requestID = new JTextField(15);
			JTextField date_needed = new JTextField(15);
			
		Object[] message ={
					"Enter The ID of the employee:",requestID,
					"Enter The date they needed",date_needed
				};
			
		int option = JOptionPane.showConfirmDialog(null,
		message,"request off", JOptionPane.YES_NO_CANCEL_OPTION);
		
		if(option == JOptionPane.YES_OPTION){
			try {
				SchedulerDatabaseUtils.updateRequestOff(requestID.getText(),date_needed.getText(),true,employeeIDLoggedIn);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(option == JOptionPane.NO_OPTION){
			try {
				SchedulerDatabaseUtils.updateRequestOff(requestID.getText(),date_needed.getText(),false,employeeIDLoggedIn);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		}
		else{
			
		}
		
		
		if (menuItem.getSource().equals(requestOff)){
			
			JTextField date_needed = new JTextField(30);
			Object[] message ={
				"Enter your date needed",date_needed
			};
			int option = JOptionPane.showConfirmDialog(null,
					 message,"request off", JOptionPane.OK_CANCEL_OPTION);		
			
			
			if (option == JOptionPane.OK_OPTION){
				try {
					SchedulerDatabaseUtils.addRequestOff(employeeIDLoggedIn, date_needed.getText());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//empID
			//Date_needed_off
			//isapproved
			//approv_manager
		}
		
		if (menuItem.getSource().equals(searchEmployee)) {
			
			/*
			 * Input some check for manager to distinguish different views in database?
			 */


				inputID = JOptionPane.showInputDialog("Enter a Employee ID: ");
				
				try {
					SchedulerDatabaseUtils.searchEmployeeID(Login.isManager,inputID,employeeIDLoggedIn);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		}
		if (menuItem.getSource().equals(updateSalary)){		
			try {
				Boolean nextStep =true;
				JTextField enterID = new JTextField(15);
				Object[] message ={
						"Enter the ID of the employee you would like to update",enterID
				};
				do {
				int option=JOptionPane.showConfirmDialog(null,
						 message,"update salary", JOptionPane.OK_CANCEL_OPTION);	
				if (option==JOptionPane.CANCEL_OPTION){
					nextStep = false;
					break;
					
				
				}
				}while((!SchedulerDatabaseUtils.validateUserID(enterID.getText())) || enterID.getText().equals(employeeIDLoggedIn));
				System.out.println(enterID.getText().toString());
				JTextField enterNewSalary = new JTextField(15);
				if (nextStep){
					int currentSalary = SchedulerDatabaseUtils.getCurrentSalary(enterID.getText());
					Object message2 [] ={
							"employee ID" + enterID.getText() + "currently makes:" + Integer.toString(currentSalary),
							enterNewSalary
					};
					
					do{
						int option2 =JOptionPane.showConfirmDialog(null, message2);
						if (option2==JOptionPane.CANCEL_OPTION){
							nextStep = false;
							break;
							
						
						}
					}while(! enterNewSalary.getText().matches("[0-9]+"));
				}
				if (nextStep){
				SchedulerDatabaseUtils.updateSalary(enterID.getText(),Integer.parseInt(enterNewSalary.getText()));
				}
				
					
					
					
					
					
					/*
					while (!validInput){
					if (newSalary.matches("[0-9]+")){
						validInput=true;
						
					}
					else{
						
					}
					
					}
					
					SchedulerDatabaseUtils.updateSalary(inputID,Integer.parseInt(newSalary));
				*/
				}
					
				
				
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}
		

		if (menuItem.getSource().equals(addEmployee)) {
			addID = new JTextField(10);
			addFName = new JTextField(15);
			addLName = new JTextField(15);
			addPassword = new JTextField(15);
			addPhone = new JTextField(15);
			addEmail = new JTextField(25);

			Object[] empInfo = { "User ID: ", addID, "First Name: ", addFName, "Last Name: ", addLName,
					"Choose a Password: ", addPassword, "Phone Number: ", addPhone, "Email: ", addEmail };
			createEmployeeInfoBox(empInfo, "Add a New Employee");
			JOptionPane.showMessageDialog(null,
					"ID: " + addID.getText() + "\n" + "Name: " + addFName.getText() + " " + addLName.getText() + "\n"
							+ "Phone: " + addPhone.getText() + "\n" + "Email: " + addEmail.getText(),
					"New Employee Added", JOptionPane.INFORMATION_MESSAGE);
		}

		if (menuItem.getSource().equals(removeEmployee)) {
			inputID = JOptionPane.showInputDialog("Enter the Employee ID for the employee you wish to remove: ");
			try {
				employee = SchedulerDatabaseUtils.searchEmployeeID(inputID);
				SchedulerDatabaseUtils.removeEmployee(inputID);
				JOptionPane.showMessageDialog(null, "Employee " + employee.getFullName() + " has been removed.",
						"Removed employee", JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		
		if (menuItem.getSource().equals(updateEmployee)) {
			inputID = JOptionPane.showInputDialog("ID for the Employee you would like to update: ");
			try {
				employee = SchedulerDatabaseUtils.searchEmployeeID(inputID);
				if(employee != null){
					addFName = new JTextField(employee.getFirstName());
					addLName = new JTextField(employee.getLastName());
					addPassword = new JTextField(employee.getPassword());
					addPhone = new JTextField(employee.getPhone());
					addEmail = new JTextField(employee.getEmail());
					
					Object[] empInfo = { "First Name: ", addFName, "Last Name: ", addLName, "Choose a Password: ", addPassword,
							"Phone Number: ", addPhone, "Email: ", addEmail };
					updateEmployeeInfoBox(empInfo, "Update Employee Information");
					JOptionPane.showMessageDialog(null,
							"ID: " + inputID + "\n" + "Name: " + addFName.getText() + " " + addLName.getText() + "\n"
									+ "Phone: " + addPhone.getText() + "\n" + "Email: " + addEmail.getText(),
							"Employee Updated", JOptionPane.INFORMATION_MESSAGE);					
				}else{
					JOptionPane.showMessageDialog(null, "No Employee with that ID was found", "Invaild ID",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (Exception NullPointer) {
				// TODO Auto-generated catch block
			}
			


		}

		// Actions for Venue Menu Items
		if (menuItem.getSource().equals(searchVenue)) {
			inputID = JOptionPane.showInputDialog("Enter a Venue ID Name: ");

			try {
				venue = SchedulerDatabaseUtils.searchVenueID(inputID);
				if (venue == null) {
					JOptionPane.showMessageDialog(null, "No venue with that ID was found", "Invaild ID",
							JOptionPane.INFORMATION_MESSAGE);
					System.out.println("Invalid ID");
				} else {
					JOptionPane.showMessageDialog(null,
							"ID: " + venue.getID().toUpperCase() + "\n" + "Name: " + venue.getName() + "\n"
									+ "Table Amount: " + venue.getTables() + "\n" + "Address: " + venue.getAddress(),
							"Venue Info", JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (menuItem.getSource().equals(addVenue)) {
			addID = new JTextField(10);
			addVenName = new JTextField(15);
			addVenTables = new JTextField(15);
			addVenAddress = new JTextField(25);

			Object[] venInfo = { "Venue ID: ", addID, "Venue Name: ", addVenName, "Table Amount: ", addVenTables,
					"Address: ", addVenAddress };
			createVenueInfoBox(venInfo, "Add a New Venue");
			JOptionPane.showMessageDialog(null,
					"ID: " + addID.getText() + "\n" + "Name: " + addVenName.getText() + "\n" + "Tables: "
							+ addVenTables.getText() + "\n" + "Address: " + addVenAddress.getText(),
					"New Venue Added", JOptionPane.INFORMATION_MESSAGE);

		}

		if (menuItem.getSource().equals(removeVenue)) {
			inputID = JOptionPane.showInputDialog("Enter a Venue ID: ");
			try {
				venue = SchedulerDatabaseUtils.searchVenueID(inputID);
				if (venue == null) {
					JOptionPane.showMessageDialog(null, "No venue with that ID was found", "Invaild ID",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					SchedulerDatabaseUtils.removeVenue(inputID);
					JOptionPane.showMessageDialog(null, "Venue " + venue.getName() + " has been removed.", "Venue Info",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (menuItem.getSource().equals(updateVenue)) {
			inputID = JOptionPane.showInputDialog("ID for the Venue you would like to update: ");

			try {
				venue = SchedulerDatabaseUtils.searchVenueID(inputID);
				addVenName = new JTextField(venue.getName());
				addVenTables = new JTextField(String.valueOf(venue.getTables()));
				addVenAddress = new JTextField(venue.getAddress());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Object[] venInfo = { "Venue Name: ", addVenName, "Table Amount: ", addVenTables, "Address: ",
					addVenAddress };

			updateVenueInfoBox(venInfo, "Update Venue Information");
			JOptionPane.showMessageDialog(null,
					"ID: " + inputID + "\n" + "Name: " + addVenName.getText() + "\n" + "Table Amount: "
							+ addVenTables.getText() + "\n" + "Address: " + addVenAddress.getText(),
					"Venue Updated", JOptionPane.INFORMATION_MESSAGE);

		}
		
		if(menuItem.getSource().equals(viewAverageRating)){
			JTable mytable = new JTable();
			try {
				SchedulerDatabaseUtils.fillAllTables(mytable, "SELECT employeeID, AVG(rating) as Avg_Rating  FROM venue_emp_rating GROUP BY employeeID;");
				putTableInFrame(mytable,myPanel);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// Actions for Blacklisting Employees
		if (menuItem.getSource().equals(addBlacklisted)) {
			addEID = new JTextField(10);
			addVID = new JTextField(10);

			Object[] blackInfo = { "Employee ID: ", addEID, "Venue ID: ", addVID };
			createBlacklistInfoBox(blackInfo, "Add a New Blacklist");
		}

		if (menuItem.getSource().equals(searchBlacklistedEmployee)) {
			JTable myTable2 = new JTable();
			try {
				SchedulerDatabaseUtils.fillAllTables(myTable2, "select * from blacklist");
				putTableInFrame(myTable2,myPanel);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		/*schedule generator
		 * function and loads the information into a 2D Object array. This data
		 * array is then loaded into a a JTable. It calls the Overridden classes for
		 * editing cells in order to make employee names on the left hand side
		 * Intractable.
		 * */
		if (menuItem.getSource().equals(createSchedule)){
			try {
				add(doSchedule());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	

	/**
	 * 
	 * @param empInfo
	 *            Loads in an object array for a popup window with editable text
	 *            fields in order to create a new Employee employee.
	 * @param boxTitle
	 *            Title of the new popup window.
	 */
	public void createEmployeeInfoBox(Object[] empInfo, String boxTitle) {
		try {
			JOptionPane.showConfirmDialog(null, empInfo, boxTitle, JOptionPane.OK_CANCEL_OPTION);
			SchedulerDatabaseUtils.addEmployee(addID.getText(), addFName.getText(), addLName.getText(), addPassword.getText(),
					addPhone.getText(), addEmail.getText(), false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"I'm sorry, something went wrong. Please try to add the employee again!",
					"Add Employee Error", JOptionPane.INFORMATION_MESSAGE);
		}

	}

	/**
	 * 
	 * @param empInfo
	 *            Loads in an object array for a popup window with editable text
	 *            fields in order to update an employee.
	 * @param boxTitle
	 */
	public void updateEmployeeInfoBox(Object[] empInfo, String boxTitle) {
		try {
			JOptionPane.showConfirmDialog(null, empInfo, boxTitle, JOptionPane.OK_CANCEL_OPTION);
			SchedulerDatabaseUtils.removeEmployee(inputID);
			SchedulerDatabaseUtils.addEmployee(inputID, addFName.getText(), addLName.getText(), addPassword.getText(),
					addPhone.getText(), addEmail.getText(), false);
			JOptionPane.showMessageDialog(null,
					"Successfully updated " + addFName.getText() + " " + addLName.getText() + " in the database!",
					"Updated Employee Successfully", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"I'm sorry, something went wrong. Please try to update the employee again!",
					"Update Employee Error", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * 
	 * @param venInfo
	 *            Loads in an object array for a popup window with editable text
	 *            fields in order to create a new Venue venue.
	 * @param boxTitle
	 */
	public void createVenueInfoBox(Object[] venInfo, String boxTitle) {
		try {
			JOptionPane.showConfirmDialog(null, venInfo, boxTitle, JOptionPane.OK_CANCEL_OPTION);
			SchedulerDatabaseUtils.addVenue(addID.getText(), addVenName.getText(), addVenTables.getText(), addVenAddress.getText());
			JOptionPane.showMessageDialog(null,
					"Successfully added " + addVenName.getText() + " in the database!",
					"Added Venue Successfully", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"I'm sorry, something went wrong. Please try to add the venue again!",
					"Add Venue Error", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * 
	 * @param blackInfo
	 *            Loads in an object array for a popup window with editable text
	 *            fields in order to create a new Blacklist between an employee
	 *            and venue.
	 * @param boxTitle
	 */
	public void createBlacklistInfoBox(Object[] blackInfo, String boxTitle) {
		try {
			JOptionPane.showConfirmDialog(null, blackInfo, boxTitle, JOptionPane.OK_CANCEL_OPTION);
		
			if(SchedulerDatabaseUtils.searchVenueID(addVID.getText()) != null){
			
				SchedulerDatabaseUtils.addBlacklisted(addEID.getText(), addVID.getText());
				JOptionPane.showMessageDialog(null,
						"Successfully added a blacklist for employee " + addEID.getText() + " and " + addVID.getText() + " in the database!",
						"Added Blacklist Successfully", JOptionPane.INFORMATION_MESSAGE);
			}else{
				JOptionPane.showMessageDialog(null,
						"I'm sorry, something went wrong. Please try to add the blacklist again!",
						"Add Blacklist Error", JOptionPane.INFORMATION_MESSAGE);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param venInfo
	 *            Loads in an object array for a popup window with editable text
	 *            fields in order to update a venue.
	 * @param boxTitle
	 */
	public void updateVenueInfoBox(Object[] venInfo, String boxTitle) {
		try {
			JOptionPane.showConfirmDialog(null, venInfo, boxTitle, JOptionPane.OK_CANCEL_OPTION);
			SchedulerDatabaseUtils.removeVenue(inputID);
			SchedulerDatabaseUtils.addVenue(inputID, addVenName.getText(), addVenTables.getText(), addVenAddress.getText());
			JOptionPane.showMessageDialog(null,
					"Successfully updated " + addVenName.getText() + " in the database!",
					"Updated Venue Successfully", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"I'm sorry, something went wrong. Please try to update the venue again!",
					"Update Venue Error", JOptionPane.INFORMATION_MESSAGE);
		}
	}


}

/**
 * Necessary Renderer class to override JTables distaste for JButtons.
 * 
 */
class ButtonRenderer extends JButton implements TableCellRenderer {
	// CONSTRUCTOR
	public ButtonRenderer() {
		setOpaque(true);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object obj, boolean selected, boolean focused, int row,
			int col) {

		// SET PASSED OBJECT AS BUTTON TEXT
		Employee employee = (Employee) obj;
		setText((employee == null) ? "" : employee.getFullName());

		return this;
	}

}

/**
 * Creates a JTable filled with instanced employee information for the button
 * clicked. Includes Name, ID, Phone number, and email.
 *
 */
class ButtonEditor extends DefaultCellEditor {
	protected JButton btn;
	private String lbl;
	private boolean clicked;
	Object[][] empInfoRows;
	Object[] empColNames = { "ID", "Name", "Phone Number", "Email" };
	JTable empInfoTable;
	JDialog empInfoBox;

	public ButtonEditor(JTextField txt) {
		super(txt);

		btn = new JButton(txt.getText());
		btn.setOpaque(true);
	}

	/**
	 * sets the label of the cell as an Employee's name if it exists. When the
	 * button is clicked, it displayed all information related to that employee.
	 */
	@Override
	public Component getTableCellEditorComponent(JTable table, Object obj, boolean selected, int row, int col) {

		// SET TEXT TO BUTTON,SET CLICKED TO TRUE,THEN RETURN THE BTN OBJECT
		Employee employee = (Employee) obj;
		lbl = (employee == null) ? "" : employee.getFullName();
		btn.setText(lbl);
		clicked = true;

		// WHEN BUTTON IS CLICKED
		for (ActionListener al : btn.getActionListeners())
			btn.removeActionListener(al);

		Object[][] empInfoRows = {
				{ employee.getId(), employee.getFullName(), employee.getPhone(), employee.getEmail() } };
		empInfoTable = new JTable(empInfoRows, empColNames);

		btn.addActionListener(new ActionListener() {

			// Creates a dialog box displaying Employee information
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,
						"ID: " + employee.getId().toUpperCase() + "\n" + "Name: " + employee.getFullName() + "\n"
								+ "Phone: " + employee.getPhone() + "\n" + "Email: " + employee.getEmail(),
						"Employee Info", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		return btn;
	}

	// DON'T DEAD OPEN INSIDE
	@Override
	public Object getCellEditorValue() {
		if (clicked) {
			// NUH UH
		}
		clicked = false;
		return super.getCellEditorValue();
	}

	@Override
	public boolean stopCellEditing() {
		// SET CLICKED TO FALSE FIRST
		clicked = false;
		return super.stopCellEditing();
	}

	// DON'T DEAD OPEN INSIDE
	@Override
	protected void fireEditingStopped() {
		// DON'T EVEN
	}
}