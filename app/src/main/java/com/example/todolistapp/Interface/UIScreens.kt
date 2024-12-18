package com.example.todolistapp.Interface

sealed class Screens (val screen:String){
    data object taskScreen: Screens("taskScreen")
    data object deleted: Screens("deleted")
    data object settings: Screens("settings")
}