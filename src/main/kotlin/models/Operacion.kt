package models

import java.io.Serializable

data class Operacion(
    val numero1: Int,
    val numero2: Int,
    val operacion: TipoOperacion,
    var resultado: Double? = null
): Serializable

enum class TipoOperacion(val value: String) {
    SUMAR("sumar"),
    RESTAR("restar"),
    MULTIPLICAR("multiplicar"),
    DIVIDIR("dividir")
}
