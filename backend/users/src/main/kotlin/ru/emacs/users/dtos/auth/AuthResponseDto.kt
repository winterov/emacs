package ru.emacs.users.dtos.auth

import com.fasterxml.jackson.annotation.JsonProperty


internal data class AuthResponseDto(@field: JsonProperty("access_token")val accessToken: String)
