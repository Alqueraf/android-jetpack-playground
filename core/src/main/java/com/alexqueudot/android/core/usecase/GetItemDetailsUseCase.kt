package com.alexqueudot.android.core.usecase

import com.alexqueudot.android.core.entity.Item
import com.alexqueudot.android.core.repository.ItemsRepository

/**
 * Created by alex on 2019-05-20.
 */

class GetItemDetailsUseCase private constructor() {

    companion object {
        suspend operator fun invoke(repository: ItemsRepository, itemId: Int): Item? {
            return repository.getItem(itemId)
        }
    }
}