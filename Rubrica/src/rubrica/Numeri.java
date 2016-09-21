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
public class Numeri {
    
    int id, 
        contatto;
    String tipo,
           numero;

    public Numeri(int id, int contatto, String tipo, String numero) {
        this.id = id;
        this.contatto = contatto;
        this.tipo = tipo;
        this.numero = numero;
    }
    
    public Numeri(String tipo, String numero){
        this.tipo = tipo;
        this.numero = numero;
    }   

    public void setId(int id) {
        this.id = id;
    }

    public int getContatto() {
        return contatto;
    }

    public void setContatto(int contatto) {
        this.contatto = contatto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }    
    
    public static void visualizzaListaNumeri(ArrayList<Numeri> recapiti){        
        
        int i, j;
        String strId;
        Numeri recapito;
        
        System.out.print("ID");        for(i = 0; i <  9; i++) System.out.print(" ");
        System.out.print("Numero");    for(i = 0; i < 14; i++) System.out.print(" ");
        System.out.print("Tipo");            
        System.out.print("\n");
                
       for (i = 0; i < recapiti.size(); i++) {
            recapito = recapiti.get(i);           
            
            System.out.print(recapito.id);     for(j = 0; j < 11 - Integer.toString(recapito.id).length(); j++) System.out.print(" ");
            System.out.print(recapito.numero); for(j = 0; j < 20 - recapito.numero.length();               j++) System.out.print(" ");
            System.out.println(recapito.tipo);
        }
        
        for(i = 0; i < 180; i++) System.out.print("-");
        System.out.print("\n");
    }
}
