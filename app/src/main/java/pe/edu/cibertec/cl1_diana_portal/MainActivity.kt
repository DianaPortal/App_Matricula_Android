    package pe.edu.cibertec.cl1_diana_portal

    import android.content.Intent
    import android.os.Bundle
    import android.widget.*
    import androidx.appcompat.app.AlertDialog
    import androidx.appcompat.app.AppCompatActivity
    import androidx.appcompat.widget.AppCompatButton
    import androidx.appcompat.widget.AppCompatTextView
    import com.google.android.material.textfield.TextInputEditText

    class MainActivity : AppCompatActivity() {

        // Declaración de componentes
        lateinit var inputDni: TextInputEditText
        lateinit var inputAlumno: TextInputEditText
        lateinit var spinnerCarrera: Spinner
        lateinit var rgDescuento: RadioGroup
        lateinit var rbDescuento5: RadioButton
        lateinit var rbDescuento10: RadioButton
        lateinit var rbDescuento12: RadioButton
        lateinit var btnCalcular: AppCompatButton
        lateinit var btnImprimir: AppCompatButton

        // Declaración de TextViews para mostrar los resultados en la tabla
        lateinit var txtPension: AppCompatTextView
        lateinit var txtDesc1: AppCompatTextView
        lateinit var txtDesc2: AppCompatTextView
        lateinit var txtTotalDesc: AppCompatTextView
        lateinit var txtTotalPagar: AppCompatTextView

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            // Inicializar los componentes
            inputDni = findViewById(R.id.input_dni)
            inputAlumno = findViewById(R.id.input_alumno)
            spinnerCarrera = findViewById(R.id.spinner_carrera)
            rgDescuento = findViewById(R.id.rg_descuento)
            rbDescuento5 = findViewById(R.id.rb_descuento_5)
            rbDescuento10 = findViewById(R.id.rb_descuento_10)
            rbDescuento12 = findViewById(R.id.rb_descuento_12)
            btnCalcular = findViewById(R.id.btn_calcular)
            btnImprimir = findViewById(R.id.btn_imprimir)

            // Inicialización de TextViews de la tabla
            txtPension = findViewById(R.id.txt_pension)
            txtDesc1 = findViewById(R.id.txt_desc1)
            txtDesc2 = findViewById(R.id.txt_desc2)
            txtTotalDesc = findViewById(R.id.txt_total_desc)
            txtTotalPagar = findViewById(R.id.txt_total_pagar)

            // Crear el adaptador para el Spinner
            val carrerasAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.carreras_array, // Hace referencia al array creado en recursos xml
                android.R.layout.simple_spinner_item
            )
            carrerasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerCarrera.adapter = carrerasAdapter

            // Configurar el listener para el botón "CALCULAR"
            btnCalcular.setOnClickListener {
                validarYCalcularMontos()
            }

            // Configurar el listener para el botón "IMPRIMIR"
            btnImprimir.setOnClickListener {
                enviarResumenMatr()
            }
        }

        // Función para validar los campos
        private fun validarYCalcularMontos() {
            val dni = inputDni.text.toString()
            val alumno = inputAlumno.text.toString()
            val carrera = spinnerCarrera.selectedItem.toString()

            // Verificar si se ha seleccionado un descuento
            val descuentoSeleccionado = rgDescuento.checkedRadioButtonId != -1

            // Validación de campos
            if (dni.isEmpty() || alumno.isEmpty() || carrera.isEmpty() || !descuentoSeleccionado) {
                mostrarAlerta("Por favor, complete todos los campos obligatorios.")
                return
            }

            // Si la validación es exitosa, proceder con el cálculo
            calcularMontos()
        }

        // Mostrar un AlertDialog con un mensaje de error
        private fun mostrarAlerta(mensaje: String) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Campos faltantes")
            builder.setMessage(mensaje)
            builder.setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            builder.show()
        }

        // Función para calcular los montos
        private fun calcularMontos() {
            // Obtener los datos ingresados por el usuario
            val carrera = spinnerCarrera.selectedItem.toString()

            // Obtener el monto de pensión según la carrera seleccionada
            val pension = when (carrera) {
                "Computación e informática" -> 780.0
                "Administración de redes y comunicaciones" -> 700.0
                "Administración y sistemas" -> 640.0
                else -> 0.0
            }

            // Cálculo del descuento adicional (5%, 10%, 12%)
            val descuento1 = when {
                rbDescuento5.isChecked -> pension * 0.05
                rbDescuento10.isChecked -> pension * 0.10
                rbDescuento12.isChecked -> pension * 0.12
                else -> 0.0
            }

            // Descuento adicional de S/.50 para las primeras dos carreras
            val descuento2 = if (carrera == "Computación e informática" || carrera == "Administración de redes y comunicaciones") 50.0 else 0.0

            // Calcula el total de descuentos y el monto total a pagar
            val totalDescuento = descuento1 + descuento2
            val totalPagar = pension - totalDescuento

            // Actualizar los TextViews con los resultados
            txtPension.text = pension.toString()
            txtDesc1.text = String.format("%.2f", descuento1)
            txtDesc2.text = String.format("%.2f", descuento2)
            txtTotalDesc.text = String.format("%.2f", totalDescuento)
            txtTotalPagar.text = String.format("%.2f", totalPagar)
        }

        private fun enviarResumenMatr() {
            // Obtener los datos ingresados por el usuario
            val dni = inputDni.text.toString()
            val alumno = inputAlumno.text.toString()
            val carrera = spinnerCarrera.selectedItem.toString()

            // Obtener el monto de pensión según la carrera seleccionada
            val pension = when (carrera) {
                "Computación e informática" -> 780
                "Administración de redes y comunicaciones" -> 700
                "Administración y sistemas" -> 640
                else -> 0
            }

            // Cálculo del descuento adicional (5%, 10%, 12%)
            val descuento1 = when {
                rbDescuento5.isChecked -> pension * 0.05
                rbDescuento10.isChecked -> pension * 0.10
                rbDescuento12.isChecked -> pension * 0.12
                else -> 0.0
            }

            // Descuento adicional de S/.50 para las primeras dos carreras
            val descuento2 = if (carrera == "Computación e informática" || carrera == "Administración de redes y comunicaciones") 50.0 else 0.0

            // Calcula el total de descuentos y el monto total a pagar
            val totalDescuento = descuento1 + descuento2
            val totalPagar = pension - totalDescuento

            // Crear el Intent para iniciar la segunda actividad
            val intent = Intent(this@MainActivity, ResumenResultado::class.java).apply {
                putExtra("dni", dni)
                putExtra("alumno", alumno)
                putExtra("carrera", carrera)
                putExtra("pension", pension)
                putExtra("descuento1", descuento1)
                putExtra("descuento2", descuento2)
                putExtra("totalDescuento", totalDescuento)
                putExtra("totalPagar", totalPagar)
            }
            startActivity(intent)
        }
    }
