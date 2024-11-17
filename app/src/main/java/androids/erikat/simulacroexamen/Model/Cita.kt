package androids.erikat.simulacroexamen.Model

import java.time.LocalDateTime

class Cita(var fechaHora:LocalDateTime, var lugar:String, val personas:List<Persona>, nombre:String, completada:Boolean):Recordatorio,Actividad(nombre, completada){
    fun agregarPersona(persona: Persona){
        (personas as MutableList).add(persona)
    }
    override fun mostrarDetalles():String{
        return "Cita:[Fecha y Hora: $fechaHora, Lugar: $lugar, Personas: $personas, ${super.mostrarDetalles()}]"
    }

    override fun programarRecordatorio(fecha_hora_notif: LocalDateTime) {
        TODO("Not yet implemented")
    }

    override fun cancelarRecordatorio() {
        TODO("Not yet implemented")
    }
}