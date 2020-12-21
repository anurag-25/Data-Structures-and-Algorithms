package ProjectManagement;

import java.util.ArrayList;


public class Project {
	
	String nameOfProject;
	int priorityOfProject;
	int budgetOfProject;
	public ArrayList <Job> project_JobList;

	
	public Project(String name, int priority, int budget)	{
		this.nameOfProject = name;
		this.priorityOfProject = priority;
		this.budgetOfProject = budget;
		this.project_JobList = new ArrayList<Job>();
	}
	
	public String toString()	{
		return nameOfProject;
	}
}