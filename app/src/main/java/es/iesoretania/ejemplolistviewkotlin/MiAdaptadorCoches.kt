package es.iesoretania.ejemplolistviewkotlin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import es.iesoretania.ejemplolistviewkotlin.databinding.CocheItemBinding

/**
 * Adaptador personalizado para mostrar objetos Coche en un ListView.
 * Extiende ArrayAdapter para personalizar la visualización de cada elemento.
 *
 * @param miContext Contexto de la aplicación
 * @param resource ID del recurso de layout para cada item
 * @param listaCoches Lista de coches a mostrar
 */
class MiAdaptadorCoches(
    private val miContext: Context, // NOTA: Realmente no es necesario, ArrayAdapter ya tiene 'context'
    val resource: Int,
    private val listaCoches: List<Coche>
) : ArrayAdapter<Coche>(miContext, resource, listaCoches) {

    // Declaramos el mapa de iconos como propiedad de clase
    // Así solo se crea una vez y no en cada llamada a getView()
    // companion object permite acceder sin instanciar la clase (similar a static en Java)
    companion object {
        // Mapeo de marcas de coche a sus recursos de imagen correspondientes
        private val iconosMarca = mapOf(
            "Mercedes" to R.drawable.mercedes_benz,
            "BMW" to R.drawable.bmw_logo,
            "Audi" to R.drawable.audi_logo
        )
    }

    /**
     * Método que se llama para cada elemento de la lista cuando se renderiza en pantalla.
     *
     * @param position Posición del elemento en la lista
     * @param convertView Vista reciclada (puede ser null si no hay vista para reciclar)
     * @param parent ViewGroup padre al que se añadirá la vista
     * @return Vista configurada para mostrar el elemento en la posición indicada
     */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Patrón ViewHolder optimizado con ViewBinding
        // Si convertView es null, creamos un nuevo binding inflando el layout
        // Si no es null, reutilizamos la vista existente (más eficiente)
        val binding = if (convertView == null) {
            CocheItemBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false // false porque el ListView añadirá la vista automáticamente
            )
        } else {
            // Reutilizar la vista existente creando un binding a partir de ella
            CocheItemBinding.bind(convertView)
        }

        // Obtener el objeto Coche correspondiente a esta posición
        val cocheActual: Coche = listaCoches[position]

        // Configurar los TextViews con los datos del coche
        binding.textViewMarca.text = cocheActual.marca
        binding.textViewModelo.text = cocheActual.modelo

        // Configurar el ImageView con el icono correspondiente a la marca
        // El operador Elvis (?:) devuelve el icono de interrogación si la marca no está en el mapa
        binding.imageViewMarca.setImageResource(
            iconosMarca[cocheActual.marca] ?: R.drawable.interrogacion
        )

        // Retornar la vista raíz del binding completamente configurada
        return binding.root
    }
}
