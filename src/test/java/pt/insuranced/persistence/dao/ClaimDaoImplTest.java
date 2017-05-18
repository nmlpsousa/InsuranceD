package pt.insuranced.persistence.dao;

import org.junit.Test;
import pt.insuranced.models.Claim;
import pt.insuranced.models.ReserveLine;
import pt.insuranced.persistence.dao.sdk.interfaces.ClaimDao;
import pt.insuranced.sdk.enums.ClaimStatusEnum;

import java.time.LocalDate;

public class ClaimDaoImplTest {

    @Test
    public void testInsert() throws Exception {

        Claim claim = new Claim();

        ReserveLine reserveLine = new ReserveLine();
        reserveLine.setDescription("Side Money");
        reserveLine.setLimit(3000.0);
        reserveLine.setUsedFunds(0.0);

        claim.setCoverableId(1);
        claim.setDescription("Car crash");
        claim.setIncidentDate(LocalDate.now());
        claim.setReserveLine(reserveLine);
        claim.setStatus(ClaimStatusEnum.DRAFT);

        ClaimDao claimDao = new ClaimDaoImpl();
        claimDao.insert(claim);
    }
}
