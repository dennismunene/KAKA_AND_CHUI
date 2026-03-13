package com.game254studios.kakaandchui.data.repository

import com.game254studios.kakaandchui.data.model.LearningItem
import com.game254studios.kakaandchui.data.model.Module

object ContentRepository {

    fun getItems(module: Module): List<LearningItem> = when (module) {
        Module.VOKALI -> vowels
        Module.VOKALI_MANENO -> vokaliManeno
        Module.TARAKIMU -> numbers
        Module.TARAKIMU_11_20 -> numbers11to20
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

    private val vokaliManeno = listOf(
        LearningItem("baba", "Baba", "gfx/somavokali/maneno/baba.png", "mfx/Vokali/Vokali Maneno/Baba.aac", "mfx/Vokali/Vokali Maneno Zoezi/Chagua Baba.aac", "Father"),
        LearningItem("dawa", "Dawa", "gfx/somavokali/maneno/dawa.png", "mfx/Vokali/Vokali Maneno/Dawa.aac", "mfx/Vokali/Vokali Maneno Zoezi/Chagua Dawa.aac", "Medicine"),
        LearningItem("embe", "Embe", "gfx/somavokali/maneno/embe.png", "mfx/Vokali/Vokali Maneno/Embe.aac", "mfx/Vokali/Vokali Maneno Zoezi/Chagua Embe.aac", "Mango"),
        LearningItem("gari", "Gari", "gfx/somavokali/maneno/gari.png", "mfx/Vokali/Vokali Maneno/Gari.aac", "mfx/Vokali/Vokali Maneno Zoezi/Chagua Gari.aac", "Car"),
        LearningItem("jiko", "Jiko", "gfx/somavokali/maneno/jiko.png", "mfx/Vokali/Vokali Maneno/Jiko.aac", "mfx/Vokali/Vokali Maneno Zoezi/Chagua Jiko.aac", "Stove"),
        LearningItem("kijiko", "Kijiko", "gfx/somavokali/maneno/kijiko.png", "mfx/Vokali/Vokali Maneno/Kijiko.aac", "mfx/Vokali/Vokali Maneno Zoezi/Chagua Kijiko.aac", "Spoon"),
        LearningItem("kisu", "Kisu", "gfx/somavokali/maneno/kisu.png", "mfx/Vokali/Vokali Maneno/Kisu.aac", "mfx/Vokali/Vokali Maneno Zoezi/Chagua Kisu.aac", "Knife"),
        LearningItem("kiti", "Kiti", "gfx/somavokali/maneno/kiti.png", "mfx/Vokali/Vokali Maneno/Kiti.aac", "mfx/Vokali/Vokali Maneno Zoezi/Chagua Kiti.aac", "Chair"),
        LearningItem("kuku", "Kuku", "gfx/somavokali/maneno/kuku.png", "mfx/Vokali/Vokali Maneno/Kuku.aac", "mfx/Vokali/Vokali Maneno Zoezi/Chagua Kuku.aac", "Chicken"),
        LearningItem("mama", "Mama", "gfx/somavokali/maneno/mama.png", "mfx/Vokali/Vokali Maneno/Mama.aac", "mfx/Vokali/Vokali Maneno Zoezi/Chagua Mama.aac", "Mother"),
        LearningItem("meli", "Meli", "gfx/somavokali/maneno/meli.png", "mfx/Vokali/Vokali Maneno/Meli.aac", "mfx/Vokali/Vokali Maneno Zoezi/Chagua  Meli.aac", "Ship"),
        LearningItem("paka", "Paka", "gfx/somavokali/maneno/paka.png", "mfx/Vokali/Vokali Maneno/Paka.aac", "mfx/Vokali/Vokali Maneno Zoezi/Chagua Paka.aac", "Cat"),
        LearningItem("pesa", "Pesa", "gfx/somavokali/maneno/pesa.png", "mfx/Vokali/Vokali Maneno/Pesa.aac", "mfx/Vokali/Vokali Maneno Zoezi/Chagua Pesa.aac", "Money"),
        LearningItem("pete", "Pete", "gfx/somavokali/maneno/pete.png", "mfx/Vokali/Vokali Maneno/Pete.aac", "mfx/Vokali/Vokali Maneno Zoezi/Chagua  Pete.aac", "Ring"),
        LearningItem("pikipiki", "Piki Piki", "gfx/somavokali/maneno/pikipiki.png", "mfx/Vokali/Vokali Maneno/Piki Piki.aac", "mfx/Vokali/Vokali Maneno Zoezi/Chagua Pikipiki.aac", "Motorcycle"),
        LearningItem("punda", "Punda", "gfx/somavokali/maneno/punda.png", "mfx/Vokali/Vokali Maneno/Punda.aac", "mfx/Vokali/Vokali Maneno Zoezi/Chagua Punda.aac", "Donkey"),
        LearningItem("rula", "Rula", "gfx/somavokali/maneno/rula.png", "mfx/Vokali/Vokali Maneno/Rula.aac", "mfx/Vokali/Vokali Maneno Zoezi/Chagua Rula.aac", "Ruler"),
        LearningItem("sufuria", "Sufuria", "gfx/somavokali/maneno/sufuria.png", "mfx/Vokali/Vokali Maneno/Sufuria.aac", "mfx/Vokali/Vokali Maneno Zoezi/Chagua Sufuria.aac", "Cooking Pot"),
        LearningItem("ua", "Ua", "gfx/somavokali/maneno/ua.png", "mfx/Vokali/Vokali Maneno/Ua.aac", "mfx/Vokali/Vokali Maneno Zoezi/Chagua Ua.aac", "Flower"),
        LearningItem("wembe", "Wembe", "gfx/somavokali/maneno/wembe.png", "mfx/Vokali/Vokali Maneno/Wembe.aac", "mfx/Vokali/Vokali Maneno Zoezi/Chagua Wembe.aac", "Razor"),
    )

    private val numbers = listOf(
        LearningItem("t1", "Moja", "gfx/somatarakimu/t1.png", "mfx/Tarakimu/Tarakimu Soma/Moja.aac", "mfx/Tarakimu/Tarakimu  Zoezi/Chagua Moja .aac", "One (1)"),
        LearningItem("t2", "Mbili", "gfx/somatarakimu/t2.png", "mfx/Tarakimu/Tarakimu Soma/Mbili.aac", "mfx/Tarakimu/Tarakimu  Zoezi/Chagua Mbili.aac", "Two (2)"),
        LearningItem("t3", "Tatu", "gfx/somatarakimu/t3.png", "mfx/Tarakimu/Tarakimu Soma/Tatu.aac", "mfx/Tarakimu/Tarakimu  Zoezi/Chagua Tatu.aac", "Three (3)"),
        LearningItem("t4", "Nne", "gfx/somatarakimu/t4.png", "mfx/Tarakimu/Tarakimu Soma/Nne.aac", "mfx/Tarakimu/Tarakimu  Zoezi/Chagua  Nne.aac", "Four (4)"),
        LearningItem("t5", "Tano", "gfx/somatarakimu/t5.png", "mfx/Tarakimu/Tarakimu Soma/Tano.aac", "mfx/Tarakimu/Tarakimu  Zoezi/Chagua Tano.aac", "Five (5)"),
        LearningItem("t6", "Sita", "gfx/somatarakimu/t6.png", "mfx/Tarakimu/Tarakimu Soma/Sita.aac", "mfx/Tarakimu/Tarakimu  Zoezi/Chagua  Sita.aac", "Six (6)"),
        LearningItem("t7", "Saba", "gfx/somatarakimu/t7.png", "mfx/Tarakimu/Tarakimu Soma/Saba.aac", "mfx/Tarakimu/Tarakimu  Zoezi/Chagua Saba.aac", "Seven (7)"),
        LearningItem("t8", "Nane", "gfx/somatarakimu/t8.png", "mfx/Tarakimu/Tarakimu Soma/Nane.aac", "mfx/Tarakimu/Tarakimu  Zoezi/Chagua Nane.aac", "Eight (8)"),
        LearningItem("t9", "Tisa", "gfx/somatarakimu/t9.png", "mfx/Tarakimu/Tarakimu Soma/Tisa.aac", "mfx/Tarakimu/Tarakimu  Zoezi/Chagua Tisa.aac", "Nine (9)"),
        LearningItem("t10", "Kumi", "gfx/somatarakimu/t10.png", "mfx/Tarakimu/Tarakimu Soma/Kumi.aac", "mfx/Tarakimu/Tarakimu  Zoezi/Chagua Kumi.aac", "Ten (10)"),
    )

    private val numbers11to20 = listOf(
        LearningItem("t11", "Kumi na Moja", "gfx/somatarakimu/t11.png", "", "", "Eleven (11)"),
        LearningItem("t12", "Kumi na Mbili", "gfx/somatarakimu/t12.png", "", "", "Twelve (12)"),
        LearningItem("t13", "Kumi na Tatu", "gfx/somatarakimu/t13.png", "", "", "Thirteen (13)"),
        LearningItem("t14", "Kumi na Nne", "gfx/somatarakimu/t14.png", "", "", "Fourteen (14)"),
        LearningItem("t15", "Kumi na Tano", "gfx/somatarakimu/t15.png", "", "", "Fifteen (15)"),
        LearningItem("t16", "Kumi na Sita", "gfx/somatarakimu/t16.png", "", "", "Sixteen (16)"),
        LearningItem("t17", "Kumi na Saba", "gfx/somatarakimu/t17.png", "", "", "Seventeen (17)"),
        LearningItem("t18", "Kumi na Nane", "gfx/somatarakimu/t18.png", "", "", "Eighteen (18)"),
        LearningItem("t19", "Kumi na Tisa", "gfx/somatarakimu/t19.png", "", "", "Nineteen (19)"),
        LearningItem("t20", "Ishirini", "gfx/somatarakimu/t20.png", "", "", "Twenty (20)"),
    )

    private val shapes = listOf(
        LearningItem("duara", "Duara", "gfx/maumbo/duara.png", "mfx/Maumbo/Maumbo Soma/Duara.aac", "mfx/Maumbo/Maumbo Zoezi/Chagua Duara.aac", "Circle"),
        LearningItem("duaradufu", "Duara Dufu", "gfx/maumbo/duaradufu.png", "mfx/Maumbo/Maumbo Soma/Duara Dufu.aac", "mfx/Maumbo/Maumbo Zoezi/Chagua Duaradufu.aac", "Oval"),
        LearningItem("mraba", "Mraba", "gfx/maumbo/mraba.png", "mfx/Maumbo/Maumbo Soma/Mraba.aac", "mfx/Maumbo/Maumbo Zoezi/Chagua Mraba.aac", "Square"),
        LearningItem("mstatili", "Mstatili", "gfx/maumbo/mstatili.png", "mfx/Maumbo/Maumbo Soma/Mstatili.aac", "mfx/Maumbo/Maumbo Zoezi/Chagua Mstatili.aac", "Rectangle"),
        LearningItem("pembetatu", "Pembe Tatu", "gfx/maumbo/pembetatu.png", "mfx/Maumbo/Maumbo Soma/Pembe Tatu.aac", "mfx/Maumbo/Maumbo Zoezi/Pembe Tatu.aac", "Triangle"),
        LearningItem("nyota", "Nyota", "gfx/maumbo/nyota.png", "mfx/Maumbo/Maumbo Soma/Nyota.aac", "mfx/Maumbo/Maumbo Zoezi/Chagua Nyota.aac", "Star"),
    )

    private val colors = listOf(
        LearningItem("nyekundu", "Nyekundu", "gfx/rangi/nyekundu.png", "mfx/Rangi/Rangi Soma/Nyekundu.aac", "mfx/Rangi/Rangi Zoezi/Chagua Nyekundu.aac", "Red"),
        LearningItem("samawati", "Samawati", "gfx/rangi/samawati.png", "mfx/Rangi/Rangi Soma/Samawati.aac", "mfx/Rangi/Rangi Zoezi/Chagua Samawati.aac", "Blue"),
        LearningItem("kijani", "Kijani", "gfx/rangi/kijani.png", "mfx/Rangi/Rangi Soma/Kijani.aac", "mfx/Rangi/Rangi Zoezi/Chagua Kijani.aac", "Green"),
        LearningItem("manjano", "Manjano", "gfx/rangi/manjano.png", "mfx/Rangi/Rangi Soma/Manjano.aac", "mfx/Rangi/Rangi Zoezi/Chagua Manjano.aac", "Yellow"),
        LearningItem("nyeupe", "Nyeupe", "gfx/rangi/nyeupe.png", "mfx/Rangi/Rangi Soma/Nyeupe.aac", "mfx/Rangi/Rangi Zoezi/Chagua Nyeupe.aac", "White"),
        LearningItem("nyeusi", "Nyeusi", "gfx/rangi/nyeusi.png", "mfx/Rangi/Rangi Soma/Nyeusi.aac", "mfx/Rangi/Rangi Zoezi/Chagua Nyeusi.aac", "Black"),
    )
}
