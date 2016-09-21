/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rubrica;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;

/**
 *
 * @author david
 */
public class Rubrica {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        String scelta;
        boolean running = true;
        Scanner sc = new Scanner(System.in);
        DBConnect connect = new DBConnect();
        
        while(running){
            mostraMenuPrincipale();
            scelta = sc.nextLine();
            
            switch(scelta){
                case "0": 
                    saluti();                    
                    running = false;
                    break;
                 
                case "1":                    
                    nuovoContatto(connect);
                    break;
                    
                case "2": 
                    modificaContatto(connect);
                    break;
                    
                case "3":
                    eliminaContatto(connect);
                    break;
                    
                case "4":
                    cercaContatto(connect);
                    break;
                    
                case "5":                    
                    visualizzaListaContatti(connect, true, "");                    
                    break;
                    
                default:
                    System.out.println("\nInput non valido\n");
                    break;
            }        
        }
    }
    
    private static void mostraMenuPrincipale(){
        
        System.out.println("");
        System.out.println("_________________________________________");
        System.out.println("|                   |                   |");
        System.out.println("|       - 1 -       |       - 2 -       |");
        System.out.println("| Aggiungi contatto | Modifica contatto |");
        System.out.println("|___________________|___________________|");
        System.out.println("|                   |                   |");
        System.out.println("|       - 3 -       |       - 4 -       |");
        System.out.println("| Elimina contatto  |  Cerca contatto   |");
        System.out.println("|___________________|___________________|");
        System.out.println("|                   |                   |");
        System.out.println("|       - 5 -       |       - 0 -       |");
        System.out.println("|       Lista       |       Esci        |");
        System.out.println("|___________________|___________________|");
        System.out.print("Operazione: ");
    }
    
    private static void saluti(){
        
        System.out.println("\nGrazie per aver usato il programma.");
        System.out.println("Ci auguriamo una continua collaborazione...");
        System.out.println("Poichè non sono depositario della conoscenza assoluta, eventuali modifiche, bugfix e suggerimenti verranno inclusi nelle prossime versioni.");
    }
    
    private static void nuovoContatto(DBConnect connect){
        
        int id,
            i;
        
        String cognome,
               nome,
               citta,
               indirizzo,
               tipo,
               numero,
               scelta;
        
        boolean running = true;                 
        
        Scanner sc = new Scanner(System.in);
        Scanner scInt = new Scanner(System.in);
        
        ArrayList<Numeri> recapiti = new ArrayList<>();
        
        System.out.print("\n");
        System.out.println("--------------------------");
        System.out.println("Inserimento nuovo contatto");
        System.out.println("--------------------------");
        
        System.out.print("Cognome: ");
        cognome = sc.nextLine();
        
        System.out.print("Nome: ");
        nome = sc.nextLine();
        
        System.out.print("Citta': ");
        citta = sc.nextLine();
        
        System.out.print("Indirizzo: ");
        indirizzo = sc.nextLine();
        
        // creazione oggetto anagrafica
        Contatto anagrafica = new Contatto(0, cognome, nome, citta, indirizzo);
        
        // Se è una rubrica di numeri, logica vuole che deve essere memorizzato almeno un numero... almeno credo
        while(running){
            System.out.print("Tipologia di numero: ");
            tipo = sc.nextLine();            
            
            System.out.print("Inserisci numero: ");
            numero = sc.nextLine();
            
            Numeri recapito = new Numeri(tipo, numero); // creo oggetto recapito
            recapiti.add(recapito);                     // inserisco il recapito nell'arraylist
            
            //connect.addNumber(id, numero, tipo);            
            
            do{
                System.out.print("Inserire altro numero? <0 - no; 1 - si>: ");
                scelta = sc.nextLine();
            }while(!scelta.equals("0") && !scelta.equals("1"));
                        
            if(scelta.equals("0")) {
                running = false;
            }
        }
        
        // aggiungi contatto e numeri alla tabella anagrafica e numeri
        connect.aggiungiContatto(anagrafica, recapiti);
                
        System.out.print("Premi <invio> per continuare...");
        sc.nextLine();
    }
    
    private static void eliminaContatto(DBConnect connect){
                
        Scanner sc = new Scanner(System.in);
        int id = 0;
        String filtro = "";
        boolean newRicerca = true;
        
        System.out.print("\n");
        System.out.println("---------------------");
        System.out.println("Eliminazione contatto");
        System.out.println("---------------------");
        
        while(newRicerca){            
            visualizzaListaContatti(connect, false, filtro);
            System.out.print("\nInserisci nominativo da cercare / digita id numerico da eliminare / non scrivere nulla per tornare indietro: ");
            filtro = sc.nextLine();
            
            if(filtro.equals("")) newRicerca = false;
            else{
                if(isInteger(filtro)){
                    id = Integer.parseInt(filtro);
                    
                    // VERIFICA SE L'ID INSERITO ESISTE                    
                    if(connect.findContactById(id)){                    
                        connect.eliminaContatto(id);
                        System.out.println("Premi <invio> per continuare...");
                        sc.nextLine();
                    }else{
                        System.out.println("\nId contatto non trovato.\n");
                    }         
                    filtro = "";    // resetta il filtro
                }
                // se non è intero fa una nuova ricerca
            }
        }
    }
    
    private static void cercaContatto(DBConnect connect){
        
        String filtro = "";
        
        boolean newRicerca = true;
        
        Scanner sc = new Scanner(System.in);
        
        System.out.print("\n");
        System.out.println("----------------------");
        System.out.println("Ricerca di un contatto");
        System.out.println("----------------------");
        
        while(newRicerca){
            System.out.print("\nInserisci nominativo da cercare o non scrivere nulla per tornare indietro: ");
            filtro = sc.nextLine();
            
            if(!filtro.equals("")) visualizzaListaContatti(connect, false, filtro);
            else newRicerca = false;
        }
    }
    
    private static void visualizzaListaContatti(DBConnect connect, boolean premiTasto, String filtro){
        
        Scanner sc = new Scanner(System.in);
        
        connect.lista(filtro);
        
        if(premiTasto){
            System.out.print("Premi <invio> per continuare...");
            sc.nextLine();
        }
    }
        
    private static void modificaContatto(DBConnect connect){
        
        int id;
        int idNum = 0;
        int index;
        String filtro = "",
               stringa = "",
               scelta,
               tipo,
               numero;
        String IdNumStr;
        
        boolean modify = true,
                running = true,
                newRicerca = true,
                flMenuModifica = true;
        
        Scanner sc = new Scanner(System.in);
                        
        Contatto c;
        Numeri recapito;
        ArrayList<Numeri> recapiti = new ArrayList<Numeri>();
        
        while(newRicerca){            
            visualizzaListaContatti(connect, false, filtro);
            System.out.print("\nInserisci nominativo da cercare / digita id numerico da modificare / non scrivere nulla per tornare indietro: ");
            filtro = sc.nextLine();
            
            if(filtro.equals("")) newRicerca = false;
            else{
                if(isInteger(filtro)){
                    id = Integer.parseInt(filtro);
                    
                    // VERIFICA SE L'ID UTENTE INSERITO ESISTE                    
                    if(checkContactId(connect, id)){
                        
                        // seleziona contatto e numeri
                        c = connect.selezionaAnagrafica(id);
                        recapiti = connect.selezionaNumeri(id);                                                
                        
                        while(flMenuModifica){
                            mostraMenuModifica(id);
                            scelta = sc.nextLine();

                            switch(scelta){                            
                                case "1":                                
                                    System.out.print("\nInserisci cognome / non digitare nulla per tornare indietro: ");
                                    stringa = sc.nextLine();
                                    if(!stringa.equals("")) {
                                        c.setCognome(stringa);
                                        System.out.println("Cognome modificato");
                                    }                                
                                    break;

                                case "2":                                
                                    System.out.print("\nInserisci nome / non digitare nulla per tornare indietro: ");
                                    stringa = sc.nextLine();
                                    if(!stringa.equals("")) {
                                        c.setNome(stringa);
                                        System.out.println("Nome modificato");
                                    }       
                                    break; 

                                case "3":                                
                                    System.out.print("\nInserisci citta / non digitare nulla per tornare indietro: ");
                                    stringa = sc.nextLine();
                                    if(!stringa.equals("")) {
                                        c.setCitta(stringa);
                                        System.out.println("Citta modificata");
                                    }       
                                    break;        

                                case "4":                                
                                    System.out.print("\nInserisci indirizzo / non digitare nulla per tornare indietro: ");
                                    stringa = sc.nextLine();
                                    if(!stringa.equals("")) {
                                        c.setIndirizzo(stringa);
                                        System.out.println("Indirizzo modificato");
                                    }       
                                    break;

                                case "5":                                
                                    System.out.print("\nInserisci tipologia numero: ");
                                    tipo = sc.nextLine();
                                    System.out.print("\nInserisci numero numero: ");
                                    numero = sc.nextLine();
                                    recapito = new Numeri(0, id, tipo, numero);
                                    recapiti.add(recapito);
                                    break;

                                case "6":
                                    // visualizza lista numeri
                                    Numeri.visualizzaListaNumeri(recapiti);
                                    System.out.print("\nDigita id numerico da modificare / non scrivere nulla per tornare indietro: ");
                                    IdNumStr = sc.nextLine();                                

                                    if(isInteger(IdNumStr)){
                                        idNum = Integer.parseInt(IdNumStr);
                                        // VERIFICA SE L'ID NUMERO INSERITO ESISTE                    
                                        if(checkNumberId(connect, idNum)){
                                            // recupera indice arraylist
                                            index = findIndexArray(recapiti, idNum);
                                            System.out.print("Modifica tipologia <vecchia: " + recapiti.get(index).tipo + "> / non scrivere nulla per lasciare invariato: ");
                                            tipo = sc.nextLine();

                                            System.out.print("Modifica numero <vecchio: " + recapiti.get(index).numero + "> / non scrivere nulla per lasciare invariato: ");
                                            numero = sc.nextLine();
                                            if(!tipo.equals("") || !numero.equals("")){
                                                recapito = new Numeri(idNum, id, tipo, numero);
                                                recapiti.set(index, recapito);
                                            }
                                        }else{
                                            System.out.println("\nId numero non trovato.\n");
                                        }   
                                    }else{
                                        System.out.println("\nInput errato\n");
                                    }                      
                                    break;
                                    
                                case "7":
                                    // visualizza lista numeri
                                    Numeri.visualizzaListaNumeri(recapiti);
                                    System.out.print("\nDigita id numerico da eliminare / non scrivere nulla per tornare indietro: ");
                                    IdNumStr = sc.nextLine();                                

                                    if(isInteger(IdNumStr)){
                                        idNum = Integer.parseInt(IdNumStr);
                                        // VERIFICA SE L'ID NUMERO INSERITO ESISTE                    
                                        if(checkNumberId(connect, idNum)){
                                            // recupera indice arraylist
                                            index = findIndexArray(recapiti, idNum);
                                            recapiti.get(index).id *= -1;
                                        }else{
                                            System.out.println("\nId numero non trovato.\n");
                                        }   
                                    }else{
                                        System.out.println("\nInput errato\n");
                                    }            
                                    break;

                                case "9": 
                                    System.out.println("Tutte le modifiche effettuate verranno perse\n");
                                    newRicerca = false;
                                    flMenuModifica = false;
                                    break;

                                case "0":
                                    aggiornaContatto(connect, c, recapiti);
                                    System.out.println("Modifiche effettuate");
                                    newRicerca = false;
                                    flMenuModifica = false;
                                    break;
                            }
                        }
                        System.out.println("Premi <invio> per continuare...");
                        sc.nextLine();
                    }else{
                        System.out.println("\nId contatto non trovato.\n");
                    }         
                    filtro = "";    // resetta il filtro
                }
                // se non è intero fa una nuova ricerca
            }
        }
    }
    
    
    /*= S U B S ==========================================================*/      
    
    private static void mostraMenuModifica(int id){
        
        System.out.print("\n");
        System.out.println("-----------------------------------");
        System.out.println("Modifica contatto - id: " + id);
        System.out.println("-----------------------------------");
        
        System.out.print("\n");
        System.out.println("+-Anagrafica------------+-Numeri----------------+");
        System.out.println("| 1. Modifica cognome   | 5. Aggiungi numero    |");
        System.out.println("| 2. Modifica nome      | 6. Modifica numero    |");
        System.out.println("| 3. Modifica citta     | 7. Elimina numero     |");
        System.out.println("| 4. Modifica indirizzo |                       |");
        System.out.println("|                                               |");
        System.out.println("|                0. salva ed esci               |");
        System.out.println("|             9. esci senza salvare             |");
        System.out.println("+-----------------------------------------------|");
        System.out.print("Operazione: ");
    }
    
    private static boolean checkContactId(DBConnect connect, int id){
        return connect.findContactById(id);
    }
    
    private static boolean checkNumberId(DBConnect connect, int id){
        return connect.findNumberById(id);
    }
    
    private static boolean isInteger(String stringa) {
        try {
            Integer.parseInt(stringa);
            return true;
        } catch (NumberFormatException nfe) {}
        
        return false;
    }   
    
    private static int findIndexArray(ArrayList<Numeri> recapiti, int id){
        int i, index = 0;        
        
        for(i = 0; i < recapiti.size(); i++){
            if(id == recapiti.get(i).id){
                index = i;
                break;
            }
        }
        return index;
    }
    
    private static void aggiornaContatto(DBConnect connect, Contatto c, ArrayList<Numeri> recapiti){
        
        //Aggiorna tabella anagrafica
        connect.modificaContatto(c, recapiti);
    }
}