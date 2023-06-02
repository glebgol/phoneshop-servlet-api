<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="order" scope="request" type="com.es.phoneshop.model.order.Order"/>
<jsp:useBean id="paymentMethods" scope="request" type="java.util.List"/>

<tags:master pageTitle="Checkout">
    <c:if test="${not empty param.message}">
        <div class="success">
            ${param.message}
        </div>
    </c:if>
    <c:if test="${not empty errors}">
        <div class="error">
            Error occurred while placing an order
        </div>
    </c:if>
    <form method="post">
        <table>
            <thead>
            <tr>
                <td>Image</td>
                <td>
                    Description
                </td>
                <td class="price">
                    Quantity
                </td>
                <td class="price">
                    Price
                </td>
            </tr>
            </thead>
            <c:forEach var="item" items="${order.orderItems}" varStatus="status">
                <tr>
                    <td>
                        <img class="product-tile" src="${item.product.imageUrl}">
                    </td>
                    <td>
                        <a href="${pageContext.servletContext.contextPath}/products/${item.product.id}">
                                ${item.product.description}
                        </a>
                    </td>
                    <td class="quantity">
                        <fmt:formatNumber value="${item.quantity}" var="quantity"/>
                        ${quantity}
                    </td>
                    <td class="price">
                        <c:set var="currency" value="${item.product.currency.symbol}" />
                        <a href="${pageContext.servletContext.contextPath}/products/price-history/${item.product.id}">
                            <fmt:formatNumber value="${item.product.price}" type="currency" currencySymbol="${currency}"/>
                        </a>
                    </td>
                </tr>
            </c:forEach>
            <tr>
                <td></td>
                <td></td>
                <td>Subtotal:</td>
                <td class="price">
                    <p>
                        <fmt:formatNumber value="${order.subtotal}" type="currency" currencySymbol="${currency}"/>
                    </p>
                </td>
            </tr>
            <tr>
                <td></td>
                <td></td>
                <td>Delivery cost:</td>
                <td class="price">
                    <p>
                        <fmt:formatNumber value="${order.deliveryCost}" type="currency" currencySymbol="${currency}"/>
                    </p>
                </td>
            </tr>
            <tr>
                <td></td>
                <td></td>
                <td>Total cost:</td>
                <td>
                    <fmt:formatNumber value="${order.totalCost}" type="currency" currencySymbol="${currency}"/>
                </td>
            </tr>
        </table>
        <h2>Your details</h2>
        <table>
            <tags:orderFormRow name="firstName" label="First Name" errors="${errors}" order="${order}"/>
            <tags:orderFormRow name="lastName" label="Last Name" errors="${errors}" order="${order}"/>
            <tags:orderFormRow name="phone" label="Phone" errors="${errors}" order="${order}"/>
            <tags:orderFormRow name="deliveryDate" label="Delivery Date" errors="${errors}" order="${order}"
                               placeholder="yyyy-mm-dd"/>
            <tags:orderFormRow name="deliveryAddress" label="Delivery Address" errors="${errors}" order="${order}"/>
            <tr>
                <td>Payment method<span style="color: red">*</span></td>
                <td>
                    <select name="paymentMethod">
                        <option></option>
                        <c:forEach var="paymentMethod" items="${paymentMethods}">
                            <option ${paymentMethod eq param['paymentMethod'] ? 'selected="selected"' : ''}>
                                    ${paymentMethod}
                            </option>
                        </c:forEach>
                    </select>
                    <c:set var="error" value="${errors['paymentMethod']}"/>
                    <c:if test="${not empty error}">
                        <div class="error">
                                ${error}
                        </div>
                    </c:if>
                </td>
            </tr>
        </table>
        <p>
            <button>Place order</button>
        </p>
    </form>
</tags:master>
