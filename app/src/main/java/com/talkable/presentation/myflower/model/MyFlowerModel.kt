package com.talkable.presentation.myflower.model

data class MyFlowerModel(
    val flowerId: Int,
    val flowerImage: String,
    val flowerName: String,
    val flowerStartDate: String,
    val flowerEndDate: String? = null,
    val studyTime: String,
    val getDateData: List<MyFlowerGetDate>,
    val itemData: List<MyFlowerItem>,
)

data class MyFlowerEnd(
    val flowerId: Int,
    val flowerImage: String,
)

data class MyFlowerGetDate(
    val time: Int,
    val getDate: String? = null,
)

data class MyFlowerItem(
    val itemType: MyFlowerItemType,
    val count: Int? = null,
)

enum class MyFlowerItemType{
    SUN,
    COMPOST,
    WATER,
    MEDICINE,
    WIND,
    SHOVEL,
}