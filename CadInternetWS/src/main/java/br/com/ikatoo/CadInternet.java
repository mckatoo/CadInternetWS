package br.com.ikatoo;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("ws")
public class CadInternet extends ResourceConfig {
    public CadInternet(){
        packages("br.com.ikatoo.ws");
    }
}
