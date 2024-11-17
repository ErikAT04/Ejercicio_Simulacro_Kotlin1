package androids.erikat.simulacroexamen.util

import android.os.Build
import androids.erikat.simulacroexamen.Model.Actividad
import androids.erikat.simulacroexamen.Model.Cita
import androids.erikat.simulacroexamen.Model.Pago
import androids.erikat.simulacroexamen.Model.Tarea
import androids.erikat.simulacroexamen.Model.Urgencia
import androidx.annotation.RequiresApi

/*FUNCION DE EXTENSION DE ORDEN SUPERIOR QUE FILTRA UNA LISTA DE ACTIVIDADES*/

fun ArrayList<Actividad>.filtrarLista(funcion:(Actividad)->Boolean):ArrayList<Actividad>{
    val filtrada:ArrayList<Actividad> = arrayListOf()
    for (actividad in this){
        if (funcion(actividad)){
            filtrada.add(actividad)
        }
    }
    return filtrada;
}

/*FUNCION QUE FILTRA LAS TAREAS CON NIVEL DE URGENCIA ALTO*/

fun filtrarTareasUrgenciaAlta(tareas:ArrayList<Tarea>):ArrayList<Tarea>{
    val filtrada:ArrayList<Tarea> = (tareas as ArrayList<Actividad>).filtrarLista{
        (it as Tarea).urgencia == Urgencia.ALTO
    } as ArrayList<Tarea>
    return filtrada;
}

/*FUNCION QUE FILTRA LAS CITAS QUE HAN QUEDADO CON ANA*/

fun filtrarCitasDeAna(citas:ArrayList<Cita>):ArrayList<Cita>{
    val filtrada:ArrayList<Cita> = (citas as ArrayList<Actividad>).filtrarLista {
        var b:Boolean = false
        for (persona in (it as Cita).personas){
            if(persona.nombre == "Ana"){
                b = true
            }
        }
        b
    } as ArrayList<Cita>
    return filtrada
}

/*FUNCION DE EXTENSION DE ARRAYLIST DE PAGOS QUE RECIBE UN LAMBDA CON PARÁMETRO DE PAGOS Y DEVUELVE DOUBLE*/

fun ArrayList<Pago>.filtrarPagos(funcion:(Pago)->Double):Double{
    var importe:Double = 0.0
    for (pago in this){
        importe += funcion(pago)
    }
    return importe
}

/*FUNCION QUE DEVUELVE EL IMPORTE DE LOS PAGOS REALIZADOS*/

fun pagosRealizados(pagos:ArrayList<Pago>):Double{
    return pagos.filtrarPagos {
        if (it.completada) it.cantidad else 0.0
    }
}

/*FUNCION QUE DEVUELVE EL IMPORTE DE LOS PAGOS DE 2024*/

@RequiresApi(Build.VERSION_CODES.O)
fun pagosen2024(pagos:ArrayList<Pago>):Double{
    return pagos.filtrarPagos {
        if (it.fechaPago.year == 2024) it.cantidad else 0.0
    }
}

/*FUNCION QUE AÑADE UNA CITA NUEVA SIEMPRE Y CUANDO NO COINCIDA EN FECHA Y HORA CON LAS EXISTENTES*/

fun addCita(cita:Cita, citas:ArrayList<Cita>):Boolean{
    var fechaHora = cita.fechaHora;
    var existe = false;
    citas.forEach {
        if (it.fechaHora == fechaHora){
            existe = true //Existe se vuelve true si encuentra una con la misma fecha y hora
        }
    }
    if(!existe){
        citas.add(cita)
    }
    return !existe //Devuelve true si ha añadido la cita y false si no la ha añadido
}