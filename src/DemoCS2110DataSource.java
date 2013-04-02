/**
 * Name: Travis Dean and Alex Martinez
 * Unix ID: tjd2qj and cam4sn
 * Assignment: HW4
 * Lab Section: 102
 */
import java.util.Scanner;


public class DemoCS2110DataSource
{
    public static void main(String[] args)
    {
        ProfileDataSource ps = new CS2110Profile();
        ps.init();

        // Add stuff here to test further.
        System.out.println("Enter an ID and Category (\"quit\" to end).");
        System.out.print("> ");
        Scanner scn = new Scanner(System.in);
        String id = scn.next();
        while ( ! id.equals("quit") ) {
            String cat = scn.next();
            System.out.println(id + "," + ps.getName(id) + "," + ps.getGroup(id)
                    + "," + cat + ": " + ps.getCategoryValues(id, cat));
            System.out.print("> ");
            id = scn.next();
        }
        System.out.println("Program done.");
    }
}
