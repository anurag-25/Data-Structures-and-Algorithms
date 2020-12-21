
public class Student implements Student_ {
	
	private String firstName, lastName, hostelName, departmentName, cGPA;
	
	Student (String firstName, String lastName, String hostelName, String departmentName, String cGPA ){
		this.firstName= firstName;
		this.lastName = lastName;
		this.hostelName = hostelName;
		this.departmentName = departmentName;
		this.cGPA = cGPA;
	}

	public String fname() {
		return this.firstName;
	}

	public String lname() {
		return this.lastName;
	}

	public String hostel() {
		return this.hostelName;
	}

	public String department() {
		return this.departmentName;
	}

	public String cgpa() {
		return this.cGPA;
	}
	
	public String toString(){
		return (fname()+" "+lname()+" "+hostel()+" "+department()+" "+cgpa() );
	}
}
