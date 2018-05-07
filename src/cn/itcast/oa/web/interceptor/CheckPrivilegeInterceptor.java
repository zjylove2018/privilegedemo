package cn.itcast.oa.web.interceptor;

import java.util.List;

import org.apache.struts2.ServletActionContext;

import cn.itcast.oa.domain.User;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 进行权限检查的拦截器
 * 
 * @author zhaoqx
 * 
 */
public class CheckPrivilegeInterceptor extends AbstractInterceptor {
	public String intercept(ActionInvocation ai) throws Exception {
		System.out.println("进行权限检查。。。。");
		ActionProxy proxy = ai.getProxy();
		String namespace = proxy.getNamespace();
		String actionName = proxy.getActionName();
		String url = namespace + actionName;   //  /departmentAction_delete.action
		
		if(url.endsWith("UI")){
			url = url.substring(0, url.length() - 2);
		}

		// 从session中获取登录用户
		User user = (User) ServletActionContext.getRequest().getSession()
				.getAttribute("loginUser");

		// 一：如果用户没有登录
		if (user == null) {
			// 如果用户访问的是登录功能，就放行
			if (url.equals("/userAction_login")) {
				return ai.invoke();
			} else {
				// 如果用户访问的不是登录功能，就跳转到登录页面
				return "loginUI";
			}
		} else {
			//url集合存放是需要进行权限校验url请求
			List<String> urlList = (List<String>) ServletActionContext.getServletContext().getAttribute("urlList");
			// 二：如果用户已经登录

			// 如果当前请求url需要进行校验
			if (urlList.contains(url)) {
				// 如果用户有权限，就放行
				if (user.checkPrivilegeByUrl(url)) {
					return ai.invoke();
				} else {
					// 如果用户没有权限，就跳转到没有权限提示页面
					return "noPrivilegeUI";
				}
			} else {
				// 如果当前请求url不需要进行校验
				return ai.invoke();
			}

		}
	}

}
