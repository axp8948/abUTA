package message;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import account.Account;

public class DirectMessage extends Message
{
    //Fields
    private Account to;

    // Constructor
    public DirectMessage(Account from, Account to, Message repliedTo, String body)
    {
        super(from, repliedTo, body);
        this.to = to;
    }

    public DirectMessage(BufferedReader br, Message repliedTo) throws IOException
    {
        super(br, repliedTo);
        this.to = new Account(br);
    }

   @Override
   public String toString() {
       StringBuilder sb = new StringBuilder();
       sb.append("To: ").append(to).append("\n");
       sb.append(super.toString());
       return sb.toString();
   }

   @Override
   public void save(BufferedWriter bw) throws IOException
   {
        super.save(bw);
        to.save(bw);
   }
}
