<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ attribute name="title" required="true" %>
<%@ attribute name="error" required="true" %>

<tags:master pageTitle="${title}">
    <h1>${error}</h1>
    <a href="${pageContext.servletContext.contextPath}/products">Back to product list</a>
</tags:master>
