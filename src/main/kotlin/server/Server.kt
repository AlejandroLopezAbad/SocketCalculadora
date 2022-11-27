package server

import models.Calculadora
import java.net.ServerSocket

fun main() {
    var numclientes = 0
    val puertoServidor = 9999
    val calculadora = Calculadora()
    var servidor: ServerSocket

    println("Iniciando Servidor....")
    servidor = ServerSocket(puertoServidor)
    try {
        while (numclientes < 10) {//probar con 0
            println("Esperando Conexion...")
            val cliente = servidor.accept()
            numclientes++

            //hay que pasar el control al gestor de clientes
         //   println("Petición --> ${cliente.inetAddress} se ha conectado con el pruerto: ${cliente.port}")

            val gc = GestionClientes(numclientes, cliente, calculadora)
            gc.start()
        }
    } catch (e: Exception) {
        println("Error iniciando el servidor: ${e.printStackTrace()}")
    } finally {
        servidor.close()
    }


}