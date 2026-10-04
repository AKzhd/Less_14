package org.example.orders;

import java.util.List;

public class OrderService {
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
        double s = 0;
        for (Item i : items) {
            s += i.getPrice() * i.getQuantity();
        }

        if (type.equals("VIP")) {
            s = s * 0.9;
        }

        if (type.equals("NEW")) {
            s = s * 0.95;
        }

        if (s > 1000) {
            s = s - 50;
        }

        return s;
    }
}