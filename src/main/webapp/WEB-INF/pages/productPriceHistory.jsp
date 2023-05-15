<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="productPriceHistory" type="com.es.phoneshop.model.ProductPriceHistory" scope="request"/>
<tags:master pageTitle="Product List">
    <p>
        ${productPriceHistory.productDescription}
    </p>
    <table>
        <thead>
        <tr>
            <td>Date</td>
            <td>Price</td>
        </tr>
        </thead>
        <c:forEach var="datePrice" items="${productPriceHistory.priceHistory}">
            <tr>
                <td>
                    ${datePrice.startDate}
                </td>
                <td class="price">
                    <fmt:formatNumber value="${datePrice.price}" type="currency" currencySymbol="${datePrice.currency.symbol}"/>
                </td>
            </tr>
        </c:forEach>
    </table>
    <a href="${pageContext.servletContext.contextPath}/products">Back to product list</a>
</tags:master>
