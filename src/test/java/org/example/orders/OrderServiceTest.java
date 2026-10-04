package org.example.orders;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderServiceTest {

    private OrderService service;

    @BeforeEach
    void setUp() {
        service = new OrderService();
    }

    @Test
    @DisplayName("Обычный клиент: сумма без скидок")
    void regularCustomerNoDiscount() {
        List<Item> items = List.of(
                new Item("A", 100.0, 2),
                new Item("B", 50.0, 1)
        );
        assertEquals(250.0, service.calc(items, "REGULAR"), 0.0001);
    }

    @Test
    @DisplayName("VIP клиент: скидка 10%")
    void vipCustomerDiscount() {
        List<Item> items = List.of(new Item("A", 100.0, 2));
        assertEquals(180.0, service.calc(items, "VIP"), 0.0001);
    }

    @Test
    @DisplayName("NEW клиент: скидка 5%")
    void newCustomerDiscount() {
        List<Item> items = List.of(new Item("A", 100.0, 2));
        assertEquals(190.0, service.calc(items, "NEW"), 0.0001);
    }

    @Test
    @DisplayName("Сумма больше 1000: вычитается 50")
    void largeOrderDeduction() {
        List<Item> items = List.of(new Item("A", 600.0, 2));
        assertEquals(1150.0, service.calc(items, "REGULAR"), 0.0001);
    }

    @Test
    @DisplayName("VIP + сумма больше 1000: скидка и вычет")
    void vipAndLargeOrder() {
        List<Item> items = List.of(new Item("A", 600.0, 2));
        // 1200 * 0.9 = 1080; 1080 - 50 = 1030
        assertEquals(1030.0, service.calc(items, "VIP"), 0.0001);
    }

    @Test
    @DisplayName("Пустой список товаров")
    void emptyCart() {
        assertEquals(0.0, service.calc(List.of(), "REGULAR"), 0.0001);
    }
    @Test
    @DisplayName("Больше 10 товаров: дополнительная скидка 1%")
    void moreThanTenItemsExtraDiscount() {
        List<Item> items = List.of(new Item("A", 100.0, 11));
        // 1100 * 0.99 = 1089; 1089 > 1000 => 1089 - 50 = 1039
        assertEquals(1039.0, service.calc(items, "REGULAR"), 0.0001);
    }

    @Test
    @DisplayName("Ровно 10 товаров: дополнительная скидка не применяется")
    void exactlyTenItemsNoExtraDiscount() {
        List<Item> items = List.of(new Item("A", 100.0, 10));
        // 1000, не больше 1000 => без вычета
        assertEquals(1000.0, service.calc(items, "REGULAR"), 0.0001);
    }

    @Test
    @DisplayName("Больше 10 товаров + VIP: обе скидки")
    void moreThanTenItemsWithVip() {
        List<Item> items = List.of(new Item("A", 100.0, 11));
        // 1100 * 0.9 = 990; 990 * 0.99 = 980.1; 980.1 < 1000 => без вычета
        assertEquals(980.1, service.calc(items, "VIP"), 0.0001);
    }



}
