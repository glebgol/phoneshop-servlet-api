<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<jsp:useBean id="recentlyViewedProducts" scope="request" type="java.util.List<com.es.phoneshop.model.product.Product>"/>
<tags:master pageTitle="Product List">
  <p>
    Welcome to Expert-Soft training!
  </p>
  <p>
    <a href="${pageContext.servletContext.contextPath}/advancedSearch">Advanced search page</a>
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
  <form>
    <input name="query" value="${param.query}">
    <button>Search</button>
  </form>
  <table>
    <thead>
      <tr>
        <td>Image</td>
        <td>
          Description
          <tags:sortLink sort="description" order="asc"/>
          <tags:sortLink sort="description" order="desc"/>
        </td>
        <td>Quantity</td>
        <td class="price">
          Price
          <tags:sortLink sort="price" order="asc"/>
          <tags:sortLink sort="price" order="desc"/>
        </td>
        <td></td>
      </tr>
    </thead>
    <c:forEach var="product" items="${products}">
      <form method="post" action="${pageContext.servletContext.contextPath}/products">
        <tr>
          <td>
            <img class="product-tile" src="${product.imageUrl}">
          </td>
          <td>
            <a href="${pageContext.servletContext.contextPath}/products/${product.id}">
                ${product.description}
            </a>
          </td>
          <td class="quantity">
            <c:set var="quantityString" value="${param.quantity}"/>
            <input name="quantity" value="${not empty error and quantityErrorId eq product.id ? quantityString : 1}" class="quantity"/>
            <input type="hidden" name="productId" value="${product.id}"/>
            <c:if test="${not empty error and quantityErrorId eq product.id}">
              <div class="error">
                  ${error}
              </div>
            </c:if>
          </td>
          <td class="price">
            <a href="${pageContext.servletContext.contextPath}/products/price-history/${product.id}">
              <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
            </a>
          </td>
          <td>
            <button>
              Add to cart
            </button>
          </td>
        </tr>
      </form>
    </c:forEach>
  </table>
  <tags:recentlyViewedProducts recentlyViewedProducts="${recentlyViewedProducts}"/>
</tags:master>
