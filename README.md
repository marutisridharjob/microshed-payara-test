# payara-microshed-examples
Examples of using Payara Micro and MicroProfile Microshed Testing


## Directory `basic`

Test Rest endpoints in your applciation.

## Directory `jwt`

Test Rest endpoints that require authentication with a JWT token.

Important notice

- Define `disable.type.verification=true` within the _payara-mp-jwt.properties_ file to avoid the `JWTProcessingException: Not signed JWT, typ must be 'JWT'` error message.

- Define the `jti` claim within the `@JwtConfig` to avoid the `JWTProcessingException: Not all required claims present` error message.
