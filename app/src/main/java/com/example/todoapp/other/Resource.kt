package com.example.todoapp.other

sealed class Resource<T>(data:T?){
    class Success<T>(data: T?):Resource<T>(data)
}
