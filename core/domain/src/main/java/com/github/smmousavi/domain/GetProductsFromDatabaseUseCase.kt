package com.github.smmousavi.domain

import com.github.smmousavi.repository.product.OfflineFirstProductRepository
import javax.inject.Inject

class GetProductsFromDatabaseUseCase @Inject constructor(private val productsRepository: OfflineFirstProductRepository) {
    suspend operator fun invoke() = productsRepository.getAllProducts()
}