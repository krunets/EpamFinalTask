<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${not empty sessionScope.locale ? sessionScope.locale : 'ru_RU'}"/>
<fmt:setBundle basename="property/authentication/signup/signUp" var="rb" />
<%@ page isELIgnored="false"%>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/bootstrap.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/bootstrap-theme.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/form_style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/social_icon.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/styles.css">
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/index.jsp">Buber</a>
        </div>
        <ul class="nav navbar-nav">
            <li ><a href="${pageContext.request.contextPath}/index.jsp"><fmt:message key="label.homePage"  bundle="${rb}"/></a></li>
            <li class="active"> <a href="#"><fmt:message key="label.current"  bundle="${rb}"/></a></li>
        </ul>
        <div class="top-lang-list">
            <select id="mySelect" name="locale" onchange="location = this.options[this.selectedIndex].value;">
                <option disabled selected><fmt:message key="label.language" bundle="${rb}" /></option>
                <option value="${pageContext.request.contextPath}/controller?command=change_locale&locale=ru_RU">Русский</option>
                <option value="${pageContext.request.contextPath}/controller?command=change_locale&locale=be_BY">Мова</option>
                <option value="${pageContext.request.contextPath}/controller?command=change_locale&locale=en_US">English</option>
            </select>
        </div>
    </div>
</nav>
<div class="form">
    <div id="signUpDriver">
        <form id="driverForm" action="${pageContext.request.contextPath}/controller" method="POST">
            <h1><fmt:message key="label.current"  bundle="${rb}"/></h1>
            <input type="hidden" name="command" value="signup" />
            <div id="hiddenError">
                <br/>
                ${errorEmailPasswordMessage}
                <br/>
            </div>
            <div class="top-row">
                <div class="field-wrap">
                    <label><fmt:message key="label.firstName"  bundle="${rb}"/></label>
                    <input  name="firstName" type="text" required placeholder=<fmt:message key="label.firstNamePlaceholder"  bundle="${rb}"/>>
                </div>
                <div class="field-wrap">
                    <label><fmt:message key="label.secondName"  bundle="${rb}"/></label>
                    <input  name="secondName" type="text" required placeholder=<fmt:message key="label.secondNamePlaceholder"  bundle="${rb}"/>>
                </div>
            </div>
            <label>E-mail:</label>
            <input  name="email" type="email" required placeholder=<fmt:message key="label.emailPlaceholder"  bundle="${rb}"/>>
            <label><fmt:message key="label.password"  bundle="${rb}"/></label>
            <input name="password" type="password" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" title=<fmt:message key="label.passwordPatternMessage"  bundle="${rb}"/> required placeholder=<fmt:message key="label.password"  bundle="${rb}"/>>
            <label><fmt:message key="label.role"  bundle="${rb}"/></label>
            <select name="role" id="userRole">
                <option value="NONE"><fmt:message key="label.chooseRole"  bundle="${rb}"/></option>
                <option value="DRIVER"><fmt:message key="label.driver"  bundle="${rb}"/></option>
                <option value="PASSENGER"><fmt:message key="label.passenger"  bundle="${rb}"/></option>
            </select>
            <button type="submit" class="button button-block"><fmt:message key="label.submit"   bundle="${rb}"/></button>
        </form>
    </div>
</div>
<c:import url="${pageContext.request.contextPath}/jsp/footer.jsp"/>
    <script src="${pageContext.request.contextPath}/lib/jquery/jquery-3.2.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/lib/bootstrap/bootstrap.js"></script>
</body>
</html>