package hello.resources;

import hello.core.Saying;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/")
public class RestResource {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/saying")
	public Saying sayHello(@QueryParam("name") String name) {
		System.out.println("NAME: " + name);
		return new Saying(name);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/hello")
	public String hello() {
		return "Hello World";
	}

}