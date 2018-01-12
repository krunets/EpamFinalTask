package by.runets.buber.application.service.car;

import by.runets.buber.domain.entity.Car;
import by.runets.buber.infrastructure.constant.DAOType;
import by.runets.buber.infrastructure.dao.AbstractDAO;
import by.runets.buber.infrastructure.dao.factory.DAOFactory;
import by.runets.buber.infrastructure.exception.DAOException;
import by.runets.buber.infrastructure.exception.ServiceException;

public class EditCarService {
    public void edit(Car car) throws ServiceException {
        try {
            AbstractDAO abstractDAO = DAOFactory.getInstance().createDAO(DAOType.CAR_DAO_TYPE);
            abstractDAO.update(car);
        } catch (DAOException e) {
            throw new ServiceException("Update car exception " + e);
        }
    }
}