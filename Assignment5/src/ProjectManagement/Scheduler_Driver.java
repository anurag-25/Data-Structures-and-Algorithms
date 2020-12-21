package ProjectManagement;


import PriorityQueue.MaxHeap;
import PriorityQueue.PriorityQueueDriverCode;
import RedBlack.RedBlackNode;
import Trie.Trie;
import Trie.TrieNode;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Scheduler_Driver extends Thread implements SchedulerInterface {


    public static void main(String[] args) throws IOException {
//

        Scheduler_Driver scheduler_driver = new Scheduler_Driver();
        File file;
        if (args.length == 0) {
            URL url = Scheduler_Driver.class.getResource("INP");
            file = new File(url.getPath());
        } else {
            file = new File(args[0]);
        }

        scheduler_driver.execute(file);
    }

    public void execute(File commandFile) throws IOException {


        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(commandFile));

            String st;
            while ((st = br.readLine()) != null) {
                String[] cmd = st.split(" ");
                if (cmd.length == 0) {
                    System.err.println("Error parsing: " + st);
                    return;
                }
                String project_name, user_name;
                Integer start_time, end_time;

                long qstart_time, qend_time;

                switch (cmd[0]) {
                    case "PROJECT":
                        handle_project(cmd);
                        break;
                    case "JOB":
                        handle_job(cmd);
                        break;
                    case "USER":
                        handle_user(cmd[1]);
                        break;
                    case "QUERY":
                        handle_query(cmd[1]);
                        break;
                    case "": // HANDLE EMPTY LINE
                        handle_empty_line();
                        break;
                    case "ADD":
                        handle_add(cmd);
                        break;
                    //--------- New Queries
                    case "NEW_PROJECT":
                    case "NEW_USER":
                    case "NEW_PROJECTUSER":
                    case "NEW_PRIORITY":
                        timed_report(cmd);
                        break;
                    case "NEW_TOP":
                        qstart_time = System.nanoTime();
                        timed_top_consumer(Integer.parseInt(cmd[1]));
                        qend_time = System.nanoTime();
                        System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));
                        break;
                    case "NEW_FLUSH":
                        qstart_time = System.nanoTime();
                        timed_flush( Integer.parseInt(cmd[1]));
                        qend_time = System.nanoTime();
                        System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));
                        break;
                    default:
                        System.err.println("Unknown command: " + cmd[0]);
                }

            }


            run_to_completion();
            print_stats();

        } catch (FileNotFoundException e) {
            System.err.println("Input file Not found. " + commandFile.getAbsolutePath());
        } catch (NullPointerException ne) {
            ne.printStackTrace();

        }
    }

    @Override
    public ArrayList<JobReport_> timed_report(String[] cmd) {
        long qstart_time, qend_time;
        ArrayList<JobReport_> res = null;
        switch (cmd[0]) {
            case "NEW_PROJECT":
                qstart_time = System.nanoTime();
                res = handle_new_project(cmd);
                qend_time = System.nanoTime();
                System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));
                break;
            case "NEW_USER":
                qstart_time = System.nanoTime();
                res = handle_new_user(cmd);
                qend_time = System.nanoTime();
                System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));

                break;
            case "NEW_PROJECTUSER":
                qstart_time = System.nanoTime();
                res = handle_new_projectuser(cmd);
                qend_time = System.nanoTime();
                System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));
                break;
            case "NEW_PRIORITY":
                qstart_time = System.nanoTime();
                res = handle_new_priority(cmd[1]);
                qend_time = System.nanoTime();
                System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));
                break;
        }

        return res;
    }
    
    MaxHeap<Job> PQ = new MaxHeap<>();
    Trie<Project> Trie = new Trie <Project> ();
    Trie < User> UserTrie = new Trie ();
    List <Job> listOfCompletedJobs = new ArrayList <Job> ();
    List <Job> listOfNonCompletedJobs = new ArrayList <Job> ();
    List <User> listOfUsers = new ArrayList <User> ();
    
    public static int requested = 0;
    public static int completed =1;
    public static int jobID = 0;
    public int globalTime=0;

    @Override
    public ArrayList<UserReport_> timed_top_consumer(int top) {
//    	System.out.println("Top");
    	ArrayList <UserReport_> users = new ArrayList <UserReport_> (top);
    	ArrayList <User> temp = (ArrayList<User>) listOfUsers;
    	
    	mergeSortUser(temp);
    	if (temp.size() < top ) top = temp.size();
    	for (int i=0; i<top; i++){
    		users.add(temp.get(i) );
    	}
//    	for (UserReport_ user: users) System.out.println(user + ": " + user.consumed() + ", ");
        return users;
    }

    private void mergeSortUser (ArrayList <User> users)	{
    	int n = users.size();
    	if ( n<2 ) return;
    	int mid = n/2;
    	ArrayList <User> left = new ArrayList<User>(mid);
    	ArrayList <User> right = new ArrayList<User> (n-mid);
    	for (int i=0; i<mid; i++)	{
    		left.add(users.get(i) );
    	}
    	for (int i=mid; i<n; i++)	{
    		right.add(users.get(i) );
    	}
    	mergeSortUser(left);
    	mergeSortUser(right);
    	mergeSortUser(users, left, right);
    }


    private void mergeSortUser(ArrayList<User> users, ArrayList<User> left, ArrayList<User> right) {
		int nL = left.size();
		int nR = right.size();
		int i=0, j=0, k=0;
		while (i<nL && j< nR)	{
			if ( left.get(i).budgetConsumed > right.get(j).budgetConsumed && (left.get(i).budgetConsumed== right.get(j).budgetConsumed && left.get(i).latestCompletionTime < right.get(j).latestCompletionTime ) )	{
				users.set(k, left.get(i) );
				i++;
			}
			else {
				users.set(k, right.get(j) );
				j++;
			}
			k++;
		}
		while (i< nL)	{
			users.set(k, left.get(i) );
			i++;
			k++;
		}
		while (j <nR)	{
			users.set(k, right.get(j) );
			j++;
			k++;
		}
	}
    @Override
    public void timed_flush(int waittime) {
//    	System.out.println("Flush query");
    	int tempGlobalTime= globalTime;
    	
    	ArrayList<Job> temp = new ArrayList<Job>();
		while (PQ.size() !=0 )	{
			temp.add( PQ.extractMax() );
		}
		for (int i=0; i<temp.size(); i++)	{
    		Job job = temp.get(i);
    		if ( (tempGlobalTime-job.arrival_time() ) <waittime || job.runTime > job.job_Project.budgetOfProject )	{
    			PQ.insert(job);
    		}
    		else {
        		job.job_Status = completed;
        		globalTime += job.runTime;
        		job.completedTime = this.globalTime;
        		job.job_Project.budgetOfProject -= job.runTime;
        		
        		job.job_User.budgetConsumed += job.runTime;
        		job.job_User.latestCompletionTime = globalTime;
        		
        		listOfCompletedJobs.add (job);
//        		System.out.println("Pushed: " +job);
    		}
    	}
    }
    //(tempGlobalTime-job.arrival_time() ) <waittime || job.runTime > job.job_Project.budgetOfProject
    

    private ArrayList<JobReport_> handle_new_priority(String s) {
//    	System.out.println("Priority");
    	ArrayList <JobReport_> jobs = new ArrayList <JobReport_> ();
    	int priority = Integer.parseInt(s);
    	
    	for (int i=0; i<listOfNonCompletedJobs.size (); i++){
    		Job job= listOfNonCompletedJobs.get(i);
    		if (job.priorityOfJob >= priority) {
    			jobs.add(job);
    		}
    	}
    	for (int i=0; i<PQ.numberOfDifferentPriorities(); i++)	{
    		Job job = (Job) PQ.get(i).getElement();
    		if (job.priorityOfJob >= priority ) {
    			jobs.add(job);
    			List<Job> FIFOLIST = PQ.get(i).getFIFOList() ;
        		for (int j=0; j<FIFOLIST.size(); j++)	{
        			Job job2 = FIFOLIST.get(j);
            		if (job2.priorityOfJob >= priority) {
            			jobs.add(job2);
            		}
        		}
    		}
    	}
//    	for (JobReport_ job : jobs) System.out.print(job + ", ");
        return jobs;
    }

    private ArrayList<JobReport_> handle_new_projectuser(String[] cmd) {
//    	System.out.println("Project User");
    	User user = null;
    	Project project = null;
    	ArrayList <JobReport_> jobs = new ArrayList <JobReport_> ();

        Object searchProject = Trie.search(cmd[1]);
        if (searchProject != null) {
             project = (Project) ((TrieNode) searchProject).getValue();
        } else {
        	return null;
        }
        for (int i =0; i<listOfUsers.size(); i++)	{
        	if (listOfUsers.get(i).nameOfUser.compareTo(cmd[2]) ==0) {
        		user = listOfUsers.get(i);
        	}
        }
        if (user == null) {
        	return null;
        }
        
        int t1 = Integer.parseInt(cmd[3]);
    	int t2= Integer.parseInt(cmd[4]);
    	for (int i=0; i<listOfCompletedJobs.size (); i++){
    		Job job= listOfCompletedJobs.get(i);
    		if (job.user().compareTo(user.nameOfUser) == 0 && job.project_name().compareTo(project.nameOfProject) == 0 && job.startTime >=t1 && job.startTime<t2) {
    			jobs.add(job);
    		}
    	}
    	for (int i=0; i<listOfNonCompletedJobs.size (); i++){
    		Job job= listOfNonCompletedJobs.get(i);
    		if (job.user().compareTo(user.nameOfUser) == 0 && job.project_name().compareTo(project.nameOfProject) == 0 && job.startTime >=t1 && job.startTime<=t2 ) {
    			jobs.add(job);
    		}
    	}
    	for (int i=0; i<PQ.numberOfDifferentPriorities(); i++)	{
    		Job job = (Job) PQ.get(i).getElement();
    		if (job.user().compareTo(user.nameOfUser) == 0 && job.project_name().compareTo(project.nameOfProject) == 0 && job.startTime >=t1 && job.startTime<=t2 ) {
    			jobs.add(job);
    			List<Job> FIFOLIST = PQ.get(i).getFIFOList() ;
        		for (int j=0; j<FIFOLIST.size(); j++)	{
        			Job job2 = FIFOLIST.get(j);
            		if (job2.user().compareTo(user.nameOfUser) == 0 &&  job.project_name().compareTo(project.nameOfProject) == 0 && job2.startTime >=t1 && job2.startTime<=t2 ) {
            			jobs.add(job2);
            		}
        		}
    		}
    		
    	}
//    	for (JobReport_ job : jobs) System.out.print(job + ", ");

        return jobs;
    }

    private ArrayList<JobReport_> handle_new_user(String[] cmd) {
//    	System.out.println("User query");
    	User user = null;
    	ArrayList <JobReport_> jobs = new ArrayList <JobReport_> ();
    	for (int i =0; i<listOfUsers.size(); i++)	{
        	if (listOfUsers.get(i).nameOfUser.compareTo(cmd[1]) ==0) {
        		user = listOfUsers.get(i);
        	}
        }
        if (user == null) ;
        
    	int t1 = Integer.parseInt(cmd[2]);
    	int t2= Integer.parseInt(cmd[3]);
    	
    	for (int i=0; i<listOfCompletedJobs.size (); i++){
    		Job job= listOfCompletedJobs.get(i);
    		if (job.user().compareTo(user.nameOfUser) == 0 && job.startTime >=t1 && job.startTime<t2) {
    			jobs.add(job);
    		}
    	}
    	
    	for (int i=0; i<listOfNonCompletedJobs.size (); i++){
    		Job job= listOfNonCompletedJobs.get(i);
    		if (job.user().compareTo(user.nameOfUser) == 0 && job.startTime >=t1 && job.startTime<=t2 ) {
    			jobs.add(job);
    		}
    	}
    	for (int i=0; i<PQ.numberOfDifferentPriorities(); i++)	{
    		Job job = (Job) PQ.get(i).getElement();
    		if (job.user().compareTo(user.nameOfUser) == 0 && job.startTime >=t1 && job.startTime<=t2 ) {
    			jobs.add(job);
    			List<Job> FIFOLIST = PQ.get(i).getFIFOList() ;
        		for (int j=0; j<FIFOLIST.size(); j++)	{
        			Job job2 = FIFOLIST.get(j);
            		if (job2.user().compareTo(user.nameOfUser) == 0 && job2.startTime >=t1 && job2.startTime<=t2 ) {
            			jobs.add(job2);
            		}
        		}
    		}
    		
    	}
//    	for (JobReport_ job : jobs) System.out.print(job + ", ");

        return jobs;
    }

    private ArrayList<JobReport_> handle_new_project(String[] cmd) {
//    	System.out.println("Project query");
    	Project project = null;
    	ArrayList <JobReport_> jobs = new ArrayList <JobReport_> ();
    	Object search = Trie.search(cmd[1]);
        if (search != null) {
             project = (Project) ((TrieNode) search).getValue();
        } else {
        	return null;
        }
        
    	int t1 = Integer.parseInt(cmd[2]);
    	int t2= Integer.parseInt(cmd[3]);
    	
    	for (int i=0; i<listOfCompletedJobs.size (); i++){
    		Job job= listOfCompletedJobs.get(i);
    		if (job.project_name().compareTo(project.nameOfProject) == 0 && job.startTime >=t1 && job.startTime<t2) {
    			jobs.add(job);
    		}
    	}
    	
    	for (int i=0; i<listOfNonCompletedJobs.size (); i++){
    		Job job= listOfNonCompletedJobs.get(i);
    		if (job.project_name().compareTo(project.nameOfProject) == 0 && job.startTime >=t1 && job.startTime<=t2 ) {
    			jobs.add(job);
    		}
    	}
    	for (int i=0; i<PQ.numberOfDifferentPriorities(); i++)	{
    		Job job = (Job) PQ.get(i).getElement();
    		if (job.project_name().compareTo(project.nameOfProject) == 0 && job.startTime >=t1 && job.startTime<=t2 ) {
    			jobs.add(job);
    			List<Job> FIFOLIST = PQ.get(i).getFIFOList() ;
        		for (int j=0; j<FIFOLIST.size(); j++)	{
        			Job job2 = FIFOLIST.get(j);
            		if (job2.project_name().compareTo(project.nameOfProject) == 0 && job2.startTime >=t1 && job2.startTime<=t2 ) {
            			jobs.add(job2);
            		}
        		}
    		}
    		
    	}
//    	for (JobReport_ job : jobs) System.out.print(job + ", ");

        return jobs;
    }




    public void schedule() {
            execute_a_job();
    }

    @Override
    public void run_to_completion() {
    	while(PQ.size() >0){
    		handle_empty_line2();
    	}
    }
    public  void timed_run_to_completion(){
    	while(PQ.size() >0){
    		handle_empty_line3();
    	}
    }

	private void handle_empty_line3() {
    	if (PQ.size() == 0) return;
    	Job job = PQ.extractMax();
    	Project project = job.job_Project;
    	while (PQ.size() >= 0 ){
	    	int projectBudget = project.budgetOfProject;
	    	int jobRunTime = job.runTime;

    		if (projectBudget >= jobRunTime ) 	{
        		job.job_Status = completed;
        		globalTime += job.runTime;
        		job.completedTime = this.globalTime;
        		project.budgetOfProject -= job.runTime;
        		
        		job.job_User.budgetConsumed += job.runTime;
        		job.job_User.latestCompletionTime = globalTime;
        		
        		listOfCompletedJobs.add (job);
        		break;
        	}
    		listOfNonCompletedJobs.add(job);
    		if (PQ.size() ==0 ) break;
    		if (PQ.size() > 0 ) job = PQ.extractMax();
    		if (PQ.size() > 0 ) project = job.job_Project;
    	}
	}

	@Override
    public void handle_project(String[] cmd) {
    	System.out.println("Creating project");
    	int priority = Integer.parseInt(cmd[2] );
    	int budget = Integer.parseInt(cmd[3] );
    	Project project = new Project (cmd[1], priority, budget);
    	this.Trie.insert(cmd[1], project);
    }
	public void timed_handle_project(String[] cmd){
		int priority = Integer.parseInt(cmd[2] );
    	int budget = Integer.parseInt(cmd[3] );
    	Project project = new Project (cmd[1], priority, budget);
    	this.Trie.insert(cmd[1], project);
	}
    @Override
    public void handle_user(String name) {
    	System.out.println("Creating user");
    	User user = new User (name);
    	listOfUsers.add(user);
    }
    public void timed_handle_user(String name){ 
    	User user = new User (name);
    	listOfUsers.add(user);
    }

    @Override
    public void handle_job(String[] cmd) {
    	System.out.println("Creating job " + cmd[1]);
    	int runTime = Integer.parseInt(cmd[4] );
    	Project project = null;
    	User user = null;
    	
    	Object searchProject = Trie.search(cmd[2]);
        if (searchProject != null) {
             project = (Project) ((TrieNode) searchProject).getValue();
        } else System.out.println("No such project exists. "+ cmd[2]);
        
        for (int i =0; i<listOfUsers.size(); i++)	{
        	if (listOfUsers.get(i).nameOfUser.compareTo(cmd[3]) ==0) {
        		user = listOfUsers.get(i);
        	}
        }
        if (user == null) System.out.println("No such user exists: "+ cmd[3]);
        
        if (user != null && project != null)	{
        	Job job = new Job (cmd[1], project, user, runTime);
        	job.startTime = globalTime;
        	job.id = this.jobID++;
        	
        	project.project_JobList.add(job);
        	user.user_JobList.add(job);
        	
        	this.PQ.insert(job);
        }
    }
    public void timed_handle_job(String[] cmd) {
    	int runTime = Integer.parseInt(cmd[4] );
    	Project project = null;
    	User user = null;
    	
    	Object searchProject = Trie.search(cmd[2]);
        if (searchProject != null) {
             project = (Project) ((TrieNode) searchProject).getValue();
        } else ;
        
        for (int i =0; i<listOfUsers.size(); i++)	{
        	if (listOfUsers.get(i).nameOfUser.compareTo(cmd[3]) ==0) {
        		user = listOfUsers.get(i);
        	}
        }
        if (user == null);
        
        if (user != null && project != null)	{
        	Job job = new Job (cmd[1], project, user, runTime);
        	job.startTime = globalTime;
        	job.id = this.jobID++;
        	
        	project.project_JobList.add(job);
        	user.user_JobList.add(job);
        	
        	this.PQ.insert(job);
        }
    }
    @Override
    public void handle_query(String key) {
    	System.out.println("Querying");
    	Job job = null;
    	boolean checkingCompletedJobs = false;
    	boolean checkingNonCompletedJobs = false;
    	for (int i=0; i<listOfNonCompletedJobs.size(); i++ )	{
			if (listOfNonCompletedJobs.get(i).nameOfJob.compareTo(key) ==0) checkingNonCompletedJobs = true;
		}
    	for (int i=0; i<listOfCompletedJobs.size(); i++ )	{
			if (listOfCompletedJobs.get(i).nameOfJob.compareTo(key) ==0) checkingCompletedJobs = true;
		}
    	
    	if (PQ.search(key) == null && checkingCompletedJobs == false && checkingNonCompletedJobs == false){
    		System.out.println(key+": NO SUCH JOB");
    		return;
    	}
    		if (PQ.search (key) != null) job = PQ.search(key);
    		//else {
    			for (int i=0; i<listOfNonCompletedJobs.size(); i++ )	{
    				if (listOfNonCompletedJobs.get(i).nameOfJob.compareTo(key) ==0) job = listOfNonCompletedJobs.get(i);
    			}
    			for (int i=0; i<listOfCompletedJobs.size(); i++ )	{
    				if (listOfCompletedJobs.get(i).nameOfJob.compareTo(key) ==0) job = listOfCompletedJobs.get(i);
    			}
    		if (job.job_Status == requested ) System.out.println(key+": NOT FINISHED");
    		else System.out.println(key+": COMPLETED");
    	}
    

    @Override
    public void handle_empty_line() {
        schedule();
     }
    public void handle_empty_line2() {
    	System.out.println("Running code");
    	System.out.println("\tRemaining jobs: " + PQ.size() );
    	
    	Job job = PQ.extractMax();
    	Project project = job.job_Project;
    	while (PQ.size() >= 0 ){
			System.out.println("\tExecuting: " + job.nameOfJob + " from: "+ project.nameOfProject);
	    	int projectBudget = project.budgetOfProject;
	    	int jobRunTime = job.runTime;

    		if (projectBudget >= jobRunTime ) 	{
        		job.job_Status = completed;
        		globalTime += job.runTime;
        		job.completedTime = this.globalTime;
        		project.budgetOfProject -= job.runTime;
        		
        		job.job_User.budgetConsumed += job.runTime;
        		job.job_User.latestCompletionTime = globalTime;
        		
        		listOfCompletedJobs.add (job);
        		System.out.println("\tProject: " + project.nameOfProject + " budget remaining: " + project.budgetOfProject);
        		System.out.println("Execution cycle completed");
        		break;
        	}
    		System.out.println("\tUn-sufficient budget.");
    		listOfNonCompletedJobs.add(job);
    		if (PQ.size() ==0 ) break;
    		if (PQ.size() > 0 ) job = PQ.extractMax();
    		if (PQ.size() > 0 ) project = job.job_Project;
    	}
    }
    
    @Override
    public void handle_add(String[] cmd) {
    	System.out.println("ADDING Budget");
    	Project project = null;
    	Object search = Trie.search(cmd[1]);
        if (search != null) {
             project = (Project) ((TrieNode) search).getValue();
        } else System.out.println("No such project exists. "+ cmd[1]);
        int budgetToBeAdded = Integer.parseInt(cmd[2] );
        project.budgetOfProject += budgetToBeAdded;
        
        List<Job> temporaryList = new ArrayList<Job>();
        int length = PQ.size();
        for (int i =0; i< length; i++)	{
        	Job job = PQ.extractMax();
        	temporaryList.add(job);
        }
        length = listOfNonCompletedJobs.size();
        for (int i =0; i<listOfNonCompletedJobs.size(); i++)	{
        	Job job = listOfNonCompletedJobs.get(i);
        	if (job.job_Project.nameOfProject.compareTo(project.nameOfProject)==0) {
        		PQ.insert(job);
        		listOfNonCompletedJobs.remove(i);
        		i=-1;
        	}
        }
        length = temporaryList.size();
        for (int i =0 ; i< length; i++)	{
        	Job job = temporaryList.remove(0);
        	PQ.insert(job);
        }
    }
    
     public void print_stats() {
    	System.out.println("--------------STATS---------------");
    	System.out.println("Total jobs done: "+ listOfCompletedJobs.size());
    	
    	for (int i=0; i<listOfCompletedJobs.size(); i++	)	{
    		Job job = listOfCompletedJobs.get(i);
    		System.out.println("Job{user='" + job.job_User + "', project='" + job.job_Project + "', jobstatus=COMPLETED, execution_time=" + job.runTime+ ", end_time="+ job.completedTime +", name='" + job.nameOfJob +"'}" );
    	}
    	System.out.println("------------------------");
    	System.out.println("Unfinished jobs: ");
    	
    	for (int i=0; i<listOfNonCompletedJobs.size(); i++	)	{
    		Job job = listOfNonCompletedJobs.get(i);
    		System.out.println("Job{user='" + job.job_User + "', project='" + job.job_Project + "', jobstatus=REQUESTED, execution_time=" + job.runTime+ ", end_time=null, name='" + job.nameOfJob +"'}" );
    	}
    	System.out.println("Total unfinished jobs: " + listOfNonCompletedJobs.size() );
    	System.out.println("--------------STATS DONE---------------");
    }

    public void execute_a_job() {
    	System.out.println("Running code");
    	System.out.println("\tRemaining jobs: " + PQ.size() );
    	if (PQ.size() == 0) return;
    	Job job = PQ.extractMax();
    	Project project = job.job_Project;
    	while (PQ.size() >= 0 ){
			System.out.println("\tExecuting: " + job.nameOfJob + " from: "+ project.nameOfProject);
	    	int projectBudget = project.budgetOfProject;
	    	int jobRunTime = job.runTime;

    		if (projectBudget >= jobRunTime ) 	{
        		job.job_Status = completed;
        		globalTime += job.runTime;
        		job.completedTime = this.globalTime;
        		project.budgetOfProject -= job.runTime;
        		
        		job.job_User.budgetConsumed += job.runTime;
        		job.job_User.latestCompletionTime = globalTime;
        		
        		listOfCompletedJobs.add (job);
        		System.out.println("\tProject: " + project.nameOfProject + " budget remaining: " + project.budgetOfProject);
        		System.out.println("Execution cycle completed");
        		break;
        	}
    		System.out.println("\tUn-sufficient budget.");
    		listOfNonCompletedJobs.add(job);
    		if (PQ.size() ==0 ) {
    			System.out.println("Execution cycle completed");
    			break;
    		}
    		if (PQ.size() > 0 ) job = PQ.extractMax();
    		if (PQ.size() > 0 ) project = job.job_Project;
    	}
    }
}
