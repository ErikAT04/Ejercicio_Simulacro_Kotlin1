package androids.erikat.simulacroexamen.Model

import java.time.LocalDate

class Pago(var cantidad:Double, var fechaPago: LocalDate, var metodoPago:String, nombre:String, completada:Boolean):Actividad(nombre, completada){
    fun procesarPago():String{
        return "Pago procesado";
    }
    override fun mostrarDetalles():String{
        return "Pago: [Cantidad: $cantidad, Fecha de pago: $fechaPago, Método: $metodoPago, ${super.mostrarDetalles()}]"
    }
}