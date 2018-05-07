package cn.itcast.oa.base;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import javax.annotation.Resource;
import cn.itcast.oa.service.IDepartmentService;
import cn.itcast.oa.service.IPrivilegeService;
import cn.itcast.oa.service.IRoleService;
import cn.itcast.oa.service.IUserService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * 抽取通用Action
 * @author zhaoqx
 *
 */
public class BaseAction<T> extends ActionSupport implements ModelDriven<T>{
	@Resource
	protected IRoleService roleService;
	@Resource
	protected IDepartmentService departmentService;
	@Resource
	protected IUserService userService;
	@Resource
	protected IPrivilegeService privilegeService;
	
	protected T model; // 模型驱动 
	public T getModel() {
		return model;
	}
	
	/**
	 * 在构造方法中动态获得模型对象类型
	 */
	public BaseAction() {
		ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
		Type[] types = type.getActualTypeArguments();
		Class<T> type2 = (Class<T>) types[0];
		try {
			model = type2.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	protected ValueStack getValueStack(){
		return ActionContext.getContext().getValueStack();
	}

}
