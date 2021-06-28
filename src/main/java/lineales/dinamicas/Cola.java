package lineales.dinamicas;

public class Cola {
    private Nodo frente;
    private Nodo fin;
    
    //Constructor
    public Cola(){
        this.fin = null;
        this.frente = null;
    }
    
    //Modificadores
    //poner, sacar
    public boolean poner (Object nuevoElem){
        Nodo nuevoNodo = new Nodo(nuevoElem, null);
        if(!this.esVacia()){
            this.fin.setEnlace(nuevoNodo);
            this.fin = this.fin.getEnlace();
        } else {
            this.fin = nuevoNodo;
            this.frente = nuevoNodo;
        }
        return true;
    }
    
    public boolean sacar(){
        boolean exito = true;
        if(this.esVacia()){
            exito = false;
        } else {
            this.frente = this.frente.getEnlace();
            if(this.esVacia()){
                this.fin = null;
            }
        }
        return exito;
    }
    
    //Observadores
    //obtenerFrente, esVacia
    public Object obtenerFrente(){
        Object elementoFrente;
        if(this.esVacia()){
            elementoFrente = null;
        } else {
            elementoFrente = this.frente.getElemento();
        }
        return elementoFrente;
    }
    
    public boolean esVacia(){
        return this.frente == null;
    }
    
    //Propias del tipo
    //vaciar, clon
    public void vaciar(){
        this.fin = null;
        this.frente = null;
    }
    
    @Override
    public Cola clone(){
        Cola clon = new Cola();
        clon.fin = this.frente;
        clon.frente = new Nodo(clon.fin.getElemento(), null);
        clon.fin = clon.fin.getEnlace();
        Nodo nodoAtras = clon.frente;
        
        while(clon.fin.getEnlace() != null){
            nodoAtras.setEnlace(new Nodo(clon.fin.getElemento(), null));
            nodoAtras = nodoAtras.getEnlace();
            clon.fin = clon.fin.getEnlace();
        }
        nodoAtras.setEnlace(clon.fin);
        return clon;
    }
    
    //Testing
    @Override
    public String toString(){
        String texto;
        if(this.esVacia()){
            texto = "Cola vacía";
        } else {
            Nodo nodoAuxiliar = this.frente;
            texto = "";
            while (nodoAuxiliar != null){
                texto += nodoAuxiliar.getElemento();
                nodoAuxiliar = nodoAuxiliar.getEnlace();
                if(nodoAuxiliar != null){
                    texto += ",";
                }
            }
        }
        return texto;
    }
}
