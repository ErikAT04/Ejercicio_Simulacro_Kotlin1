package androids.erikat.simulacroexamen.Model

abstract class Actividad(var nombre:String, var completada:Boolean){

    fun marcarComoCompletada(){
        completada = true
    }
    open fun mostrarDetalles():String{
        return "Detalles de actividad:[${nombre}, ${if (completada) "Completada" else "No completada"}]"
    }
}