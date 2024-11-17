package androids.erikat.simulacroexamen.Model

import java.time.LocalDate
import java.time.LocalDateTime

class Tarea(var fechaLimite: LocalDate, var urgencia: Urgencia, var notificacion: Notificacion?, nombre:String, completada: Boolean) : Recordatorio, Actividad(nombre,
    completada
){
    override fun programarRecordatorio(fecha_hora_notif: LocalDateTime) {
        this.notificacion = Notificacion(fecha_hora_notif, true)
    }
    override fun cancelarRecordatorio() {
        this.notificacion = null;
    }
    override fun mostrarDetalles():String{
        return "Tarea: [Fecha límite: ${fechaLimite}, Urgencia: ${urgencia.name}, ${if(notificacion==null) "Notificación no establecida" else notificacion!!.mostrarNotificacion()}\n${super.mostrarDetalles()}]"
    }
    inner class Notificacion(var fecha_hora_notif:LocalDateTime, var activo:Boolean){
        fun mostrarNotificacion():String{
            return "Notificación: [Establecida para $fecha_hora_notif, estado: ${if(activo) "activada" else "desactivada"}]"
        }
    }
}