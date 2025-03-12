package message;
import account.Account;
import account.AccountStatus;

import java.util.Date;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Message
{
    // Fields
    private Account from;
    private Date date;
    private Message repliedTo;
    private ArrayList <Message> replies;
    private String body;

    // Constructor
    public Message(Account from, Message repliedTo, String body)
    {
        this.from = from;
        this.repliedTo = repliedTo;
        this.body = body;
        this.date = new Date();
        this.replies = new ArrayList <>();

        if (repliedTo != null)
        {
            repliedTo.addReply(this);
        }
    }

    public Message(BufferedReader br, Message repliedTo) throws IOException
    {
        this.from = new Account(br);
        
        this.date = new Date(Long.parseLong(br.readLine().trim()));

        this.body = br.readLine();

        String repliesLine;
        do 
        {
            repliesLine = br.readLine();

        } 
        while (repliesLine != null && repliesLine.isEmpty());

        int numOfReplies = 0;
        if (repliesLine != null) 
        {
            try 
            {
                numOfReplies = Integer.parseInt(repliesLine.trim());

            } catch (NumberFormatException e) 
            {
                System.out.println("Error parsing reply count: '" + repliesLine + "'");
                
            }
        }

        this.replies = new ArrayList<>();

        for(int i = 0; i < numOfReplies; i++)
        {
            String s = br.readLine();
            if(s.equals("message.Post"))
            {
                replies.add(new Post(br, this));
            }
            else if(s.equals("message.DirectMessage"))
            {
                replies.add(new DirectMessage(br, this));
            }
        }

        this.repliedTo = repliedTo;
        if(this.repliedTo != null)
        {
            this.repliedTo.addReply(this);
        }
    }

    // Methods
    public Message getRepliedTo()
    {
        return repliedTo;
    }

    public Message getReply(int index)
    {   
        Message m = null;
        if (index >= 0 && index < replies.size())
        {
            m = replies.get(index);
        }
        else
        {
            System.out.println("Invalid reply index: " + index); 
        }
       
        return m;
    }

    public int getNumReplies() 
    {
        return replies.size();
    }

    private void addReply(Message message)
    {
        replies.add(message);

    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("Date: ").append(date).append("\n");
        sb.append("From: ").append(from).append("\n");

        if (repliedTo != null)
        {
            sb.append("In reply to: ").append(repliedTo.from).append("\n"); // repliedTo.from
        }

        if (!replies.isEmpty()) // !replies.isEmpty() 
        {
            sb.append("Replies: ");
        
            for(int i = 0; i < replies.size(); i++ )
            {
                sb.append(" [" + i + "] " + replies.get(i).from);
            }
            sb.append("\n");
        }

        sb.append("\n").append(body).append("\n");
        return sb.toString();
    }

    public void save(BufferedWriter bw) throws IOException
    {
        from.save(bw);
        bw.write("" + date.getTime() + '\n');
        
        // To ensure there are not double new lines!
        String cleanBody = body.trim();
        bw.write(cleanBody + '\n');
    
        
        bw.write("" + replies.size() + '\n');
        // System.out.println("Replies size: " + replies.size()); // Debug Line

        for(Message reply: replies)
        {
            bw.write(reply.getClass().getName() + '\n');
            reply.save(bw);
        }

    }
}