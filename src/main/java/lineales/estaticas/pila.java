
package lineales.estaticas;
public class pila {
    private Object[] arreglo;
    private int tope;
    private static final int TAMANIO = 10;
    
    //Constructor
    public pila(){
        this.arreglo = new Object[TAMANIO];
        this.tope = 1;
    }
    
    //Modificadores
    //apilar, desapilar
    public boolean apilar (Object nuevoElem){
        boolean exito;
        if (this.tope == pila.TAMANIO -1){
            exito = false;
        } else {
            this.tope++;
            this.arreglo[tope] = nuevoElem;
            exito = true;
        }
        return exito;
    }
    
    public boolean desapilar(){
        boolean exito;
        if(this.tope == -1){
            exito = false;
        } else {
            this.arreglo[tope] = null;
            this.tope--;
            exito = true;
        }
        return exito;
    }
    
    //Observadores
    //obtenerTope, esVacia
    public Object obtenerTope(){
        Object objeto = null;
        if(this.tope > -1){
            objeto = this.arreglo[tope];
        }
        return objeto;
    }
    
    public boolean esVacia(){
        return this.tope == -1;
    }
    
    //Propias del tipo
    //vaciar, clone
    public void vaciar (){
        this.arreglo = new Object[TAMANIO];
        this.tope = -1;
    }
    
    public pila clone(){
        pila clon = new pila();
        clon.tope = this.tope;
        clon.arreglo = this.arreglo.clone();
        return clon;
    }
    
    //Testing
    //toString
    @Override
    public String toString(){
        String texto;
        if(this.esVacia()){
            texto = "Pila vacía";
        } else {
            int i = 0;
            texto = "";
            while (i < pila.TAMANIO && arreglo[i] != null){
                texto += arreglo[i];
                if(i < pila.TAMANIO - 1 && arreglo[i+1] != null){
                    texto += ",";
                }
                i++;
            }
        }
        return texto;
    }
}
