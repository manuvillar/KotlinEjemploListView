package es.iesoretania.ejemplolistviewkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import es.iesoretania.ejemplolistviewkotlin.databinding.ActivityMainBinding
import es.iesoretania.ejemplolistviewkotlin.databinding.DialogAddCarBinding

/**
 * Activity principal que muestra un ListView personalizado con una lista de coches.
 * Permite añadir nuevos coches mediante un FloatingActionButton.
 */
class MainActivity : AppCompatActivity() {

    // ViewBinding para acceder a las vistas del layout de forma segura y eficiente
    // lateinit indica que se inicializará antes de usarse (en onCreate)
    private lateinit var binding: ActivityMainBinding

    // Adaptador personalizado que gestiona cómo se muestran los coches en el ListView
    private lateinit var adaptadorCoches: MiAdaptadorCoches

    // Lista mutable que contiene los objetos Coche
    private val listaCoches = mutableListOf(
        Coche("GLA", "Mercedes"),
        Coche("X3", "BMW"),
        Coche("A4", "Audi"),
        Coche("GLC", "Mercedes"),
        Coche("Prueba", "Extraña")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializar ViewBinding inflando el layout
        binding = ActivityMainBinding.inflate(layoutInflater)
        // Establecer la vista raíz del binding como contenido de la Activity
        setContentView(binding.root)

        // Inicializar el adaptador personalizado con el contexto, layout del item y la lista
        adaptadorCoches = MiAdaptadorCoches(this, R.layout.coche_item, listaCoches)

        // Asignar el adaptador al ListView
        binding.lvpersonalizado.adapter = adaptadorCoches

        // Configurar el listener para clicks en elementos del ListView
        binding.lvpersonalizado.onItemClickListener = AdapterView.OnItemClickListener {
                parent, view, position, id ->

            // Obtener el coche correspondiente a la posición clickeada
            val coche = listaCoches[position]

            // Mostrar un Toast con la información del coche
            Toast.makeText(
                this,
                "${coche.modelo} - ${coche.marca}",
                Toast.LENGTH_SHORT
            ).show()
        }

        // Configuración del botón flotante para añadir nuevos coches
        binding.fabAdd.setOnClickListener {
            muestraAddCarDialog()
        }
    }

    /**
     * Muestra un diálogo personalizado para añadir un nuevo coche a la lista.
     * MEJORA: Usar ViewBinding en lugar de findViewById para mantener consistencia
     */
    private fun muestraAddCarDialog() {
        val dialogBinding = DialogAddCarBinding.inflate(LayoutInflater.from(this))

        // Crear el cuadro de diálogo con AlertDialog.Builder
        val dialog = AlertDialog.Builder(this)
            .setTitle("Agregar Coche")
            .setView(dialogBinding.root) // Usar la vista raíz del binding
            .setPositiveButton("Agregar") { _, _ ->
                // Obtener los textos ingresados y eliminar espacios en blanco
                val modelo = dialogBinding.etModelo.text.toString().trim()
                val marca = dialogBinding.etMarca.text.toString().trim()

                // Validar que ambos campos tengan contenido
                if (modelo.isNotEmpty() && marca.isNotEmpty()) {
                    // Agregar el nuevo coche a la lista
                    listaCoches.add(Coche(modelo, marca))

                    // Notificar al adaptador que los datos han cambiado para refrescar el ListView
                    // NOTA: notifyDataSetChanged() reconstruye toda la vista
                    adaptadorCoches.notifyDataSetChanged()

                    // Mostrar confirmación al usuario
                    Toast.makeText(
                        this,
                        "Coche añadido correctamente",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    // Mostrar mensaje de error si algún campo está vacío
                    Toast.makeText(
                        this,
                        "Por favor, ingresa todos los datos", // TODO: Mover a strings.xml
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            .setNegativeButton("Cancelar", null) // null = no hace nada al cancelar
            .create()

        // Mostrar el diálogo
        dialog.show()
    }
}