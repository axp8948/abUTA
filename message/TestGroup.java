package message;

public class TestGroup 
{
    public static void main(String[] args)
    {
        int result = 0;
        int vector = 1;

        String expected = "";
        String actual = "";

        Group g1 = new Group("Anmol");

        //Test the constructor and toString together.
        expected = "Anmol";
        actual = g1.toString();
        if(!expected.equals(actual))
        {
            System.err.println(" \nERROR: Nominal case toString");
            System.err.println("    Expected: " + expected);
            System.err.println("    Actual: " + actual);
            result |= vector;
        }
        vector <<= 1;


        // Verify that the newly constructed group is active by default
        if (g1.isActive() == false)
        {
            System.err.println("\n ERROR: Nominal case default status");
            System.err.println("     Expected: true");
            System.err.println("     Actual: " + g1.isActive());
            result |= vector; // Bitwise or
        }

        vector <<= 1; // Left Bitshift by  1;

        // verify that the disable method makes the group inactive
        g1.disable();
        if(g1.isActive() == true)
        {
            System.err.println("\n ERROR: Disabled case status");
            System.err.println("    Expected: false");
            System.err.println("    Actual: " + g1.isActive());
            result |= vector;
        }

        vector <<= 1;

        // Verify that an inactive group reports " [inactive]" as part of its toString method.
        expected = "Anmol [inactive]";
        actual = g1.toString().trim();

        if(!actual.equals(expected))
        {
            System.err.println(" \n ERROR: inactive toString");
            System.err.println("    Expected: " + expected);
            System.err.println("    Actual: " + actual);
            result |= vector;
        }

        vector <<= 1;

        // Verify that method enable makes an inactive Group active again.
        g1.enable();
        if(g1.isActive() == false)
        {
            System.err.println(" \n ERROR: Enabled case active status");
            System.err.println("    Expected: false");
            System.err.println("    Actual: " + g1.isActive());
            result |= vector;
        }

        vector <<= 1;


        // zero length name case

        try
        {
            g1 = new Group("");
            System.err.println("\nERROR: Zero-length name case");
            System.err.println("    Expected: IllegalArgumentException");
            System.err.println("    Actual:   (No exception)");
            result |= vector;
        }
        catch(IllegalArgumentException e)
        {}
        catch(Exception e)
        {
            System.err.println("\nERROR: Zero-length name case");
            System.err.println("    Expected: IllegalArgumentException");
            System.err.println("    Actual:   " + e.getClass().getSimpleName());
            result |= vector;
        }

        vector <<= 1;


        // Show results
        if (result != 0)
        {
            System.err.println("\nFAIL: error code " + result);
        }
        System.exit(result);

    }
}
