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

import org.junit.jupiter.api.Test;
import org.microshed.testing.jaxrs.RESTClient;
import org.microshed.testing.jupiter.MicroShedTest;
import org.microshed.testing.jwt.JwtConfig;
import org.microshed.testing.testcontainers.ApplicationContainer;
import org.testcontainers.junit.jupiter.Container;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotAuthorizedException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MicroShedTest
public class SecuredSvcIT {

    @Container
    public static ApplicationContainer app = new ApplicationContainer()
            .withAppContextRoot("/myservice")
            .withReadinessPath("/health/ready");

    @RESTClient
    @JwtConfig(claims = { "groups=users", "jti=id" })
    public static SecuredService securedSvc;

    @RESTClient
    @JwtConfig(claims = { "groups=wrong", "jti=id" })
    public static SecuredService misSecuredSvc;

    @RESTClient
    public static SecuredService noJwtSecuredSvc;

    @Test
    public void testGetSecuredInfo() {
        String result = securedSvc.getSecuredInfo();
        assertTrue(result.contains("this is some secured info"));
    }

    @Test
    public void testGetSecuredInfoBadJwt() {
        // user will be authenticated but not in role, expect 403
        assertThrows(ForbiddenException.class, () -> misSecuredSvc.getSecuredInfo());
    }

    @Test
    public void testGetSecuredInfoNoJwt() {
        // no user, expect 401
        assertThrows(NotAuthorizedException.class, () -> noJwtSecuredSvc.getSecuredInfo());
    }
}
