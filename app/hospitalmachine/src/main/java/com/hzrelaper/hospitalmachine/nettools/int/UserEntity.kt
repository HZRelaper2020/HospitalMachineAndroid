package com.hzrelaper.hospitalmachine.nettools.int

class LoginResult{
    var result :Int ?=null
    var message: String ?=null
    var data: UserEntity? = null;
}

class UserEntity {
    var id :Int? = null
    var action: String? = null
    var username: String? = null
    var password: String? = null

    var roleId:Int?=null
    var roleName:String?= null
    var permissions:Int?=null
}