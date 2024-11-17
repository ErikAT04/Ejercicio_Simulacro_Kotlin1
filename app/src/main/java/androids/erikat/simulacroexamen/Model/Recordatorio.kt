package androids.erikat.simulacroexamen.Model

import java.time.LocalDateTime

interface Recordatorio {
    fun programarRecordatorio(fecha_hora_notif: LocalDateTime)
    fun cancelarRecordatorio()
}