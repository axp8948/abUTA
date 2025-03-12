package message;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import account.Account;


public class Post extends Message
{
    // Fields
    private Group group;

    //Constructor
    public Post(Account from, Group group, Message repliedTo, String body)
    {
        super(from, repliedTo, body);
        this.group = group;
    }

    public Post(BufferedReader br, Message repliedTO) throws IOException
    {
        super(br, repliedTO);
        this.group = new Group(br);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Group: ").append(group).append("\n");
        sb.append(super.toString()).append("\n");
        return sb.toString();
    }

    @Override
    public void save(BufferedWriter bw) throws IOException
    {
        super.save(bw);
        group.save(bw);
    }
}
