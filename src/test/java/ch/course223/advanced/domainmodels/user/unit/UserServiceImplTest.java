package ch.course223.advanced.domainmodels.user.unit;

import ch.course223.advanced.domainmodels.authority.Authority;
import ch.course223.advanced.domainmodels.role.Role;
import ch.course223.advanced.domainmodels.user.User;
import ch.course223.advanced.domainmodels.user.UserRepository;
import ch.course223.advanced.domainmodels.user.UserServiceImpl;
import ch.course223.advanced.error.BadRequestException;
import org.assertj.core.api.Assertions;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application-test.properties")
public class UserServiceImplTest {

    /*
    Exercise 2
    The ExtendedService imposes a method T findById(String id) on UserServiceImpl. The method is outsourced to ExtendedServiceImpl.
    Write a UnitTest that ensures that User findById(String id) functions correctly. Mock the UserRepository and make sure its only being called once.
    Also implement a capturer that tests if the mock is invoked with the right parameters.
    P.S Try to solve the exercises with Lambdas :-)
     */
    /*
    Answer: you need just to remove an user while adding to a list, because the size of list will be checked
     */

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private UserRepository userRepository;

    private static User userToBeTestedAgainst;
    private static List<User> listOfUsersToBeTestedAgainst;

    @BeforeClass
    public static void setUp(){
        UUID uuidToBeTestedAgainst = UUID.randomUUID();
        Set<Authority> authoritiesToBeTestedAgainst = Stream.of(new Authority().setName("USER_SEE"), new Authority().setName("USER_CREATE"), new Authority().setName("USER_MODIFY"), new Authority().setName("USER_DELETE")).collect(Collectors.toSet());
        Set<Role> rolesToBeTestedAgainst = Stream.of(new Role().setName("BASIC_USER").setAuthorities(authoritiesToBeTestedAgainst)).collect(Collectors.toSet());
        userToBeTestedAgainst = new User(uuidToBeTestedAgainst.toString()).setFirstName("John").setLastName("Doe").setEmail("john.doe@noseryoung.ch").setEnabled(true).setPassword(new BCryptPasswordEncoder().encode(UUID.randomUUID().toString())).setRoles(rolesToBeTestedAgainst);
        listOfUsersToBeTestedAgainst = Arrays.asList(userToBeTestedAgainst, userToBeTestedAgainst);
    }

    @Test
    public void findById_requestUserById_returnsUser() {
        Mockito.when(userRepository.findById(userToBeTestedAgainst.getId())).thenAnswer(invocation -> {
            if ("".equals(invocation.getArgument(0))) throw new NoSuchElementException();
            return Optional.ofNullable(userToBeTestedAgainst);
        });

        User userReturnedByService = userService.findById(userToBeTestedAgainst.getId());

        Assertions.assertThat(userReturnedByService.getId()).isEqualTo(userToBeTestedAgainst.getId());
        Assertions.assertThat(userReturnedByService.getFirstName()).isEqualTo(userToBeTestedAgainst.getFirstName());
        Assertions.assertThat(userReturnedByService.getLastName()).isEqualTo(userToBeTestedAgainst.getLastName());
        Assertions.assertThat(userReturnedByService.getEmail()).isEqualTo(userToBeTestedAgainst.getEmail());
        Assertions.assertThat(userReturnedByService.getEnabled()).isEqualTo(userToBeTestedAgainst.getEnabled());
        Assertions.assertThat(userReturnedByService.getPassword()).isEqualTo(userToBeTestedAgainst.getPassword());
        Assertions.assertThat(userReturnedByService.getRoles()).isEqualTo(userToBeTestedAgainst.getRoles());
        Assertions.assertThat(userReturnedByService.getRoles().stream().map(Role::getAuthorities).toArray()).containsExactlyInAnyOrder(userToBeTestedAgainst.getRoles().stream().map(Role::getAuthorities).toArray());


        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(userRepository, times(1)).findById(stringArgumentCaptor.capture());
        Assertions.assertThat(stringArgumentCaptor.getValue().equals(userToBeTestedAgainst.getId()));
    }

    @Test
    public void findAll_requestAllUsers_returnsAllUsers() {
        Mockito.when(userRepository.findAll()).thenReturn(listOfUsersToBeTestedAgainst);

        List<User> listOfUserReturnedByService = userService.findAll();

        Assertions.assertThat(listOfUserReturnedByService.size()).isEqualTo(listOfUsersToBeTestedAgainst.size());
        Assertions.assertThat(listOfUserReturnedByService.stream().map(User::getId).toArray()).isEqualTo(listOfUsersToBeTestedAgainst.stream().map(User::getId).toArray());
        Assertions.assertThat(listOfUserReturnedByService.stream().map(User::getFirstName).toArray()).isEqualTo(listOfUsersToBeTestedAgainst.stream().map(User::getFirstName).toArray());
        Assertions.assertThat(listOfUserReturnedByService.stream().map(User::getLastName).toArray()).isEqualTo(listOfUsersToBeTestedAgainst.stream().map(User::getLastName).toArray());
        Assertions.assertThat(listOfUserReturnedByService.stream().map(User::getEmail).toArray()).isEqualTo(listOfUsersToBeTestedAgainst.stream().map(User::getEmail).toArray());
        Assertions.assertThat(listOfUserReturnedByService.stream().map(User::getEnabled).toArray()).isEqualTo(listOfUsersToBeTestedAgainst.stream().map(User::getEnabled).toArray());
        Assertions.assertThat(listOfUserReturnedByService.stream().map(User::getPassword).toArray()).isEqualTo(listOfUsersToBeTestedAgainst.stream().map(User::getPassword).toArray());
        Assertions.assertThat(listOfUserReturnedByService.stream().map(User::getRoles).toArray()).isEqualTo(listOfUsersToBeTestedAgainst.stream().map(User::getRoles).toArray());
        Assertions.assertThat(listOfUserReturnedByService.stream().map(User::getRoles).flatMap(Collection::stream).map(Role::getAuthorities).toArray()).containsExactlyInAnyOrder(listOfUsersToBeTestedAgainst.stream().map(User::getRoles).flatMap(Collection::stream).map(Role::getAuthorities).toArray());

        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void create_deliverUserToCreate_returnCreatedUser(){
        UUID uuidToBeTestedAgainst = UUID.randomUUID();
        Mockito.when(userRepository.save(userToBeTestedAgainst)).thenAnswer(invocation -> {
            if (Objects.isNull(invocation.getArgument(0))) throw new BadRequestException();
            User userToBeReturnedToService = invocation.getArgument(0);
            return userToBeReturnedToService.setId(uuidToBeTestedAgainst.toString());
        });

        User userReturnedByService = userService.save(userToBeTestedAgainst);

        Assertions.assertThat(userReturnedByService.getId()).isEqualTo(uuidToBeTestedAgainst.toString());
        Assertions.assertThat(userReturnedByService.getFirstName()).isEqualTo(userToBeTestedAgainst.getFirstName());
        Assertions.assertThat(userReturnedByService.getLastName()).isEqualTo(userToBeTestedAgainst.getLastName());
        Assertions.assertThat(userReturnedByService.getEmail()).isEqualTo(userToBeTestedAgainst.getEmail());
        Assertions.assertThat(userReturnedByService.getEnabled()).isEqualTo(userToBeTestedAgainst.getEnabled());
        Assertions.assertThat(userReturnedByService.getPassword()).isEqualTo(userToBeTestedAgainst.getPassword());
        Assertions.assertThat(userReturnedByService.getRoles()).isEqualTo(userToBeTestedAgainst.getRoles());
        Assertions.assertThat(userReturnedByService.getRoles().stream().map(Role::getAuthorities).toArray()).containsExactlyInAnyOrder(userToBeTestedAgainst.getRoles().stream().map(Role::getAuthorities).toArray());

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).save(userArgumentCaptor.capture());
        Assertions.assertThat(userArgumentCaptor.getValue().equals(userToBeTestedAgainst));
    }

    @Test
    public void updateUserById_requestUserToBeUpdated_returnUpdatedUser(){
        Mockito.when(userRepository.existsById(userToBeTestedAgainst.getId())).thenAnswer(invocation -> {
            if ("".equals(invocation.getArgument(0))) throw new BadRequestException();
            return true;
        });
        Mockito.when(userRepository.save(userToBeTestedAgainst)).thenAnswer(invocation -> {
            //check if user has set Id -> Throw BadRequestException
            return userToBeTestedAgainst;
        });

        User userReturnedByService = userService.updateById(userToBeTestedAgainst.getId(), userToBeTestedAgainst);

        Assertions.assertThat(userReturnedByService.getId()).isEqualTo(userToBeTestedAgainst.getId());
        Assertions.assertThat(userReturnedByService.getFirstName()).isEqualTo(userToBeTestedAgainst.getFirstName());
        Assertions.assertThat(userReturnedByService.getLastName()).isEqualTo(userToBeTestedAgainst.getLastName());
        Assertions.assertThat(userReturnedByService.getEmail()).isEqualTo(userToBeTestedAgainst.getEmail());
        Assertions.assertThat(userReturnedByService.getEnabled()).isEqualTo(userToBeTestedAgainst.getEnabled());
        Assertions.assertThat(userReturnedByService.getPassword()).isEqualTo(userToBeTestedAgainst.getPassword());
        Assertions.assertThat(userReturnedByService.getRoles()).isEqualTo(userToBeTestedAgainst.getRoles());
        Assertions.assertThat(userReturnedByService.getRoles().stream().map(Role::getAuthorities).toArray()).containsExactlyInAnyOrder(userToBeTestedAgainst.getRoles().stream().map(Role::getAuthorities).toArray());

        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(userRepository, times(1)).existsById(stringArgumentCaptor.capture());
        Assertions.assertThat(stringArgumentCaptor.getValue().equals(userToBeTestedAgainst.getId()));

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).save(userArgumentCaptor.capture());
        Assertions.assertThat(userArgumentCaptor.getValue().getId()).isEqualTo(userToBeTestedAgainst.getId());
        Assertions.assertThat(userArgumentCaptor.getValue().getFirstName()).isEqualTo(userToBeTestedAgainst.getFirstName());
        Assertions.assertThat(userArgumentCaptor.getValue().getLastName()).isEqualTo(userToBeTestedAgainst.getLastName());
        Assertions.assertThat(userArgumentCaptor.getValue().getEmail()).isEqualTo(userToBeTestedAgainst.getEmail());
        Assertions.assertThat(userArgumentCaptor.getValue().getEnabled()).isEqualTo(userToBeTestedAgainst.getEnabled());
        Assertions.assertThat(userArgumentCaptor.getValue().getPassword()).isEqualTo(userToBeTestedAgainst.getPassword());
        Assertions.assertThat(userArgumentCaptor.getValue().getRoles()).isEqualTo(userToBeTestedAgainst.getRoles());
        Assertions.assertThat(userArgumentCaptor.getValue().getRoles().stream().map(Role::getAuthorities).toArray()).containsExactlyInAnyOrder(userToBeTestedAgainst.getRoles().stream().map(Role::getAuthorities).toArray());
    }

    @Test
    public void deleteUserById_requestADeletionOfUserById_returnAppropriateState(){
        Mockito.when(userRepository.existsById(userToBeTestedAgainst.getId())).thenAnswer(invocation -> {
            if ("".equals(invocation.getArgument(0))) throw new BadRequestException();
            return true;
        });
        Mockito.doNothing().when(userRepository).deleteById(userToBeTestedAgainst.getId());

        Assertions.assertThat(userService.deleteById(userToBeTestedAgainst.getId())).isNull();

        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(userRepository, times(1)).deleteById(stringArgumentCaptor.capture());
        Assertions.assertThat(stringArgumentCaptor.getValue().equals(userToBeTestedAgainst.getId()));
    }


}
