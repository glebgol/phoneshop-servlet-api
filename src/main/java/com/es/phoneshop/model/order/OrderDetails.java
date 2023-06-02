package com.es.phoneshop.model.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class OrderDetails {
    private String firstName;
    private String lastName;
    private String phone;
    private LocalDate deliveryDate;
    private String deliveryAddress;
    private PaymentMethod paymentMethod;

    public static OrderDetailsBuilder builder() {
        return new OrderDetailsBuilder();
    }

    public static class OrderDetailsBuilder {
        private String firstName;
        private String lastName;
        private String phone;
        private LocalDate deliveryDate;
        private String deliveryAddress;
        private PaymentMethod paymentMethod;

        public OrderDetailsBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public OrderDetailsBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public OrderDetailsBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public OrderDetailsBuilder deliveryDate(LocalDate deliveryDate) {
            this.deliveryDate = deliveryDate;
            return this;
        }

        public OrderDetailsBuilder deliveryAddress(String deliveryAddress) {
            this.deliveryAddress = deliveryAddress;
            return this;
        }

        public OrderDetailsBuilder paymentMethod(PaymentMethod paymentMethod) {
            this.paymentMethod = paymentMethod;
            return this;
        }

        public OrderDetails build() {
            return new OrderDetails(firstName, lastName, phone, deliveryDate, deliveryAddress, paymentMethod);
        }
    }
}
