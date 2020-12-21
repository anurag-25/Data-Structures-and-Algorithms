package ProjectManagement;

import PriorityQueue.MaxHeap;
import PriorityQueue.PriorityQueueDriverCode;
import RedBlack.RBTree;
import RedBlack.RedBlackNode;
import Trie.Trie;
import Trie.TrieNode;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Scheduler_Driver extends Thread implements SchedulerInterface {

    public static void main(String[] args) throws IOException {
    	
        Scheduler_Driver scheduler_driver = new Scheduler_Driver();

        File file;
        if (args.length == 0) {
            URL url = PriorityQueueDriverCode.class.getResource("INP");
            file = new File(url.getPath());
        } else {
            file = new File(args[0]);
        }

        scheduler_driver.execute(file);
    }
    
    public void execute(File file) throws IOException {
    	
    	

        URL url = Scheduler_Driver.class.getResource("INP");
        file = new File(url.getPath());

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            System.err.println("Input file Not found. "+file.getAbsolutePath());
        }
        String st;
        while ((st = br.readLine()) != null) {
            String[] cmd = st.split(" ");
            if (cmd.length == 0) {
                System.err.println("Error parsing: " + st);
                return;
            }

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
                case "":
                    handle_empty_line();
                    break;
                case "ADD":
                    handle_add(cmd);
                    break;
                default:
                    System.err.println("Unknown command: " + cmd[0]);
            }
        }


        run_to_completion();

        print_stats();

    }
    
	RBTree<String, Project> RBT = new RBTree();
    MaxHeap<Job> PQ = new MaxHeap<>();
    Trie<Project> Trie = new Trie <Project> ();
    RBTree <String, User> UserRBT = new RBTree ();
    List <Job> listOfCompletedJobs = new ArrayList <Job> ();
    List <Job> listOfNonCompletedJobs = new ArrayList <Job> ();
    
    public static int requested = 0;
    public static int completed =1;
    
    public int globalTime=0;

    @Override
    public void run() {
        // till there are JOBS
        schedule();
    }


    @Override
    public void run_to_completion() {
    	while(PQ.size() >0){
    		handle_empty_line2();
    	}
    }

    @Override
    public void handle_project(String[] cmd) {
    	System.out.println("Creating project");
    	int priority = Integer.parseInt(cmd[2] );
    	int budget = Integer.parseInt(cmd[3] );
    	Project project = new Project (cmd[1], priority, budget);
    	this.Trie.insert(cmd[1], project);
    	this.RBT.insert(cmd[1], project);
    }

    @Override
    public void handle_job(String[] cmd) {
    	System.out.println("Creating job");
    	int runTime = Integer.parseInt(cmd[4] );
    	Project project = null;
    	User user = null;
    	
    	Object search = Trie.search(cmd[2]);
        if (search != null) {
             project = (Project) ((TrieNode) search).getValue();
        } else System.out.println("No such project exists. "+ cmd[2]);
        
       
        
        Object searchUser = UserRBT.search(cmd[3]);
        if ( ((RedBlackNode<String, User>) searchUser).getValue() != null) {
            user = ((RedBlackNode<String,User>) searchUser).getValue();

        } else System.out.println("No such user exists: "+ cmd[3]);
        if (user != null && project != null)	{
        	Job job = new Job (cmd[1], project, user, runTime);
        	this.PQ.insert(job);
        }
    }

    @Override
    public void handle_user(String name) {
    	System.out.println("Creating user");
    	User user = new User (name);
    	UserRBT.insert(name, user);
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
    		//}
    		//else {
    			for (int i=0; i<listOfCompletedJobs.size(); i++ )	{
    				if (listOfCompletedJobs.get(i).nameOfJob.compareTo(key) ==0) job = listOfCompletedJobs.get(i);
    			}
    		//}
    		if (job.job_Status == requested ) System.out.println(key+": NOT FINISHED");
    		else System.out.println(key+": COMPLETED");
    	}
    

    @Override
    public void handle_empty_line() {
    	System.out.println("Running code");
    	System.out.println("Remaining jobs: " + PQ.size() );
    	
    	Job job = PQ.extractMax();
    	Project project = job.job_Project;
    	while (PQ.size() >= 0 ){
			System.out.println("Executing: " + job.nameOfJob + " from: "+ project.nameOfProject);
	    	int projectBudget = project.budgetOfProject;
	    	int jobRunTime = job.runTime;

    		if (projectBudget >= jobRunTime ) 	{
        		job.job_Status = completed;
        		project.budgetOfProject -= job.runTime;
        		listOfCompletedJobs.add (job);
        		job.completedTime = globalTime;
        		System.out.println("Project: " + project.nameOfProject + " budget remaining: " + project.budgetOfProject);
        		System.out.println("Execution cycle completed");
        		break;
        	}
    		System.out.println("Un-sufficient budget.");
    		listOfNonCompletedJobs.add(job);
    		job = PQ.extractMax();
    		project = job.job_Project;
    	}
    	
    }
    public void handle_empty_line2() {
    	System.out.println("Running code");
    	System.out.println("Remaining jobs: " + PQ.size() );
    	
    	Job job = PQ.extractMax();
    	Project project = job.job_Project;
    	while (PQ.size() >= 0 ){
			System.out.println("Executing: " + job.nameOfJob + " from: "+ project.nameOfProject);
	    	int projectBudget = project.budgetOfProject;
	    	int jobRunTime = job.runTime;

    		if (projectBudget >= jobRunTime ) 	{
        		job.job_Status = completed;
        		project.budgetOfProject -= job.runTime;
        		listOfCompletedJobs.add (job);
        		job.completedTime = globalTime;
        		System.out.println("Project: " + project.nameOfProject + " budget remaining: " + project.budgetOfProject);
        		System.out.println("System execution completed");
        		break;
        	}
    		System.out.println("Un-sufficient budget.");
    		listOfNonCompletedJobs.add(job);
    		job = PQ.extractMax();
    		project = job.job_Project;
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

    @Override
    public void print_stats() {
    	System.out.println("--------------STATS---------------");
    	System.out.println("Total jobs done: "+ listOfCompletedJobs.size());
    	
    	for (int i=0; i<listOfCompletedJobs.size(); i++	)	{
    		Job job = listOfCompletedJobs.get(i);
    		globalTime+=job.runTime;
    		System.out.println("Job{user='" + job.job_User + "', project='" + job.job_Project + "', jobstatus=COMPLETED, execution_time=" + job.runTime+ ", end_time="+ globalTime +", name='" + job.nameOfJob +"'}" );
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

    @Override
    public void schedule() {

    }
}
