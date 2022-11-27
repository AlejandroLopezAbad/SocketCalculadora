package client

import models.Calculadora
import models.Operacion
import models.TipoOperacion
import java.io.DataInputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.InetAddress
import java.net.Socket
import java.util.*

fun main(){
    val dir:InetAddress=InetAddress.getLocalHost()
    val ser:Socket
    val port= 9999

    try {
        ser= Socket(dir,port)
        println("Conectandose al servidor....")

        val listaIn=ObjectInputStream(ser.getInputStream())
        val listaOp:Calculadora=listaIn.readObject() as Calculadora
        if (listaOp.size()==0){
            println("No hay operaciones en la cache")
        }else{
            println(listaOp.show())
        }
        val send = send()
        val write=ObjectOutputStream(ser.getOutputStream())
        write.writeObject(send)
        val result=DataInputStream(ser.getInputStream()).readDouble()
        println(result)
        ser.close()

    }catch (e: Exception){
        e.printStackTrace()
    }
}





private fun send(): Operacion {
    println("Escriba el primer numero:")
    val num1 = readln().toInt()
    println("Digame el segundo numero:")
    val num2 = readln().toInt()
    println("Digame el tipo de operacion a realizar (Sumar, Restar, Multiplicar, Dividir)")
    val operacion = TipoOperacion.valueOf(readln().uppercase(Locale.getDefault()))
    return Operacion(num1, num2, operacion)

}