package pt.insuranced.persistence.dao;

import org.junit.Test;
import pt.insuranced.models.Coverage;
import pt.insuranced.persistence.dao.sdk.interfaces.CoverageDao;

import java.util.Optional;

public class CoverageDaoImplTest {

    @Test
    public void testInsert() throws Exception {

        Coverage coverage = new Coverage();
        coverage.setCoverableId(1);
        coverage.setLimit(1000.0);
        coverage.setPremium(100.0);
        coverage.setDescription("Broken Windows");

        CoverageDao coverageDao = new CoverageDaoImpl();
        coverageDao.insert(coverage);
    }

    @Test
    public void testGet() throws Exception {

        CoverageDao coverageDao = new CoverageDaoImpl();
        coverageDao.get(1);
    }

    @Test
    public void testUpdate() throws Exception {

        CoverageDao coverageDao = new CoverageDaoImpl();

        Optional<Coverage> cov = coverageDao.get(1);

        if (cov.isPresent()) {
            Coverage coverage = cov.get();
            coverage.setPremium(200.0);

            coverageDao.update(coverage);
        }
    }
}
