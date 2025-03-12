package abuta;

import account.Account;
import message.Group;
import message.Message;
import menu.Menu;
import menu.MenuItem;
import message.Post;
import message.DirectMessage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class Abuta{
    
    // Fields 
    private ArrayList<Account> accounts;
    private ArrayList<Group> groups;
    private Message message;
    private Menu menu;
    private String output;
    private boolean running;
    private String filename = "output.txt";

    // Constructor
    public Abuta()
    {
        this.running = true;
        this.menu = new Menu();

        this.accounts = new ArrayList<>();
        accounts.add(new Account("John"));
        accounts.add(new Account("Tom"));
        accounts.add(new Account("Harry"));
        accounts.add(new Account("Sam"));
        accounts.add(new Account("Teddy"));

        this.groups = new ArrayList<>();
        groups.add(new Group("The Galactic Explorers"));
        groups.add(new Group("Cosmic Crusaders"));
        groups.add(new Group("Solar Flare Squad"));
        groups.add(new Group("Planetary Pioneers"));
        groups.add(new Group("Stellar Syndicate"));

        message = new Post (accounts.get(0), groups.get(0), null, "Hello from Mars!!");

        menu.addMenuItem(new MenuItem("Exit", () -> endApp()));
        menu.addMenuItem(new MenuItem("Show Reply", () -> showReply()));
        menu.addMenuItem(new MenuItem("Show Replied To", () -> showRepliedTo()));
        menu.addMenuItem(new MenuItem("Reply", () -> reply()));
        menu.addMenuItem(new MenuItem("New Abuta", () -> newAbuta()));
        menu.addMenuItem(new MenuItem("Save", () -> save()));
        menu.addMenuItem(new MenuItem("Save As", () -> saveAs()));
        menu.addMenuItem(new MenuItem("Open", () -> open()));
    }

    // Methods
    public static void main(String[] args)
    {
        Abuta abuta = new Abuta();
        abuta.mdi();
    }

    public void mdi()
    {
        while(running)
        {
            try
            {
                System.out.println("");
                System.out.println("===================> abUTA <=====================");
                System.out.println();
                System.out.println(menu.toString());

                System.out.println("=================================================");

                System.out.println(message.toString());

                if (output != null && !output.isEmpty())
                    System.out.println(output);

                output = "";

                System.out.println();
                int selection = Menu.getInt("Selection: ");
                menu.run(selection);
            }
            catch(Exception e)
            {
                System.out.println(e.getMessage());
                running = false;
            }
        }

    }

    private void endApp()
    {
        this.running = false;
    }

    private void showRepliedTo()
    {
        if(message.getRepliedTo() == null)
        {
            this.output = "The current message was not in reply to any other message!";
        }
        else
        {
            message = message.getRepliedTo();
        }
    }

    private void showReply()
    {
        int numOfReplies = message.getNumReplies();

        // System.out.println("Number of replies: " + numOfReplies);  // Debugging line

        if(numOfReplies == 0)
        {
            this.output = "The current message has no replies!";
        }
        else if (numOfReplies == 1)
        {
            this.message = message.getReply(0);
        }
        else if (numOfReplies >= 2)
        {
            System.out.println("This message has " + numOfReplies + " replies.");
            int selection = Menu.getInt("To which reply abUTA should switch (0 to " + (numOfReplies - 1) + ")" +": ");

            if (selection >= 1 && selection <= numOfReplies)
            {
                message = message.getReply(selection - 1);
            }
            else
            {
                System.out.println("Invalid Choice!");
            }

        }

    }

    private void reply()
    {
        // Add data validation!!!
        char typeOfMessage = Menu.getChar("(P)ost or (D)irect Message? ");
        typeOfMessage = Character.toUpperCase(typeOfMessage);

        Integer indexOfAuthor = Menu.selectItemFromList("Author? ", accounts);
        Account author = accounts.get(indexOfAuthor);

        Message newMessage = null;

        if(typeOfMessage == 'P')
        {
            int indexOfGroup = Menu.selectItemFromList("Group? ", groups);

            Group group = groups.get(indexOfGroup);

            String messageBody = Menu.getString("Post from " + author + " in group " + group + ":");

            newMessage = new Post(author, group, message, messageBody);
            

        }
        else if (typeOfMessage == 'D')
        {
            int indexOfReceiver = Menu.selectItemFromList("To? ", accounts);

            Account receiver = accounts.get(indexOfReceiver);

            String messageBody = Menu.getString("Message from " + author + "to " + receiver + ":");

            newMessage = new DirectMessage(author, receiver, message, messageBody);

        }
        if (newMessage != null) 
        {
            message = newMessage; 
        
        }

    }

    private void newAbuta()
    {
        message = new Post(accounts.get(0), groups.get(0), null, "Hello from Mars!!");
    }

    private void save()
    {
        if(filename == null)
        {
            output = "Invalid filename!!";
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) 
        {
            Message root = message;

            while (root.getRepliedTo() != null)
            {
                root = root.getRepliedTo();
            }
            
            root.save(bw);
            output = "Saved!";

        } catch (IOException error) 
        {
            output = "Couldn't save to the file!";
            error.printStackTrace();
        }
    }

    private void saveAs()
    {
        String newFileName = Menu.getString("Enter a new filename: ");
        if(newFileName != null)
        {
            filename = newFileName;
            save();
        }
    }

    private void open()
    {
        String newFileName = Menu.getString("Enter the filename that you want to open: ");
        if(newFileName != null)
        {
            filename = newFileName;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(filename)))
        {
            message = new Post(br, null);
            output = "File Opened!";
        } catch (IOException error) {
            output = "Couldn't open the file";
            error.printStackTrace();
        }
    }

}