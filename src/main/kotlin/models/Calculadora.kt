package models

import java.io.Serializable

class Calculadora:Serializable {
    private val listaUltimasOperaciones: ArrayList<Operacion> = ArrayList<Operacion>()
    private val maxCache = 3


    @Synchronized
    fun add(r: Operacion) {
        if(listaUltimasOperaciones.size == 3){
            listaUltimasOperaciones.removeAt(0)
            listaUltimasOperaciones.add(r)
        }else{
            listaUltimasOperaciones.add(r)
        }
    }

    @Synchronized
    fun show() {
        listaUltimasOperaciones.forEach {
            println(it)
        }

    }


    @Synchronized
    fun size(): Int {
        return listaUltimasOperaciones.size

    }
}