package lineales.dinamicas;

public class Lista {
    private Nodo cabecera;
    
    //Constructor
    public Lista(){
        this.cabecera = null;
    }
    
    //Modificadores
    public boolean insertar(Object elemento, int pos){
        boolean exito = false;
        if(1 <= pos && pos <= this.longitud() + 1){
            if(pos == 1){
                this.cabecera = new Nodo(elemento, this.cabecera);
            } else {
                Nodo aux = this.cabecera;
                int iteracion = 1;
                while (iteracion < pos -1){
                    aux = aux.getEnlace();
                    iteracion++;
                }
                Nodo nuevoNodo = new Nodo(elemento, aux.getEnlace());
                aux.setEnlace(nuevoNodo);
            }
            exito = true;
        }
        return exito;
    }
    
    public boolean eliminar(int pos){
        boolean exito = false;
        if(1 <= pos && pos <= this.longitud()){
            Nodo aux = this.cabecera;
            int iteracion = 1;
            if (pos == 1){
                this.cabecera = this.cabecera.getEnlace();
            } else {
                while (iteracion < pos - 1){
                    aux = aux.getEnlace();
                    iteracion++;
                }
                aux.setEnlace(aux.getEnlace().getEnlace());
            }
            exito = true;
        }
        return exito;
    }
    
    public Object recuperar(int posicion){
        Object elemento = null;
        int iteracion = 1;
        Nodo aux = this.cabecera;
        if(1 <= posicion && posicion < this.longitud() + 1){
            while(iteracion <posicion){
                iteracion ++;
                aux = aux.getEnlace();
            }
            elemento = aux.getElemento();
        }
        return elemento;
    }
    
    public int localizar(Object elemento){
        int largo = this.longitud();
        int iteracion = 1, posicion =-1;
        boolean encontrado = false;
        Nodo aux = this.cabecera;
        while (!encontrado && iteracion <= largo){
            if (aux.getElemento().equals(elemento)){
                posicion = iteracion;
                encontrado = true;
            } else {
                aux = aux.getEnlace();
                iteracion++;
            }
        }
        return posicion;
    }
    
    public int longitud(){
        int longitud = 0;
        Nodo nodoAuxiliar = this.cabecera;
        while( nodoAuxiliar != null){
            longitud++;
            nodoAuxiliar = nodoAuxiliar.getEnlace();
        }
        return longitud;
    }
    
    public boolean esVacia(){
        return this.cabecera == null;
    }
    
    @Override
    public Lista clone(){
        Lista clon = new Lista();
        Nodo nodoAdelante = this.cabecera;
        clon.cabecera = new Nodo(nodoAdelante.getElemento(), null);
        nodoAdelante = nodoAdelante.getEnlace();
        Nodo nodoAtras = clon.cabecera;
        while(nodoAdelante != null){
            nodoAtras.setElemento(new Nodo(nodoAdelante.getElemento(), null));
            nodoAtras = nodoAtras.getEnlace();
            nodoAdelante = nodoAdelante.getEnlace();
        }
        return clon;
    }
    
    @Override
    public String toString(){
        String texto;
        if(this.esVacia()){
         texto = "Lista vacia";   
        } else {
            Nodo nodoAuxiliar = this.cabecera;
            texto = "";
            while(nodoAuxiliar != null){
                texto += nodoAuxiliar.getElemento();
                nodoAuxiliar = nodoAuxiliar.getEnlace();
                if(nodoAuxiliar != null){
                    texto += ",";
                }
            }
        }
        return texto;
    }
    
    //Extras
     public Lista obtenerMultiplos(int numero) {
        Lista lista = new Lista();

        obtenerMultiplosAux(lista, this.cabecera, 1);

        return lista;
    }

    private int obtenerMultiplosAux(Lista lista, Nodo original, int numero) {
        int posicion = 0;

        if (original != null) {
            posicion = obtenerMultiplosAux(lista, original.getEnlace(), numero);
            posicion++;
            if (posicion % numero == 0) {
                Nodo nuevoNodo = new Nodo(original.getElemento(), null);
                nuevoNodo.setEnlace(lista.cabecera);
                lista.cabecera = nuevoNodo;
            }
        }

        return posicion;
    }

    public void insertarPosterior(Object valor1, Object valor2) {
        if (this.cabecera != null) {
            if(this.cabecera.getElemento().equals(valor1)){
                this.cabecera.setEnlace(new Nodo(valor2, this.cabecera.getEnlace()));
                this.cabecera = new Nodo(valor2, this.cabecera);
                insertarPosteriorAux(this.cabecera.getEnlace().getEnlace(), valor1, valor2);
            } else {
                insertarPosteriorAux(this.cabecera, valor1, valor2);
            }
        }
    }

    private void insertarPosteriorAux(Nodo posicion, Object valor1, Object valor2) {
        if (posicion.getEnlace() != null) {
            if (posicion.getEnlace().getElemento().equals(valor1)) {
                posicion.setEnlace(new Nodo(valor2, posicion.getEnlace()));
                insertarPosteriorAux(posicion.getEnlace().getEnlace(), valor1, valor2);
            } else {
                insertarPosteriorAux(posicion.getEnlace(), valor1, valor2);
            }
        }
    }
}
