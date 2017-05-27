package service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by sebo on 24.05.17.
 */
@Path("sgcheck")
public class SGCheck {
    public SGCheck() {
    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String message()
    {
      return "Yea! ";
    }
}
