package io.teachmeskills.domain.usecase

import io.teachmeskills.data.User

interface GetUserByUidUseCase {

    suspend operator fun invoke(uid: String): Result<User>

}