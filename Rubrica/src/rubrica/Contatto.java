/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rubrica;
import java.util.ArrayList;

/**
 *
 * @author david
 */
public class Contatto {
    int    id;
    String cognome,
           nome,
           citta,
           indirizzo;

    //public Contatto(int id, String cognome, String nome, String citta, String indirizzo) {
    public Contatto(int id, String cognome, String nome, String citta, String indirizzo) {
        this.id = id;
        this.cognome = cognome;
        this.nome = nome;
        this.citta = citta;
        this.indirizzo = indirizzo;
    }
    
    public Contatto() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }      
}
