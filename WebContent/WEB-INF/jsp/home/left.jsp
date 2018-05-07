<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<title>导航菜单</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script language="JavaScript" src="script/jquery.js"></script>
<script language="JavaScript" src="script/menu.js"></script>
<link type="text/css" rel="stylesheet" href="style/blue/menu.css" />
</head>
<body style="margin: 0">
<div id="Menu">
    <ul id="MenuUl">
    	<s:iterator value="#application.privilegeTopList">
    		<s:if test="#session.loginUser.checkPrivilegeByName(name)">
		        <li class="level1">
		            <div onClick="menuClick(this);" class="level1Style">
		            	<img src="style/images/MenuIcon/${id }.gif" class="Icon" /> ${name }
		            </div>
		            <ul style="display: none;" class="MenuLevel2">
		            	<s:iterator value="children">
		            		<s:if test="#session.loginUser.checkPrivilegeByName(name)">
				                <li class="level2">
				                    <div class="level2Style">
				                    	<img src="style/images/MenuIcon/menu_arrow_single.gif" /> 
				                    	<a target="right" href="${pageContext.request.contextPath }${url}.action"> ${name }</a>
				                    </div>
				                </li>
		            		</s:if>
		            	</s:iterator>
		            </ul>
		        </li>
    		</s:if>
    	</s:iterator>
    </ul>
</div>
</body>
</html>
