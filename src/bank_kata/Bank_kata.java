/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank_kata;

import java.io.BufferedWriter;
import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hichem
 */
public class Bank_kata {

    /**
     * @param args the command line arguments
     */
    //static File file=null; // fle object
    
    public static void main(String[] args) {
        Account client = new Account(); // instance of the account classe. that represent a client count.
        process(access(client), client); // procedure for processing all the operations or exit.
    }
    /*******************************************************************************************/
    // procedure accessing the menu for deposit withdraw, consult historic .
    public static void process(boolean valid, Account client){
        String choice="0" ;         // var choice indicating the choice of user
        Scanner sc; //  object for read the informations 
        String inpId; // 
        double amount;// the amount of transaction
        File file; // fle object
        System.out.println(" Welcome to your Bank E_Count : " + client.user);
        System.out.println(" Your Balance is : " + client.Balance);
        OUTER:
        while ((!choice.equals("4"))&&(valid==true)) {
            System.out.println("\n Choose Operation : ");
            System.out.println("    -  1 : Deposit");
            System.out.println("    -  2 : Withdraw");
            System.out.println("    -  3 : Consult your operations");
            System.out.println("    -  4 : Exit.");
            sc = new Scanner(System.in);
            choice  = sc.next();
            //read the choice and check
            switch (choice) {
            // withdarwal operation
                case "1":
                    System.out.println("Enter the amount of your deposit in euro :");
                    try
                    {
                        amount = Integer.parseInt(sc.next());// amount conversion and validation
                        if (amount>0){
                            client.Balance += amount;// add the amount to the balance
                            System.out.println("Operation validated");
                            System.out.println("your new balance is : "+ client.Balance);
                            // procedure for validating operation. with the actual date,
                            insert_operation("Deposit",amount, new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()), client.Balance, client.id);
                        } 
                    }
                    catch(NumberFormatException e){
                        System.out.println("The value is not correct");// value not correct.
                    }
                    break;
            // the 3rd choice printing the history of operation
                case "2":
                    System.out.println("Enter the amount of your withdrawal in euro :");
                    try
                    {
                        amount = Integer.parseInt(sc.next());
                        if (amount>client.Balance){
                            //the amount of withdarawal must be less than the balance ;
                            System.out.println("Sorry, you do not have that sum, you can make maximum withdrawal of: "+ client.Balance);
                        } 
                        else if(amount >0){
                            client.Balance -= amount; // withdrawal operation.
                            System.out.println("Operation validated");
                            System.out.println("your new balance is :"+ client.Balance);// 
                            insert_operation("Withdrw ",amount,new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()), client.Balance, client.id);
                        }
                        
                    }
                    catch(NumberFormatException e){
                        System.out.println("The value is not correct"); // bad number 
                    }
                    break;
                case "3":
                    try {
                        file = new File("ressources/trace.bnk.txt"); // read the file of operation containing id count, operation type, the amount , date and time
                        sc = new Scanner (file);
                        System.out.println("the Historic of your operations is :");
                        System.out.println("|----------------------------------|");
                        System.out.println("   D/W   AMOUNT     DATE     TIME  ");
                        while(sc.hasNextLine()) 
                        {
                            inpId= sc.next();
                            if (client.id.equals(inpId)) // when match the count number
                            {
                                sc.nextLine();
                                System.out.println(" "+sc.nextLine()); // printing the operation historic.
                            }
                            else
                            {
                                sc.nextLine();
                            }
                        }
                    }
                    catch (FileNotFoundException ex)
                    {
                        Logger.getLogger(Bank_kata.class.getName()).log(Level.SEVERE, null, ex);
                    }   break;
                case "4":
                    break OUTER;
                default:
                    System.out.println("enter valid choice");
            }
        }
    
    }

    /*******************************************************************************************/
    // function to validate the input information from the user, compared to the informations in the file base.bnk.txt
    public static boolean access(Account client){
        String inpUser =""; // store the input user name 
        String inpPass =""; // store the input pass
        File file; // fle object
        boolean valid= false;
        String inpId;
        Console console = System.console();
        Scanner sc = new Scanner(System.in); // scanner to read the information inputed from user.
        System.out.println("Enter your count number");  
        client.id = console.readLine();
                //sc.next();
        System.out.println("Enter your name");  
        client.user = console.readLine();
        //client.user = sc.next();
        client.pass =  new String(console.readPassword("Please enter your password: "));
        System.out.println(client.user + " " + client);  
        //client.pass = sc.next(); 
        try {
                file = new File("ressources/base.bnk.txt");
                sc = new Scanner (file);//read the file base.bnk.txt containing the information of all users. count number, name password, balance
            } 
        catch (FileNotFoundException ex) 
            {
                Logger.getLogger(Bank_kata.class.getName()).log(Level.SEVERE, null, ex);
            }
            while(sc.hasNextLine())
            {
                inpId= sc.next();
                if (client.id.equals(inpId)) // if the count number is matched then read the other informations.
                {
                    inpUser = sc.next();
                    inpPass = sc.next(); // gets input from user
                    client.Balance = Double.parseDouble(sc.next()); 
                    break;
                }
                else // other loop
                {
                    sc.nextLine();
                }
            }
        if (inpUser.equals(""))
        {
            System.out.println("ce numero de compte n'exite pas");
        }
        // 3 time password and user validation.
        int i = 1;
        while (i < 3)
        {
        // user and password validation.
        if (inpUser.equalsIgnoreCase(client.user) && inpPass.equals(client.pass)) {
            System.out.println("Welcome to the Bank Console");
            valid=true;
            break;
            //access granted.
        } else {
                System.out.println("wrong password, check your informations");
            }
            sc = new Scanner(System.in); // scanner to read the information inputed from user.
            i++;       
            System.out.println("for the "+ i  +" th Reenter your count number, please check your informations before validating");  
            System.out.println("Enter Valid name");  
            client.user = sc.next();  
            console = System.console();
            client.pass =
            new String(console.readPassword("Please enter your password: "));
            //System.out.println("Enter Valid password");  
            //client.pass = sc.next();  
        }
        return valid;

    }
    
    /*******************************************************************************************/
    // procedure for inserting operations in texte files.
   public static void insert_operation(String operation, double amount, String date, double balance, String id)
    {
        // insert the operation in the file of operation history
            BufferedWriter bw = null; // used for writing on file
            PrintWriter pw;// used for writing on file
            FileWriter fw = null;// used for writing on file        
            File file; // fle object
        try {
            file = new File("ressources/trace.bnk.txt");
            fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);
            bw.write(id + "\n"+ operation + " " + amount + " " + date + "\n");
            }
        catch (IOException e) {
            System.out.println("erreur system"+ e);
            }
        finally {
            try {
                if (bw != null)
                    bw.close();
                if (fw != null)
                    fw.close();
            }
            catch (IOException ex) {
            System.out.println("erreur system"+ ex);
            }
        }
        // update the balance value in the file base.bnk.txt
        Scanner sc;
        file = new File("ressources/base.bnk.txt"); // scan the base file for updating the balance 
        String text_file ="", // the new texte to update the file
                text; 
        try {
            sc = new Scanner(file);
            while(sc.hasNext())
            { text = sc.next();
                text_file += " " + text; // populating the new texte 
                if (id.equals(text)) 
                {                 
                text_file += " " +sc.next();
                text_file += " " +sc.next();
                sc.next();
                text_file += " " +balance; //inserting the new balance
                }
                else if (text.equals(";"))
                    text_file += " \n" ; // when reach the ; jump to the new line
                else
                    text_file += " "+ sc.next();
            }
            pw = new PrintWriter(file);
            pw.write(""); // erase the file
            pw.write(text_file);// populate the file.
            pw.close();
        } catch (Exception ex) {
            Logger.getLogger(Bank_kata.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
    

