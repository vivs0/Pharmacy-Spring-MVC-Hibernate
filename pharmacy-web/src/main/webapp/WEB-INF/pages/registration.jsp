<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:genericpage>
    <jsp:attribute name="title">
        Jetzt bei medizin-finder.de anmelden
    </jsp:attribute>
    <jsp:attribute name="header">
        <%@include file="includes/header.jsp" %>
    </jsp:attribute>
    <jsp:attribute name="footer">
        <%@include file="includes/footer.jsp" %>
    </jsp:attribute>
    <jsp:body>
        <%@include file="contents/registerContent.jsp" %>
    </jsp:body>
</t:genericpage>