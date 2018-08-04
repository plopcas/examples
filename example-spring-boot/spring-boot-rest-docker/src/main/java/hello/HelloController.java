package hello;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	private final AtomicLong counter = new AtomicLong();

	@RequestMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}

	@RequestMapping("/hello-world")
	public Saying saying(@RequestParam(value="name", required=false, defaultValue="Stranger") String name) {
		return new Saying(counter.incrementAndGet(), name);
	}

  @RequestMapping("/foo")
	public Saying foo(@RequestParam(value="name", required=false, defaultValue="Foo") String name) {
		return new Saying(counter.incrementAndGet(), name);
	}

}
