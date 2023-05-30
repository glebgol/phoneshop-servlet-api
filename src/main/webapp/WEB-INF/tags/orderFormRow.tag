<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="name" required="true" type="java.lang.String"%>
<%@ attribute name="label" required="true" type="java.lang.String"%>
<%@ attribute name="errors" required="true" type="java.util.HashMap"%>
<%@ attribute name="order" required="true" type="com.es.phoneshop.model.order.Order"%>
<%@ attribute name="placeholder" required="false" %>

<tr>
    <td>
        ${label}<span title="Required field" style="color: red">*</span>:
    </td>
    <td>
        <c:set var="error" value="${errors[name]}"/>
        <input name=${name}
                       value="${param[name]}" placeholder="${placeholder}"/>
        <c:if test="${not empty error}">
            <div class="error">
                    ${error}
            </div>
        </c:if>
    </td>
</tr>
