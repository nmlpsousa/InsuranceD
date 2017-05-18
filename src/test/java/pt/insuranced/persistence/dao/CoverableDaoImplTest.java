package pt.insuranced.persistence.dao;

import org.junit.Test;
import pt.insuranced.models.Coverable;
import pt.insuranced.persistence.dao.sdk.interfaces.CoverableDao;

import java.util.Optional;

public class CoverableDaoImplTest {

    @Test
    public void testInsert() throws Exception {

        Coverable coverable = new Coverable();
        coverable.setDescription("Car");
        coverable.setPolicyId(1);

        CoverableDao coverableDao = new CoverableDaoImpl();
        coverableDao.insert(coverable);
    }

    @Test
    public void testGet() throws Exception {

        CoverableDao coverableDao = new CoverableDaoImpl();
        coverableDao.get(1);
    }

    @Test
    public void testUpdate() throws Exception {

        CoverableDao coverableDao = new CoverableDaoImpl();

        Optional<Coverable> cov = coverableDao.get(1);

        if (cov.isPresent()) {
            Coverable coverable = cov.get();
            coverable.setDescription("Caravan");

            coverableDao.update(coverable);
        }
    }
}
