package jerarquicas.dinamicas;
import lineales.dinamicas.Lista;

public class ArbolBin {
    private NodoArbol raiz;
    
    //Constructor
    public ArbolBin(){
        this.raiz = null;
    }
    
    //Modificadores
    public boolean insertar (Object elemNuevo, Object elemPadre, char lugar){
        boolean exito = false;
        if(this.esVacio()){
            this.raiz = new NodoArbol(elemNuevo);
            exito = true;
        } else {
            NodoArbol nodoPadre = obtenerNodo(this.raiz, elemPadre);
            if(nodoPadre != null){
                if( lugar == 'I' && nodoPadre.getIzquierdo() == null){
                    nodoPadre.setIzquierdo(new NodoArbol(elemNuevo));
                    exito = true;
                } else {
                    if(lugar == 'D' && nodoPadre.getDerecho() == null){
                        nodoPadre.setDerecho(new NodoArbol(elemNuevo));
                        exito = true;
                    }
                }
            }
        }
        return exito;
    }
    
    public void vaciar(){
        this.raiz = null;
    }
    
    //Observadores
    private NodoArbol obtenerNodo(NodoArbol nodo, Object buscado){
        NodoArbol resultado = null;
        if(nodo != null){
            if(nodo.getElem().equals(buscado)){
                resultado = nodo;
            } else {
                resultado = obtenerNodo(nodo.getIzquierdo(), buscado);
                if(resultado == null){
                    resultado = obtenerNodo(nodo.getDerecho(), buscado);
                }
            }
        }
        return resultado;
    }
    
    public boolean esVacio(){
        return this.raiz == null;
    }
    
    public int altira(){
        int altura = 0;
        altura = this.getCantNiveles(this.raiz, altura);
        return altura;
    }
    private int getCantNiveles(NodoArbol nodo, int nivel){
        if(nodo != null){
            nivel = Math.max(this.getCantNiveles(nodo.getIzquierdo(), nivel + 1), this.getCantNiveles(nodo.getDerecho(), nivel+1));
        } else {
            nivel--;
        }
        return nivel;
    }
    
    public int nivel (Object elemento){
        int nivel;
        nivel = buscarNivel(this.raiz, 0, elemento);
        return nivel;
    }
    private int buscarNivel(NodoArbol nodo, int nivel, Object elemento){
        int retorna = -1;
        if(nodo != null){
            if(nodo.getElem().equals(elemento)){
                retorna = nivel;
            } else {
                retorna = buscarNivel(nodo.getIzquierdo(), nivel + 1, elemento);
            }
        }
        return retorna;
    }
    
    public Object padre (Object elemento){
        Object padre = null;
        padre = buscarPadre(this.raiz, elemento, padre);
        return padre;
    }
    private Object buscarPadre(NodoArbol nodo, Object hijo, Object padre){
        Object retorna = null;
        if(nodo != null){
            if(nodo.getElem().equals(hijo)){
                retorna = padre;
            } else {
                retorna = buscarPadre(nodo.getIzquierdo(), hijo, nodo.getElem());
                if(retorna == null){
                    retorna = buscarPadre(nodo.getDerecho(), hijo, nodo.getElem());
                }
            }
        }
        return retorna;
    }
    
    public Lista listarPreorden() {
        Lista lista = new Lista();
        listarPreordenAux(this.raiz, lista);
        return lista;
    }
    private void listarPreordenAux(NodoArbol nodo, Lista lista) {
        if (nodo != null) {
            lista.insertar(nodo.getElem(), lista.longitud() + 1);
            listarPreordenAux(nodo.getIzquierdo(), lista);
            listarPreordenAux(nodo.getDerecho(), lista);
        }
    }

    public Lista listarInorden() {

        Lista lista = new Lista();
        listarInordenAux(this.raiz, lista);
        return lista;
    }
    private void listarInordenAux(NodoArbol nodo, Lista lista) {
        if (nodo != null) {
            listarInordenAux(nodo.getIzquierdo(), lista);

            lista.insertar(nodo.getElem(), lista.longitud() + 1);
            listarInordenAux(nodo.getDerecho(), lista);
        }
    }

    public Lista listarPosorden() {

        Lista lista = new Lista();
        listarPosordenAux(this.raiz, lista);
        return lista;
    }
    private void listarPosordenAux(NodoArbol nodo, Lista lista) {
        if (nodo != null) {
            listarPosordenAux(nodo.getIzquierdo(), lista);
            listarPosordenAux(nodo.getDerecho(), lista);

            lista.insertar(nodo.getElem(), lista.longitud() + 1);
        }
    }

    public Lista listarPorNiveles() {
        Lista lista = new Lista();
        if (this.raiz != null) {
            lista.insertar(this.raiz.getElem(), 1);
            listarPorNivelesAux(this.raiz, lista);
        }
        return lista;
    }
    private void listarPorNivelesAux(NodoArbol nodo, Lista lista) {
        if (nodo != null) {
            if (nodo.getIzquierdo() != null) {
                lista.insertar(nodo.getIzquierdo().getElem(), lista.longitud() + 1);
            }
            if (nodo.getDerecho() != null) {
                lista.insertar(nodo.getDerecho().getElem(), lista.longitud() + 1);
            }
            listarPorNivelesAux(nodo.getIzquierdo(), lista);
            listarPorNivelesAux(nodo.getDerecho(), lista);
        }
    }
    
      // Propias del tipo
    @Override
    public ArbolBin clone() {
        ArbolBin copia = new ArbolBin();
        if (!this.esVacio()) {
            copia.raiz = clonAux(this.raiz);
        }
        return copia;
    }
    private NodoArbol clonAux(NodoArbol nodo) {
        NodoArbol nuevo = new NodoArbol(nodo.getElem());
        if (nodo.getIzquierdo() != null) {
            nuevo.setIzquierdo(clonAux(nodo.getIzquierdo()));
        }
        if (nodo.getDerecho() != null) {
            nuevo.setDerecho(clonAux(nodo.getDerecho()));
        }
        return nuevo;
    }
    
    public Lista frontera() {
        Lista retorno = new Lista();

        fronteraAux(this.raiz, retorno);

        return retorno;
    }
    private void fronteraAux(NodoArbol raiz, Lista lista) {
        if (raiz != null) {
            if (raiz.getIzquierdo() == null && raiz.getDerecho() == null) {
                lista.insertar(raiz.getElem(), lista.longitud() + 1);
            } else {
                if (raiz.getIzquierdo() != null) {
                    fronteraAux(raiz.getIzquierdo(), lista);
                }
                if (raiz.getDerecho() != null) {
                    fronteraAux(raiz.getDerecho(), lista);
                }
            }
        }
    }
      
    public boolean verificarPatron(Lista listaPatron) {
        boolean retorno = false;
        // Evaluamos que la lista no esta vacia
        if (listaPatron.esVacia()) {
            // En caso de que la lista este vacia y el arbol este vacio
            if (this.raiz == null) {
                retorno = true;
            }
        } else {
            // En caso de no estar la lista vacia y que el arbol no lo este
            if (this.raiz != null) {
                retorno = verificarPatronAux(this.raiz, listaPatron);
            }
        }
        return retorno;
    }
    private boolean verificarPatronAux(NodoArbol raiz, Lista listaPatron) {
        // Verificamos si el elemento en el frente de la lista es igual a la raiz
        boolean retorno = false;
        if (raiz != null && !listaPatron.esVacia()) {
            // Comparamos con la raiz
            Object primerElemento = listaPatron.recuperar(1);
            if (raiz.getElem().equals(primerElemento)) {
                // Si esta sacamos este elemento en la lista y llamamos con el HI
                boolean retornoIzquierdo = false;
                listaPatron.eliminar(1);
                retorno = true;
                if (raiz.getIzquierdo() != null) {
                    retornoIzquierdo = verificarPatronAux(raiz.getIzquierdo(), listaPatron);
                    retorno = retornoIzquierdo;
                }
                // Si no encontro el patron por el HI, lo busca por le HD
                if (raiz.getDerecho() != null && !retornoIzquierdo) {
                    retorno = verificarPatronAux(raiz.getDerecho(), listaPatron);
                }
            }
        }
        return retorno;
    }

    public void modificarSubarboles(Object d1, Object d2, Object d3) {

        this.modificarSubarbolesAux(this.raiz, d1, d2, d3);
    }
    private void modificarSubarbolesAux(NodoArbol nodoActual, Object d1, Object d2, Object d3) {
        // Precondición: d1 debe ser distinto de d2 y d3 para no entrar en bucle
        if (nodoActual != null) {
            if (nodoActual.getIzquierdo() != null) {
                if (nodoActual.getElem().equals(d1)) {
                    nodoActual.getIzquierdo().setValor(d2);
                }
                modificarSubarbolesAux(nodoActual.getIzquierdo(), d1, d2, d3);
            } else {
                if (nodoActual.getElem().equals(d1)) {
                    nodoActual.setIzquierdo(new NodoArbol(d2));
                }
            }
            if (nodoActual.getDerecho() != null) {
                if (nodoActual.getElem().equals(d1)) {
                    nodoActual.getDerecho().setValor(d3);
                }
                modificarSubarbolesAux(nodoActual.getDerecho(), d1, d2, d3);
            } else {
                if (nodoActual.getElem().equals(d1)) {
                    nodoActual.setDerecho(new NodoArbol(d3));
                }
            }
        }
    }

    // Testing
    public String toString() {
        String cadena = "Arbol vacio";
        if (this.raiz != null) {
            cadena = "";
            cadena = stringAux(this.raiz, cadena);
        }
        return cadena;
    }
    private String stringAux(NodoArbol nodo, String cadena) {
        String cadena2 = cadena;
        cadena2 += "Nodo:" + nodo.getElem();
        if (nodo.getIzquierdo() != null) {
            cadena2 += " HI:" + nodo.getIzquierdo().getElem();
        } else {
            cadena2 += " HI:-";
        }
        if (nodo.getDerecho() != null) {
            cadena2 += " HD:" + nodo.getDerecho().getElem() + "\n";
        } else {
            cadena2 += " HD:- \n";
        }
        if (nodo.getIzquierdo() != null) {
            cadena2 = stringAux(nodo.getIzquierdo(), cadena2);
        }
        if (nodo.getDerecho() != null) {
            cadena2 = stringAux(nodo.getDerecho(), cadena2);
        }
        return cadena2;
    }
} 

