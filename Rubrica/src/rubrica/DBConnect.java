/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rubrica;
import java.sql.*;
import java.util.Scanner;
import java.util.ArrayList;

/**
 *
 * @author david
 */
public class DBConnect {
    
    private Connection con;
    private Statement  st;
    private ResultSet  rs;
    
    public DBConnect(){
        try{            
            Class.forName("com.mysql.jdbc.Driver");       
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/rubrica", "root", "");
            st = con.createStatement();            
        }catch(Exception ex){            
            System.out.println("Error: " + ex);
        }
    }
    
    public void lista(String filtro){
        try{                        
            int i;
            
            boolean hasRow = false;
            
            String id, preId = "",
                   cognome,
                   nome,
                   citta,
                   indirizzo,
                   numero,
                   tipo;
            
            String query = "select * from anagrafica left join numeri on anagrafica.Id = numeri.Contatto";         
            
            Scanner sc = new Scanner(System.in);
            
            // uso dei filtri (se richiesto)
            if(filtro != "") query = query + " where anagrafica.Cognome like '%" + filtro + "%' or anagrafica.Nome like '%" + filtro + "%'";
            
            // ordinamento
            query = query + " order by anagrafica.Id";                                                
            
            System.out.print("ID");        for(i = 0; i <  9; i++) System.out.print(" ");
            System.out.print("Cognome");   for(i = 0; i < 23; i++) System.out.print(" ");
            System.out.print("Nome");      for(i = 0; i < 26; i++) System.out.print(" ");
            System.out.print("Citta");     for(i = 0; i < 25; i++) System.out.print(" ");
            System.out.print("Indirizzo"); for(i = 0; i < 41; i++) System.out.print(" ");
            System.out.print("Numero");    for(i = 0; i < 14; i++) System.out.print(" ");
            System.out.print("Tipo");            
            System.out.print("\n");
            
            rs = st.executeQuery(query);    
                                    
            while(rs.next()){ 
                
                hasRow = true;
                
                id        = rs.getString("Id");
                cognome   = rs.getString("Cognome");
                nome      = rs.getString("nome");
                citta     = rs.getString("Citta");
                indirizzo = rs.getString("Indirizzo");
                numero    = rs.getString("Numero");
                tipo      = rs.getString("Tipo");
                
                if(!id.equals(preId)){
                                          
                    for(i = 0; i < 180; i++) System.out.print("-");
                    System.out.print("\n");
                    
                    System.out.print(id);        for(i = 0; i < 11 - id.length();        i++) System.out.print(" ");
                    System.out.print(cognome);   for(i = 0; i < 30 - cognome.length();   i++) System.out.print(" ");
                    System.out.print(nome);      for(i = 0; i < 30 - nome.length();      i++) System.out.print(" ");
                    System.out.print(citta);     for(i = 0; i < 30 - citta.length();     i++) System.out.print(" ");
                    System.out.print(indirizzo); for(i = 0; i < 50 - indirizzo.length(); i++) System.out.print(" ");
                }else                            for(i = 0; i < 151;                     i++) System.out.print(" ");
                
                System.out.print(numero);        for(i = 0; i < 20 - numero.length();    i++) System.out.print(" ");
                System.out.print(tipo);
                System.out.print("\n");
                                
                preId = id;
            }
            
            if(hasRow == false){
                System.out.println("N E S S U N   C O N T A T T O   D A   M O S T R A R E");
            }
            
            for(i = 0; i < 180; i++) System.out.print("-");
            System.out.print("\n");
            
        }catch(Exception ex){
            System.out.println("Error: " + ex);
        }
    }
    
    public void aggiungiContatto(Contatto c, ArrayList<Numeri> n){
        
        int idAnagrafica;
        PreparedStatement pst;
        
        System.out.println("\nCreazione contatto in corso...");
        
        try{                   
            pst = con.prepareStatement("insert into anagrafica (Cognome, Nome, Citta, Indirizzo) values(?, ?, ?, ?)");
            pst.setString(1, c.cognome);
            pst.setString(2, c.nome);
            pst.setString(3, c.citta);
            pst.setString(4, c.indirizzo);
            pst.execute();
            pst.close();
        }catch(Exception ex){
            System.out.println("Errore durante l'esecuzione della query:");
            System.out.println("insert into anagrafica (Cognome, Nome, Citta, Indirizzo) values(" + c.cognome + ", " + c.nome + ", " + c.citta + ", " + c.indirizzo + ")\n");
            System.out.println(ex + "\n");
        } 
        
        // Inserimento anagrafica andato a buon fine. 
        
        //Recupero l'Id appena generato per assegnarlo a numeri.contatti
        idAnagrafica = getLastId();
        
        // se non ci sono stati errori nel recupero dell'ultimo id inserito
        if(idAnagrafica != 0){
            
            for(int i = 0; i < n.size(); i++){        
                try{
                    pst = con.prepareStatement("insert into numeri (Contatto, Numero, Tipo) values(?, ?, ?)");
                    pst.setInt(1, idAnagrafica);
                    pst.setString(2, n.get(i).numero);
                    pst.setString(3, n.get(i).tipo);            
                    pst.execute();
                    pst.close();
                }catch(Exception ex){
                    System.out.println("Errore durante l'esecuzione della query:");
                    System.out.println("insert into numeri (Contatto, Numero, Tipo) values(" + idAnagrafica + ", " + n.get(i).numero + ", " + n.get(i).tipo + ")\n");
                    System.out.println(ex + "\n");
                }             
            }
        }        
        System.out.println("\nCreazione contatto eseguito con successo!");
    }
    
    public int getLastId(){
        
        int id = 0; 
        try{
            rs = st.executeQuery("select last_insert_id() as last_id from anagrafica");  
            if(rs.next()) id = rs.getInt("last_id");
        }catch(Exception ex){
            System.out.println("Errore durante il recupero dell'ultimo id inserito: " + ex);
        }
        
        return id;
    }
    
    public void eliminaContatto(int id){
        
        try{
            PreparedStatement pst;
            Scanner sc = new Scanner(System.in);
            
            System.out.println("\nEliminazione contatto in corso...");
            
            pst = con.prepareStatement("delete from numeri where Contatto = ?");
            pst.setInt(1, id);
            pst.execute();
            pst.close();
            
            pst = con.prepareStatement("delete from anagrafica where Id = ?");
            pst.setInt(1, id);
            pst.execute();
            pst.close();            
        }catch(Exception ex){
            System.out.println("Errore: " + ex);
        }
        
        System.out.println("\nEliminazione contatto riuscito!\n");
    }
    
    public boolean findContactById(int id){
        
        String query = "select Id from anagrafica where id = " + id;
        try{                        
            rs = st.executeQuery(query);

            if(rs.next()) return true;
            
            return false;
        }catch(Exception ex){
            System.out.print("Errore: " + ex);
            return false;
        }
    }
    
    public boolean findNumberById(int id){
        
        String query = "select Id from numeri where id = " + id;
        try{                        
            rs = st.executeQuery(query);

            if(rs.next()) return true;
            
            return false;
        }catch(Exception ex){
            System.out.print("Errore: " + ex);
            return false;
        }
    }
    
    public Contatto selezionaAnagrafica(int id){
        
        Contatto c;
        
        String query = "select * from anagrafica where id = " + id;
        try{                        
            rs = st.executeQuery(query);

            if(rs.next()){ 
                c = new Contatto(rs.getInt("Id"), rs.getString("Cognome"), rs.getString("Nome"), rs.getString("Citta"), rs.getString("Indirizzo"));            
                return c;
            }
        }catch(Exception ex){
            System.out.print("Errore: " + ex);
        }
        
         return c = new Contatto();
    }
    
    public ArrayList<Numeri> selezionaNumeri(int id){
        
        ArrayList<Numeri> recapiti = new ArrayList<Numeri>();
        Numeri numero;
        
        String query = "select * from numeri where contatto = " + id;
        try{                        
            rs = st.executeQuery(query);

            while(rs.next()){
                numero = new Numeri(rs.getInt("Id"), rs.getInt("Contatto"), rs.getString("Tipo"), rs.getString("Numero"));                
                recapiti.add(numero); 
            }                        
        }catch(Exception ex){
            System.out.print("Errore: " + ex);
        }
        return recapiti;
    }
    
    public void modificaContatto(Contatto c, ArrayList<Numeri> recapiti){
        
        Numeri recapito;
        PreparedStatement pst,
                          insNum,
                          updNum;
        try{              
            pst = con.prepareStatement("update anagrafica set Cognome = ?, Nome = ?, Citta = ?, Indirizzo = ? where Id = ?");
            pst.setString(1, c.cognome);
            pst.setString(2, c.nome);
            pst.setString(3, c.citta);
            pst.setString(4, c.indirizzo);
            pst.setInt(5, c.id);
            pst.execute();
            pst.close();
        }catch(Exception ex){            
            System.out.println(ex + "\n");
        } 
        
        // aggiornamento numeri: i nuovi numeri hanno ID = 0. Se ID < 0 deve essere eliminato
        for(int i = 0; i < recapiti.size(); i++){
            recapito = recapiti.get(i);

            if(recapito.id == 0){
                try{
                    insNum = con.prepareStatement("insert into numeri (Contatto, Numero, Tipo) values(?, ?, ?)");
                    insNum.setInt(1, recapito.contatto);
                    insNum.setString(2, recapito.numero);
                    insNum.setString(3, recapito.tipo);
                    insNum.execute();
                    insNum.close();
                }catch(Exception ex){
                    System.out.println(ex + "\n");
                }
            }else if(recapito.id > 0){
                try{
                    updNum = con.prepareStatement("update numeri set Numero = ?, Tipo = ? where id = ?");
                    updNum.setString(1, recapito.tipo);
                    updNum.setString(2, recapito.numero);
                    updNum.setInt(3, recapito.id);
                    updNum.execute();
                    updNum.close();
                    
                }catch(Exception ex){
                    System.out.println(ex + "\n");
                }
            }else{
                try{
                    updNum = con.prepareStatement("delete from numeri where id = ?");
                    updNum.setInt(1, -recapito.id);
                    updNum.execute();
                    updNum.close();
                    
                }catch(Exception ex){
                    System.out.println(ex + "\n");
                }
            }
            
            
        }
    }
}