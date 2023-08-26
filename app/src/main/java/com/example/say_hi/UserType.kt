package com.example.say_hi

data class UserType(val Name:String?,
val phone:String?,
val userid:String?,
val downloadurl:String?,
val uid:String?)

{
    constructor() : this("", "","","","")
}