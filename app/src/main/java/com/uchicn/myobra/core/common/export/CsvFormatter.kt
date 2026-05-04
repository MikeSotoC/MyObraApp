package com.uchicn.myobra.core.common.export

object CsvFormatter {

    fun nivelacion(resultados: List<String>): String {
        val sb = StringBuilder()
        sb.append("Progresiva,Cama,Tubería,Excavación\n")

        resultados.forEach {
            sb.append(
                it.replace(" | ", ",")
                    .replace("=", "")
            ).append("\n")
        }
        return sb.toString()
    }
}