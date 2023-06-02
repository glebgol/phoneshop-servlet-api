package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.OrderDetails;
import com.es.phoneshop.model.order.PaymentMethod;
import com.es.phoneshop.services.OrderService;
import com.es.phoneshop.services.ValidationService;
import com.es.phoneshop.services.impl.DefaultOrderService;
import com.es.phoneshop.services.impl.DefaultValidationService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.codehaus.plexus.util.StringUtils.isBlank;

public class CheckoutPageServlet extends AbstractServlet {
    protected OrderService orderService;
    protected ValidationService validationService;
    protected static final String CHECKOUT_JSP = "/WEB-INF/pages/checkout.jsp";

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        orderService = DefaultOrderService.getInstance();
        validationService = DefaultValidationService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request.getSession());
        if (isEmpty(cart)) {
            request.getRequestDispatcher(EMPTY_CART_JSP).forward(request, response);
            return;
        }
        request.setAttribute(ORDER_ATTRIBUTE_NAME, orderService.getOrder(cart));
        request.setAttribute(PAYMENT_METHODS_ATTRIBUTE_NAME, orderService.getPaymentMethods());
        request.getRequestDispatcher(CHECKOUT_JSP).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request.getSession());
        Order order = orderService.getOrder(cart);

        Map<String, String> errors = new HashMap<>();
        validateOrderDetails(request, errors);

        if (errors.isEmpty()) {
            setOrderDetails(request, order);
            orderService.placeOrder(order, cart);
            response.sendRedirect(getRedirectUrl(request, order));
        } else {
            request.setAttribute(ERRORS_ATTRIBUTE_NAME, errors);
            doGet(request, response);
        }
    }

    private boolean isEmpty(Cart cart) {
        return cart.getItems().size() == 0;
    }

    private String getRedirectUrl(HttpServletRequest request, Order order) {
        return request.getContextPath() + "/order/overview/" + order.getSecureId();
    }

    private void setOrderDetails(HttpServletRequest request, Order order) {
        order.setOrderDetails(OrderDetails.builder()
                .firstName(request.getParameter(FIRST_NAME_ATTRIBUTE_NAME))
                .lastName(request.getParameter(LAST_NAME_ATTRIBUTE_NAME))
                .phone(request.getParameter(PHONE_ATTRIBUTE_NAME))
                .deliveryDate(parseDate(request.getParameter(DELIVERY_DATE_ATTRIBUTE_NAME)))
                .deliveryAddress((request.getParameter(DELIVERY_ADDRESS_ATTRIBUTE_NAME)))
                .paymentMethod(PaymentMethod.valueOf(request.getParameter(PAYMENT_METHOD_ATTRIBUTE_NAME)))
                .build());
    }

    public void validateOrderDetails(HttpServletRequest request, Map<String, String> errors) {
        validateName(request, FIRST_NAME_ATTRIBUTE_NAME, FIRST_NAME_ERROR_MESSAGE, errors);
        validateName(request, LAST_NAME_ATTRIBUTE_NAME, LAST_NAME_ERROR_MESSAGE, errors);
        validatePhone(request, errors);
        validateDeliveryDate(request, errors);
        validateDeliveryAddress(request, errors);
        validatePaymentMethod(request, errors);
    }

    public void validateName(HttpServletRequest request, String attributeName, String errorName, Map<String, String> errors) {
        if (!validationService.isValidName(request.getParameter(attributeName))) {
            errors.put(attributeName, errorName);
        }
    }

    public void validatePhone(HttpServletRequest request, Map<String, String> errors) {
        if (!validationService.isValidPhone(request.getParameter(PHONE_ATTRIBUTE_NAME))) {
            errors.put(PHONE_ATTRIBUTE_NAME, PHONE_ERROR_MESSAGE);
        }
    }

    public void validateDeliveryDate(HttpServletRequest request, Map<String, String> errors) {
        if (!validationService.isValidDate(request.getParameter(DELIVERY_DATE_ATTRIBUTE_NAME))) {
            errors.put(DELIVERY_DATE_ATTRIBUTE_NAME, DELIVERY_DATE_ERROR_MESSAGE);
        }
    }

    public void validateDeliveryAddress(HttpServletRequest request, Map<String, String> errors) {
        if (!validationService.isValidAddress(request.getParameter(DELIVERY_ADDRESS_ATTRIBUTE_NAME))) {
            errors.put(DELIVERY_ADDRESS_ATTRIBUTE_NAME, DELIVERY_ADDRESS_ERROR_MESSAGE);
        }
    }

    public void validatePaymentMethod(HttpServletRequest request, Map<String, String> errors) {
        if (isBlank(request.getParameter(PAYMENT_METHOD_ATTRIBUTE_NAME))) {
            errors.put(PAYMENT_METHOD_ATTRIBUTE_NAME, PAYMENT_METHOD_ERROR_MESSAGE);
        }
    }

    private LocalDate parseDate(String dateString) {
        List<Integer> parts = Arrays.stream(dateString.split("([:/.\\-])"))
                .map(Integer::parseInt)
                .toList();
        return LocalDate.of(parts.get(0), parts.get(1), parts.get(2));
    }
}
