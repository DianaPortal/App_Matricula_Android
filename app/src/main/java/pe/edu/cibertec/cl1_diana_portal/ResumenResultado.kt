package pe.edu.cibertec.cl1_diana_portal

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResumenResultado : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.resumen_resultado)

        // Inicializar los TextView del layout
        val lblAlumnoResul: TextView = findViewById(R.id.lblAlumnoResul)
        val lblDniResul: TextView = findViewById(R.id.lblDniResul)
        val lblCarreraResul: TextView = findViewById(R.id.lblCarreraResul)
        val lblPension: TextView = findViewById(R.id.lblPension     )
        val lblDesc1: TextView = findViewById(R.id.lblDesc1)
        val lblDesc2: TextView = findViewById(R.id.lblDesc2)
        val lblTotalDesc: TextView = findViewById(R.id.lblTotalDesc)
        val lblTotalPagar: TextView = findViewById(R.id.lblTotalPagar)
        val imgResumenMat: ImageView = findViewById(R.id.imgResumenMat)

        // Obtener los datos pasados desde la actividad anterior
        val intent = intent
        val alumno = intent.getStringExtra("alumno")
        val dni = intent.getStringExtra("dni")
        val carrera = intent.getStringExtra("carrera")
        val pension = intent.getIntExtra("pension", 0)
        val descuento1 = intent.getDoubleExtra("descuento1", 0.0)
        val descuento2 = intent.getDoubleExtra("descuento2", 0.0)
        val totalDescuento = intent.getDoubleExtra("totalDescuento", 0.0)
        val totalPagar = intent.getDoubleExtra("totalPagar", 0.0)


        // Asignar los valores a los TextViews
        lblAlumnoResul.text = "Alumno: $alumno"
        lblDniResul.text = "DNI: $dni"
        lblCarreraResul.text = "Carrera: $carrera"
        lblPension.text = "PENSIÓN: S/. $pension"
        lblDesc1.text = "DESC 1: S/. $descuento1"
        lblDesc2.text = "DESC 2: S/. $descuento2"
        lblTotalDesc.text = "T. DESC TOTAL: S/. $totalDescuento"
        lblTotalPagar.text = "TOTAL: S/. $totalPagar"

        // Configurar el botón de volver
        val btnVolver: Button = findViewById(R.id.btn_volver)
        btnVolver.setOnClickListener {
            // Solo finalizamos la actividad para volver a la anterior
            finish()
        }
    }
}
