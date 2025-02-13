package org.gs;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import io.netty.handler.codec.rtsp.RtspResponseStatuses;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/movies")
public class MovieRespource {
    

public static List<Movie> movies = new ArrayList<>();


    @GET
    @Produces(MediaType.APPLICATION_JSON )
    @Path("/all")
    public Response getMovies(){
        return Response.ok(movies).build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/size")
    public Integer countMovies(){
         return movies.size();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/add")
    public Response createMovie(Movie newMovie) {
        movies.add(newMovie);
        return Response.ok(movies).build();
    }


    @PUT
    @Path("{id}/{movieName}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateMovie(@PathParam("id")Long id, @QueryParam("movieName")String movieName){
        movies = movies.stream().map(movie -> {
            if(movie.getId().equals(id))
            {
                movie.setMovieName(movieName);
            }
            return movie;

        }).collect(Collectors.toList());
        return Response.ok(movies).build();
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteMovie(@PathParam("id")Long id){
        Optional<Movie> movieToDelete = movies.stream().filter(movie -> movie.getId().equals(id)).findFirst();
        boolean removed = false;
      if(movieToDelete.isPresent()){
         removed = movies.remove(movieToDelete.get());
      }
       
      if(removed){
        return Response.noContent().build();
      }
        return Response.status(Response.Status.BAD_REQUEST).build();

    }
}
