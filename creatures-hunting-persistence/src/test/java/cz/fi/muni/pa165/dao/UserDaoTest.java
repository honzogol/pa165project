package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.PersistenceApplicationContext;
import cz.fi.muni.pa165.entity.User;
import cz.fi.muni.pa165.enums.UserRole;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import javax.inject.Inject;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * @author Jan Gol <gol.honza@gmail.com>
 */
@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional

public class UserDaoTest extends AbstractTestNGSpringContextTests {

    @Inject
    private UserDao userDao;

    private User createTestUser() {
        User user = new User();
        user.setFirstName("Regular");
        user.setLastName("User");
        user.setEmail("somerandomregularuser@worldofjava.com");
        user.setPasswordHash("65df2x8e5s");
        user.setRole(UserRole.REGULAR);
        userDao.create(user);
        return user;
    }

    private User createTestSecondUser() {
        User user = new User();
        user.setFirstName("Second");
        user.setLastName("Regularuser");
        user.setEmail("secondrandomuserwithemail@worldofjava.com");
        user.setPasswordHash("xdfs6e52x5d");
        user.setRole(UserRole.REGULAR);
        userDao.create(user);
        return user;
    }

    @Test
    public void testCreateWithNull() {
        assertThatExceptionOfType(DataAccessException.class)
                .isThrownBy(() -> userDao.create(null));
    }

    @Test
    public void testCreate() {
        User user = createTestUser();
        User foundUser = userDao.findById(user.getId());
        assertThat(foundUser).isEqualToComparingFieldByField(user);
    }

    @Test
    public void testDeleteWithNull() {
        assertThatExceptionOfType(DataAccessException.class)
                .isThrownBy(() -> userDao.delete(null));
    }

    @Test
    public void testDelete() {
        User user = createTestUser();

        userDao.delete(user);

        User foundUser = userDao.findById(user.getId());
        assertThat(foundUser).isEqualTo(null);
    }

    @Test
    public void testFindByIdWithNull() {
        assertThatExceptionOfType(DataAccessException.class)
                .isThrownBy(() -> userDao.delete(null));
    }

    @Test
    public void testFindById() {
        User user = createTestUser();

        User foundUser = userDao.findById(user.getId());

        assertThat(foundUser).isEqualToComparingFieldByField(user);
    }

    @Test
    public void testFindByIdNothingFound() {
        User foundUser = userDao.findById(1L);
        assertThat(foundUser).isEqualTo(null);
    }

    @Test
    public void testFindByIdNegativeId() {
        assertThatExceptionOfType(DataAccessException.class)
                .isThrownBy(() -> userDao.findById(-1L));
    }

    @Test
    public void testFindByEmailWithNull() {
        assertThatExceptionOfType(DataAccessException.class)
                .isThrownBy(() -> userDao.findByEmail(null));
    }

    @Test
    public void testFindByEmailWithEmptyValue() {
        assertThatExceptionOfType(DataAccessException.class)
                .isThrownBy(() -> userDao.findByEmail(""));
    }

    @Test
    public void testFindByEmail() {
        User user = createTestUser();

        User foundUser = userDao.findByEmail(user.getEmail());

        assertThat(foundUser).isEqualToComparingFieldByField(user);
    }

    @Test
    public void testFindByEmailNothingFound() {
        User foundUser = userDao.findByEmail("randomfakeuserwithoutemail@worldwithoutjava.com");
        assertThat(foundUser).isEqualTo(null);
    }

    @Test
    public void testGetAll() {
        User user = createTestUser();
        User secondUser = createTestSecondUser();

        List<User> foundUsers = userDao.getAll();

        assertThat(foundUsers).containsExactly(user, secondUser);
    }

    @Test
    public void testGetAllNothingFound() {
        List<User> foundUsers = userDao.getAll();
        assertThat(foundUsers).isEmpty();
    }
}
