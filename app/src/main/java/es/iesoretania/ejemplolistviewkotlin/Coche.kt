package es.iesoretania.ejemplolistviewkotlin

/**
 * Clase de datos (data class) que representa un coche.
 *
 * VENTAJAS de data class en Kotlin:
 * - Genera automáticamente equals(), hashCode(), toString(), copy()
 * - Ideal para objetos que solo almacenan datos
 *
 * @property modelo El modelo del coche (ej: "GLA", "X3")
 * @property marca La marca del coche (ej: "Mercedes", "BMW")
 */
data class Coche(
    val modelo: String,
    val marca: String
)