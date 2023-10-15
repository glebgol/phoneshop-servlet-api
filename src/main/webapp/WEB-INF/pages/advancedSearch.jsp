<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="recentlyViewedProducts" scope="request" type="java.util.List<com.es.phoneshop.model.product.Product>"/>
<jsp:useBean id="searchMethods" scope="request" type="java.util.List"/>

<tags:master pageTitle="Product List">
  <p>
    Welcome to Expert-Soft training!
  </p>
  <form>
    <p>
      Description
      <input name="query" value="${param.query}">
      <select name="searchMethod">
        <c:forEach var="searchMethod" items="${searchMethods}">
          <option ${searchMethod eq param['searchMethod'] ? 'selected="selected"' : ''} value="${searchMethod}">
              ${searchMethod.searchType}
          </option>
        </c:forEach>
      </select>
    </p>
    <tags:priceInput name="minPrice" label="Min price" errors="${errors}"/>
    <tags:priceInput name="maxPrice" label="Max price" errors="${errors}"/>
    <p></p>
    <button>Search</button>
  </form>
  <c:if test="${not empty products}">
    <table>
      <thead>
      <tr>
        <td>Image</td>
        <td>
          Description
        </td>
        <td class="price">
          Price
        </td>
      </tr>
      </thead>
      <c:forEach var="product" items="${products}">
        <tr>
          <td>
            <img class="product-tile" src="${product.imageUrl}">
          </td>
          <td>
              ${product.description}
          </td>
          <td class="price">
            <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
          </td>
        </tr>
      </c:forEach>
    </table>
  </c:if>
  <tags:recentlyViewedProducts recentlyViewedProducts="${recentlyViewedProducts}"/>
</tags:master>
