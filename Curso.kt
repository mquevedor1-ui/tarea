import javax.swing.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener

// ================== CLASE ESTUDIANTE ==================
class Estudiante(
    val nombre: String,
    val notas: MutableList<Double>
) {

    fun promedio(): Double = notas.average()

    fun obtenerNivel(): String {
        val prom = promedio()
        return when {
            prom >= 90 -> "Excelente"
            prom >= 60 -> "Aprobado"
            else -> "Reprobado"
        }
    }
}

// ================== CLASE CURSO ==================
class Curso(
    val nombre: String,
    val estudiantes: MutableList<Estudiante>
) {

    fun promedioGeneral(): Double {
        return estudiantes.map { it.promedio() }.average()
    }

    fun mejorEstudiante(): Estudiante? {
        return estudiantes.maxByOrNull { it.promedio() }
    }

    fun generarReporte(): String {
        var texto = "===== REPORTE =====\n\n"

        for (e in estudiantes) {
            texto += "Nombre: ${e.nombre}\n"
            texto += "Promedio: ${"%.2f".format(e.promedio())}\n"
            texto += "Nivel: ${e.obtenerNivel()}\n"
            texto += "----------------------\n"
        }

        texto += "\nPromedio General: ${"%.2f".format(promedioGeneral())}\n"

        val mejor = mejorEstudiante()
        if (mejor != null) {
            texto += "Mejor Estudiante: ${mejor.nombre}\n"
        }

        return texto
    }
}

// ================== INTERFAZ ==================
class CursoGUI : JFrame(), ActionListener {

    private val curso = Curso("Programación", mutableListOf())

    private val txtNombre = JTextField()
    private val txtNotas = JTextField() // ejemplo: 80,90,70

    private val btnAgregar = JButton("Agregar Estudiante")
    private val btnReporte = JButton("Generar Reporte")

    private val area = JTextArea()

    init {
        title = "Sistema de Curso"
        layout = null
        setSize(450, 450)
        defaultCloseOperation = EXIT_ON_CLOSE

        add(JLabel("Nombre")).setBounds(20, 20, 100, 20)
        add(txtNombre).setBounds(120, 20, 200, 20)

        add(JLabel("Notas (coma)")).setBounds(20, 60, 100, 20)
        add(txtNotas).setBounds(120, 60, 200, 20)

        add(btnAgregar).setBounds(50, 100, 150, 30)
        add(btnReporte).setBounds(220, 100, 150, 30)

        add(JScrollPane(area)).setBounds(20, 150, 390, 230)

        btnAgregar.addActionListener(this)
        btnReporte.addActionListener(this)
    }

    override fun actionPerformed(e: ActionEvent) {

        if (e.source == btnAgregar) {
            try {
                val nombre = txtNombre.text

                val notasTexto = txtNotas.text.split(",")
                val notas = mutableListOf<Double>()

                for (n in notasTexto) {
                    val nota = n.trim().toDouble()
                    if (nota !in 0.0..100.0) {
                        throw Exception()
                    }
                    notas.add(nota)
                }

                val estudiante = Estudiante(nombre, notas)
                curso.estudiantes.add(estudiante)

                JOptionPane.showMessageDialog(this, "Estudiante agregado")

                txtNombre.text = ""
                txtNotas.text = ""

            } catch (ex: Exception) {
                JOptionPane.showMessageDialog(this, "Error: Ingresa datos válidos (ej: 80,90,70)")
            }
        }

        if (e.source == btnReporte) {
            area.text = curso.generarReporte()
        }
    }
}
