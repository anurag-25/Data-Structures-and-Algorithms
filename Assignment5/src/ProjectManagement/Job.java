package ProjectManagement;

public class Job implements Comparable<Job>, JobReport_ {
	
	String nameOfJob;
	Project job_Project;
	int id;
	User job_User;
	int runTime;
    public static int requested = 0;
    public static int completed =1;
    int job_Status;
    int startTime;
    int completedTime;
    public int priorityOfJob;
	
	public Job (String nameOfJob, Project job_Project, User job_User, int runTime )	{
		this.id = 0;
		this.nameOfJob = nameOfJob;
		this.job_Project = job_Project;
		this.job_User = job_User;
		this.runTime = runTime;
		this.job_Status = requested;
		this.startTime = 1;
		this.completedTime = -1;
		this.priorityOfJob = job_Project.priorityOfProject;
	}
	
    @Override
    public int compareTo(Job job) {
        if (this.job_Project.priorityOfProject < job.job_Project.priorityOfProject) return -1;
        else if (this.job_Project.priorityOfProject > job.job_Project.priorityOfProject) return 1;
        else {
        	if (this.id <job.id ) return 1;
        	else if (this.id > job.id ) return -1;
        	else return 0;
        }
    }
    
    public String toString()	{
    	return nameOfJob;
    }
    
    public String user() { 
    	return job_User.nameOfUser; }

    public String project_name()  { return job_Project.nameOfProject; }

    public int budget()  { return runTime; }

    public int arrival_time()  { return startTime; }

    public int completion_time() { return completedTime; }
}