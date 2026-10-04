package org.example.orders;

import java.util.List;

/**
 * Сервис расчёта стоимости заказа.
 */
public class OrderService {

    private static final String VIP = "VIP";
    private static final String NEW = "NEW";

    private static final double VIP_DISCOUNT = 0.9;
    private static final double NEW_DISCOUNT = 0.95;

    private static final double LARGE_ORDER_THRESHOLD = 1000.0;
    private static final double LARGE_ORDER_DEDUCTION = 50.0;

    /**
     * Рассчитывает итоговую стоимость заказа с учётом скидок.
     *
     * <p>Порядок применения скидок:
     * <ol>
     *     <li>Сначала суммируется стоимость всех позиций
     *         ({@code price * quantity}).</li>
     *     <li>Применяется процентная скидка в зависимости от типа клиента:
     *         {@code VIP} — 10%, {@code NEW} — 5%.</li>
     *     <li>Если итоговая сумма превышает 1000, вычитается фиксированная
     *         скидка 50.</li>
     * </ol>
     *
     * @param items список товаров в заказе; не может быть {@code null}
     * @param type  тип клиента ({@code VIP}, {@code NEW} или любой другой
     *              — тогда скидка не применяется)
     * @return итоговая стоимость заказа
     */
    public double calc(List<Item> items, String type) {
        double total = calculateSubtotal(items);
        total = applyCustomerDiscount(total, type);
        total = applyLargeOrderDeduction(total);
        return total;
    }

    private double calculateSubtotal(List<Item> items) {
        double subtotal = 0;
        for (Item item : items) {
            subtotal += item.getPrice() * item.getQuantity();
        }
        return subtotal;
    }

    private double applyCustomerDiscount(double total, String type) {
        if (VIP.equals(type)) {
            return total * VIP_DISCOUNT;
        }
        if (NEW.equals(type)) {
            return total * NEW_DISCOUNT;
        }
        return total;
    }

    private double applyLargeOrderDeduction(double total) {
        if (total > LARGE_ORDER_THRESHOLD) {
            return total - LARGE_ORDER_DEDUCTION;
        }
        return total;
    }
}