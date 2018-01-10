package by.runets.buber.presentation.command.impl;

import by.runets.buber.application.service.user.RegisterUserService;
import by.runets.buber.application.validation.RequestValidator;
import by.runets.buber.domain.entity.Role;
import by.runets.buber.domain.entity.User;
import by.runets.buber.infrastructure.constant.*;
import by.runets.buber.infrastructure.exception.DAOException;
import by.runets.buber.infrastructure.util.LocaleFileManager;
import by.runets.buber.infrastructure.util.PasswordEncrypt;
import by.runets.buber.presentation.command.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
        User user = init(req);
        try {
            if (RequestValidator.getInstance().isValidateRegisterData(user.getEmail(), user.getPassword(), user.getFirstName(), user.getSecondName(), user.getRole().getRoleName())){
                userService.registerUser(user);
                page = JspPagePath.LOGIN_PAGE;
            }
        } catch (DAOException e) {
            LOGGER.error(e);
            String locale = req.getSession(false).getAttribute(RequestParameter.LOCALE) == null ? RequestParameter.DEFAULT_LOCALE : req.getSession().getAttribute(RequestParameter.LOCALE).toString();
            req.setAttribute(LabelParameter.ERROR_LABEL, LocaleFileManager.getLocale(locale).getProperty(PropertyKey.SIGN_UP_ERROR_LABEL_MESSAGE));
            page = JspPagePath.SIGN_UP_PAGE;
        }
        return page;
    }

    private User init(HttpServletRequest req){
        String email = req.getParameter(CommandParameter.PARAM_NAME_EMAIL);
        String password = req.getParameter(CommandParameter.PARAM_NAME_PASSWORD);
        String firstName = req.getParameter(CommandParameter.PARAM_NAME_FIRST_NAME);
        String secondName = req.getParameter(CommandParameter.PARAM_NAME_SECOND_NAME);
        String role = req.getParameter(CommandParameter.PARAM_NAME_ROLE);

        return new User(email, PasswordEncrypt.encryptPassword(password), firstName, secondName, new Role(role));
    }

}
