package bank;


public class Server
{
   private static final String USAGE = "java bank.Server <bank_rmi_url>";
   private static final String BANK = "rmi://localhost/bank";


   public Server(String bankName)
   {
      try
      {
         IBank bankobj = new Bank(bankName);
         // Register the newly created object at rmiregistry.
         java.rmi.Naming.rebind(bankName, bankobj);
         System.out.println(bankobj + " is ready.");
      }
      catch (Exception e)
      {
         System.err.println("Error registering the object Bank.");
      }
   }


   public static void main(String[] args)
   {
      if (args.length > 1 || (args.length > 0 && args[0].equalsIgnoreCase("-h")))
      {
         System.out.println(USAGE);
         System.exit(1);
      }

      String bankName = null;
      if (args.length > 0)
      {
         bankName = args[0];
      }
      else
      {
         bankName = BANK;
      }

      new Server(bankName);
   }
}
