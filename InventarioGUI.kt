import javax.swing.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener

// Clase Producto (SOLO UNA VEZ en todo el proyecto)
data class Producto(
    val id: Int,
    val nombre: String,
    val precio: Double,
    var stock: Int
)

// Ventana
class InventarioGUI : JFrame(), ActionListener {

    private val inventario = mutableListOf<Producto>()

    private val txtId = JTextField()
    private val txtNombre = JTextField()
    private val txtPrecio = JTextField()
    private val txtStock = JTextField()

    private val btnAgregar = JButton("Agregar")
    private val btnMostrar = JButton("Mostrar")

    private val area = JTextArea()

    init {
        title = "Sistema Inventario"
        layout = null
        setSize(400, 400)
        defaultCloseOperation = EXIT_ON_CLOSE

        add(JLabel("ID")).setBounds(20, 20, 100, 20)
        add(txtId).setBounds(120, 20, 150, 20)

        add(JLabel("Nombre")).setBounds(20, 50, 100, 20)
        add(txtNombre).setBounds(120, 50, 150, 20)

        add(JLabel("Precio")).setBounds(20, 80, 100, 20)
        add(txtPrecio).setBounds(120, 80, 150, 20)

        add(JLabel("Stock")).setBounds(20, 110, 100, 20)
        add(txtStock).setBounds(120, 110, 150, 20)

        add(btnAgregar).setBounds(50, 150, 100, 30)
        add(btnMostrar).setBounds(200, 150, 100, 30)

        add(JScrollPane(area)).setBounds(20, 200, 340, 120)

        btnAgregar.addActionListener(this)
        btnMostrar.addActionListener(this)
    }

    override fun actionPerformed(e: ActionEvent) {

        if (e.source == btnAgregar) {
            try {
                val id = txtId.text.toInt()
                val nombre = txtNombre.text
                val precio = txtPrecio.text.toDouble()
                val stock = txtStock.text.toInt()

                val producto = Producto(id, nombre, precio, stock)
                inventario.add(producto)

                JOptionPane.showMessageDialog(this, "Producto agregado correctamente")

                // Limpiar campos
                txtId.text = ""
                txtNombre.text = ""
                txtPrecio.text = ""
                txtStock.text = ""

            } catch (ex: Exception) {
                JOptionPane.showMessageDialog(this, "Error: Ingresa datos válidos")
            }
        }

        if (e.source == btnMostrar) {
            var texto = ""

            for (p in inventario) {
                texto += "ID: ${p.id}\n"
                texto += "Nombre: ${p.nombre}\n"
                texto += "Precio: ${p.precio}\n"
                texto += "Stock: ${p.stock}\n"
                texto += "----------------------\n"
            }

            area.text = texto
        }
    }
}
