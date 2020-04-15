package ch.course223.advanced.domainmodels.user.unit;

import ch.course223.advanced.domainmodels.authority.Authority;
import ch.course223.advanced.domainmodels.authority.AuthorityDTO;
import ch.course223.advanced.domainmodels.role.Role;
import ch.course223.advanced.domainmodels.role.RoleDTO;
import ch.course223.advanced.domainmodels.user.User;
import ch.course223.advanced.domainmodels.user.UserDTO;
import ch.course223.advanced.domainmodels.user.UserService;
import ch.course223.advanced.error.BadRequestException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.ArrayUtils;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-test.properties")
public class UserControllerTest {

    /*
    Exercise 1
    The REST Endpoint UserController.updateById expects a UserDTO and an adequate Id. The annotation @Valid indicates that
    the sent UserDTO is to be validated before it is updated. Spring Boot offers multiple validators such as @Max, @Min or @NotNull.
    These validators can be placed above attributes in DTO classes. Annotate the UserDTO attribute email with @Email and write a negative
    UnitTest that ensures that all sent UserDTOs with invalid emails are rejected. Mock the UserService and make sure its only being called once.
    Also implement a capturer that tests if the mock is invoked with the right parameters.
    P.S Try to solve the exercises with Lambdas :-)
     */
    /*
    Answer: check method 'updateUserById_requestUserDTOWithWrongEmailToBeUpdated_returnBadRequest'.
     */

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    private static User userToBeTestedAgainst;
    private static List<User> listOfUsersToBeTestedAgainst;

    private static UserDTO userDTOToBeTestedAgainst;
    private static List<UserDTO> listOfUserDTOSToBeTestedAgainst;

    @BeforeClass
    public static void setUp(){
        UUID uuidToBeTestedAgainst = UUID.randomUUID();
        Set<Authority> authoritiesToBeTestedAgainst = Stream.of(new Authority().setName("USER_SEE"), new Authority().setName("USER_CREATE"), new Authority().setName("USER_MODIFY"), new Authority().setName("USER_DELETE")).collect(Collectors.toSet());
        Set<Role> rolesToBeTestedAgainst = Stream.of(new Role().setName("BASIC_USER").setAuthorities(authoritiesToBeTestedAgainst)).collect(Collectors.toSet());
        userToBeTestedAgainst = new User(uuidToBeTestedAgainst.toString()).setFirstName("John").setLastName("Doe").setEmail("john.doe@noseryoung.ch").setEnabled(true).setPassword(new BCryptPasswordEncoder().encode(uuidToBeTestedAgainst.randomUUID().toString())).setRoles(rolesToBeTestedAgainst);
        listOfUsersToBeTestedAgainst = Arrays.asList(userToBeTestedAgainst, userToBeTestedAgainst);

        Set<AuthorityDTO> authorityDTOSToBeTestedAgainst = Stream.of(new AuthorityDTO().setName("USER_SEE"), new AuthorityDTO().setName("USER_CREATE"), new AuthorityDTO().setName("USER_MODIFY"), new AuthorityDTO().setName("USER_DELETE")).collect(Collectors.toSet());
        Set<RoleDTO> roleDTOSToBeTestedAgainst = Stream.of(new RoleDTO().setName("BASIC_USER").setAuthorities(authorityDTOSToBeTestedAgainst)).collect(Collectors.toSet());
        userDTOToBeTestedAgainst = new UserDTO(uuidToBeTestedAgainst.toString()).setFirstName("John").setLastName("Doe").setEmail("john.doe@noseryoung.ch").setRoles(roleDTOSToBeTestedAgainst);
        listOfUserDTOSToBeTestedAgainst = Arrays.asList(userDTOToBeTestedAgainst, userDTOToBeTestedAgainst);
    }

    @Test
    @WithMockUser
    public void findById_requestUserById_returnsUser() throws Exception {
        given(userService.findById(anyString())).will(invocation -> {
            if ("non-existent".equals(invocation.getArgument(0))) throw new BadRequestException();
            return (userToBeTestedAgainst);
        });

        UUID uuid = UUID.randomUUID();

        mvc.perform(
                MockMvcRequestBuilders.get("/users/{id}", uuid.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(userToBeTestedAgainst.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(userToBeTestedAgainst.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(userToBeTestedAgainst.getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(userToBeTestedAgainst.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.roles[*].name").value(Matchers.containsInAnyOrder(userToBeTestedAgainst.getRoles().stream().map(Role::getName).toArray())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.roles[*].authorities[*].name").value(Matchers.containsInAnyOrder(userToBeTestedAgainst.getRoles().stream().map(Role::getAuthorities).flatMap(Collection::stream).map(Authority::getName).toArray())));

        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(userService, times(1)).findById(stringArgumentCaptor.capture());
        Assertions.assertThat(stringArgumentCaptor.getValue().equals(userToBeTestedAgainst.getId()));
    }

    @Test
    @WithMockUser
    public void findAll_requestAllUsers_returnsAllUsers() throws Exception {
        given(userService.findAll()).willReturn(listOfUsersToBeTestedAgainst);

        mvc.perform(
                MockMvcRequestBuilders.get("/users")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].id").value(Matchers.containsInAnyOrder(userToBeTestedAgainst.getId(),userToBeTestedAgainst.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].firstName").value(Matchers.containsInAnyOrder(userToBeTestedAgainst.getFirstName(),userToBeTestedAgainst.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].lastName").value(Matchers.containsInAnyOrder(userToBeTestedAgainst.getLastName(),userToBeTestedAgainst.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].email").value(Matchers.containsInAnyOrder(userToBeTestedAgainst.getEmail(),userToBeTestedAgainst.getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].roles[*].name").value(Matchers.containsInAnyOrder(ArrayUtils.addAll(userToBeTestedAgainst.getRoles().stream().map(Role::getName).toArray(), userToBeTestedAgainst.getRoles().stream().map(Role::getName).toArray()))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].roles[*].authorities[*].name").value(Matchers.containsInAnyOrder(ArrayUtils.addAll(userToBeTestedAgainst.getRoles().stream().map(Role::getAuthorities).flatMap(Collection::stream).map(Authority::getName).toArray(), userToBeTestedAgainst.getRoles().stream().map(Role::getAuthorities).flatMap(Collection::stream).map(Authority::getName).toArray()))));

        verify(userService, times(1)).findAll();
    }

    @Test
    @WithMockUser
    public void create_deliverUserDTOToCreate_returnCreatedUserDTO() throws Exception {
        String userDTOAsJsonString = new ObjectMapper().writeValueAsString(userDTOToBeTestedAgainst);

        given(userService.save(any(User.class))).will(invocation -> {
            if ("non-existent".equals(invocation.getArgument(0))) throw new BadRequestException();
            UUID uuid = UUID.randomUUID();
            User user = invocation.getArgument(0);
            return user.setId(uuid.toString());
        });

        mvc.perform(
                MockMvcRequestBuilders
                        .post("/users")
                        .content(userDTOAsJsonString)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(userDTOToBeTestedAgainst.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(userDTOToBeTestedAgainst.getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(userDTOToBeTestedAgainst.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.roles[*].name").value(Matchers.containsInAnyOrder(userDTOToBeTestedAgainst.getRoles().stream().map(RoleDTO::getName).toArray())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.roles[*].authorities[*].name").value(Matchers.containsInAnyOrder(userDTOToBeTestedAgainst.getRoles().stream().map(RoleDTO::getAuthorities).flatMap(Collection::stream).map(AuthorityDTO::getName).toArray())));

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userService, times(1)).save(userArgumentCaptor.capture());
        Assertions.assertThat(userArgumentCaptor.getValue().getFirstName().equals(userDTOToBeTestedAgainst.getFirstName()));
        Assertions.assertThat(userArgumentCaptor.getValue().getFirstName().equals(userDTOToBeTestedAgainst.getLastName()));
        Assertions.assertThat(userArgumentCaptor.getValue().getFirstName().equals(userDTOToBeTestedAgainst.getEmail()));
        Assertions.assertThat(userArgumentCaptor.getValue().getRoles().stream().map(Role::getName).toArray()).containsExactlyInAnyOrder(userDTOToBeTestedAgainst.getRoles().stream().map(RoleDTO::getName).toArray());
        Assertions.assertThat(userArgumentCaptor.getValue().getRoles().stream().map(Role::getAuthorities).flatMap(Collection::stream).map(Authority::getName).toArray()).containsExactlyInAnyOrder(userDTOToBeTestedAgainst.getRoles().stream().map(RoleDTO::getAuthorities).flatMap(Collection::stream).map(AuthorityDTO::getName).toArray());
    }

    @Test
    @WithMockUser
    public void updateUserById_requestUserDTOToBeUpdated_returnUpdatedUserDTO() throws Exception {
        String userDTOAsJsonString = new ObjectMapper().writeValueAsString(userDTOToBeTestedAgainst);

        given(userService.updateById(anyString(), any(User.class))).will(invocation -> {
            if ("non-existent".equals(invocation.getArgument(0)) || "non-existent".equals(invocation.getArgument(1))) throw new BadRequestException();
            return ((User) invocation.getArgument(1)).setId(invocation.getArgument(0));
        });

        UUID uuid = UUID.randomUUID();

        mvc.perform(
                MockMvcRequestBuilders.put("/users/{id}", uuid.toString())
                        .content(userDTOAsJsonString)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(userDTOToBeTestedAgainst.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(userDTOToBeTestedAgainst.getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(userDTOToBeTestedAgainst.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.roles[*].name").value(Matchers.containsInAnyOrder(userDTOToBeTestedAgainst.getRoles().stream().map(RoleDTO::getName).toArray())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.roles[*].authorities[*].name").value(Matchers.containsInAnyOrder(userDTOToBeTestedAgainst.getRoles().stream().map(RoleDTO::getAuthorities).flatMap(Collection::stream).map(AuthorityDTO::getName).toArray())));

        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userService, times(1)).updateById(stringArgumentCaptor.capture(), userArgumentCaptor.capture());
        Assertions.assertThat(stringArgumentCaptor.getValue().equals(userToBeTestedAgainst.getId()));
        Assertions.assertThat(userArgumentCaptor.getValue().getFirstName().equals(userDTOToBeTestedAgainst.getFirstName()));
        Assertions.assertThat(userArgumentCaptor.getValue().getFirstName().equals(userDTOToBeTestedAgainst.getLastName()));
        Assertions.assertThat(userArgumentCaptor.getValue().getFirstName().equals(userDTOToBeTestedAgainst.getEmail()));
        Assertions.assertThat(userArgumentCaptor.getValue().getRoles().stream().map(Role::getName).toArray()).containsExactlyInAnyOrder(userDTOToBeTestedAgainst.getRoles().stream().map(RoleDTO::getName).toArray());
        Assertions.assertThat(userArgumentCaptor.getValue().getRoles().stream().map(Role::getAuthorities).flatMap(Collection::stream).map(Authority::getName).toArray()).containsExactlyInAnyOrder(userDTOToBeTestedAgainst.getRoles().stream().map(RoleDTO::getAuthorities).flatMap(Collection::stream).map(AuthorityDTO::getName).toArray());
    }

    // Negative Test, tries to update an user with a false email
    @Test
    @WithMockUser
    public void updateUserById_requestUserDTOWithWrongEmailToBeUpdated_returnBadRequest() throws Exception {
        String originEmail = userDTOToBeTestedAgainst.getEmail();
        String badEmailFromUserDTOAsJsonString = new ObjectMapper().writeValueAsString(userDTOToBeTestedAgainst.setEmail("eifachepis"));

        mvc.perform(MockMvcRequestBuilders.put("/users/{id}", userDTOToBeTestedAgainst.getId())
                .content(badEmailFromUserDTOAsJsonString)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userService, never()).updateById(stringArgumentCaptor.capture(), userArgumentCaptor.capture());

        Assertions.assertThat(stringArgumentCaptor.getAllValues().isEmpty());
        Assertions.assertThat(userArgumentCaptor.getAllValues().isEmpty());

        userDTOToBeTestedAgainst.setEmail(originEmail);
    }


    @Test
    @WithMockUser
    public void deleteUserById_requestADeletionOfUserById_returnAppropriateState() throws Exception {
        given(userService.deleteById(anyString())).will(invocation -> {
            if ("non-existent".equals(invocation.getArgument(0))) throw new BadRequestException();
            return null;
        });

        UUID uuid = UUID.randomUUID();

        mvc.perform(
                MockMvcRequestBuilders.delete("/users/{id}", uuid.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(userService, times(1)).deleteById(stringArgumentCaptor.capture());
        Assert.assertEquals(uuid.toString(),stringArgumentCaptor.getValue());
    }

}