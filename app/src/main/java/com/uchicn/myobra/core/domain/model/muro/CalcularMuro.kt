package com.uchicn.myobra.core.domain.model.muro

class CalcularMuro {

    fun ejecutar(input: MuroInput): List<String> {

        // ==============================
        // 1️⃣ Conversión de unidades
        // ==============================
        val L = input.ladrillo.largoCm / 100.0
        val A = input.ladrillo.anchoCm / 100.0
        val H = input.ladrillo.altoCm / 100.0

        val Jh = input.juntaHorizontalCm / 100.0
        val Jv = input.juntaVerticalCm / 100.0

        // ==============================
        // 2️⃣ Cara visible según asentado  ✅ CLAVE
        // ==============================
        val (dimHorizontal, dimVertical) = when (input.asentado) {
            TipoAsentado.SOGA -> Pair(L, H)
            TipoAsentado.CABEZA -> Pair(A, H)
            TipoAsentado.CANTO -> Pair(L, A)
        }

        // ==============================
        // 3️⃣ Ladrillos por m² (CAMBIA según asentado)
        // ==============================
        val ladrillosPorM2 =
            1.0 / ((dimHorizontal + Jh) * (dimVertical + Jv))

        val totalLadrillos =
            ladrillosPorM2 *
                    input.areaMuroM2 *
                    (1 + input.desperdicioLadrilloPct / 100.0)

        // ==============================
        // 4️⃣ Espesor del muro
        // ==============================
        val espesorMuro = when (input.asentado) {
            TipoAsentado.SOGA -> A
            TipoAsentado.CABEZA -> L
            TipoAsentado.CANTO -> A
        }

        // ==============================
        // 5️⃣ Volúmenes por m²
        // ==============================
        val volumenMuroPorM2 = espesorMuro

        val volumenLadrilloUnit = L * A * H

        val volumenLadrillosPorM2 =
            ladrillosPorM2 * volumenLadrilloUnit

        val volumenMorteroPorM2 =
            volumenMuroPorM2 - volumenLadrillosPorM2

        val volumenMorteroTotal =
            volumenMorteroPorM2 *
                    input.areaMuroM2 *
                    (1 + input.desperdicioMorteroPct / 100.0)

        // ==============================
        // 6️⃣ Materiales del mortero
        // ==============================
        val mortero = input.tipoMortero

        val cementoBolsas =
            volumenMorteroTotal * (mortero.cementoKg / 42.5)

        val arenaM3 =
            volumenMorteroTotal * mortero.arenaM3

        val aguaM3 =
            volumenMorteroTotal * (mortero.aguaLitros / 1000.0)

        // ==============================
        // 7️⃣ Resultados
        // ==============================
        return listOf(
            "Ladrillos: ${totalLadrillos.toInt()} und",
            "Mortero: %.2f m³".format(volumenMorteroTotal),
            "Cemento: %.2f bolsas".format(cementoBolsas),
            "Arena: %.2f m³".format(arenaM3),
            "Agua: %.2f m³".format(aguaM3)
        )
    }
}