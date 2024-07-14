package ru.emacs.properties.models

class OurOrganization {
    var name: String = "ОБЩЕСТВО С ОГРАНИЧЕННОЙ ОТВЕТСТВЕННОСТЬЮ «ЭМАКС»"
    var abbreviatedName:String="ООО «ЭМАКС»"
    var inn:String ="7811788730"
    var kpp:String="781101001"
    var ogrn:String="1237800070958"
    var okpo:String="+79523950346"
    var email:String="sales@emacs.spb.ru"
    var phone:String=""
    var address:String ="192012, г. Санкт-Петербург, пр-кт Обуховской Обороны, дом 112, к.2, литер З, помещ.520"
    var addressFact:String="192012, г. Санкт-Петербург, пр-кт Обуховской Обороны, дом 112, к.2, литер З, помещ.520"
    var chief:String="Соколова Е.В."
    var chiefAccount:String="Соколова Е.В."
    var accounts:MutableList<PaymentAccount> = mutableListOf(PaymentAccount())
}
class PaymentAccount{
    var bankName:String ="СЕВЕРО-ЗАПАДНЫЙ БАНК ПАО СБЕРБАНК"
    var bik:String="044030653"
    var paymentAccount:String="40702810355000477953"
    var correspondentAccount:String="30101810500000000653"
}