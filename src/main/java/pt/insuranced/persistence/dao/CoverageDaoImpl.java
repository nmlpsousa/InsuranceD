package pt.insuranced.persistence.dao;

import pt.insuranced.models.Coverage;
import pt.insuranced.persistence.dao.sdk.interfaces.CoverageDao;
import pt.insuranced.sdk.exceptions.InsuranceDException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoverageDaoImpl implements CoverageDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CoverageDaoImpl.class);
	
    @Override
    public Optional<Coverage> get(long id) throws InsuranceDException {
        return null;
    }

    @Override
    public Coverage insert(Coverage coverage) throws InsuranceDException {
        return null;
    }

    @Override
    public Coverage update(Coverage coverage) throws InsuranceDException {
        return null;
    }
    
    private static void rollbackConnection(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException e) {
            LOGGER.error("Error rolling back the connection.", e);
        }
    }

    private static void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            LOGGER.error("Error closing connection to the DB.", e);
        }
    }
    
    private static void restoreAutoCommitAndCloseConnection(Connection connection, Boolean previousAutoCommit) {
        try {
            connection.setAutoCommit(previousAutoCommit);
            connection.close();
        } catch (SQLException e) {
            LOGGER.error("Error closing connection to the DB.", e);
        }
    }
}
