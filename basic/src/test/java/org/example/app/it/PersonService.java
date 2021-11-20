/*
 * Copyright (c) 2021 Rudy De Busscher
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.example.app.it;

import be.rubus.payara.demo.microshed.Person;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

@Path("/people")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface PersonService {

    @GET
    Collection<Person> getAllPeople();

    @GET
    @Path("/{personId}")
    Person getPerson(@PathParam("personId") long id);

    @POST
    Long createPerson(@QueryParam("name") String name,
                      @QueryParam("age") int age);

    @POST
    @Path("/{personId}")
    void updatePerson(@PathParam("personId") long id, Person p);

    @DELETE
    @Path("/{personId}")
    void removePerson(@PathParam("personId") long id);

}
