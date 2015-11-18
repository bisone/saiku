package org.saiku.web.rest.bisone;

import com.qmino.miredot.annotations.ReturnType;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Component
@Path("/saiku/api/license")
public class License {

    public License() {
    }


    @GET
    @Produces({"application/json"})
    @ReturnType("org.saiku.web.rest.bisone.SoneLicense")
    public Response getLicense() {
        return Response.ok().entity(new SoneLicense()).build();

    }

    @GET
    @Path("/users")
    @Produces({"application/json"})
    @ReturnType("java.util.ArrayList<UserList>")
    public Response getUserlist() {
        ArrayList var2 = new ArrayList();

        var2.add(new UserList("admin", "0"));

        return Response.ok().entity(var2).build();

    }
}
