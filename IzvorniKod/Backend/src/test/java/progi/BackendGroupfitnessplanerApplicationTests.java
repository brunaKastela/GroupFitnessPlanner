package progi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import progi.domain.Coach;
import progi.domain.Schedule;
import progi.domain.Training;
import progi.domain.Workout;
import progi.repository.*;
import progi.rest.ClientController;
import progi.rest.LoginController;
import progi.rest.dto.*;
import progi.service.ClientService;
import progi.service.CoachService;
import progi.service.UserService;
import progi.service.impl.ClientServiceJPA;
import progi.service.impl.CoachServiceJPA;

import javax.persistence.EntityManager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@AutoConfigureMockMvc
class BackendGroupfitnessplanerApplicationTests {
	@Mock
	UserService userService;

	MockMvc mockMvc;

	ClientService clientService;

	CoachService coachServiceForNotVerifiedTest;
	CoachService coachServiceForVerifiedTest;

	@Autowired
	ClientRepository clientRepo;

	@Mock
	ScheduleRepository scheduleRepo;

	@Mock
	TrainingRepository trainingRepo;




	@Mock
	CoachRepository coachRepoMock;

	@Autowired
	CoachRepository coachRepoReal;



	@Autowired
	EntityManager em;

	@Autowired
	GoalRepository goalRepo;

	@Autowired
	UserRepository userRepo;

	@Autowired
	WorkoutRepository workoutRepo;

	LoginController loginController;


	@BeforeEach
	public void setup(){
		this.clientService = new ClientServiceJPA(trainingRepo,  scheduleRepo,  goalRepo,  userRepo,  coachRepoReal,  clientRepo,  em);
		this.coachServiceForNotVerifiedTest = new CoachServiceJPA(trainingRepo,  scheduleRepo,  userRepo,  workoutRepo, clientRepo, coachRepoReal,  em);
		this.coachServiceForVerifiedTest = new CoachServiceJPA(trainingRepo,  scheduleRepo,  userRepo,  workoutRepo, clientRepo, coachRepoMock,  em);
		this.loginController = new LoginController(userService);
		this.mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();

	}

	@Test
	void addReservationForTrainingThatDoesntExist(){
		ClientReservation clientReservation = new ClientReservation(2L, 1L, "11.01.2023");
		Exception ex = Assert.assertThrows(Exception.class,()->clientService.makeReservation(clientReservation));
		Assert.assertEquals(ex.getMessage(), "Rezervacija ne postoji!");
	}

	@Test
	void addReservationForNotAssignedTraining(){
			ClientReservation clientReservation = new ClientReservation(2L, 1L, "11.01.2023");
			LocalDate date = LocalDate.parse("11.01.2023", DateTimeFormatter.ofPattern("dd.MM.yyyy"));
			when(scheduleRepo.getSchedule(1L, LocalDate.parse("11.01.2023", DateTimeFormatter.ofPattern("dd.MM.yyyy")))).thenReturn(new Schedule(new Training(),date, 1, 2));
			Exception ex = Assert.assertThrows(Exception.class,()->clientService.makeReservation(clientReservation));
			Assert.assertEquals(ex.getMessage(), "Pokušavate dodati rezervaciju za trening koji vam nije dodijeljen!");
	}


	@Test
	void createTrainingWithoutBeingVerified(){
		ScheduleTraining training = new ScheduleTraining("test","test",25,new int[]{1,2,3}, 7L,new int[][]{{1,2,3},{1,2,3}});
		Exception ex = Assert.assertThrows(Exception.class,()->coachServiceForNotVerifiedTest.createTraining(training));
		Assert.assertEquals(ex.getMessage(), "Još niste verificirani od strane admina!");
	}

	@Test
	void createTrainingAfterBeingVerified() throws Exception {
		Coach coach = coachRepoReal.findById(7L).get();
		coach.setVerified(true);

		when(coachRepoMock.findById(7L)).thenReturn(java.util.Optional.of(coach));
		ScheduleTraining scheduleTraining = new ScheduleTraining("test","test",25,new int[]{1,2}, 7L,new int[][]{{1,2,3},{14,16,14}});


		when(scheduleRepo.saveAll(Mockito.any(List.class))).thenAnswer(i -> i.getArguments()[0]);
		when(trainingRepo.save(Mockito.any(Training.class))).thenAnswer(i -> i.getArguments()[0]);
		Message message = coachServiceForVerifiedTest.createTraining(scheduleTraining);
		Assert.assertEquals(message.getMessage(), "OK");
	}

	@Test
	void changeGoalToNonexistentGoal() {
		ClientGoal clientGoal = new ClientGoal(2L, "xyz-nepostoji");
		Exception ex = Assert.assertThrows(Exception.class,()->clientService.changeGoal(clientGoal));
		Assert.assertEquals(ex.getMessage(), "Navedeni cilj ne postoji!");
	}


	@Test
	void wrongPasswordAuth() throws Exception {
		when(userService.login("johnsmith", "password1234")).thenThrow(new Exception("Pogrešno korisničko ime i/ili lozinka!"));
		LoginForm loginForm = new LoginForm("johnsmith", "password1234");
		mockMvc.perform(post("/login/user")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(loginForm)))
				.andExpect(status().isBadRequest());
	}


}
