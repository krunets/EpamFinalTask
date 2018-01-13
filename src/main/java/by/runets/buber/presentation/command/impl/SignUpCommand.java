package by.runets.buber.presentation.command.impl;

import by.runets.buber.application.service.user.RegisterUserService;
import by.runets.buber.application.validation.RequestValidator;
import by.runets.buber.domain.entity.Role;
import by.runets.buber.domain.entity.User;
import by.runets.buber.infrastructure.constant.*;
import by.runets.buber.infrastructure.exception.DAOException;
import by.runets.buber.infrastructure.exception.ServiceException;
import by.runets.buber.infrastructure.util.LocaleFileManager;
import by.runets.buber.infrastructure.util.PasswordEncrypt;
import by.runets.buber.presentation.command.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;

public class SignUpCommand implements Command {
    private final static Logger LOGGER = LogManager.getLogger(SignUpCommand.class);
    private RegisterUserService userService;

    public SignUpCommand(RegisterUserService createUserService) {
        userService = createUserService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        String page = null;

        String locale = req.getSession(false).getAttribute(RequestParameter.LOCALE) == null ? RequestParameter.DEFAULT_LOCALE : req.getSession().getAttribute(RequestParameter.LOCALE).toString();
        User user = null;
        try {
            user = init(req);
            page = user != null && userService.registerUser(user)
                    ? JspPagePath.LOGIN_PAGE
                    : setErrorAttribute(req, LabelParameter.ERROR_LABEL, LocaleFileManager.getLocale(locale).getProperty(PropertyKey.SIGN_UP_ERROR_LABEL_MESSAGE), user);
        } catch (ServiceException e) {
            LOGGER.error(e);
        } catch (AuthenticationException e) {
            LOGGER.error(e);
            page = setErrorAttribute(req, LabelParameter.ERROR_LABEL, LocaleFileManager.getLocale(locale).getProperty(PropertyKey.EMAIL_EXIST_LABEL_MESSAGE), user);
        }

        return page;
    }

    private User init(HttpServletRequest req){
        String email = req.getParameter(CommandParameter.PARAM_NAME_EMAIL);
        String password = req.getParameter(CommandParameter.PARAM_NAME_PASSWORD);
        String firstName = req.getParameter(CommandParameter.PARAM_NAME_FIRST_NAME);
        String secondName = req.getParameter(CommandParameter.PARAM_NAME_SECOND_NAME);
        String role = req.getParameter(CommandParameter.PARAM_NAME_ROLE);

        return RequestValidator.getInstance().isValidateRegisterData(email, password, firstName, secondName, role) ? new User(email, password, firstName, secondName, new Role(role)) : null;
    }

    private String setErrorAttribute(HttpServletRequest req, String label, String message, User user){
        req.setAttribute(label, message);
        req.setAttribute(UserRoleType.USER, user);
        return JspPagePath.SIGN_UP_PAGE;
    }
}


