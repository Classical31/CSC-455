
/**
 * Object containing an Employee's contact information.
 */
public class Employee {
	private String id;
	private String firstName;
	private String lastName;
	private String password;
	private String phone;
	private String email;
	private boolean manager;

	// do not allow initialization
	//force them to use builder
	//this will insure that each employee object is immutable
	private Employee() {

	}
	/**
	 * Returns employee's email.
	 */

	public String getEmail() {

		return email;
	}
	/**
	 * Returns employee's first name.
	 */

	public String getFirstName() {


		return firstName;
	}

	/**
	 * Returns employee's ID number.
	 */

	public String getId() {

		return id;
	}

	/**
	 * Returns employee's last name.
	 */

	public String getLastName() {

		return lastName;
	}
	/**
	 * Returns employee's first name concatenated with their last name.
	 */
	public String getFullName() {


		return this.firstName + " " + this.lastName;
	}
	/**
	 * Returns employee's phone number.
	 */
	public String getPhone() {


		return phone;
	}
	/**
	 * Returns boolean value telling if this employee is a manager.
	 */
	public boolean isManager() {


		return manager;
	}
	/**
	 * Returns employee's password.
	 */
	public String getPassword() {


		return password;
	}

	/**
	 * Two employees will only be equal iff their IDs match,
	 */
	@Override
	public boolean equals(Object employee) {
		if(this == employee)
		{
			return true;
		}
		if(!(employee instanceof Employee)) {
			return false;
		}
		else{
			return this.getId().equals(((Employee) employee).getId());
		}

	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("Employee{");
		sb.append("id='").append(id).append('\'');
		sb.append(", firstName='").append(firstName).append('\'');
		sb.append(", lastName='").append(lastName).append('\'');
		sb.append(", password='").append(password).append('\'');
		sb.append(", phone='").append(phone).append('\'');
		sb.append(", email='").append(email).append('\'');
		sb.append(", manager=").append(manager);
		sb.append('}');
		return sb.toString();
	}

	public static class EmployeeBuilder
	{
		private String id;
		private String firstName;
		private String lastName;
		private String password;
		private String phone;
		private String email;
		private boolean manager;

		public EmployeeBuilder(String id)
		{
			this.id =  id;
		}

		public EmployeeBuilder firstName(String firstName)
		{
			this.firstName = firstName;
			return this;
		}

		public EmployeeBuilder lastName(String lastName)
		{
			this.lastName = lastName;
			return this;
		}

		public EmployeeBuilder password(String password)
		{
			this.password = password;
			return this;
		}

		public EmployeeBuilder phone(String phone)
		{
			this.phone = phone;
			return this;
		}
		public EmployeeBuilder email(String email)
		{
			this.email = email;
			return this;
		}

		public EmployeeBuilder isManager(boolean isManager)
		{
			this.manager = isManager;
			return this;
		}
		public Employee build()
		{
			Employee employee = new Employee();
			employee.id = this.id;
			employee.firstName = this.firstName;
			employee.lastName = this.lastName;
			employee.password = this.password;
			employee.email = this.email;
			employee.phone = this.phone;
			employee.manager = this.manager;
			return employee;

		}
	}

}
