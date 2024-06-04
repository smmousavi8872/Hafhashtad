package com.github.smmousavi.domain

import com.github.smmousavi.repository.product.OfflineFirstProductRepository
import javax.inject.Inject

class FetchProductsFromRemoteUseCase @Inject constructor(private val productsRepository: OfflineFirstProductRepository) {
    suspend operator fun invoke() = productsRepository.fetchAllProducts()
}