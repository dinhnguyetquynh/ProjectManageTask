package datafaker;

import java.sql.Date;
import java.text.Normalizer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Pattern;

import model.Account;
import model.Gender;
import model.Priority;
import model.Project;
import model.Status;
import model.Task;
import model.User;
import net.datafaker.Faker;

public class DataFakerGenerator {

	private final Faker faker;
	private static final Random random = new Random();

	public DataFakerGenerator() {
		this.faker = new Faker();
	}

	// Tạo dữ liệu giả cho người dùng
	public User generateFakeUsers() {

		String fullName = faker.name().fullName();
		String email = faker.internet().emailAddress();
		Gender gender = Gender.values()[random.nextInt(Gender.values().length)];
		int age = faker.random().nextInt(18, 40);
		User u = new User(fullName, gender, age, email, null);

		return u;
	}

	// Tạo dữ liệu giả cho Account
	public Account generateFakerAccounts(User user) {
		String accountName = normalizeAccountName(user.getName());
		String password = faker.internet().password();
		String role = user.getManager() == null ? "Manager" : "Employee";
		return new Account(0, accountName, password, role, user);
	}
	
	private String normalizeAccountName(String input) {
	    String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
	    String withoutAccents = normalized.replaceAll("[đĐ]", "d").replaceAll("[^\\p{ASCII}]", "");
	    return withoutAccents.replaceAll("\\s+", "").toLowerCase();
	}
	
	public Project generateProject() {
        String title = faker.company().name();
        String description = faker.lorem().sentence();
        Date startDate = Date.valueOf(LocalDate.now().minusDays(random.nextInt(100)));
        Date endDate = Date.valueOf(startDate.toLocalDate().plusDays(random.nextInt(200)));

        return new Project(title, description, startDate, endDate);
    }
	
	public Task generateTask(Project project, Task parentTask) {
        String title = faker.job().title();
        String description = faker.lorem().paragraph();
        Priority priority = Priority.values()[random.nextInt(Priority.values().length)];
        Date createAt = Date.valueOf(LocalDate.now().minusDays(random.nextInt(10)));
        Date dueDate = Date.valueOf(createAt.toLocalDate().plusDays(random.nextInt(50)));
        Status status = Status.values()[random.nextInt(Status.values().length)];

        return new Task(title, description, priority, createAt, dueDate, status, project, parentTask);
    }

}
