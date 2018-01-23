package by.runets.buber.application.service.user;

import by.runets.buber.application.service.join.JoinService;
import by.runets.buber.domain.entity.Ban;
import by.runets.buber.domain.entity.Car;
import by.runets.buber.domain.entity.Order;
import by.runets.buber.domain.entity.User;
import by.runets.buber.infrastructure.constant.DAOType;
import by.runets.buber.infrastructure.constant.DatabaseQueryConstant;
import by.runets.buber.infrastructure.constant.UserRoleType;
import by.runets.buber.infrastructure.dao.AbstractDAO;
import by.runets.buber.infrastructure.dao.OrderDAO;
import by.runets.buber.infrastructure.dao.UserDAO;
import by.runets.buber.infrastructure.dao.factory.DAOFactory;
import by.runets.buber.infrastructure.exception.DAOException;
import by.runets.buber.infrastructure.exception.ServiceException;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ReadUserService {
    private final static int NULL = 0;

    public List<User> find(String role) throws ServiceException {
        List<User> userList = null;
        List<User> userListByRole = null;
        JoinService joinService = new JoinService();
        try {
            UserDAO userDAO = (UserDAO) DAOFactory.getInstance().createDAO(DAOType.USER_DAO_TYPE);
            userList = userDAO.findAll();
            userListByRole = joinService.collectUserByRole(userList, role);
            for (User user : userList){
                joinService.join(user);
            }
        } catch (DAOException e) {
            throw new ServiceException("Find user throw exception " , e);
        }

        return userListByRole;
    }

    public User find(int id) throws ServiceException {
        User user = null;
        try {
            UserDAO userDAO = (UserDAO) DAOFactory.getInstance().createDAO(DAOType.USER_DAO_TYPE);
            user = userDAO.find(id);
        } catch (DAOException e) {
            throw new ServiceException("Find user service " , e);
        }
        return user;
    }
}
