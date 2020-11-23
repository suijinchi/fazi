<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%-- 关闭以提高性能 --%>
<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--spring form表单标签 http://haohaoxuexi.iteye.com/blog/1807330 --%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%-- 自定义jsp函数 --%>
<%@ taglib prefix="cf" uri="/WEB-INF/tlds/cf.tld" %>

<%-- 项目路径 --%>
<c:set var="base" value="${pageContext.request.contextPath}"/>
<%-- admin resources base path --%>
<c:set var="respath" value="${pageContext.request.contextPath}/resources"/>

<%-- admin js css 图片 静态资源版本 --%>
<c:set var="ver" value="201807331261"/>
