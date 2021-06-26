package tests;

import base.BaseTest;
import issues.Defect;
import models.OrderModel;
import org.testng.SkipException;
import org.testng.annotations.Test;

import static data.ExistingUser.EMAIL;
import static data.ExistingUser.PASSWORD;
import static issues.Defect.DEFECT_MESSAGE;
import static issues.Defect.Defects.DEFECT_2;
import static pages.cart.CheckoutPage.CONFIRMATION_MESSAGE;
import static tests.TestMethods.assertEquals;
import static tests.TestMethods.assertNotEquals;

/**
 * A collection of tests that check basic cart functionalities
 */

public class Order extends BaseTest {

    /**
     * OrderModel instance that is instantiated during tests to verify the UI data against the data stored while testing
     */

    private OrderModel order;

    @Test
    public void shouldCheckOrderPrice() {

        if (Defect.isOpen(DEFECT_2)) {
            throw new SkipException(DEFECT_MESSAGE);
        }

        order = new OrderModel();

        assertEquals(prestaShop.openPrestaShop()
                             .enterRandomPopularProduct()
                             .addProductToCart(order)
                             .getTopMenuPage()
                             .goToRandomProductsSection()
                             .addRandomProducts(order, 5)
                             .getTopMenuPage()
                             .goToCart()
                             .getItemsPrice(), order.getOrderItemsPrice());
    }

    @Test
    public void shouldCheckItemsQuantity() {

        order = new OrderModel();

        assertEquals(prestaShop.openPrestaShop()
                             .getTopMenuPage()
                             .goToRandomProductsSection()
                             .goToRandomProduct()
                             .increaseQuantityBy(3)
                             .addProductToCart(order)
                             .getTopMenuPage()
                             .goToRandomProductsSection()
                             .goToRandomProduct()
                             .increaseQuantityBy(5)
                             .decreaseQuantityBy(3)
                             .addProductToCart(order)
                             .getTopMenuPage()
                             .goToRandomProductsSection()
                             .goToRandomProduct()
                             .decreaseQuantityBy(10)
                             .getTopMenuPage()
                             .goToRandomProductsSection()
                             .addRandomProducts(order, 3)
                             .getTopMenuPage()
                             .goToCart()
                             .getItemsQuantity(), order.getOrderItemsQuantity());
    }

    @Test
    public void shouldCheckNotEqualItemsQuantity() {

        order = new OrderModel();

        assertNotEquals(prestaShop.openPrestaShop()
                                .getTopMenuPage()
                                .goToSignInSection()
                                .logIn(EMAIL, PASSWORD)
                                .getTopMenuPage()
                                .goToRandomProductsSection()
                                .addRandomProducts(order, 3)
                                .getTopMenuPage()
                                .goToCart()
                                .getItemsQuantity(), 5);
    }

    @Test
    public void shouldPlaceOrderAsGuest() {

        assertEquals(prestaShop.openPrestaShop()
                             .getTopMenuPage()
                             .goToRandomProductsSection()
                             .addRandomProducts(5)
                             .getTopMenuPage()
                             .goToCart()
                             .proceedToCheckout()
                             .placeOrderAsGuest(guestUser.getFirstName(), guestUser.getLastName(), guestUser.getEmail(), guestUser.getAddress(), guestUser.getCity(), guestUser.getPostalCode(), guestUser.getCountry())
                             .getConfirmationMessage(), CONFIRMATION_MESSAGE);
    }

    @Test
    public void shouldPlaceOrderAsLoggedUser() {

        assertEquals(prestaShop.openPrestaShop()
                             .getTopMenuPage()
                             .goToSignInSection()
                             .logIn(EMAIL, PASSWORD)
                             .getTopMenuPage()
                             .goToRandomProductsSection()
                             .addRandomProducts(5)
                             .getTopMenuPage()
                             .goToCart()
                             .proceedToCheckout()
                             .placeOrderAsLoggedUser()
                             .getConfirmationMessage(), CONFIRMATION_MESSAGE);
    }

    @Test
    public void shouldStoreOrderNumber() {

        order = new OrderModel();

        assertEquals(prestaShop.openPrestaShop()
                             .getTopMenuPage()
                             .goToSignInSection()
                             .logIn(EMAIL, PASSWORD)
                             .getTopMenuPage()
                             .goToRandomProductsSection()
                             .addRandomProducts(5)
                             .getTopMenuPage()
                             .goToCart()
                             .proceedToCheckout()
                             .placeOrderAsLoggedUser()
                             .getOrderReference(order)
                             .getTopMenuPage()
                             .goToSignInSection()
                             .goToOrderHistory()
                             .findOrderNumber(order.getOrderReferenceNumber()), order.getOrderReferenceNumber());
    }
}