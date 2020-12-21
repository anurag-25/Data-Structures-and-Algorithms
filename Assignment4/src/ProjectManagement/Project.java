package ProjectManagement;


public class Project {
	
	String nameOfProject;
	int priorityOfProject;
	int budgetOfProject;
	
	public Project(String name, int priority, int budget)	{
		this.nameOfProject = name;
		this.priorityOfProject = priority;
		this.budgetOfProject = budget;
	}
	
	public String toString()	{
		return nameOfProject;
	}
}