package fr.insa.ms.authenticationMS.model;

public class AuthResponse {
	private String msg;
	private StudentWithoutPassword student;
	
	public AuthResponse() {
		
	}
	
	public AuthResponse(String msg, StudentWithoutPassword student) {
		this.msg=msg;
		this.student=student;
	}
	
	public StudentWithoutPassword getStudent() {
		return student;
	}
	public void setStudent(StudentWithoutPassword student) {
		this.student = student;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

}
