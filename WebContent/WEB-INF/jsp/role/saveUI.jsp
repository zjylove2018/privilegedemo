<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
	<title>角色设置</title>
    <%@include file="/WEB-INF/jsp/public/header.jsp" %>
    <script type="text/javascript"> 
    	function checkRoleName(name){
			//发送ajax请求，查询当前岗位名称是否存在
			var url = '${pageContext.request.contextPath}/roleAction_checkRoleName.action';
			$.post(url,{'name':name},function(data){
				if(data == '1'){
					$("#showMsg").html('当前角色名称已经存在！');
					document.getElementById("saveButton").style.display = "none";
				}else{
					$("#showMsg").html('当前角色名称可以使用！');
					document.getElementById("saveButton").style.display = "";
				}
			});
        }
    </script>
</head>
<body> 

<!-- 标题显示 -->
<div id="Title_bar">
    <div id="Title_bar_Head">
        <div id="Title_Head"></div>
        <div id="Title"><!--页面标题-->
            <img border="0" width="13" height="13" src="${pageContext.request.contextPath}/style/images/title_arrow.gif"/> 角色设置
        </div>
        <div id="Title_End"></div>
    </div>
</div>

<s:fielderror></s:fielderror>

<!--显示表单内容-->
<div id="MainArea">
	<s:form action="roleAction_%{  id==null? 'save' : 'update' }" namespace="/" method="post">
       <s:hidden name="id"></s:hidden>
        <div class="ItemBlock_Title1"><!-- 信息说明<DIV CLASS="ItemBlock_Title1">
        	<IMG BORDER="0" WIDTH="4" HEIGHT="7" SRC="${pageContext.request.contextPath}/style/blue/images/item_point.gif" /> 岗位信息 </DIV>  -->
        </div>
        
        <!-- 表单内容显示 -->
        <div class="ItemBlockBorder">
            <div class="ItemBlock">
                <table cellpadding="0" cellspacing="0" class="mainForm">
                    <tr>
                        <td width="100">名称</td>
                        <td>
                        	<s:textfield onblur="checkRoleName(this.value);" name="name" cssClass="InputStyle"></s:textfield>
                        *
                        	<div id="showMsg"></div>
                        </td>
                    </tr>
                    <tr>
                        <td>说明</td>
                        <td>
                        	<s:textarea name="description" cssClass="TextareaStyle"></s:textarea>
                       </td>
                    </tr>
                </table>
            </div>
        </div>
        
        <!-- 表单操作 -->
        <div id="InputDetailBar">
            <input id="saveButton" type="image" src="${pageContext.request.contextPath}/style/images/save.png"/>
            <a href="javascript:history.go(-1);"><img src="${pageContext.request.contextPath}/style/images/goBack.png"/></a>
        </div>
    </s:form>
</div>

</body>
</html>
