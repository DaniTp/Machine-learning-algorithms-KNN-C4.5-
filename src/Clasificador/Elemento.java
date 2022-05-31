/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clasificador;

import java.util.ArrayList;

/**
 *
 * @author dbgt2
 */
public class Elemento {
    private ArrayList<Float> atributos = new ArrayList();
    private int clase=0;
    public Elemento(ArrayList<Float> atributos) {
        this.atributos = atributos;
    }

 
    
    public float getAtributo(int atributo){
        
        return atributos.get(atributo);
    }

    public int getClase() {
        return clase;
    }

    public void setClase(int clase) {
        this.clase = clase;
    }
    
   public String imprime(){
        String texto="";
        for(int i=0;i<atributos.size();i++){
            texto+=atributos.get(i)+"\t";
        }
        
        texto+="\tClase: "+atributos.get(atributos.size()-1)+"\tClase Asignada: "+clase;
        
        return texto;                
    }
   
   
   public boolean Acerto(){
       if(clase ==(atributos.get(atributos.size()-1))){
           return true;
       }
       else return false;
   }
   
   public void imprime2(){
       String texto="";
         for(int i=0;i<atributos.size();i++){
            texto+=atributos.get(i)+"\t";
        }
         System.out.println(texto);
   }
}
