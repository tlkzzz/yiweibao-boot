package com.src.common.utils;

import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.http.HttpRequest;

/**  
 * @author ylj  E-mail:  
 * @version 创建时间：2014年4月16日 上午10:46:04 
 * 去消息提示页面
 */
public class GoPage {

	private final static String INDEX = "返回首页";
	private final static String URL = "/index";
	
	/**
	 * 
	 * @author ylj
	 * 2014年4月16日
	 * @param model
	 * @param success 页面提示信息
	 * @param returnMess 页面按钮连接文字
	 * @param messUrl 按钮跳转路径
	 * @param returnDesc 页面按钮连接文字
	 * @param DescUrl 按钮跳转路径
	 * @return
	 */
	public static String operateSuccess(Model model,String success, String returnMess, String messUrl, String returnDesc, String DescUrl) {
		model.addAttribute("index", INDEX);
		model.addAttribute("url", URL);
		model.addAttribute("success", success);
		model.addAttribute("returnMess", returnMess);
		model.addAttribute("messUrl", messUrl);
		model.addAttribute("returnDesc", returnDesc);
		model.addAttribute("DescUrl", DescUrl);
        return "/messager";
    }
	
	/**
	 * 
	 * @author ylj
	 * 2014年4月16日
	 * @param model
	 * @param lose 页面提示信息
	 * @param returnMess 页面按钮连接文字
	 * @param messUrl 按钮跳转路径
	 * @param returnDesc 页面按钮连接文字
	 * @param DescUrl 按钮跳转路径
	 * @return
	 */
	public static String operateLose(Model model,String lose, String returnMess, String messUrl, String returnDesc, String DescUrl) {
		model.addAttribute("index", INDEX);
		model.addAttribute("url", URL);
		model.addAttribute("lose", lose);
		model.addAttribute("returnMess", returnMess);
		model.addAttribute("messUrl", messUrl);
		model.addAttribute("returnDesc", returnDesc);
		model.addAttribute("DescUrl", DescUrl);
        return "/messager";
    }
}
