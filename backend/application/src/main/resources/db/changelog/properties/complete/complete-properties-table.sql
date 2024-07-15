INSERT INTO properties(clazz, property)
VALUES ('aleksey.gritsenko.ru.server.properties.models', '{
  "host": "http://localhost:4200/",
  "timeZone": "GMT"
}'::jsonb),
       ('ru/emacs/properties/models/SecurityPropertiesKt', '{
         "jwtProperties": {
           "secret": "h4f8093h4f983yhrt9834hr0934hf0hf493g493gf438rh438th34g34g",
           "jwtLifetime": 3600000,
           "jwtRefreshLifetime": 36000000,
           "emailVerifyTokenLifeTime": 36000000
         },
         "userPasswordStrength": {
           "passwordMinLowerCase": 1,
           "passwordMinNumber": 1,
           "passwordMinSymbol": 2,
           "passwordMinUpperCase": 1,
           "passwordMinCharacters": 8,
           "unit": "MONTHS",
           "passwordExpired": 6
         },
         "restorePasswordTokenProperty": {
           "restorePasswordTokenLength": 40,
           "restorePasswordTokenLifetime": 24,
           "unit": "HOURS"
         },
         "approvedEmailProperty": {
           "approvedEmailTokenLength": 40,
           "approvedEmailTokenLifetime": 24,
           "unit": "HOURS"
         },
         "passwordProperties": {
           "secret": "FZK2DZ82odqS13e8aENggaMbb_fAkl-nJL4AEVBX43g",
           "iteration": 64,
           "keyLength": 256
         }
       }'::jsonb),
       ('ru/emacs/properties/models/OurOrganizationKt',
        '{
          "name": "ОБЩЕСТВО С ОГРАНИЧЕННОЙ ОТВЕТСТВЕННОСТЬЮ «ЭМАКС»",
          "abbreviatedName": "ООО «ЭМАКС»",
          "inn": "7811788730",
          "kpp": "781101001",
          "ogrn": "1237800070958",
          "okpo": "+79523950346",
          "email": "sales@emacs.spb.ru",
          "phone": "",
          "address": "192012, г. Санкт-Петербург, пр-кт Обуховской Обороны, дом 112, к.2, литер З, помещ.520",
          "addressFact": "192012, г. Санкт-Петербург, пр-кт Обуховской Обороны, дом 112, к.2, литер З, помещ.520",
          "chief": "Соколова Е.В.",
          "chiefAccount": "Соколова Е.В.",
          "accounts": [
            {
              "bankName": "СЕВЕРО-ЗАПАДНЫЙ БАНК ПАО СБЕРБАНК",
              "bik": "044030653",
              "paymentAccount": "40702810355000477953",
              "correspondentAccount": "30101810500000000653"
            }
          ]
        }'::jsonb)