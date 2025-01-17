<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>
<jsp:useBean id="cart" type="com.es.phoneshop.model.cart.Cart" scope="request"/>
<jsp:useBean id="recentlyViewedProducts" scope="request" type="java.util.List<com.es.phoneshop.model.product.Product>"/>

<tags:master pageTitle="Product List">
    <p>
        Cart: ${cart}
    </p>
    <p>
        ${product.description}
    </p>
    <c:if test="${not empty param.message and empty error}">
        <div class="success">
                ${param.message}
        </div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="error">
            An error during adding to cart
        </div>
    </c:if>
    <form method="post">
        <table>
            <tr>
                <td>Image</td>
                <td>
                    <img class="product-tile" src="${product.imageUrl}">
                </td>
            </tr>
            <tr>
                <td>Price</td>
                <td class="price">
                    <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
                </td>
            </tr>
            <tr>
                <td>Code</td>
                <td>${product.code}</td>
            </tr>
            <tr>
                <td>Stock</td>
                <td>${product.stock}</td>
            </tr>
            <tr>
                <td>quantity</td>
                <td>
                    <input name="quantity" value="${not empty param.quantity ? param.quantity : 1}" required>
                    <c:if test="${not empty error}">
                        <div class="error">
                            ${error}
                        </div>
                    </c:if>
                </td>
            </tr>
        </table>
        <p>
            <button>Add to cart</button>
        </p>
    </form>
    <a href="${pageContext.servletContext.contextPath}/products">Back to product list</a>
    <tags:recentlyViewedProducts recentlyViewedProducts="${recentlyViewedProducts}"/>
</tags:master>
