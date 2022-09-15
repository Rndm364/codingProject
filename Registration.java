package net.htlgrieskirchen.SimmerD190277.starters;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import net.htlgrieskirchen.SimmerD190277.sports.Starter;

/**
 *
 * @author dominik
 */
public class Registration{

    private final Scanner scanner = new Scanner(System.in);
    private final List<Starter> starterList = new LinkedList<>();
    private final static Logger LOGGER = Logger.getLogger(Registration.class.getName());
    
    public static void main(String[] args){
        initLogger();
        Registration reg = new Registration();
        reg.scanner.useDelimiter("\n");
        String response;
        do{
           reg.readAndAddStarter();
           System.out.println("Wollen Sie weitere Starter erfassen (J/N)?");
           response = reg.scanner.next();
        }while (response.toUpperCase().equals("J"));
        LOGGER.info("Starter wurden erfasst.");
        reg.printStarters();
        LOGGER.info("Starter wurden angezeigt.");
        reg.saveStarters();
        LOGGER.info("Starter wurden in CSV-Datei gespeichert.");
    
    }
    private void readAndAddStarter(){
        System.out.print("Geben Sie den Nachnamen des Starters ein: ");
        String name = scanner.next();
        System.out.print("Geben Sie den Vornamen des Starters ein: ");
        String firstname = scanner.next();
        starterList.add(new Starter(name, firstname));
    }
    
     private void printStarters(){
        for(Starter starter : starterList){
            System.out.println(starter);
        }
    }
     
     private void saveStarters(){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("../Starters/Starters.csv"))){
            for (Starter starter : starterList) {
                bw.write(starter.getName() + ";" + starter.getFirstname());
                bw.newLine();
            }
        }catch (IOException ex){
            System.err.println("Fehler beim Speichern der Starterliste");
            LOGGER.severe("Fehler beim Speichern der Starterliste");
        }
    }
     
     private static void initLogger(){
        try{
            LOGGER.setLevel(Level.INFO);
            Logger rootLogger = Logger.getLogger("");
            Handler[] handlers = rootLogger.getHandlers();
            if (handlers[0] instanceof ConsoleHandler){
                rootLogger.removeHandler(handlers[0]);
            }
            
            FileHandler fileTxt = new FileHandler("../Starters/RegistrationLog.txt", true);
            SimpleFormatter formatterTxt = new SimpleFormatter();
            fileTxt.setFormatter(formatterTxt);
            LOGGER.addHandler(fileTxt);
        } catch (IOException | SecurityException ex){
            System.err.println(ex);
        }
      
    
}
}