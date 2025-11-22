package fr.insa.ms.authenticationMS.model;

public class AuthResponse {
	private String msg;
	private Student student;
	
	public AuthResponse() {
		
	}
	
	public AuthResponse(String msg, Student student) {
		this.msg=msg;
		this.student=student;
	}
	
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

}
