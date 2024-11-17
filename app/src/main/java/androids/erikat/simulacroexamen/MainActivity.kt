package androids.erikat.simulacroexamen

import android.graphics.drawable.GradientDrawable.Orientation
import android.icu.lang.UProperty.NameChoice
import android.os.Build
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.SpinnerAdapter
import android.widget.Switch
import android.widget.TimePicker
import android.widget.Toast
import androids.erikat.simulacroexamen.Model.Cita
import androids.erikat.simulacroexamen.Model.Pago
import androids.erikat.simulacroexamen.Model.Tarea
import androids.erikat.simulacroexamen.Model.Urgencia
import androids.erikat.simulacroexamen.util.addCita
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import java.sql.Time
import java.time.LocalDate
import java.time.LocalDateTime

class MainActivity : AppCompatActivity() {
    lateinit var nombreEText:EditText
    lateinit var completadoSwitch:Switch
    lateinit var tipoGroup:RadioGroup
    lateinit var layoutActual:LinearLayout

    lateinit var citaLayout:LinearLayout
    lateinit var addCitaBtt:Button
    lateinit var fechaChoice:DatePicker
    lateinit var horaChoice: TimePicker
    lateinit var lugarEditText: EditText

    lateinit var addPagoBtt:Button
    lateinit var pagoLayout: LinearLayout
    lateinit var cantidadEText:EditText
    lateinit var fechaPagoPicker: DatePicker
    lateinit var metodoPagoChoice:Spinner

    lateinit var tareaLayout: LinearLayout
    lateinit var addTareaBtt:Button
    lateinit var limitePicker: DatePicker
    lateinit var urgenciaPicker:Spinner

    val listaPagos:MutableList<Pago> = mutableListOf()
    val listaCitas:MutableList<Cita> = mutableListOf()
    val listaTareas:MutableList<Tarea> = mutableListOf()


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        nombreEText = findViewById(R.id.nombreEText)
        completadoSwitch = findViewById(R.id.completadoSwitch)
        tipoGroup = findViewById(R.id.actividadGroup)
        layoutActual = findViewById(R.id.innerLayout)
        //layoutActual.addView()
        crearLayouts()
        darContenidoABotones()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun darContenidoABotones() {
        addPagoBtt.setOnClickListener{
            try{
                val fechaPago:LocalDate = LocalDate.of(fechaPagoPicker.year, fechaPagoPicker.month, fechaPagoPicker.dayOfMonth)
                val pago:Pago = Pago(cantidadEText.text.toString().toDouble(), fechaPago, metodoPagoChoice.selectedItem.toString(), nombreEText.text.toString(), completadoSwitch.isChecked)
                listaPagos.add(pago)
                Toast.makeText(this, "Actualizado", Toast.LENGTH_SHORT).show()
            }catch (e:Exception){
                Toast.makeText(this, "Error de datos", Toast.LENGTH_SHORT).show()
            }
        }
        addTareaBtt.setOnClickListener{
            try{
                val fechaLimite:LocalDate = LocalDate.of(limitePicker.year, limitePicker.month, limitePicker.year)
                val tarea:Tarea = Tarea(fechaLimite, urgenciaPicker.selectedItem as Urgencia, null, nombreEText.text.toString(), completadoSwitch.isChecked)
                listaTareas.add(tarea)
                Toast.makeText(this, "Añadido", Toast.LENGTH_SHORT).show()
            }catch (e:Exception){
                Toast.makeText(this, "Error de datos", Toast.LENGTH_SHORT).show()
            }
        }
        addCitaBtt.setOnClickListener{
            try{
                val fechaHoraCita:LocalDateTime = LocalDateTime.of(fechaChoice.year, fechaChoice.month, fechaChoice.dayOfMonth, horaChoice.hour, horaChoice.minute)
                val cita:Cita = Cita(fechaHoraCita, lugarEditText.text.toString(), listOf(), nombreEText.text.toString(), completadoSwitch.isChecked)
                val arrayListCita:ArrayList<Cita> = ArrayList(listaCitas)
                if (addCita(cita, arrayListCita)){
                    listaCitas.add(cita)
                    Toast.makeText(this, "Añadido", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "No se ha podido añadir", Toast.LENGTH_SHORT).show()
                }
            }catch (e:Exception){
                Toast.makeText(this, "Error de datos", Toast.LENGTH_SHORT).show()
            }
        }
        tipoGroup.setOnCheckedChangeListener { group, checkedId ->
            val button:RadioButton = findViewById(group.checkedRadioButtonId)
            when(button.text.toString()){
                "Cita" -> {
                    layoutActual.removeAllViews()
                    layoutActual.addView(citaLayout)
                }
                "Tarea" -> {
                    layoutActual.removeAllViews()
                    layoutActual.addView(tareaLayout)
                }
                else -> {
                    layoutActual.removeAllViews()
                    layoutActual.addView(pagoLayout)
                }

            }
        }
    }

    private fun crearLayouts() {
        citaLayout = LinearLayout(this)
        lugarEditText = EditText(this)
        lugarEditText.layoutParams = ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT)
        citaLayout.layoutParams = ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
        citaLayout.orientation = LinearLayout.VERTICAL
        addCitaBtt = Button(this)
        addCitaBtt.text = "Añadir Cita"
        addCitaBtt.layoutParams = ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT)
        fechaChoice = DatePicker(this)
        fechaChoice.layoutParams = ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT)
        horaChoice = TimePicker(this)
        horaChoice.layoutParams = ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT)
        citaLayout.addView(lugarEditText)
        citaLayout.addView(fechaChoice)
        citaLayout.addView(horaChoice)
        citaLayout.addView(addCitaBtt)


        pagoLayout = LinearLayout(this)
        pagoLayout.orientation = LinearLayout.VERTICAL
        pagoLayout.layoutParams = ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
        addPagoBtt = Button(this)
        addPagoBtt.text = "Insertar Pago"
        addPagoBtt.layoutParams = ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT)
        cantidadEText = EditText(this)
        cantidadEText.layoutParams = ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT)
        cantidadEText.hint = "Elige una cantidad"
        fechaPagoPicker = DatePicker(this)
        fechaPagoPicker.layoutParams = ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT)
        metodoPagoChoice = Spinner(this)
        var pagos:ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_spinner_item, mutableListOf("Bizum", "Transferencia", "Físico", "Tarjeta"))
        metodoPagoChoice.adapter = pagos
        metodoPagoChoice.layoutParams = ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT)

        pagoLayout.addView(cantidadEText)
        pagoLayout.addView(fechaPagoPicker)
        pagoLayout.addView(metodoPagoChoice)
        pagoLayout.addView(addPagoBtt)


        tareaLayout = LinearLayout(this)
        tareaLayout.orientation = LinearLayout.VERTICAL
        tareaLayout.layoutParams = ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
        addTareaBtt = Button(this)
        addTareaBtt.text = "Añadir Tarea"
        addTareaBtt.layoutParams = ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT)
        limitePicker = DatePicker(this)
        limitePicker.layoutParams = ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT)
        urgenciaPicker = Spinner(this)
        var nivelesUrgencia:MutableList<String> = mutableListOf()
        for (urgencia:Urgencia in Urgencia.entries){
            nivelesUrgencia.add(urgencia.name)
        }
        urgenciaPicker.adapter = ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item)

        tareaLayout.addView(limitePicker)
        tareaLayout.addView(urgenciaPicker)
        tareaLayout.addView(addTareaBtt)
    }
}