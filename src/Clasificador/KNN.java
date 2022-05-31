/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clasificador;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author dbgt2
 */
public class KNN {
    private Vista vs;
        private int totalElementos;
        private int totalAtributos;
        private int maxval=0;
        private ArrayList<Elemento> elementos;
        private ArrayList<Integer> informacion;
        private int totalElementosprueba;
        private int totalAtributosprueba;
        private int maxvalprueba=0;
        private ArrayList<Elemento> elementosprueba;
        private ArrayList<Integer> informacionprueba;
        private float[][][] datos;
        private int[][] frecAtributos;
        private int k=3;

    public KNN(Vista vs, int totalElementos, int totalAtributos, ArrayList<Elemento> elementos, ArrayList<Integer> informacion, int totalElementosprueba, int totalAtributosprueba, ArrayList<Elemento> elementosprueba, ArrayList<Integer> informacionprueba, int k) {
        this.vs = vs;
        this.totalElementos = totalElementos;
        this.totalAtributos = totalAtributos;
        this.elementos = elementos;
        this.informacion = informacion;
        this.totalElementosprueba = totalElementosprueba;
        this.totalAtributosprueba = totalAtributosprueba;
        this.elementosprueba = elementosprueba;
        this.informacionprueba = informacionprueba;
        this.k=k;
    }
        

 
    
    
    public void Ejecutar(){
      //  vs.limpiar();
        
        frecuencias();
        
        Test(k);
        
        imprimir();
        
        matrizConfucion();
    }
    
    public void encuentraMax(){
        int max=0;
        for (int i=0;i<totalAtributos;i++) {
            if(informacion.get(i)>max){
                max=informacion.get(i);
            }
        }
        maxval=max;
    }
    
    public void frecuencias(){
        encuentraMax();
        datos =  new float[informacion.get(totalAtributos)][totalAtributos][maxval];
        frecAtributos = new int[totalAtributos][maxval];
        for(int clase=0;clase<informacion.get(totalAtributos);clase++){
            for(int atri=0;atri<totalAtributos;atri++){
                for(int valor=0;valor<maxval;valor++){
                    if(informacion.get(atri)!=0){
                    datos[clase][atri][valor]= prob(clase, atri, valor);
                    frecAtributos[atri][valor]=totalAtributo(atri, valor);
                    }
                    else{
                        datos[clase][atri][valor]=desviacionEstandar(atri);
                    }
                        
                }
                
            }
            
        }
    }
    
    
    public float prob(int clase, int atri,int valor){
        int c=0;
        int t=0;
        for (Elemento elemento : elementos) {
            if(elemento.getAtributo(totalAtributos)==clase && elemento.getAtributo(atri)==valor){
                c++;
            }            
        }
        
        for (Elemento elemento : elementos) {
            if(elemento.getAtributo(totalAtributos)==clase){
                t++;
            }            
        }

       // System.out.println(c);
        return  c;        
    }
    
    public float desviacionEstandar(int numatributo){
        float suma=0;
        float promedio=0;
        for (Elemento elemento : elementos) {
            suma+=elemento.getAtributo(numatributo);
        }
        //System.out.println("Suma: "+suma);
        promedio = (float) suma/totalElementos;
        //System.out.println("Promedio: "+promedio);
        float E=0;
        for (Elemento elemento : elementos) {
            E+= pow((elemento.getAtributo(numatributo)-promedio),2);
        }
        //System.out.println("E: "+E);
        float desEst = (float) sqrt(E/(totalElementos-1));
      //  System.out.println("Des "+desEst);
        return desEst;
    }
    
    
    
    public void imprimir(){
   /*     for(int clase=0;clase<informacion.get(totalAtributos);clase++){
             vs.setText("Clase: "+clase+"\n");
             for(int i=0;i<maxval;i++)
             vs.setText("\t"+i);
             vs.setText("\n");
            for(int atri=0;atri<totalAtributos;atri++){
                vs.setText(""+atri+"|");
                for(int valor=0;valor<maxval;valor++){                    
                    vs.setText("\t"+datos[clase][atri][valor]);
                }
                vs.setText("\n");
            }
            
        }
         
         vs.setText("\nFrecuencias de atributos: \n");
               for(int atri=0;atri<totalAtributos;atri++){
                vs.setText(""+atri+"|");
                for(int valor=0;valor<maxval;valor++){                    
                    vs.setText("\t"+frecAtributos[atri][valor]);
                }
                vs.setText("\n");
            }
               
              vs.setText("\n\n");  
      */
      
        for (Elemento elemento : elementosprueba) {
            vs.setText(elemento.imprime());     
            vs.setText("\n");
        }
        
        int cont=0;
        for (Elemento elemento : elementosprueba) {
            if(elemento.Acerto()) cont++;
        }
        System.out.println("cont"+cont);
        System.out.println("T "+totalElementosprueba);
        float exactitud= (float) cont/totalElementosprueba;
        vs.setText("------------------------------------------------------------");
        vs.setText("\nAciertos:\t"+cont);
        vs.setText("\nTotal:\t"+totalElementosprueba);
        vs.setText("\nExactitud:\t"+exactitud+"\n\n");
        
    }
    
    
    public void Test(int k){
        double[] distancias = new double[k];
        double[] clases = new double[k];
        
        for(int i=0;i<totalElementosprueba;i++){
            
            for(int h=0;h<clases.length;h++){
                distancias[h]=99999;
            }
            
            
            for(int x=0;x<totalElementos;x++){
                float total=0;
                for(int j=0;j<totalAtributosprueba;j++){
                total+=pow(HVDM(elementosprueba.get(i),elementos.get(x),j),2);                        
                }
                float hvdm = (float) sqrt(total);          
                for(int z = 0; z<k ; z++){
                   if(hvdm<distancias[z]){
                       distancias[z]=hvdm;
                       clases[z]=elementos.get(x).getAtributo(totalAtributos);
                       break;
                   } 
                }
            }
            
            int cmayor=0;
            int c=0;
            System.out.println("Elemento "+i);
            for(int z=0;z<k;z++){
                System.out.println(clases[z]);
                int cont=0;
                int cl= (int) clases[z];
                for (double clase : clases) {
                    if(clase==cl)
                        cont++;
                }
                if(cont>cmayor){
                    cmayor=cont;
                    c=cl;
                }
            }
            
            elementosprueba.get(i).setClase(c);
            
        }
    }
    
    public double HVDM(Elemento e1, Elemento e2, int natr){
        double E=0;
        if(informacion.get(natr) != 0){
            
            
            int totalClases= informacion.get(totalAtributos);
            for(int clase=0;clase<totalClases;clase++){
                   int val1 =(int) e1.getAtributo(natr);
                E+=  ((datos[clase][natr][(int) e1.getAtributo(natr)]/frecAtributos[natr][(int)e1.getAtributo(natr)])-(datos[clase][natr][(int)e2.getAtributo(natr)]/frecAtributos[natr][(int)e2.getAtributo(natr)]));
                
            }

            
        }else{
         E+= (double)  (e1.getAtributo(natr)-e2.getAtributo(natr))/(4*datos[0][natr][0]);
        }
        
        
        return E;
    }
    
    public int totalAtributo(int atr, float valor){//Regresa el total de un valor 
        
        int cont=0;
        for(int i=0; i<totalElementos;i++){
        if(elementos.get(i).getAtributo(atr)==valor){
            cont++;
        }
         }
        
        return cont;
        
    }
    
    
    public void matrizConfucion(){
        int[][] conf = new int[informacion.get(totalAtributos)][informacion.get(totalAtributos)];
        vs.setText("\nAsignadas\n      ");
        
         for(int j=0;j<informacion.get(totalAtributos);j++){//asignada
                
                vs.setText(j+"   ");     
                
            }
         vs.setText("\n");
        for(int i=0;i<informacion.get(totalAtributos);i++){//real
            vs.setText(i+"|   ");   
            for(int j=0;j<informacion.get(totalAtributos);j++){//asignada
                conf[i][j]=conteo(j,i);
                vs.setText(conf[i][j]+"   ");     
                
            }
            vs.setText("\n");
        }
    }
    
    public int conteo(int clase,int real){
        int cont=0;
       
        for(int i=0;i<totalElementosprueba;i++){
            if(elementosprueba.get(i).getClase()==clase && elementosprueba.get(i).getAtributo(totalAtributos)==real){
                cont++;
            }
        
        }
        
        return cont;
    }
}
