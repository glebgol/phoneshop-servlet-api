<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="recentlyViewedProducts" type="java.util.List<com.es.phoneshop.model.product.Product>" required="true" %>

<c:if test="${not empty recentlyViewedProducts}">
    <h3>Recently viewed</h3>
    <c:forEach var="product" items="${recentlyViewedProducts}">
        <div>
            <p>
                <img src="${product.imageUrl}">
            </p>
            <p>
                <a href="${pageContext.servletContext.contextPath}/products/${product.id}">
                        ${product.description}
                </a>
            </p>
            <p>
                <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
            </p>
        </div>
    </c:forEach>
</c:if>
