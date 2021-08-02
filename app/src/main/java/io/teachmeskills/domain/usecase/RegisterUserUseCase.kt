package io.teachmeskills.domain.usecase

interface RegisterUserUseCase {

    suspend operator fun invoke(
        uid: String,
        username:String,
        email: String
    )
}