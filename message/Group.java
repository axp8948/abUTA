package message;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

/**
* Group represents a group with name and active status.
*
* @author Anmol Pandey
* @version 1.0
* @since 1.0
* @license.agreement Gnu General Public License 3.0
*/

public class Group 
{
    // Fields
    private String name;
    private boolean active;

    // Constructor
    /**
     * Constructs a Group object with the specified name.
     * @since 1.0
     * @param name
     * @throws IllegalArgumentException
     * @since 1.0
     */
    public Group(String name)
    {
        if (name == null || name.isEmpty())
        {
            throw new IllegalArgumentException("Name can not be empty!");
        }
        this.name = name;
        this.active = true;
    }

    public Group(BufferedReader br) throws IOException
    {
        this.name = br.readLine();
        this.active = Boolean.parseBoolean(br.readLine());
    }

    // Methods
    /**
     * Returns whether the group is active or not
     * @since 1.0
     * @return true or false
     */
    public boolean isActive()
    {
        return this.active;
    }

    /**
     * Disables the active status
     * @since 1.0
     * @return void
     */
    public void disable()
    {
        this.active = false;
    }

    /**
     * Enables the active status
     * @since 1.0
     * @return void
     */
    public void enable()
    {
        this.active = true;
    }

    /**
     * returns name if active is true else name + inactive
     * @since 1.0
     * @return name
     */
    @Override
    public String toString()
    {
        return active ? name : name + " [inactive]"; // return name if active is true else name + inactive
    }

    public void save(BufferedWriter bw) throws IOException
    {
        bw.write(name + '\n');
        bw.write("" + active + '\n');
    }

}
