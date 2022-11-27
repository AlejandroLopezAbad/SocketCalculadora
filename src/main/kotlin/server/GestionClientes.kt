package server

import models.Calculadora
import models.Operacion
import models.TipoOperacion
import java.io.DataOutputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.Socket

class GestionClientes(
    private var numCliente: Int = 0,
    private val cliente: Socket,
    private val calculadora: Calculadora
) : Thread() {
    override fun run() {
        // super.run()
        val objetoEnviado: ObjectInputStream
        val objetoSalida = ObjectOutputStream(cliente.getOutputStream())
        try {
            //hay que mandar las operaciones que tenemos en cache al principio
            println("Enviando operaciones anteriores")
            cacheFun(objetoSalida)
            objetoEnviado= ObjectInputStream(cliente.getInputStream())
            val op : Operacion = objetoEnviado.readObject() as Operacion
            opFun(op)
            println("La operacion es ->$op")
            val buffer= DataOutputStream(cliente.getOutputStream())
            op.resultado?.let { buffer.writeDouble(it)
            }


            objetoEnviado.close()
            objetoSalida.close()
            cliente.close()
        } catch (e: IOException) {
            e.printStackTrace()

        }
    }

    /**
     * Metodo que envia la cache
     *
     * @param bufferObjetosSalida
     */
    private fun cacheFun(bufferObjetosSalida: ObjectOutputStream) {
        bufferObjetosSalida.writeObject(calculadora)
    }



    private fun opFun(op: Operacion) {
        when(op.operacion){
            TipoOperacion.SUMAR -> op.resultado = (op.numero1 + op.numero2).toDouble()
            TipoOperacion.RESTAR -> {
                if(op.numero2 != 0){
                    op.resultado = (op.numero1 - op.numero2).toDouble()
                }else{
                    op.resultado = Double.NaN
                }
            }
            TipoOperacion.MULTIPLICAR -> op.resultado = (op.numero1 * op.numero2).toDouble()
            TipoOperacion.DIVIDIR -> if (op.numero2==0){
                println("No se puede dividir por 0")
                op.resultado = Double.NaN
            }else{
                op.resultado = (op.numero1 / op.numero2).toDouble()}
        }
        calculadora.add(op)
    }

}

