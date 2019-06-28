package com.alexqueudot.android.core.usecase

import com.alexqueudot.android.core.entity.Item
import com.alexqueudot.android.core.repository.ItemsRepository
import io.reactivex.Single

/**
 * Created by alex on 2019-05-20.
 */

class GetItemDetailsUseCase private constructor() {

    companion object {
        operator fun invoke(repository: ItemsRepository, itemId: Int): Single<Item> {
            return repository.getItem(itemId)
        }
    }
}