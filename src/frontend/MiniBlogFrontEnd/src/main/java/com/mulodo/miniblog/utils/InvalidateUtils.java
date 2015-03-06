package com.mulodo.miniblog.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class InvalidateUtils {
	public static void invalidateLogin(HttpServletRequest request){
		HttpSession session = request.getSession(false);
        session.invalidate();
	}
}
