package com.example.demo;

import com.example.demo.SupportTicket.Ticket;
import com.example.demo.SupportTicket.TicketRepository;
import com.example.demo.SupportTicket.TicketRequest;
import com.example.demo.SupportTicket.TicketService;
import com.example.demo.admin.Admin;
import com.example.demo.admin.AdminRepository;
import com.example.demo.email.EmailService;
import com.example.demo.user.UserRepository;
import com.example.demo.user.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AutoProxyRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class BackendSepsepApplication {

	@Autowired
	private EmailService emailService;

//	public static void main(String[] args) {
//		SpringApplication.run(BackendSepsepApplication.class, args);
//	}

	@EventListener(ApplicationReadyEvent.class)
	public void sendMail(){
		/*emailService.sendEmail("gruppe.i1234@gmail.com",
				"Verifiziere dein Email Account",
				"Über diesen Link kannst du dein Account verifizieren");
		*/
	}


	@Bean
	CommandLineRunner init(AdminRepository adminRepository, UserRepository userRepository)
	{
		return args ->
		{
			adminRepository.save(new Admin("Max","Mustermann","Mustermann@gmail.com", "sicheresPasswort"));
			adminRepository.save(new Admin("a","a","a@a.a", "a"));
			userRepository.save(new Users("Max","Mustermann","Mustermann@gmail.com", "sicheresPasswort", "01.01.2000"));
			userRepository.save(new Users("a","a","a@a.a", "a", "a"));
		};
	}

	@Autowired
	private CorsConfig config;

	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter() {
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(config.corsConfigurationSource()));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
	}
private final TicketService ticketService;

	@Autowired
	public BackendSepsepApplication(TicketService ticketService) {
		this.ticketService = ticketService;
	}

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(BackendSepsepApplication.class, args);

		// Get the DemoApplication bean
		BackendSepsepApplication demoApplication = context.getBean(BackendSepsepApplication.class);

		// Create a new ticket
		String description = "Incorrect data sets";
		String email = "meftehikhalil78@gmail.com" ;
		Ticket ticket = demoApplication.createTicket(description , email);

		// Print the ticket details
		System.out.println("Ticket created:");
		System.out.println("ID: " + ticket.getId());
		System.out.println("Description: " + ticket.getDescription());
		System.out.println("Status: " + ticket.getStatus());
		System.out.println("Email: " + ticket.getEmail());
	}

	public Ticket createTicket(String description , String email) {
		// Open a new ticket
		TicketRequest ticketRequest = new TicketRequest();
		ticketRequest.setDescription("Incorrect data set");
		ticketRequest.setEmail("meftehikhalil78@gmail.com");
		Ticket ticket = ticketService.openTicket(ticketRequest );
		return ticket;
	}
	public void resolveTicket(Long ticketId) {
		// Resolve the ticket
		Ticket resolvedTicket = ticketService.resolveTicket(ticketId);

		// Print the resolved ticket details
		System.out.println("Ticket resolved:");
		System.out.println("ID: " + resolvedTicket.getId());
		System.out.println("Description: " + resolvedTicket.getDescription());
		System.out.println("Status: " + resolvedTicket.getStatus());
		System.out.println("Email: " + resolvedTicket.getEmail());
	}
}
