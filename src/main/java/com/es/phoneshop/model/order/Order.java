package com.es.phoneshop.model.order;

import com.es.phoneshop.model.IdentifiableItem;
import com.es.phoneshop.model.cart.CartItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order extends IdentifiableItem implements Serializable {
    private Long id;
    private String secureId;
    private List<CartItem> orderItems;
    private BigDecimal subtotal;
    private BigDecimal deliveryCost;
    private BigDecimal totalCost;
    private OrderDetails orderDetails;

    public static OrderBuilder builder() {
        return new OrderBuilder();
    }

    public static class OrderBuilder {
        private Long id;
        private String secureId;
        private List<CartItem> orderItems;
        private BigDecimal subtotal;
        private BigDecimal deliveryCost;
        private BigDecimal totalCost;
        private OrderDetails orderDetails;

        public OrderBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public OrderBuilder secureId(String secureId) {
            this.secureId = secureId;
            return this;
        }

        public OrderBuilder orderItems(List<CartItem> orderItems) {
            this.orderItems = orderItems.stream()
                    .map(CartItem::clone)
                    .collect(Collectors.toList());
            return this;
        }

        public OrderBuilder subTotal(BigDecimal subTotal) {
            this.subtotal = subTotal;
            return this;
        }

        public OrderBuilder deliveryCost(BigDecimal deliveryCost) {
            this.deliveryCost = deliveryCost;
            return this;
        }

        public OrderBuilder totalCost(BigDecimal totalCost) {
            this.totalCost = totalCost;
            return this;
        }

        public OrderBuilder orderDetails(OrderDetails orderDetails) {
            this.orderDetails = orderDetails;
            return this;
        }

        public Order build() {
            return new Order(id, secureId, orderItems, subtotal, deliveryCost, totalCost, orderDetails);
        }
    }
}
