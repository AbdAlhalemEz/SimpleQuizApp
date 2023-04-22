package com.example
import  androidx.annotation.StringRes;
class Questions {
// added corrcetion string
    data class Questions(@StringRes val TextResId: Int , val answer : Boolean, val correction : String);
}