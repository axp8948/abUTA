package account;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class Account
{
    // Fields or Attributes
    private String name;
    private int id;
    private static int nextID = 1;
    private AccountStatus status;
 
    // Constructor 
    public Account(String name)
    {
        // Validate name
        if(name.length() == 0)
        {
            throw new IllegalArgumentException("Invalid Name!!");
        }
        else
        {
            this.name = name;
        }

        // set the id to static attribute nextID and increment nextID
        this.id = Account.nextID;
        Account.nextID++;

        // set the status field to value of Normal enum
        this.status = AccountStatus.Normal;
    }

    public Account(BufferedReader br) throws IOException
    {
        this.name = br.readLine();
        this.id = Integer.parseInt(br.readLine());
        Account.nextID = Integer.parseInt(br.readLine());
        this.status = AccountStatus.valueOf(br.readLine()); // valueOf converts string to Enum
    }

    // Methods
    public void setStatus(AccountStatus status)
    {
        this.status = status;
    }

    public boolean isMuted()
    {
        if(this.status != AccountStatus.Normal)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean isBlocked()
    {
        if(this.status == AccountStatus.Blocked)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public String toString()
    {
        if (this.status != AccountStatus.Normal)
        {
            return this.name +  " (" + this.id + ")" + " [" + this.status + "]";
        }
        else
        {
            return this.name +  " (" + this.id + ")";
        }
    }

    public void save(BufferedWriter bw) throws IOException
    {
        bw.write(name + '\n');
        bw.write("" + id + '\n');
        bw.write("" + nextID + '\n');
        bw.write(status.name() + '\n'); // writing the string version of enum
    }
}