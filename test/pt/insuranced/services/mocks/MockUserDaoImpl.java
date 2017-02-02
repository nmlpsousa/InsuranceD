package pt.insuranced.services.mocks;

import mockit.Mock;
import mockit.MockUp;
import pt.insuranced.models.AbstractUser;
import pt.insuranced.models.Client;
import pt.insuranced.models.Password;
import pt.insuranced.persistence.dao.UserDaoImpl;
import pt.insuranced.sdk.enums.UserStatusEnum;
import pt.insuranced.sdk.enums.UserTypeEnum;
import pt.insuranced.sdk.exceptions.InsuranceDException;

import java.util.Optional;

public class MockUserDaoImpl extends MockUp<UserDaoImpl> {
    @Mock
    public Optional<AbstractUser> get(int userId) throws InsuranceDException {
        AbstractUser user = Client.Builder.newBuilder()
                .setId(userId)
                .setUsername("nuno")
                .setPassword(new Password(0, "batata"))
                .setUserStatus(UserStatusEnum.ACTIVE)
                .setUserType(UserTypeEnum.CLIENT)
                .build();
        return Optional.of(user);
    }
}
