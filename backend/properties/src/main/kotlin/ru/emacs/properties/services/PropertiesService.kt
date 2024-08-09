package ru.emacs.properties.services



interface PropertiesService {
   fun <T>  getProperties(t:Class<T>) :T
}