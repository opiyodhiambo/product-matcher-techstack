package com.adventure.gateway.controller

import com.adventure.apis.accounts.Commands.CreateSellerAccount
import com.adventure.apis.accounts.State.Sex
import com.adventure.apis.store.Category.StoreCategory
import com.adventure.apis.store.Commands.AddStock
import com.adventure.apis.store.Commands.CreateStore
import com.adventure.apis.store.QueryResults.ManageStoreQueryResults
import org.axonframework.extensions.reactor.commandhandling.gateway.ReactorCommandGateway
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.util.UUID

@RestController
class SellerController(
    private val command: ReactorCommandGateway
) {
    @PostMapping("/seller/account/create")
    fun createSellerAccount(
        @RequestBody
        firstName: String,
        lastName: String,
        emailAddress: String,
        gender: Sex
    ): Mono<ResponseEntity<String>> {
        val createSellerAccountCommand = CreateSellerAccount(
            sellerId = UUID.randomUUID(),
            firstName = firstName,
            lastName = lastName,
            email = emailAddress,
            gender = gender
        )
        return command.send<ResponseEntity<String>?>(createSellerAccountCommand)
            .then()
            .thenReturn(ResponseEntity.ok("Welcome to Soko!"))
    }

    @PostMapping("/seller/store/create")
    fun createStore(
        @RequestBody
        sellerId: UUID,
        category: StoreCategory
    ): Mono<ResponseEntity<String>> {
        val createStoreCommand = CreateStore(
            storeId = UUID.randomUUID(),
            sellerId = sellerId,
            category = category
        )
        return command.send<ResponseEntity<String>?>(createStoreCommand)
            .then()
            .thenReturn(ResponseEntity.ok("Successfully Created your store"))
    }

    @PostMapping("/seller/store/{storeId}/stock/add")
    fun addStock(
        @PathVariable
        storeId: UUID,
        @RequestBody
        productName: String,
        productCategory: String,
        productDescription: String,
        price: Double
    ): Mono<ResponseEntity<String>> {
        val addStockCommand = AddStock(
            storeId = storeId,
            productId = UUID.randomUUID(),
            productName = productName,
            productCategory = productCategory,
            productDescription = productDescription,
            price = price
        )
        return command.send<ResponseEntity<String>?>(addStockCommand)
            .then()
            .thenReturn(ResponseEntity.ok("Product Added"))
    }

    @GetMapping("/seller/store/{storeId}/manage")
    fun manageStore(@PathVariable storeId: UUID): Mono<ResponseEntity<ManageStoreQueryResults>> {
        TODO()
    }
}