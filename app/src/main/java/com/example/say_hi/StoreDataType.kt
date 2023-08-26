package com.example.say_hi

data class StoreDataType(
    val downloadurl:String?,
    val Name:String?,
    val phone:String?,
    val userid:String?,
    val uid:String,
    )

{
    constructor() : this("", "","","","")
}
