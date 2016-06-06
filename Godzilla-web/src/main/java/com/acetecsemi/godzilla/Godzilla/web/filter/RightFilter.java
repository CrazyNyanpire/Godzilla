package com.acetecsemi.godzilla.Godzilla.web.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.openkoala.application.impl.RoleApplicationImpl;
import org.openkoala.application.impl.UserApplicationImpl;
import org.openkoala.auth.application.RoleApplication;
import org.openkoala.auth.application.UserApplication;
import org.openkoala.auth.application.vo.ResourceVO;
import org.openkoala.auth.application.vo.RoleVO;
import org.openkoala.auth.application.vo.UserVO;
import org.openkoala.framework.i18n.I18NManager;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.acetecsemi.godzilla.Godzilla.application.dto.WaferCustomerLotDTO;

/**
 * 权限过滤
 * 
 * @author harlow
 * @date 2014-11-11 下午5:37:38
 */
public class RightFilter extends OncePerRequestFilter {

	public static final String MSG_SUCCESS = "success";

	public static final String MSG_FAIL = "fail";

	public static final String MSG_NO_RIGHT = "noRight";

	@Inject
	private UserApplication userApplication;

	@Inject
	private RoleApplication roleApplication = new RoleApplicationImpl();

	// 不过滤的uri
	public static String[] NOT_FILTER = new String[] { "login.koala",
			"index.koala", "/auth/", "/log/", "/employee/", "/job/",
			"/organization/", "/post/" };

	private Map<String, List<RoleVO>> roleListMap = new HashMap<String, List<RoleVO>>();

	private Map<Long, List<ResourceVO>> resListMap = new HashMap<Long, List<ResourceVO>>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.filter.OncePerRequestFilter#doFilterInternal(
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// 请求的uri
		String uri = request.getRequestURI();

		// uri中包含background时才进行过滤
		if (uri.indexOf(".koala") != -1 && this.checkNotFilter(uri)) {
			// 执行过滤
			// 从session中获取登录者实体
			ObjectMapper mapper = new ObjectMapper();
			String userName = request.getParameter("userName");
			String password = request.getParameter("password");
			if (userName == null) {
				String data = request.getParameter("data");
				if (data != null) {
					Map<String, Object> maps = mapper
							.readValue(data, Map.class);
					userName = maps.get("userName").toString();
					password = maps.get("password").toString();
				}
			}
			if (userName != null && password != null) {
				String identifier = request.getRequestURL().toString();
				String hasRight = this.resCheckMsg(userName, password,
						identifier);
				if (!MSG_SUCCESS.equals(hasRight)) {
					// 设置request和response的字符集，防止乱码
					request.setCharacterEncoding("UTF-8");
					response.setCharacterEncoding("UTF-8");
					response.setContentType("application/json;charset=UTF-8");
					PrintWriter out = response.getWriter();
					StringBuilder builder = new StringBuilder();
					if(hasRight.equals(MSG_NO_RIGHT)){
						builder.append("{\"result\":\""
								+ MSG_NO_RIGHT
								+ "\",\"actionError\":\""
								+ I18NManager.getMessage("rightFilter.msg.noRight",
										Locale.getDefault().toString()) + "\"}");
					}else{
						builder.append("{\"result\":\""
								+ hasRight
								+ "\"}");
					}
					
					out.print(builder.toString());
					return;
				}
			}
		}
		// 如果uri中不包含background，则继续
		filterChain.doFilter(request, response);
	}

	private boolean checkNotFilter(String uri) {
		for (String s : NOT_FILTER) {
			if (uri.indexOf(s) != -1 && uri.length() > 1) {
				// 如果uri中包含不过滤的uri，则不进行过滤
				return false;
			}
		}
		return true;
	}

	private boolean passwordCheck(String useraccount, String password) {
		Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
		password = md5PasswordEncoder.encodePassword(password, "");
		return this.isUserExisted(useraccount)
				&& this.userCheck(useraccount, password);
	}

	private boolean rightCheck(String useraccount, String identifier) {
		List<RoleVO> roleList;
		if (roleApplication == null) {
			roleApplication = new RoleApplicationImpl();
		}
		if (this.roleListMap.get(useraccount) == null) {
			roleList = roleApplication.findRoleByUserAccount(useraccount);
			this.roleListMap.put(useraccount, roleList);
		} else {
			roleList = this.roleListMap.get(useraccount);
		}
		for (RoleVO roleVO : roleList) {
			List<ResourceVO> resList;
			if (this.resListMap.get(roleVO.getId()) == null) {
				resList = roleApplication.findResourceByRole(roleVO);
			} else {
				resList = this.resListMap.get(roleVO.getId());
			}
			for (ResourceVO resourceVO : resList) {
				// WaferProcess/getFutureHoldReason/\\d*.koala
				Pattern pattern = Pattern.compile(resourceVO.getIdentifier());
				Matcher matcher = pattern.matcher(identifier);
				if (matcher.find()) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean isUserExisted(String useraccount) {
		if (userApplication == null) {
			userApplication = new UserApplicationImpl();
		}
		UserVO result = userApplication.findByUserAccount(useraccount);
		return result == null ? false : true;
	}

	private boolean userCheck(String useraccount, String password) {
		if (userApplication == null) {
			userApplication = new UserApplicationImpl();
		}
		UserVO result = userApplication.findByUserAccount(useraccount);
		if (result == null) {
			return false;
		}
		if (result.getUserPassword().equals(password)) {
			return true;
		}
		return false;
	}

	public String resCheckMsg(String useraccount, String password,
			String identifier) {
		if (!this.passwordCheck(useraccount, password)) {
			return MSG_FAIL;
		}
		if ("admin".equals(useraccount)) {
			return MSG_SUCCESS;
		}
		if (!this.rightCheck(useraccount, identifier)) {
			return MSG_NO_RIGHT;
		}
		return MSG_SUCCESS;
	}

}