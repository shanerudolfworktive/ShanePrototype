package com.prototype.shane.shaneprototype.model.kotlinFun

import kotlin.properties.Delegates

/**
 * Created by shane1 on 6/26/17.
 */
class KotlinFunMovieModel{
    var name: String by Delegates.notNull();
    var url: String by Delegates.notNull();
    constructor(name: String, url: String){
        this.name = name;
        this.url = url;
    }
}