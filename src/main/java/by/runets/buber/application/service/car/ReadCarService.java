package by.runets.buber.application.service.car;

import by.runets.buber.domain.entity.Car;
import by.runets.buber.infrastructure.constant.DAOType;
import by.runets.buber.infrastructure.dao.AbstractDAO;
import by.runets.buber.infrastructure.dao.factory.DAOFactory;
import by.runets.buber.infrastructure.exception.DAOException;
import by.runets.buber.infrastructure.exception.ServiceException;

public class ReadCarService {
    public Car findCarByOwner(Integer id) throws ServiceException {
        Car car = null;
        try {
            AbstractDAO abstractDAO = DAOFactory.getInstance().createDAO(DAOType.CAR_DAO_TYPE);
            car = (Car) abstractDAO.find(id);
        } catch (DAOException e) {
            throw new ServiceException("Find car exception " + e);
        }
        return car;
    }
}
