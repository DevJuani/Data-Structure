package conjuntistas.dinamicas;

import lineales.dinamicas.Lista;

public class ArbolBB {

    private NodoABB raiz;

    public boolean insertar(Comparable nuevo) {
        boolean exito;
        if (this.vacio()) {
            exito = insertarAux(this.raiz, nuevo);
        } else {
            this.raiz = new NodoABB(nuevo);
            exito = true;
        }
        return exito;
    }
    private boolean insertarAux(NodoABB nodo, Comparable nuevo) {
        boolean exito = true;
        if ((nuevo.compareTo(nodo.getElem()) == 0)) {
            exito = false;
        } else if (nuevo.compareTo(nodo.getElem()) < 0) {
            if (nodo.getIzquierdo() != null) {
                exito = insertarAux(nodo.getIzquierdo(), nuevo);
            } else {
                nodo.setIzquierdo(new NodoABB(nuevo));
            }
        } else {
            if (nodo.getDerecho() != null) {
                exito = insertarAux(nodo.getDerecho(), nuevo);
            } else {
                nodo.setDerecho(new NodoABB(nuevo));
            }
        }
        return exito;
    }
    
    public boolean eliminar(Comparable buscado) {
        return eliminarAux(null, this.raiz, buscado);
    }
    private boolean eliminarAux(NodoABB padre, NodoABB hijo, Comparable buscado) {
        boolean eliminado = false;
        if (hijo != null) {
            if (buscado.compareTo(hijo.getElem()) == 0) {
                if (hijo.getIzquierdo() == null && hijo.getDerecho() == null) {
                    eliminarHoja(padre, buscado);
                } else {
                    if (hijo.getIzquierdo() == null || hijo.getDerecho() == null) {
                        eliminarUnicoHijo(padre, hijo, buscado);
                    } else {
                        eliminarDosHijos(hijo);
                    }
                }
                eliminado = true;
            } else {
                if (buscado.compareTo(hijo.getElem()) < 0) {
                    eliminado = eliminarAux(hijo, hijo.getIzquierdo(), buscado);
                } else {
                    eliminado = eliminarAux(hijo, hijo.getDerecho(), buscado);
                }
            }
        }
        return eliminado;
    }
    private void eliminarHoja(NodoABB padre, Comparable buscado) {
        if (padre != null) {
            if (padre.getDerecho() != null && buscado.compareTo(padre.getDerecho().getElem()) == 0) {
                padre.setDerecho(null);
            } else {
                padre.setIzquierdo(null);
            }
        } else {
            this.raiz = null;
        }
    }
    private void eliminarUnicoHijo(NodoABB padre, NodoABB hijo, Comparable buscado) {
        NodoABB subArbol;
        if (hijo.getDerecho() != null) {
            subArbol = hijo.getDerecho();
        } else {
            subArbol = hijo.getIzquierdo();
        }
        if (padre != null) {
            if (padre.getDerecho() != null && buscado.compareTo(padre.getDerecho().getElem()) == 0) {
                padre.setDerecho(subArbol);
            } else {
                padre.setIzquierdo(subArbol);
            }
        } else {
            this.raiz = subArbol;
        }
    }
    private void eliminarDosHijos(NodoABB hijo) {
        NodoABB padreNuevoNodo = buscarPadreNodoMinimo(hijo.getDerecho());
        NodoABB nuevoNodo = padreNuevoNodo.getIzquierdo();
        /* Candidato con el menor hijo del subarbol derecho
           Ponemos el elemento de dicho candidato */
        if (nuevoNodo != null) {
            hijo.setElem(nuevoNodo.getElem());
            // Eliminamos al candidato
            eliminarAux(padreNuevoNodo, nuevoNodo, nuevoNodo.getElem());
        } else {
            hijo.setElem(padreNuevoNodo.getElem());
            // Eliminamos al candidato
            eliminarAux(hijo, padreNuevoNodo, padreNuevoNodo.getElem());
        }
    }
    private NodoABB buscarPadreNodoMinimo(NodoABB nodo) {
        NodoABB encontrado = nodo;
        if (nodo != null) {
            if (nodo.getIzquierdo() != null && nodo.getIzquierdo().getIzquierdo() != null) {
                encontrado = buscarPadreNodoMinimo(nodo.getIzquierdo());
            }
        }
        return encontrado;
    }

    public boolean pertenece(Comparable buscado) {
        boolean encontrado = false;
        if (!this.vacio()) {
            encontrado = perteneceAux(this.raiz, buscado);
        }
        return encontrado;
    }
    private boolean perteneceAux(NodoABB nodo, Comparable buscado) {
        boolean encontrado = false;
        if (nodo != null) {
            if (buscado.compareTo(nodo.getElem()) == 0) {
                encontrado = true;
            } else {
                if (buscado.compareTo(nodo.getElem()) < 0) {
                    encontrado = perteneceAux(nodo.getIzquierdo(), buscado);
                } else {
                    encontrado = perteneceAux(nodo.getDerecho(), buscado);
                }
            }
        }
        return encontrado;
    }

    public Lista listar() {
        Lista lista = new Lista();
        listarAux(this.raiz, lista);
        return lista;
    }
    private void listarAux(NodoABB nodo, Lista lista) {
        if (nodo != null) {
            listarAux(nodo.getIzquierdo(), lista);
            lista.insertar(nodo.getElem(), lista.longitud() + 1);
            listarAux(nodo.getDerecho(), lista);
        }
    }

    public Lista listarRango(Comparable min, Comparable max) {
        Lista lista = new Lista();
        listarRangoAux(this.raiz, min, max, lista);
        return lista;
    }
    private void listarRangoAux(NodoABB nodo, Comparable min, Comparable max, Lista lista) {
        if (nodo != null) {
            if (nodo.getElem().compareTo(min) > 0) {
                listarRangoAux(nodo.getIzquierdo(), min, max, lista);
            }
            if (nodo.getElem().compareTo(min) >= 0 && nodo.getElem().compareTo(max) <= 0) {
                lista.insertar(nodo.getElem(), lista.longitud() + 1);
            }
            if (nodo.getElem().compareTo(max) < 0) {
                listarRangoAux(nodo.getDerecho(), min, max, lista);
            }
        }
    }

    public Comparable minimoElem() {
        return minimoElemAux(this.raiz);
    }
    private Comparable minimoElemAux(NodoABB nodo) {
        Comparable encontrado = null;
        if (nodo != null) {
            if (nodo.getIzquierdo() != null) {
                encontrado = minimoElemAux(nodo.getIzquierdo());
            } else {
                encontrado = nodo.getElem();
            }
        }
        return encontrado;
    }

    public Comparable maximo() {
        return maximoAux(this.raiz);
    }
    private Comparable maximoAux(NodoABB nodo) {
        Comparable encontrado = null;
        if (nodo != null) {
            if (nodo.getDerecho() != null) {
                encontrado = maximoAux(nodo.getDerecho());
            } else {
                encontrado = nodo.getElem();
            }
        }
        return encontrado;
    }

    public boolean vacio() {
        return this.raiz == null;
    }
}
