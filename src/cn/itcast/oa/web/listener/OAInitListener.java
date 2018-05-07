package cn.itcast.oa.web.listener;

import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.itcast.oa.domain.Privilege;
import cn.itcast.oa.service.IPrivilegeService;
import cn.itcast.oa.service.PrivilegeServiceImpl;
/**
 * 项目启动时，加载此监听器，完成权限数据初始化
 * @author zhaoqx
 *
 */
public class OAInitListener implements ServletContextListener {

	/**
	 * 初始化方法
	 */
	public void contextInitialized(ServletContextEvent sce) {
		//System.out.println("contextInitialized.....");
		//1 获取spring工厂
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
		
		//2 从spring工厂中获取privilegeService
		IPrivilegeService ps =  (IPrivilegeService) ctx.getBean("privilegeServiceImpl");
		
		//3 加载权限数据 
		List<Privilege> privilegeTopList = ps.findTopList();
		
		//4 将权限数据放入application作用域中
		sce.getServletContext().setAttribute("privilegeTopList", privilegeTopList);
		
		//查询所有需要进行校验的请求url
		List<String> urlList = ps.findAllUrls();
		sce.getServletContext().setAttribute("urlList", urlList);
		System.out.println("权限数据已经放入application作用域中....");
	}

	/**
	 * 销毁方法
	 */
	public void contextDestroyed(ServletContextEvent arg0) {}
}
