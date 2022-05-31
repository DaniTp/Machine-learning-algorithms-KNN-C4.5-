/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clasificador;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author dbgt2
 */
public class Datos {
    private ArrayList<Elemento> elementos = new ArrayList<Elemento>();
    private int nelementos=0;
    private int natributos=0;
    private ArrayList<Integer> informacion = new ArrayList<Integer>();
    private ArrayList<Elemento> elementosPrueba = new ArrayList<Elemento>();
    private int nelementosprueba=0;
    private int natributosprueba=0;
    private ArrayList<Integer> informacionprueba = new ArrayList<Integer>();
    private FileReader ArchivoEntrada;
    private int[] atributosNuevos=null;
    private Vista vs;
    private String archivoT =" ";
    private String archivoP=" ";
    private int k=0;
    private int esarbol;
    String nombreTArbol;
    String nombrePArbol;
    public Datos(Vista vs, String archivoT, String archivoP, int k,String nombreTArbol, String nombrePArbol){
        this.vs=vs;
        this.archivoT=archivoT;
        this.archivoP=archivoP;
        this.k=k;
        this.nombreTArbol=nombreTArbol;
        this.nombrePArbol=nombrePArbol;
        
    }
    
    public void EjAlgoritmo(int a, int esarbol){

           
        try {
            lecturaArchivo(0);
        } catch (IOException ex) {
            Logger.getLogger(Datos.class.getName()).log(Level.SEVERE, null, ex);
        }
          


        vs.limpiar();
        switch(a){
            case 1:{
                //C4.5
                vs.setText("*******************C4.5**************************\n");
                Arbol nuevoarbol = new Arbol(vs,nombreTArbol,nombrePArbol);
            try {
                nuevoarbol.Ejecuta();
            } catch (Exception ex) {
                Logger.getLogger(Datos.class.getName()).log(Level.SEVERE, null, ex);
            }
                
             break;   
            }case 2:{
                //KNN
                vs.setText("*******************KNN**************************\n");
            KNN kalg = new KNN(vs,nelementos,natributos,elementos,informacion,nelementosprueba,natributosprueba,elementosPrueba,informacionprueba,k);
            kalg.Ejecutar();                    
            break;
            }case 3:{
            try {
                //Ambos knn con atributos de c4.5
                lecturaArchivo(0);
         
            KNN kalg = new KNN(vs,nelementos,natributos,elementos,informacion,nelementosprueba,natributosprueba,elementosPrueba,informacionprueba,k);
            kalg.Ejecutar();      
              Arbol nuevoarbol = new Arbol(vs,nombreTArbol,nombrePArbol);
             nuevoarbol.Ejecuta();
                    
            int res=JOptionPane.showConfirmDialog(null, "Desea usar alguna opción de los atributos almacenados?");
            if(JOptionPane.OK_OPTION == res){
                int res2=Integer.parseInt(JOptionPane.showInputDialog("1. Para archivos sb\n2. Para archivos aust"));
                if(res2==1){
                    
                        int[] atributosaux ={0,1,2,5,7,8,10,11,12,13,14,17,18,21,22,25,27,28,35};
                    atributosNuevos= new int[atributosaux.length]    ;
                    int n=0;
                    for (int i : atributosaux) {
                        atributosNuevos[n]=i;
                        n++;
                    }
                }else if(res2==2){
                    int[] atributosaux ={0,1,2,3,5,6,7,8,11,12,13,14};
                   atributosNuevos= new int[atributosaux.length]    ;
                    int n=0;
                    for (int i : atributosaux) {
                        atributosNuevos[n]=i;
                        n++;
                    }
                }        
            
            
        }else{
                String atrib=JOptionPane.showInputDialog("Ingrese los atributos (el ultimo elemento debe ser la clase) separe por ','");
                String[] atrib2= atrib.split(",");
                 atributosNuevos= new int[atrib2.length]    ;
                    int n=0;
                    for (String i : atrib2) {
                        atributosNuevos[n]=Integer.parseInt(i);
                        n++;
                    }
                
                
            }
                 lecturaArchivo(1); 
                 vs.setText("\n*******************KNN-C4.5**************************\n");
                  KNN kalg2 = new KNN(vs,nelementos,natributos,elementos,informacion,nelementosprueba,natributosprueba,elementosPrueba,informacionprueba,k);
            kalg2.Ejecutar(); 
           } catch (Exception ex) {
                Logger.getLogger(Datos.class.getName()).log(Level.SEVERE, null, ex);
            }
            break;
            }
            
            
            

    }
    }
    
    public String[] divideCadena(String cadena,String caracter){
        return cadena.split(caracter);
    }
    
    public void lecturaArchivo(int arb) throws IOException{


        for(int ar=0;ar<2;ar++){
            ArrayList<Elemento> auxelementos = new ArrayList<Elemento>();
            ArrayList<Integer> informacionaux = new ArrayList<Integer>();
            BufferedReader archivo;
       
            try {
            if(ar==0){
            ArchivoEntrada= new FileReader(archivoT);
                
            }
            else{
            ArchivoEntrada= new FileReader(archivoP);
            
            }
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Archivo no encontrado");
        }
        
        archivo= new BufferedReader(ArchivoEntrada);
        
        String renglon;
        int cont=0;
  
            while((renglon=archivo.readLine()) !=null){
              //  System.out.println(renglon);
              //  System.out.println(cont);
                switch(cont){
                    case 0:{
                        if(ar==0)
                        nelementos=Integer.parseInt(renglon);
                        else
                        nelementosprueba=Integer.parseInt(renglon);
                        break;
                    }
                    case 1:{
                        if(arb==0){
                        if(ar==0)
                        natributos=Integer.parseInt(renglon);
                        else
                        natributosprueba=Integer.parseInt(renglon);
                        }
                        break;
                    }
                    case 2:{
                        String[] cadena= divideCadena(renglon, ",");
             
  
                      if(arb==1)  {
                         
                          int contador=0;
                      for(int i=0;i<cadena.length;i++){
                       float n = Float.parseFloat(cadena[i]);
                     
                       if(encuentra(i)){          
                           contador++;
                           informacionaux.add(Integer.parseInt(cadena[i]));
                        
                            }
                        }   
                      if(ar==0)
                      natributos=contador-1;
                      else
                          natributosprueba=contador-1;
                      }else{
                   
                        for (String c : cadena) {
                            informacionaux.add(Integer.parseInt(c));
                          
                        }
                      }
                        break;
                    }
                    default:{
                    
                    String[] cadena= divideCadena(renglon, ",");
                    ArrayList<Float> aux = new ArrayList();

                   if(arb==1) {
                 
                   for(int i=0;i<cadena.length;i++){
                       float n = Float.parseFloat(cadena[i]);
                       if(encuentra(i)){                         
                        aux.add(n);
                       }else if(i==35){
                        aux.add(n);   
                       }
                   }
                   }else{
                   
                    for (String atr : cadena) {
                   
                        if(atr!="\n"){
                            
                        float n = Float.parseFloat(atr);
                        aux.add(n);
                        }
                    }
                   }

                   auxelementos.add(new Elemento(aux));
                
                    break;
                    }
                }
                
               
                   
      
                
                cont++;
                
            }
            if(ar==0){
            elementos=auxelementos; 
            informacion=informacionaux;
            }
            else{
            elementosPrueba=auxelementos;
            informacionprueba=informacionaux;
            }
            for (Elemento auxelemento : auxelementos) {
                auxelemento.imprime2();
            }
        }             
       
    }
    
    
       
        
        public boolean encuentra(int n){                        
            for(int i=0;i<atributosNuevos.length;i++){
                if(n==atributosNuevos[i]){
                    return true;
                  
                }
            }
            return false;
        }
    
      
}
