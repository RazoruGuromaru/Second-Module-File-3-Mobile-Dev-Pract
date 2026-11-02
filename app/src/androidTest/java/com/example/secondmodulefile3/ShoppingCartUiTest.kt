package com.example.secondmodulefile3

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.assertEquals

class ShoppingCartUiTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun addProductTest() {

        composeTestRule.setContent {
            ShoppingCartScreen(
                newProductPrice = { 200 }
            )
        }

        composeTestRule
            .onNodeWithText("Количество товаров: 3")
            .assertExists()

        composeTestRule
            .onNodeWithText("Добавить товар")
            .performClick()

        composeTestRule
            .onNodeWithText("Товар #4 - 200 рублей")
            .assertExists()

        composeTestRule
            .onNodeWithText("Количество товаров: 4")
            .assertExists()

        composeTestRule
            .onNodeWithText("Товаров на сумму: 506 рублей")
            .assertExists()
    }

    @Test
    fun freeDeliveryTest() {

        var notificationCount = 0

        composeTestRule.setContent {
            ShoppingCartScreen(
                newProductPrice = { 200 },
                onFreeDelivery = {
                    notificationCount++
                }
            )
        }

        composeTestRule
            .onNodeWithText("Добавить товар")
            .performClick()

        composeTestRule.waitForIdle()

        assertEquals(1, notificationCount)
    }

    @Test
    fun freeDeliveryNotShownAt500Test() {

        var notificationCount = 0

        composeTestRule.setContent {
            ShoppingCartScreen(
                newProductPrice = { 194 },
                onFreeDelivery = {
                    notificationCount++
                }
            )
        }

        composeTestRule
            .onNodeWithText("Добавить товар")
            .performClick()

        composeTestRule.waitForIdle()

        assertEquals(0, notificationCount)
    }

    @Test
    fun removeLastProductTest() {

        composeTestRule.setContent {
            ShoppingCartScreen()
        }

        composeTestRule
            .onNodeWithText("Товар #3 - 56 рублей")
            .assertExists()

        composeTestRule
            .onNodeWithText("Удалить товар")
            .performClick()

        composeTestRule
            .onNodeWithText("Товар #3 - 56 рублей")
            .assertDoesNotExist()

        composeTestRule
            .onNodeWithText("Товар #1 - 100 рублей")
            .assertExists()

        composeTestRule
            .onNodeWithText("Товар #2 - 150 рублей")
            .assertExists()

        composeTestRule
            .onNodeWithText("Товаров на сумму: 250 рублей")
            .assertExists()

        composeTestRule
            .onNodeWithText("Количество товаров: 2")
            .assertExists()
    }

    @Test
    fun removeButtonHiddenWhenCartIsEmptyTest() {

        composeTestRule.setContent {
            ShoppingCartScreen(
                initialProducts = emptyList()
            )
        }

        composeTestRule
            .onNodeWithText("Удалить товар")
            .assertDoesNotExist()

        composeTestRule
            .onNodeWithText("Добавить товар")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Товаров на сумму: 0 рублей")
            .assertExists()

        composeTestRule
            .onNodeWithText("Количество товаров: 0")
            .assertExists()
    }

    @Test
    fun addProductAfterEmptyCartTest() {

        composeTestRule.setContent {
            ShoppingCartScreen(
                initialProducts = emptyList(),
                newProductPrice = { 100 }
            )
        }

        composeTestRule
            .onNodeWithText("Добавить товар")
            .performClick()

        composeTestRule
            .onNodeWithText("Товар #1 - 100 рублей")
            .assertExists()

        composeTestRule
            .onNodeWithText("Удалить товар")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Товаров на сумму: 100 рублей")
            .assertExists()

        composeTestRule
            .onNodeWithText("Количество товаров: 1")
            .assertExists()
    }
}
