import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.sun.org.apache.xml.internal.security.utils.JavaUtils;

// based on www.vogella.com/tutorials/MySQLJava/article.html

/**
 * 
 * Database class to connect to the remote database and execute queries for
 * employee, venue, and any other data sets stored into class objects. Includes
 * functions to manipulate data and update the current database
 *
 */
public final class Database {
	// Data needed to access server and database
	private final static String domain = "127.0.0.1";
	private final static String port = "3306";
	private final static String database_name = "schedule";
	private final static String sql_username = "root";
	private final static String sql_passwd = "root";

	private static Connection connection;

	private static Statement statement;
	private static PreparedStatement preparedStatement;
	private static ResultSet resultSet;
	private static boolean isLoginManager;

	public Connection getConnection() {
		return connection;
	}

	// Employee functions
	/*
	 * Connects to the database and generates an array list of all the employees
	 * in the database
	 */
	public static ArrayList<Employee> getEmployees() throws Exception {
		ArrayList<Employee> employees = new ArrayList<Employee>();
		try {
			/* Open connection to the database */
			connect();

			/* Executes query and saves result into result set */
			preparedStatement = connection.prepareStatement("select * from employee;");
			resultSet = preparedStatement.executeQuery();

			/*
			 * While there is an row in result set, create a new employee and
			 * add it to the array list
			 */
			while (resultSet.next()) {
				String id = resultSet.getString("employeeID");
				String firstName = resultSet.getString("fName");
				String lastName = resultSet.getString("lName");
				String password = resultSet.getString("password");
				String phone = resultSet.getString("phone");
				String email = resultSet.getString("email");
				boolean isManager = resultSet.getBoolean("isManager");

				Employee employee = new Employee(id, firstName, lastName, password, phone, email);
				employees.add(employee);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			/* Close connection to the database */
			close();
		}
		return employees;
	}

	/*
	 * Connects to the database and generates a string of one of the column of
	 * one of the employees from the database
	 */
	public static String getEmployeeInfo(String fName, String lName, String column) throws Exception {
		String info = new String();
		try {
			/* Open connection to the database */
			connect();

			/* Executes query and saves result into result set */
			preparedStatement = connection.prepareStatement("call GetEmployeeInfo(?, ?, ?);");
			preparedStatement.setString(1, fName);
			preparedStatement.setString(2, lName);
			preparedStatement.setString(3, column);
			resultSet = preparedStatement.executeQuery();

			resultSet.next();

			/* Save data into variable */
			info = resultSet.getString(column);
		} catch (Exception e) {
			throw e;
		} finally {
			/* Close connection to the database */
			close();
		}
		return info;

	}
	
	public static ArrayList<Event> getBlackList() throws Exception{
		ArrayList<Event> blackList = new ArrayList<Event>();
		try{
			connect();
			preparedStatement=connection.prepareStatement("select * from blacklist");
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()){
				String emp =rs.getString(1);
				Employee e1 = new Employee(emp, null,null, null, null,null);
				String ven = rs.getString(2);
				Venue ven1 = new Venue(ven,null,0,null);
				Event e = new Event(ven1,e1);
				blackList.add(e);
				
				
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
			
		}
		finally{
			close();
		}
		
		return blackList;
	}
	public static void fillAllTables(JTable table,String Query) throws Exception{
		
		
		try{
			connect();
			Statement stat = connection.createStatement();
			ResultSet rs = stat.executeQuery(Query);
			Object[] modelList = new Object[rs.getMetaData().getColumnCount()];
			for (int i =1;i<=rs.getMetaData().getColumnCount();i++){
				//System.out.println(rs.getMetaData().getColumnName(i));
				modelList[i-1]=rs.getMetaData().getColumnName(i);
				
						}
			DefaultTableModel model = new DefaultTableModel(modelList,0);
			
			while(rs.next()){
			Object[] list = new Object[modelList.length];
			for(int i =0;i<list.length;i++){
				list[i]=rs.getObject(i+1);
			}
			model.addRow(list);
			}
			table.setModel(model);
			table.repaint();

			rs.close();
			stat.close();
		
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			close();
		}
	}
	

	

	public static void addRequestOff(String eID, String dateString) throws Exception {
		try {
			SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
			java.util.Date date = format.parse(dateString);
			java.sql.Date sqlRequestDate = new java.sql.Date(date.getTime());

			connect();

			preparedStatement = connection.prepareStatement("Insert into request_off(employeeID,date_needed_off,isApproved) values(?,?,?);");
			preparedStatement.setString(1, eID);
			preparedStatement.setObject(2, sqlRequestDate);
			preparedStatement.setBoolean(3, false);
			preparedStatement.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			close();
		}
	}
	
	public static void addRequestSwap(String eID, String dateString,String day1,String e2ID,String day2) throws Exception {
		try {
			SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
			java.util.Date date = format.parse(dateString);
			java.sql.Date sqlRequestDate = new java.sql.Date(date.getTime());

			connect();

			preparedStatement = connection.prepareStatement("Insert into swap_request(employeeID,weekOfRequest,dayOf,employee2,dayOf2) values(?,?,?,?,?);");
			preparedStatement.setString(1, eID);
			preparedStatement.setObject(2, sqlRequestDate);
			preparedStatement.setString(3, day1);
			preparedStatement.setString(4,e2ID);
			preparedStatement.setString(5, day2);
			preparedStatement.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			close();
		}
	}
	public static void approveSwapRequest(Integer rID,String eID, boolean isManager,boolean accept) throws Exception{
		String emp2ID = null;
		Object employee2Approval=null;
		try{
			connect();
			preparedStatement = connection.prepareStatement("select employee2,employee2Approval from swap_request where ID=?");
			preparedStatement.setInt(1, rID);
			ResultSet rs=preparedStatement.executeQuery();
			
			if(rs.next()){
				emp2ID=rs.getString(1);
				employee2Approval=rs.getObject(2);
			}
			
			if (employee2Approval ==null && emp2ID.equals(eID)){
				preparedStatement = connection.prepareStatement("update swap_request set employee2Approval=? where ID=?");
				preparedStatement.setBoolean(1, accept);
				preparedStatement.setInt(2, rID);
				preparedStatement.executeUpdate();
			}
			else if(employee2Approval !=null && (Boolean)employee2Approval ==true && !(emp2ID.equals(eID)) && isManager){
				preparedStatement = connection.prepareStatement("update swap_request set managerApr=?,approv_manager=? where ID=?");
				preparedStatement.setBoolean(1, accept);
				preparedStatement.setString(2, eID);
				preparedStatement.setInt(3, rID);
				preparedStatement.executeUpdate();
			}
			else{
				JOptionPane.showMessageDialog(null, "You are not allowed to approve this request at this time");
			}
			
			/**		 
			if eID =
			preparedStatement= connection.prepareStatement("update swap_request update employee2Approval=True");
			*/
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			
		}
	}
	

	/* Connects to the database and adds a new employee into the database */
	public static void addEmployee(String eID, String fName, String lName, String password, String phone, String email,
			Boolean isManager) throws Exception {
		try {
			/* Open connection to the database */
			connect();

			/* Executes query */
			preparedStatement = connection.prepareStatement("call AddEmployee(?, ?, ?, ?, ?, ?, ?);");
			preparedStatement.setString(1, eID);
			preparedStatement.setString(2, fName);
			preparedStatement.setString(3, lName);
			preparedStatement.setString(4, password);
			preparedStatement.setString(5, phone);
			preparedStatement.setString(6, email);
			preparedStatement.setBoolean(7, isManager);

			preparedStatement.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			/* Close connection to the database */
			close();
		}
	}

	/* Connects to the database and updates one field of one employee's info */
	public static void updateEmployee(String column, String value, String ID) throws Exception {
		try {
			/* Open connection to the database */
			connect();

			/* Executes query */
			preparedStatement = connection.prepareStatement("call UpdateEmployee(?, ?, ?);");
			preparedStatement.setString(1, column);
			preparedStatement.setString(2, value);
			preparedStatement.setString(3, ID);

			preparedStatement.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			/* Close connection to the database */
			close();
		}
	}

	/*
	 * Connects to the database and updates whether and employee is a manager or
	 * not
	 */
	public static void updateManager(Boolean isMan, String eID) throws Exception {
		try {
			/* Open connection to the database */
			connect();

			/* Executes query */
			preparedStatement = connection.prepareStatement("call UpdateManager(?, ?);");
			preparedStatement.setBoolean(1, isMan);
			preparedStatement.setString(2, eID);

			preparedStatement.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			/* Close connection to the database */
			close();
		}
	}

	/*
	 * Connects to the database and searches for an employee by their full name
	 * and returns an employee, or null if no employee if found
	 */
	public static Employee searchEmployeeID(String eID) throws Exception {
		/* Set employee as null to begin with */
		Employee employee = null;
		try {
			/* Open connection to the database */
			connect();

			/* Executes query and saves result into result set */
			preparedStatement = connection.prepareStatement("call SearchEmployeeID(?);");
			preparedStatement.setString(1, eID);
			resultSet = preparedStatement.executeQuery();

			/*
			 * If an employee if found, create an employee, otherwise keep
			 * employee as null
			 */
			if (resultSet.next()) {
				String fName = resultSet.getString("fName");
				String lName = resultSet.getString("lName");
				String password = resultSet.getString("password");
				String phone = resultSet.getString("phone");
				String email = resultSet.getString("email");

				employee = new Employee(eID, fName, lName, password, phone, email);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			/* Close connection to the database */
			close();
		}
		return employee;
	}

	/*
	 * Connects to the database and searches for an employee by their ID and
	 * returns an employee, or null if no employee if found
	 */
	public static void searchEmployeeID(boolean isManager, String eID) throws Exception {
		/* Set employee as null to begin with */

		try {
			/* Open connection to the database */
			connect();
			String myPreparedStatement = null;
			if (isManager == true) {
				myPreparedStatement = "select * from employee natural join salary where employeeID=?";
			} else {
				myPreparedStatement = "select * from empView where employeeID = ?";
			}
			/* Executes query and saves result into result set */
			preparedStatement = connection.prepareStatement(myPreparedStatement);
			preparedStatement.setString(1, eID);
			resultSet = preparedStatement.executeQuery();

			/*
			 * If an employee if found, create an employee, otherwise keep
			 * employee as null
			 */
			if (resultSet.next()) {
				String fName = resultSet.getString("fName");
				String lName = resultSet.getString("lName");
				String phone = resultSet.getString("phone");
				String email = resultSet.getString("email");

				if (isManager == true) {
					int salary = resultSet.getInt("salary");

					JOptionPane.showMessageDialog(null,
							"ID: " + eID + "\n" + "Name: " + fName + " " + lName + "\n" + "Phone: " + phone + "\n"
									+ "Email: " + email + "\n" + "Salary: " + salary,
							"Employee Info", JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane
							.showMessageDialog(null,
									"ID: " + eID + "\n" + "Name: " + fName + " " + lName + "\n" + "Phone: " + phone
											+ "\n" + "Email: " + email,
									"Employee Info", JOptionPane.INFORMATION_MESSAGE);
				}
			}

		} catch (Exception e) {
			throw e;
		} finally {
			/* Close connection to the database */
			close();
		}
	}

	public static void updateRequestOff(String eID, String dateString, Boolean approved, String manID)
			throws Exception {
		try {
			SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
			java.util.Date date = format.parse(dateString);
			java.sql.Date sqlRequestDate = new java.sql.Date(date.getTime());
			connect();
			// update request_off set isApproved = TRUE,approv_manager ='13881'
			// where employeeID ='17000' and date_needed_off='2018-04-30';
			preparedStatement = connection.prepareStatement(
					"update request_off set isApproved=?,approv_manager=? where employeeID=? and date_needed_off=?");
			preparedStatement.setBoolean(1, approved);
			preparedStatement.setString(2, manID);
			preparedStatement.setString(3, eID);
			preparedStatement.setObject(4, sqlRequestDate);

			preparedStatement.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	/* Connects to the database and removes an employee by their ID */
	public static void removeEmployee(String eID) throws Exception {
		try {
			/* Open connection to the database */
			connect();

			/* Executes query */
			preparedStatement = connection.prepareStatement("call RemoveEmployee(?)");
			preparedStatement.setString(1, eID);

			preparedStatement.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			/* Close connection to the database */
			close();
		}
	}

	// Venue functions
	/*
	 * Connects to the database and generates an array list of all the venues in
	 * the database
	 */
	public static ArrayList<Venue> getVenues() throws Exception {
		ArrayList<Venue> venues = new ArrayList<Venue>();
		try {
			/* Open connection to the database */
			connect();

			/* Executes query and saves result into result set */
			preparedStatement = connection.prepareStatement("select * from venue;");
			resultSet = preparedStatement.executeQuery();

			/*
			 * While there is an row in result set, create a new employee and
			 * add it to the array list
			 */
			while (resultSet.next()) {
				String id = resultSet.getString("venueID");
				String name = resultSet.getString("name");
				int tables = resultSet.getInt("tableNum");
				String address = resultSet.getString("address");

				Venue venue = new Venue(id, name, tables, address);
				venues.add(venue);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			/* Close connection to the database */
			close();
		}
		return venues;
	}

	/*
	 * Connects to the database and generates a string of one of the column of
	 * one of the venues from the database
	 */
	public static String getVenueInfo(String name, String column) throws Exception {
		String info = new String();
		try {
			/* Open connection to the database */
			connect();

			/* Executes query and saves result into result set */
			preparedStatement = connection.prepareStatement("call GetVenueInfo(?, ?);");
			preparedStatement.setString(1, name);
			preparedStatement.setString(3, column);
			resultSet = preparedStatement.executeQuery();

			resultSet.next();

			/* Save data into variable */
			info = resultSet.getString(column);
		} catch (Exception e) {
			throw e;
		} finally {
			/* Close connection to the database */
			close();
		}
		return info;
	}

	/* Connects to the database and adds a new venue into the database */
	public static void addVenue(String vID, String name, String tables, String address) throws Exception {
		try {
			/* Open connection to the database */
			connect();

			/* Executes query */
			preparedStatement = connection.prepareStatement("call AddVenue(?, ?, ?, ?)");
			preparedStatement.setString(1, vID);
			preparedStatement.setString(2, name);
			preparedStatement.setString(3, tables);
			preparedStatement.setString(4, address);

			preparedStatement.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			/* Close connection to the database */
			close();
		}
	}

	/* Connects to the database and updates one field of one venue's info */
	/**
	 * 
	 * @param column
	 *            is what item you want to replace
	 * @param value
	 *            is what you are changing the item to
	 * @param ID
	 *            is the employee or venue id that you are wishing to change.
	 * @throws Exception
	 */
	public static void updateVenue(String column, String value, String ID) throws Exception {
		try {
			/* Open connection to the database */
			connect();

			/* Executes query */
			preparedStatement = connection.prepareStatement("call UpdateVenue(?, ?, ?);");
			preparedStatement.setString(1, column);
			preparedStatement.setString(2, value);
			preparedStatement.setString(3, ID);

			preparedStatement.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			/* Close connection to the database */
			close();
		}
	}

	/*
	 * addRequestOff Connects to the database and searches for a venue by its
	 * name and returns an venue, or null if no venue if found
	 */
	public static Venue searchVenue(String name) throws Exception {
		Venue venue = null;
		try {
			/* Open connection to the database */
			connect();

			/* Executes query and saves result into result set */
			preparedStatement = connection.prepareStatement("call SearchVenue(?)");
			preparedStatement.setString(1, name);
			resultSet = preparedStatement.executeQuery();

			/*
			 * If an venue if found, create a venue, otherwise keep employee as
			 * null
			 */
			if (resultSet.next() == true) {
				String vID = resultSet.getString("venueID");
				int tables = resultSet.getInt("tableNum");
				String address = resultSet.getString("address");

				venue = new Venue(vID, name, tables, address);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			/* Close connection to the database */
			close();
		}
		return venue;
	}

	/*
	 * Connects to the database and searches for a venue by its ID and returns
	 * an venue, or null if no venue if found
	 */
	public static Venue searchVenueID(String vID) throws Exception {
		Venue venue = null;
		try {
			/* Open connection to the database */
			connect();

			/* Executes query and saves result into result set */
			preparedStatement = connection.prepareStatement("call SearchVenueID(?)");
			preparedStatement.setString(1, vID);
			resultSet = preparedStatement.executeQuery();

			/*
			 * If an venue if found, create a venue, otherwise keep employee as
			 * null
			 */
			if (resultSet.next() == true) {
				String name = resultSet.getString("name");
				int tables = resultSet.getInt("tableNum");
				String address = resultSet.getString("address");

				venue = new Venue(vID, name, tables, address);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			/* Close connection to the database */
			close();
		}
		return venue;
	}

	/* Connects to the database and removes a venue by its ID */
	public static void removeVenue(String vID) throws Exception {
		try {
			/* Open connection to the database */
			connect();

			/* Executes query */
			preparedStatement = connection.prepareStatement("call RemoveVenue(?)");
			preparedStatement.setString(1, vID);

			preparedStatement.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			/* Close connection to the database */
			close();
		}
	}

	// Blacklisted functions
	/*
	 * Connects to the database and generates an array list of all the
	 * blacklists in the database
	 */
	public static ArrayList<ArrayList<String>> getBlacklisted() throws Exception {
		ArrayList<String> blacklist = new ArrayList<String>();
		ArrayList<ArrayList<String>> blacklists = new ArrayList<ArrayList<String>>();
		try {
			/* Open connection to the database */
			connect();

			/* Executes query and saves result into result set */
			preparedStatement = connection.prepareStatement("call GetTable(blacklisted)");
			resultSet = preparedStatement.executeQuery();

			/*
			 * While there is an row in result set, create a new blacklist array
			 * list and add it to the array list
			 */
			while (resultSet.next())
				;
			{
				String eID = resultSet.getString("employeeID");
				String vID = resultSet.getString("venueID");

				blacklist.add(eID);
				blacklist.add(vID);
				blacklists.add(blacklist);

				blacklist.clear();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			/* Close connection to the database */
			close();
		}
		return blacklists;
	}

	/* Connects to the database and adds a new blacklist into the database */
	public static void addBlacklisted(String eID, String vID) throws Exception {
		try {
			/* Open connection to the database */
			connect();

			/* Executes query */
			preparedStatement = connection.prepareStatement("call AddBlacklisted(?, ?)");
			preparedStatement.setString(1, eID);
			preparedStatement.setString(2, vID);

			preparedStatement.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			/* Close connection to the database */
			close();
		}
	}

	/* Connects to the database and searches for a blacklist by the employee */
	public static ArrayList<String> searchBlacklistedEmployee(String eID) throws Exception {
		ArrayList<String> blacklistedVenues = new ArrayList<String>();
		try {
			/* Open connection to the database */
			connect();

			/* Executes query and saves result into result set */
			preparedStatement = connection.prepareStatement("call SearchBlacklistedEmployee(?)");
			preparedStatement.setString(1, eID);
			resultSet = preparedStatement.executeQuery();

			/*
			 * While there is an row in result set, add the venueID to the array
			 * list
			 */
			while (resultSet.next()) {
				String vID = resultSet.getString("venueID");

				blacklistedVenues.add(vID);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			/* Close connection to the database */
			close();
		}
		return blacklistedVenues;
	}

	/* Connects to the database and searches for a blacklist by the venue */
	public static ArrayList<String> searchBlacklistedVenue(String vID) throws Exception {
		ArrayList<String> blacklistedEmployees = new ArrayList<String>();
		try {
			/* Open connection to the database */
			connect();

			/* Executes query and saves result into result set */
			preparedStatement = connection.prepareStatement("call SearchBlacklistedVenue(?)");
			preparedStatement.setString(1, vID);
			resultSet = preparedStatement.executeQuery();

			/*
			 * While there is an row in result set, add the employeeID to the
			 * array list
			 */
			while (resultSet.next()) {
				String eID = resultSet.getString("employeeID");

				blacklistedEmployees.add(eID);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			/* Close connection to the database */
			close();
		}
		return blacklistedEmployees;
	}

	/* Connects to the database and removes a blacklist by its ID */
	public static void removeBlacklisted(String eID, String vID) throws Exception {
		try {
			/* Open connection to the database */
			connect();

			/* Executes query */
			preparedStatement = connection.prepareStatement("call RemoveBlacklisted(?, ?)");
			preparedStatement.setString(1, eID);
			preparedStatement.setString(2, vID);

			preparedStatement.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			/* Close connection to the database */
			close();
		}
	}

	public static boolean validateUserID(String id) throws Exception {
		try {
			/* Open connection to the database */
			connect();

			/* Executes query */
			PreparedStatement ps2 = connection.prepareStatement("select count(*) from employee where employeeID=?");
			ps2.setString(1, id);
			ResultSet rs2 = ps2.executeQuery();

			if (rs2.next()) {
				if (rs2.getInt(1) <= 0) {
					close();
					return false;
				}

			} else {
				close();
				return true;
			}
		} catch (Exception e) {
			throw e;
		}
		return true;

	}

	public boolean validateLogin(String id, String password) throws Exception {
		String DBPassword = null;
		try {
			/* Open connection to the database */
			connect();

			/* Executes query */
			PreparedStatement ps2 = connection.prepareStatement("select count(*) from employee where employeeID=?");
			ps2.setString(1, id);
			ResultSet rs2 = ps2.executeQuery();
			if (rs2.next()) {
				if (rs2.getInt(1) <= 0) {
					return false;
				}

			}

			preparedStatement = connection.prepareStatement("select password from employee where employeeID=?");
			preparedStatement.setString(1, id);

			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				DBPassword = rs.getString(1);

			}
			close();
			if (DBPassword.equals(password)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			/* Close connection to the database */

		}

	}

	public static void updateSalary(String id, int salary) throws Exception {
		connect();
		preparedStatement = connection.prepareStatement("call updateSalary(?,?)");
		try {
			preparedStatement.setString(1, id);
			preparedStatement.setLong(2, salary);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		close();

	}

	public static int getCurrentSalary(String id) throws Exception {
		int currentSalary = 0;
		try {
			/* Open connection to the database */
			connect();

			/* Executes query */
			preparedStatement = connection.prepareStatement("select salary from salary where employeeID=?");
			preparedStatement.setString(1, id);

			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				currentSalary = rs.getInt(1);
			}
			close();
			return currentSalary;
		} catch (Exception e) {
			throw e;
		} finally {
			/* Close connection to the database */

		}

	}

	public void createScheduleInDB(String id, LocalDate date) throws Exception {
		try {

			preparedStatement = connection.prepareStatement("call createNewSchedule(?,?)");
			preparedStatement.setString(1, id);
			preparedStatement.setObject(2, date);
			preparedStatement.executeUpdate();

		} catch (Exception e) {
			throw e;
		}
	}

	public void insertIntoSchedule(String id, String weekday, String ven, LocalDate date) throws Exception {
		try {

			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement("call insertSchedule(?,?,?,?)");
			preparedStatement.setString(1, id);
			preparedStatement.setString(2, weekday);
			preparedStatement.setString(3, ven);
			preparedStatement.setObject(4, date);
			preparedStatement.executeUpdate();

		} catch (Exception e) {
			throw e;
		}
	}

	public boolean isManager(String id) throws Exception {
		Boolean boolIsManager = false;
		try {
			/* Open connection to the database */
			connect();

			/* Executes query */
			preparedStatement = connection.prepareStatement("select isManager from employee where employeeID=?");
			preparedStatement.setString(1, id);

			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				boolIsManager = rs.getBoolean(1);
			}
			close();
			return boolIsManager;
		} catch (Exception e) {
			throw e;
		} finally {
			/* Close connection to the database */
		}

	}

	public
	// Connection functions
	/* Opens a connection to the database */
	static void connect() throws Exception {
		try {
			// load MySql driver
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(
					"jdbc:mysql://" + domain + ":" + port + "/" + database_name + "?useSSL=false", sql_username,
					sql_passwd);
			System.out.println("Connected to database");
		} catch (Exception e) {
			throw e;
		}
	}

	/* Closes the connection to the database */
	static void close() {
		try {
			if (resultSet != null)
				resultSet.close();
			if (statement != null)
				statement.close();
			if (connection != null)
				connection.close();
		} catch (Exception e) {
		}
	}
}
