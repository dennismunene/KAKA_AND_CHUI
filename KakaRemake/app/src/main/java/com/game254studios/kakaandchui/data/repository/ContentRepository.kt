package com.game254studios.kakaandchui.data.repository

import com.game254studios.kakaandchui.data.model.LearningItem
import com.game254studios.kakaandchui.data.model.Module

object ContentRepository {

    fun getItems(module: Module): List<LearningItem> = when (module) {
        Module.VOKALI -> vowels
        Module.TARAKIMU -> numbers
        Module.MAUMBO -> shapes
        Module.RANGI -> colors
    }

    private val vowels = listOf(
        LearningItem("a", "A", "gfx/somavokali/a.png", "mfx/Vokali/Vokali Soma/a.aac", "mfx/Vokali/Vokali Soma Zoezi/Chagua a.aac"),
        LearningItem("e", "E", "gfx/somavokali/e.png", "mfx/Vokali/Vokali Soma/e.aac", "mfx/Vokali/Vokali Soma Zoezi/Chagua e.aac"),
        LearningItem("i", "I", "gfx/somavokali/i.png", "mfx/Vokali/Vokali Soma/i.aac", "mfx/Vokali/Vokali Soma Zoezi/Chagua i.aac"),
        LearningItem("o", "O", "gfx/somavokali/o.png", "mfx/Vokali/Vokali Soma/o.aac", "mfx/Vokali/Vokali Soma Zoezi/Chagua o.aac"),
        LearningItem("u", "U", "gfx/somavokali/u.png", "mfx/Vokali/Vokali Soma/u.aac", "mfx/Vokali/Vokali Soma Zoezi/Chagua u.aac"),
    )

    private val numbers = listOf(
        LearningItem("t1", "Moja", "gfx/somatarakimu/t1.png", "mfx/Tarakimu/Tarakimu Soma/Moja.aac", "mfx/Tarakimu/Tarakimu  Zoezi/Chagua Moja .aac"),
        LearningItem("t2", "Mbili", "gfx/somatarakimu/t2.png", "mfx/Tarakimu/Tarakimu Soma/Mbili.aac", "mfx/Tarakimu/Tarakimu  Zoezi/Chagua Mbili.aac"),
        LearningItem("t3", "Tatu", "gfx/somatarakimu/t3.png", "mfx/Tarakimu/Tarakimu Soma/Tatu.aac", "mfx/Tarakimu/Tarakimu  Zoezi/Chagua Tatu.aac"),
        LearningItem("t4", "Nne", "gfx/somatarakimu/t4.png", "mfx/Tarakimu/Tarakimu Soma/Nne.aac", "mfx/Tarakimu/Tarakimu  Zoezi/Chagua  Nne.aac"),
        LearningItem("t5", "Tano", "gfx/somatarakimu/t5.png", "mfx/Tarakimu/Tarakimu Soma/Tano.aac", "mfx/Tarakimu/Tarakimu  Zoezi/Chagua Tano.aac"),
        LearningItem("t6", "Sita", "gfx/somatarakimu/t6.png", "mfx/Tarakimu/Tarakimu Soma/Sita.aac", "mfx/Tarakimu/Tarakimu  Zoezi/Chagua  Sita.aac"),
        LearningItem("t7", "Saba", "gfx/somatarakimu/t7.png", "mfx/Tarakimu/Tarakimu Soma/Saba.aac", "mfx/Tarakimu/Tarakimu  Zoezi/Chagua Saba.aac"),
        LearningItem("t8", "Nane", "gfx/somatarakimu/t8.png", "mfx/Tarakimu/Tarakimu Soma/Nane.aac", "mfx/Tarakimu/Tarakimu  Zoezi/Chagua Nane.aac"),
        LearningItem("t9", "Tisa", "gfx/somatarakimu/t9.png", "mfx/Tarakimu/Tarakimu Soma/Tisa.aac", "mfx/Tarakimu/Tarakimu  Zoezi/Chagua Tisa.aac"),
        LearningItem("t10", "Kumi", "gfx/somatarakimu/t10.png", "mfx/Tarakimu/Tarakimu Soma/Kumi.aac", "mfx/Tarakimu/Tarakimu  Zoezi/Chagua Kumi.aac"),
    )

    private val shapes = listOf(
        LearningItem("duara", "Duara", "gfx/maumbo/duara.png", "mfx/Maumbo/Maumbo Soma/Duara.aac", "mfx/Maumbo/Maumbo Zoezi/Chagua Duara.aac"),
        LearningItem("duaradufu", "Duara Dufu", "gfx/maumbo/duaradufu.png", "mfx/Maumbo/Maumbo Soma/Duara Dufu.aac", "mfx/Maumbo/Maumbo Zoezi/Chagua Duaradufu.aac"),
        LearningItem("mraba", "Mraba", "gfx/maumbo/mraba.png", "mfx/Maumbo/Maumbo Soma/Mraba.aac", "mfx/Maumbo/Maumbo Zoezi/Chagua Mraba.aac"),
        LearningItem("mstatili", "Mstatili", "gfx/maumbo/mstatili.png", "mfx/Maumbo/Maumbo Soma/Mstatili.aac", "mfx/Maumbo/Maumbo Zoezi/Chagua Mstatili.aac"),
        LearningItem("pembetatu", "Pembe Tatu", "gfx/maumbo/pembetatu.png", "mfx/Maumbo/Maumbo Soma/Pembe Tatu.aac", "mfx/Maumbo/Maumbo Zoezi/Pembe Tatu.aac"),
        LearningItem("nyota", "Nyota", "gfx/maumbo/nyota.png", "mfx/Maumbo/Maumbo Soma/Nyota.aac", "mfx/Maumbo/Maumbo Zoezi/Chagua Nyota.aac"),
    )

    private val colors = listOf(
        LearningItem("kijani", "Kijani", "gfx/rangi/kijani.png", "mfx/Rangi/Rangi Soma/Kijani.aac", "mfx/Rangi/Rangi Zoezi/Chagua Kijani.aac"),
        LearningItem("manjano", "Manjano", "gfx/rangi/manjano.png", "mfx/Rangi/Rangi Soma/Manjano.aac", "mfx/Rangi/Rangi Zoezi/Chagua Manjano.aac"),
        LearningItem("nyekundu", "Nyekundu", "gfx/rangi/nyekundu.png", "mfx/Rangi/Rangi Soma/Nyekundu.aac", "mfx/Rangi/Rangi Zoezi/Chagua Nyekundu.aac"),
        LearningItem("nyeupe", "Nyeupe", "gfx/rangi/nyeupe.png", "mfx/Rangi/Rangi Soma/Nyeupe.aac", "mfx/Rangi/Rangi Zoezi/Chagua Nyeupe.aac"),
        LearningItem("nyeusi", "Nyeusi", "gfx/rangi/nyeusi.png", "mfx/Rangi/Rangi Soma/Nyeusi.aac", "mfx/Rangi/Rangi Zoezi/Chagua Nyeusi.aac"),
        LearningItem("samawati", "Samawati", "gfx/rangi/samawati.png", "mfx/Rangi/Rangi Soma/Samawati.aac", "mfx/Rangi/Rangi Zoezi/Chagua Samawati.aac"),
    )
}
