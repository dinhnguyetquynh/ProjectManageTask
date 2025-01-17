package datafaker;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

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

}
