<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="cart" scope="request" type="com.es.phoneshop.model.cart.Cart"/>

<tags:master pageTitle="Cart">
    <p>
        Cart: ${cart} total quantity: ${cart.totalQuantity}
    </p>
    <c:if test="${not empty param.message}">
        <div class="success">
            ${param.message}
        </div>
    </c:if>
    <c:if test="${not empty errors}">
        <div class="error">
            There was errors updating cart
        </div>
    </c:if>
    <form method="post" action="${pageContext.servletContext.contextPath}/cart">
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
                <td></td>
            </tr>
            </thead>
            <c:forEach var="item" items="${cart.items}" varStatus="status">
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

                        <c:set var="error" value="${errors[item.product.id]}"/>
                        <input name="quantity" value="${not empty error ? paramValues['quantity'][status.index] : item.quantity}" class="quantity"/>
                        <input type="hidden" name="productId" value="${item.product.id}"/>
                        <c:if test="${not empty error}">
                            <div class="error">
                                    ${errors[item.product.id]}
                            </div>
                        </c:if>
                    </td>
                    <td class="price">
                        <c:set var="currency" value="${item.product.currency.symbol}" />
                        <a href="${pageContext.servletContext.contextPath}/products/price-history/${item.product.id}">
                            <fmt:formatNumber value="${item.product.price}" type="currency" currencySymbol="${currencyl}"/>
                        </a>
                    </td>
                    <td>
                        <button form="deleteCartItem"
                                formaction="${pageContext.servletContext.contextPath}/cart/deleteCartItem/${item.product.id}">Delete</button>
                    </td>
                </tr>
            </c:forEach>
            <tr>
                <td>Total cost:</td>
                <td>
                    <fmt:formatNumber value="${cart.totalCost}" type="currency" currencySymbol="${currency}"/>
                </td>
                <td>Total Quantity:</td>
                <td>${cart.totalQuantity}</td>
            </tr>
        </table>
        <p>
            <button>Update</button>
        </p>
    </form>
    <form id='deleteCartItem' method="post">
    </form>
</tags:master>
