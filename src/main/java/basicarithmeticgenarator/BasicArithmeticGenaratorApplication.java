package basicarithmeticgenarator;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BasicArithmeticGenaratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(BasicArithmeticGenaratorApplication.class, args);
    }

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
