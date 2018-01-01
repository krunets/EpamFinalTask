package by.runets.buber.infrastructure.dao.impl;

import by.runets.buber.domain.entity.Car;
import by.runets.buber.domain.entity.Point;
import by.runets.buber.domain.entity.User;
import by.runets.buber.infrastructure.connection.ConnectionPool;
import by.runets.buber.infrastructure.connection.ProxyConnection;
import by.runets.buber.infrastructure.dao.AbstractDAO;
import by.runets.buber.infrastructure.constant.DatabaseQueryConstant;
import by.runets.buber.infrastructure.dao.parser.LocationParser;
import by.runets.buber.infrastructure.exception.ConnectionException;
import by.runets.buber.infrastructure.exception.DAOException;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarDAOImpl implements AbstractDAO<Integer, Car> {
    private static CarDAOImpl instance;

    private CarDAOImpl(){}

    public static CarDAOImpl getInstance(){
        if (instance == null){
            instance = new CarDAOImpl();
        }
        return instance;
    }
    @Override
    public List<Car> findAll() throws DAOException {
        ProxyConnection proxyConnection = ConnectionPool.getInstance().getConnection();
        PreparedStatement preparedStatement = null;
        List<Car> cars = new ArrayList<>();
        try {
            preparedStatement = proxyConnection.prepareStatement(DatabaseQueryConstant.FIND_ALL_CARS);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                cars.add(getCarFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException("Selection cars exception " + e);
        } finally {
            close(preparedStatement);
            try {
                ConnectionPool.getInstance().releaseConnection(proxyConnection);
            } catch (ConnectionException e) {
                LOGGER.error(e);
            }
        }
        return cars;
    }

    @Override
    public Car find(Integer id) throws DAOException {
        ProxyConnection proxyConnection = ConnectionPool.getInstance().getConnection();
        PreparedStatement preparedStatement = null;
        Car car = null;
        try {
            preparedStatement = proxyConnection.prepareStatement(DatabaseQueryConstant.FIND_CAR_BY_OWNER);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                car = getCarFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException("Find car exception: " + e);
        } finally {
            close(preparedStatement);
            try {
                ConnectionPool.getInstance().releaseConnection(proxyConnection);
            } catch (ConnectionException e) {
                LOGGER.error(e);
            }
        }
        return car;
    }

    @Override
    public void delete(Car car) throws DAOException {
        ProxyConnection proxyConnection = ConnectionPool.getInstance().getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = proxyConnection.prepareStatement(DatabaseQueryConstant.DELETE_CAR_BY_ID);
            preparedStatement.setInt(1, car.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Delete car exception " + e);
        } finally {
            close(preparedStatement);
            try {
                ConnectionPool.getInstance().releaseConnection(proxyConnection);
            } catch (ConnectionException e) {
                LOGGER.error(e);
            }
        }
    }

    @Override
    public boolean create(Car car) throws DAOException {
        ProxyConnection proxyConnection = ConnectionPool.getInstance().getConnection();
        PreparedStatement preparedStatement = null;
        boolean state = false;
        try {
            preparedStatement = proxyConnection.prepareStatement(DatabaseQueryConstant.INSERT_INTO_CAR);
            preparedStatement.setString(1, String.valueOf(car.getMark()));
            preparedStatement.setString(2, String.valueOf(car.getModel()));
            preparedStatement.setDate(3, new Date(car.getReleaseDate().get().getTime()));
            preparedStatement.setString(4, String.valueOf(car.getLicensePlate()));
            preparedStatement.setInt(5, car.getCarOwner().get().getId());
            preparedStatement.setString(6, car.getCurrentLocation().get().toString());
            preparedStatement.executeUpdate();
            state = true;
        } catch (SQLException e) {
            throw new DAOException("Insertion exception" + e);
        } finally {
            close(preparedStatement);
            try {
                ConnectionPool.getInstance().releaseConnection(proxyConnection);
            } catch (ConnectionException e) {
                LOGGER.error(e);
            }
        }
        return state;
    }

    @Override
    public void update(Car car) throws DAOException {
        ProxyConnection proxyConnection = ConnectionPool.getInstance().getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = proxyConnection.prepareStatement(DatabaseQueryConstant.UPDATE_CAR_BY_ID);
            preparedStatement.setString(1, String.valueOf(car.getMark()));
            preparedStatement.setString(2, String.valueOf(car.getModel()));
            preparedStatement.setDate(3, new Date(car.getReleaseDate().get().getTime()));
            preparedStatement.setString(4, String.valueOf(car.getLicensePlate()));
            preparedStatement.setInt(5, car.getCarOwner().get().getId());
            preparedStatement.setString(6, car.getCurrentLocation().get().toString());
            preparedStatement.setInt(7, car.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Insertion exception" + e);
        } finally {
            close(preparedStatement);
            try {
                ConnectionPool.getInstance().releaseConnection(proxyConnection);
            } catch (ConnectionException e) {
                LOGGER.error(e);
            }
        }
    }

    private Car getCarFromResultSet(ResultSet resultSet) throws SQLException {
        Car car = new Car();
        List<Double> coordinates = new ArrayList<>();
        car.setId(resultSet.getInt("id"));
        car.setMark(Optional.ofNullable(resultSet.getString("mark")));
        car.setModel(Optional.ofNullable(resultSet.getString("model")));
        car.setReleaseDate(Optional.ofNullable(resultSet.getDate("release_date")));
        car.setLicensePlate(Optional.ofNullable(resultSet.getString("license_plate")));
        car.setCarOwner(Optional.of(new User(resultSet.getInt("car_owner"))));
        coordinates = LocationParser.parseLocation(resultSet.getString("current_location"));
        car.setCurrentLocation(Optional.of(new Point(coordinates.get(0), coordinates.get(1))));
        return car;
    }
}
