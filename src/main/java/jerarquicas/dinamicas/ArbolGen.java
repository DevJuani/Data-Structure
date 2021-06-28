package jerarquicas.dinamicas;

import lineales.dinamicas.*;

public class ArbolGen {

    private NodoGen raiz;

    //Constructor
    public ArbolGen() {
        this.raiz = null;
    }

    //Observadores
    public boolean insertar(Object hijo, Object padre) {
        boolean exito;
        if (this.esVacio()) {
            //Arbol vacio
            this.raiz = new NodoGen(hijo, null, null);
            exito = true;
        } else {
            exito = insertarAux(this.raiz, hijo, padre);
        }
        return exito;
    }

    private boolean insertarAux(NodoGen actual, Object hijo, Object padre) {
        boolean exito = false;
        if (actual != null) {
            //Visita nodo actual
            if (actual.getElem().equals(padre)) {
                //Si tiene hijos lo inserta al final
                NodoGen aux = actual.getHijoIzquierdo();
                while (aux.getHermanoDerecho() != null) {
                    aux = aux.getHermanoDerecho();
                }
                aux.setHermanoDerecho(new NodoGen(hijo, null, null));
            } else {
                actual.setHijoIzquierdo(new NodoGen(hijo, null, null));
            }
            exito = true;
        } else {
            //sino, llama a los hijos
            exito = insertarAux(actual.getHijoIzquierdo(), hijo, padre);
            //sino llama a los hermanos
            if (!exito) {
                exito = insertarAux(actual.getHermanoDerecho(), hijo, padre);
            }
        }
        return exito;
    }

    public boolean pertenece(Object buscado) {
        boolean encontrado = perteneceAux(this.raiz, buscado);
        return encontrado;
    }

    private boolean perteneceAux(NodoGen actual, Object buscado) {
        boolean encontrado = false;
        if (actual != null) {
            //Visita n
            if (actual.getElem().equals(buscado)) {
                encontrado = true;
            } else {
                //Si no llama a los hijos
                encontrado = perteneceAux(actual.getHijoIzquierdo(), buscado);
                if (!encontrado) {
                    encontrado = perteneceAux(actual.getHermanoDerecho(), buscado);
                }
            }
        }
        return encontrado;
    }

    public boolean esVacio() {
        return this.raiz == null;
    }

    public Object padre(Object hijo) {
        Object padre = null;
        if (!(this.esVacio() || this.raiz.getElem().equals(hijo))) {
            padre = padreAux(this.raiz.getHijoIzquierdo(), this.raiz, hijo);
        }
        return padre;
    }

    private Object padreAux(NodoGen actual, NodoGen padre, Object buscado) {
        Object encontrado = null;
        if (actual.getElem().equals(buscado)) {
            encontrado = padre.getElem();
        } else {
            encontrado = padreAux(actual.getHijoIzquierdo(), actual, buscado);
            if (encontrado == null) {
                encontrado = padreAux(actual.getHermanoDerecho(), actual, buscado);
            }
        }
        return encontrado;
    }

    public int altura() {
        int niveles = -1;
        if (!this.esVacio()) {
            niveles = alturaAux(this.raiz, 0);
        }
        return niveles;
    }

    public int alturaAux(NodoGen nodo, int nivel) {
        if (nodo != null) {
            //Si tiene hermanos lo revisamos con la misma altura
            int hermano = alturaAux(nodo.getHermanoDerecho(), nivel);
            //Cada vez que baja a un hijo, la altura aumenta en 1
            if (nodo.getHijoIzquierdo() != null) {
                nivel = alturaAux(nodo.getHijoIzquierdo(), nivel + 1);
            }
            if (hermano > nivel) {
                nivel = hermano;
            }
        }
        return nivel;
    }

    public int nivel(Object buscado) {
        int niveles = nivelAux(this.raiz, buscado, 0);
        return niveles;
    }

    private int nivelAux(NodoGen actual, Object buscado, int nivel) {
        int retorna = -1;
        if (actual != null) {
            //Visita n
            if (actual.getElem().equals(buscado)) {
                retorna = nivel;
            } else {
                //Si no llama a los hijos
                retorna = nivelAux(actual.getHijoIzquierdo(), buscado, nivel + 1);
                //Si no llama a los hermanos
                if (retorna == -1) {
                    retorna = nivelAux(actual.getHermanoDerecho(), buscado, nivel);
                }
            }
        }
        return retorna;
    }

    public Lista ancestros(Object elem) {
        Lista ancestros = new Lista();
        if (this.esVacio()) {
            ancestrosAux(this.raiz, elem, ancestros);
        }
        return ancestros;
    }

    private boolean ancestrosAux(NodoGen nodo, Object elem, Lista ancestros) {
        boolean encontrado = false;
        if (nodo != null) {
            if (nodo.getElem().equals(elem)) {
                encontrado = true;
            } else {
                //buscamos en preordem
                encontrado = ancestrosAux(nodo.getHijoIzquierdo(), elem, ancestros);
                if (encontrado) {
                    //Si nos retornan true estamos en un ancestro
                    ancestros.insertar(nodo.getElem(), ancestros.longitud() + 1);
                } else {
                    //Si no, el llamado de los hermanos al final para no ingresarlos si se encuentra
                    encontrado = ancestrosAux(nodo.getHermanoDerecho(), elem, ancestros);
                }
            }
        }
        return encontrado;
    }

    @Override
    public ArbolGen clone() {
        ArbolGen clon = new ArbolGen();
        if (!this.esVacio()) {
            //Clona la raiz primero
            NodoGen nodo = new NodoGen(this.raiz.getElem(), null, null);
            clon.raiz = nodo;
            cloneAux(clon.raiz, this.raiz);
        }
        return clon;
    }

    private void cloneAux(NodoGen actual, NodoGen copia) {
        if (copia != null) {
            //Si tiene hijo, lo clona y lo revisa recursivamente
            if (copia.getHijoIzquierdo() != null) {
                actual.setHijoIzquierdo(new NodoGen(copia.getHijoIzquierdo().getElem(), null, null));
                cloneAux(actual.getHijoIzquierdo(), copia.getHijoIzquierdo());
            }
            //Si tiene hermano lo clona y lo revisa recursivamente
            if (copia.getHermanoDerecho() != null) {
                actual.setHermanoDerecho(new NodoGen(copia.getHermanoDerecho().getElem(), null, null));
                cloneAux(actual.getHermanoDerecho(), copia.getHermanoDerecho());
            }
        }
    }

    public void vaciar() {
        this.raiz = null;
    }

    public Lista listarPreorden() {
        Lista salida = new Lista();
        listarPreordenAux(this.raiz, salida);
        return salida;
    }
    private void listarPreordenAux(NodoGen n, Lista ls) {
        if (n != null) {
            // Visita n
            ls.insertar(n.getElem(), ls.longitud() + 1);
            // Llama a los demas hijos
            if (n.getHijoIzquierdo() != null) {
                NodoGen hijo = n.getHijoIzquierdo();
                while (hijo != null) {
                    listarPreordenAux(hijo, ls);
                    hijo = hijo.getHermanoDerecho();
                }
            }
        }
    }

    public Lista listarInorden() {
        Lista salida = new Lista();
        listarInordenAux(this.raiz, salida);
        return salida;
    }
    private void listarInordenAux(NodoGen n, Lista ls) {
        if (n != null) {
            // Llamado recursivo con primer hijo de n
            if (n.getHijoIzquierdo() != null) {
                listarInordenAux(n.getHijoIzquierdo(), ls);
            }
            // Visita n
            ls.insertar(n.getElem(), ls.longitud() + 1);
            // Llama a los demás hijos
            if (n.getHijoIzquierdo() != null) {
                NodoGen hijo = n.getHijoIzquierdo().getHermanoDerecho();
                while (hijo != null) {
                    listarInordenAux(hijo, ls);
                    hijo = hijo.getHermanoDerecho();
                }
            }
        }
    }

    public Lista listarPosorden() {
        Lista salida = new Lista();
        listarPosordenAux(this.raiz, salida);
        return salida;
    }
    private void listarPosordenAux(NodoGen n, Lista ls) {
        if (n != null) {
            // Llama a los demas
            if (n.getHijoIzquierdo() != null) {
                NodoGen hijo = n.getHijoIzquierdo();
                while (hijo != null) {
                    listarPosordenAux(hijo, ls);
                    hijo = hijo.getHermanoDerecho();
                }
            }
            // Visitar n
            ls.insertar(n.getElem(), ls.longitud() + 1);
        }
    }

    public Lista listarPorNiveles() {
        Lista niveles = new Lista();
        Cola cola = new Cola();
        cola.poner(this.raiz);
        int longitud = 1;
        while (!cola.esVacia()) {
            NodoGen nodo = (NodoGen) cola.obtenerFrente();
            cola.sacar();
            niveles.insertar(nodo.getElem(), longitud);
            longitud++;
            nodo = nodo.getHijoIzquierdo();
            while (nodo != null) {
                cola.poner(nodo);
                nodo = nodo.getHermanoDerecho();
            }
        }
        return niveles;
    }

    // Testing
    @Override
    public String toString() {
        return toStringAux(this.raiz);
    }

    private String toStringAux(NodoGen n) {
        String salida = "";
        if (n != null) {
            // Visita n
            salida += n.getElem().toString() + " -> ";
            NodoGen hijo = n.getHijoIzquierdo();
            while (hijo != null) {
                salida += hijo.getElem().toString() + ", ";
                hijo = hijo.getHermanoDerecho();
            }
            /* Recorre hijos de n recursivamente
            para que agregen su subcadena a la original*/
            hijo = n.getHijoIzquierdo();
            while (hijo != null) {
                salida += "\n" + toStringAux(hijo);
                hijo = hijo.getHermanoDerecho();
            }
        }
        return salida;
    }
}
