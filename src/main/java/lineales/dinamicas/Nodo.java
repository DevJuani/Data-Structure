package lineales.dinamicas;

public class Nodo {
    private Object elemento;
    private Nodo enlace;
    
    //Constructor
    public Nodo (Object tipoElemento, Nodo enlace){
        this.elemento = tipoElemento;
        this.enlace = enlace;
    }
    
    //Modificadores
    //getElemento, getEnlace
    public Object getElemento(){
        return this.elemento;
    }
    
    public Nodo getEnlace(){
        return this.enlace;
    }
    
    //Observadores
    //setElemento. setEnlace
    public void setElemento (Object nuevoElemento){
        this.elemento = nuevoElemento;
    }
    
    public void setEnlace (Nodo nuevoEnlace){
        this.enlace = nuevoEnlace;
    }    
}
