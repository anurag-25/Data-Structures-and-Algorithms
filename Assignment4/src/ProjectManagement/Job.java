package ProjectManagement;

public class Job implements Comparable<Job> {
	
	String nameOfJob;
	Project job_Project;
	User job_User;
	int runTime;
    public static int requested = 0;
    public static int completed =1;
    int job_Status;
    int completedTime;
	
	public Job (String nameOfJob, Project job_Project, User job_User, int runTime )	{
		this.nameOfJob = nameOfJob;
		this.job_Project = job_Project;
		this.job_User = job_User;
		this.runTime = runTime;
		this.job_Status = requested;
		this.completedTime = 0;
	}
	
    @Override
    public int compareTo(Job job) {
        if (this.job_Project.priorityOfProject < job.job_Project.priorityOfProject) return -1;
        else if (this.job_Project.priorityOfProject > job.job_Project.priorityOfProject) return 1;
        else return 0;
    }
    
    public String toString()	{
    	return nameOfJob;
    }
}