package ru.emacs.services



interface PropertiesService {
   fun <T>  getProperties(t:T) :T
}