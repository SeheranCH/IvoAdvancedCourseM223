package ch.course223.advanced.domainmodels.user.unit;

import ch.course223.advanced.domainmodels.authority.Authority;
import ch.course223.advanced.domainmodels.role.Role;
import ch.course223.advanced.domainmodels.user.User;
import ch.course223.advanced.domainmodels.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:application-test.properties")
public class UserRepositoryTest {

    /*
    Exercise 3
    There is no real instance of PostgreSQL being involved here. Instead we are making usage of H2.
    Where are the needed credentials to connect to the datalayer fetched from? Does H2 provide a UI to check inserted tuples?
     */
    /*
    Answer: you can have access on 'localhost:8080/h2-console' (in my case). The layout is really primitive and old-fashioned.
     */

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private UserRepository userRepository;

    private User userToBeTestedAgainst1;
    private User userToBeTestedAgainst2;
    private User newUserToBeSaved;
    private List<User> listOfUsersToBeTestedAgainst;

    String userIdFromDBUser1;
    String userIdFromDBUser2;
    List<String> listOfIdsFromDB;

    @Before
    public void setUp(){
        Set<Authority> authoritiesToBeTestedAgainst = Stream.of(new Authority().setName("USER_SEE"), new Authority().setName("USER_CREATE"), new Authority().setName("USER_MODIFY"), new Authority().setName("USER_DELETE")).collect(Collectors.toSet());
        Set<Role> rolesToBeTestedAgainst = Stream.of(new Role().setName("BASIC_USER").setAuthorities(authoritiesToBeTestedAgainst)).collect(Collectors.toSet());
        userToBeTestedAgainst1 = new User().setFirstName("John").setLastName("Doe").setEmail("john.doe@noseryoung.ch").setEnabled(true).setPassword(new BCryptPasswordEncoder().encode(UUID.randomUUID().toString())).setRoles(rolesToBeTestedAgainst);
        userToBeTestedAgainst2 = new User().setFirstName("Jane").setLastName("Doe").setEmail("jane.doe@noseryoung.ch").setEnabled(true).setPassword(new BCryptPasswordEncoder().encode(UUID.randomUUID().toString())).setRoles(rolesToBeTestedAgainst);
        newUserToBeSaved = new User().setFirstName("Jack").setLastName("Doe").setEmail("jack.doe@noseryoung.ch").setEnabled(true).setPassword(new BCryptPasswordEncoder().encode(UUID.randomUUID().toString())).setRoles(rolesToBeTestedAgainst);
        userIdFromDBUser1 = testEntityManager.persistAndGetId(userToBeTestedAgainst1, String.class);
        userIdFromDBUser2 = testEntityManager.persistAndGetId(userToBeTestedAgainst2, String.class);
        listOfUsersToBeTestedAgainst = Arrays.asList(userToBeTestedAgainst1, userToBeTestedAgainst2);
        listOfIdsFromDB = Arrays.asList(userIdFromDBUser1, userIdFromDBUser2);
    }

    @Test
    public void findById_requestUserById_returnsUser() {
        Optional<User> userFromDB = userRepository.findById(userIdFromDBUser1);

        Assertions.assertThat(userFromDB).isEqualTo(Optional.ofNullable(userToBeTestedAgainst1));
        Assertions.assertThat(userFromDB.get().getId()).isEqualTo(userIdFromDBUser1);
        Assertions.assertThat(userFromDB.get().getFirstName()).isEqualTo(userToBeTestedAgainst1.getFirstName());
        Assertions.assertThat(userFromDB.get().getLastName()).isEqualTo(userToBeTestedAgainst1.getLastName());
        Assertions.assertThat(userFromDB.get().getEmail()).isEqualTo(userToBeTestedAgainst1.getEmail());
        Assertions.assertThat(userFromDB.get().getEnabled()).isEqualTo(userToBeTestedAgainst1.getEnabled());
        Assertions.assertThat(userFromDB.get().getPassword()).isEqualTo(userToBeTestedAgainst1.getPassword());
        Assertions.assertThat(userFromDB.get().getRoles()).isEqualTo(userToBeTestedAgainst1.getRoles());
    }

    @Test
    public void findAll_requestAllUsers_returnsAllUsers() {
        List<User> usersFromDB = userRepository.findAll();

        Assertions.assertThat(usersFromDB.stream().map(User::getId).toArray()).containsExactlyInAnyOrder(listOfIdsFromDB.toArray());
        Assertions.assertThat(usersFromDB.stream().map(User::getFirstName).toArray()).isEqualTo(listOfUsersToBeTestedAgainst.stream().map(User::getFirstName).toArray());
        Assertions.assertThat(usersFromDB.stream().map(User::getLastName).toArray()).isEqualTo(listOfUsersToBeTestedAgainst.stream().map(User::getLastName).toArray());
        Assertions.assertThat(usersFromDB.stream().map(User::getEmail).toArray()).isEqualTo(listOfUsersToBeTestedAgainst.stream().map(User::getEmail).toArray());
        Assertions.assertThat(usersFromDB.stream().map(User::getEnabled).toArray()).isEqualTo(listOfUsersToBeTestedAgainst.stream().map(User::getEnabled).toArray());
        Assertions.assertThat(usersFromDB.stream().map(User::getPassword).toArray()).isEqualTo(listOfUsersToBeTestedAgainst.stream().map(User::getPassword).toArray());
        Assertions.assertThat(usersFromDB.stream().map(User::getRoles).toArray()).containsExactlyInAnyOrder(listOfUsersToBeTestedAgainst.stream().map(User::getRoles).toArray());
        Assertions.assertThat(usersFromDB.stream().map(User::getRoles).flatMap(Collection::stream).map(Role::getAuthorities).flatMap(Collection::stream).map(Authority::getName).toArray()).containsExactlyInAnyOrder(listOfUsersToBeTestedAgainst.stream().map(User::getRoles).flatMap(Collection::stream).map(Role::getAuthorities).flatMap(Collection::stream).map(Authority::getName).toArray());
    }

    @Test
    public void create_deliverUserToCreate_returnCreatedUser(){
        User savedUser = userRepository.save(newUserToBeSaved);

        Assertions.assertThat(testEntityManager.find(User.class, savedUser.getId())).isEqualTo(newUserToBeSaved);
        Assertions.assertThat(savedUser.getFirstName()).isEqualTo(newUserToBeSaved.getFirstName());
        Assertions.assertThat(savedUser.getLastName()).isEqualTo(newUserToBeSaved.getLastName());
        Assertions.assertThat(savedUser.getEmail()).isEqualTo(newUserToBeSaved.getEmail());
        Assertions.assertThat(savedUser.getEnabled()).isEqualTo(newUserToBeSaved.getEnabled());
        Assertions.assertThat(savedUser.getPassword()).isEqualTo(newUserToBeSaved.getPassword());
        Assertions.assertThat(savedUser.getRoles().stream().map(Role::getName).toArray()).containsExactlyInAnyOrder(newUserToBeSaved.getRoles().stream().map(Role::getName).toArray());
        Assertions.assertThat(savedUser.getRoles().stream().map(Role::getAuthorities).flatMap(Collection::stream).map(Authority::getName).toArray()).containsExactlyInAnyOrder(newUserToBeSaved.getRoles().stream().map(Role::getAuthorities).flatMap(Collection::stream).map(Authority::getName).toArray());
    }

    @Test
    public void updateUserById_requestUserToBeUpdated_returnUpdatedUser(){
        User updatedUser = userRepository.save(userToBeTestedAgainst1);

        Assertions.assertThat(testEntityManager.find(User.class, updatedUser.getId())).isEqualTo(updatedUser);
        Assertions.assertThat(updatedUser.getFirstName()).isEqualTo(userToBeTestedAgainst1.getFirstName());
        Assertions.assertThat(updatedUser.getLastName()).isEqualTo(userToBeTestedAgainst1.getLastName());
        Assertions.assertThat(updatedUser.getEmail()).isEqualTo(userToBeTestedAgainst1.getEmail());
        Assertions.assertThat(updatedUser.getEnabled()).isEqualTo(userToBeTestedAgainst1.getEnabled());
        Assertions.assertThat(updatedUser.getPassword()).isEqualTo(userToBeTestedAgainst1.getPassword());
        Assertions.assertThat(updatedUser.getRoles().stream().map(Role::getName).toArray()).containsExactlyInAnyOrder(userToBeTestedAgainst1.getRoles().stream().map(Role::getName).toArray());
        Assertions.assertThat(updatedUser.getRoles().stream().map(Role::getAuthorities).flatMap(Collection::stream).map(Authority::getName).toArray()).containsExactlyInAnyOrder(userToBeTestedAgainst1.getRoles().stream().map(Role::getAuthorities).flatMap(Collection::stream).map(Authority::getName).toArray());
    }

    @Test
    public void deleteUserById_requestADeletionOfUserById_returnAppropriateState(){
        String userIdFromDB = testEntityManager.persistAndGetId(userToBeTestedAgainst1, String.class);

        userRepository.deleteById(userIdFromDB);

        Assertions.assertThat(userRepository.findById(userIdFromDB)).isEmpty();
    }

}
