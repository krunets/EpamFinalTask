package by.runets.buber.presentation.command.impl;

import by.runets.buber.infrastructure.constant.JspPagePath;
import by.runets.buber.infrastructure.constant.RequestParameter;
import by.runets.buber.infrastructure.constant.UserRoleType;
import by.runets.buber.presentation.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LogoutCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        String page = null;

        HttpSession httpSession = req.getSession(false);

        if (httpSession != null){
            httpSession.removeAttribute(UserRoleType.USER);
        }

        page = JspPagePath.INDEX_PAGE;

        return page;
    }
}