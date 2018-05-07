<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <title>部门列表</title>
   	<%@include file="/WEB-INF/jsp/public/header.jsp" %>
</head>
<body>
<div id="Title_bar">
    <div id="Title_bar_Head">
        <div id="Title_Head"></div>
        <div id="Title"><!--页面标题-->
            <img border="0" width="13" height="13" src="${pageContext.request.contextPath}/style/images/title_arrow.gif"/> 部门管理
        </div>
        <div id="Title_End"></div>
    </div>
</div>

<div id="MainArea">
    <table cellspacing="0" cellpadding="0" class="TableStyle">
       
        <!-- 表头-->
        <thead>
            <tr align=center valign=middle id=TableTitle>
            	<td width="150px">部门名称</td>
				<td width="150px">上级部门名称</td>
				<td width="200px">职能说明</td>
				<td>相关操作</td>
            </tr>
        </thead>

		<!--显示部门数据列表-->
        <tbody id="TableData" class="dataContainer" datakey="departmentList">
        	<s:iterator value="list">
				<tr class="TableDetail1 template">
					<td><s:a action="departmentAction_list?parentId=%{id}">${name}</s:a>&nbsp;</td>
					<td>${parent.name}&nbsp;</td>
					<td>${description}&nbsp;</td>
					<td>
						<s:if test="#session.loginUser.checkPrivilegeByName('部门删除')">
							<s:a onclick="return window.confirm('确定删除部门【%{name}】吗？')" action="departmentAction_delete?id=%{id}">删除</s:a>
						</s:if>
						<s:if test="#session.loginUser.checkPrivilegeByName('部门修改')">
							<s:a action="departmentAction_updateUI?id=%{id}" namespace="/">修改</s:a>
						</s:if>
					</td>
				</tr>
        	</s:iterator>
        </tbody>
    </table>
    
    <!-- 其他功能超链接 -->
    <div id="TableTail">
        <div id="TableTail_inside">
           <s:if test="#session.loginUser.checkPrivilegeByName('部门添加')">
				<s:a action="departmentAction_saveUI?parentId=%{parentId}" namespace="/"><img src="${pageContext.request.contextPath}/style/images/createNew.png" /></s:a>
           </s:if>
        	<s:if test="parentId != null">
	        	<s:a action="departmentAction_list?parentId=%{dept.parent.id}"><IMG SRC="${pageContext.request.contextPath}/style/blue/images/button/ReturnToPrevLevel.png" /></s:a>
        	</s:if>
        </div>
    </div>
</div>
</body>
</html>
