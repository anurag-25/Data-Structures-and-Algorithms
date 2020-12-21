package ProjectManagement;

public class User implements Comparable<User> {

	String nameOfUser;
	public User	(String name)	{
		this.nameOfUser= name;
	}
    @Override
    public int compareTo(User user) {
        if (this.nameOfUser.compareTo(user.nameOfUser) <0 ) return -1;
        else if (this.nameOfUser.compareTo(user.nameOfUser) >0 ) return 1;
        else return 0;
    }
    public String toString()	{
    	return nameOfUser;
    }
}