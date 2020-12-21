package ProjectManagement;
import java.util.ArrayList;

public class User implements Comparable<User>, UserReport_ {

	String nameOfUser;
	public int budgetConsumed;
	public int latestCompletionTime;
	public ArrayList <Job> user_JobList;
	
	public User	(String name)	{
		this.nameOfUser= name;
		this.budgetConsumed = 0;
		this.latestCompletionTime = 0;
		this.user_JobList = new ArrayList <Job> ();
	}
    @Override
    public int compareTo(User user) {
        if (this.budgetConsumed<user.budgetConsumed) return -1;
        else if (this.budgetConsumed> user.budgetConsumed ) return 1;
        else {
        	if (this.latestCompletionTime < user.latestCompletionTime ) return 1;
        	else if (this.latestCompletionTime > user.latestCompletionTime ) return -1;
        	else return 0;
        }
    }
    public String toString()	{
    	return nameOfUser;
    }
    public String user()    { return nameOfUser; }

    public int consumed() { return this.budgetConsumed; }
}