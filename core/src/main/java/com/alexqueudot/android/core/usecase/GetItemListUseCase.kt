package com.alexqueudot.android.core.usecase

import com.alexqueudot.android.core.entity.Item
import com.alexqueudot.android.core.repository.ItemsRepository

/**
 * Created by alex on 2019-05-20.
 */

class GetItemListUseCase private constructor() {

    companion object {
        suspend operator fun invoke(repository: ItemsRepository, refresh: Boolean): List<Item> {
            return repository.getItems(refresh)
        }
    }
}