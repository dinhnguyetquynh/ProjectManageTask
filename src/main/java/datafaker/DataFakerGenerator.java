package datafaker;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Pattern;

import model.Account;
import model.Gender;
import model.User;
import net.datafaker.Faker;

public class DataFakerGenerator {

	private final Faker faker;
	private static final Random random = new Random();

	public DataFakerGenerator() {
		this.faker = new Faker(new Locale("vi"));
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
	


}
