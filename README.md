<h1>Authentication API with Spring Boot</h1>
<p><strong>🔗 Swagger UI:</strong> <a target="_blank" href="http://localhost:8080/swagger-ui.html" target="_blank">http://localhost:8080/swagger-ui.html</a></p>


<p>This is an authentication API that uses JWT for user authentication, with ADMIN and USER roles implemented in Spring Boot and Spring Security. The database used is PostgreSQL.</p>

   <h2>Prerequisites</h2>
    <ul>
        <li>Java 21</li>
        <li>PostgreSQL</li>
        <li>Spring Boot</li>
    </ul>

   <h2>How to Run</h2>
   <p>The application must be started manually, as well as the PostgreSQL database.</p>
    <h3>1. Start PostgreSQL Database</h3>
   <h3>2. Start the Application</h3>
    <p>To start the application, run the following command in the root of the project:</p>
    <pre><code>
    ./mvnw spring-boot:run
    </code></pre>

   <h2>Endpoints</h2>
    <p>The following endpoints are available in the API:</p>
    <ul>
        <li><strong>POST /api/auth/register</strong> - User registration</li>
        <li><strong>POST /api/auth/authenticate</strong> - User login (authenticates and returns a JWT)</li>
        <li><strong>GET /api/auth/hello-admin</strong> - Route for administrators only (requires ADMIN role)</li>
        <li><strong>GET /api/auth/hello-users</strong> - Route for users only (requires USER role)</li>
        <li><strong>GET /api/auth/admin-users</strong> - Route accessible for all roles (ADMIN, USER)</li>
    </ul>

   <h2>Authentication</h2>
    <p>To interact with protected routes, you need to include a valid JWT token in the <code>Authorization</code> header of the request. The format is:</p>
    <pre><code>
    Authorization: Bearer *jwt_token*
    </code></pre>

   <h2>Roles</h2>
    <p>The available roles are:</p>
    <ul>
        <li><strong>ADMIN</strong>: Full access to all routes.</li>
        <li><strong>USER</strong>: Access to specific user routes.</li>
    </ul>

  <h3>
    The application.properties file should look like this (using the same name as in the code):
    <pre>
      <code>
spring.application.name=security-jwt

spring.datasource.url=jdbc:postgresql://localhost:5432/databasename
spring.datasource.username=postgres
spring.datasource.password=yourPasswordOfDatabase
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.sql.init.mode=always
spring.jpa.defer-datasource-initialization=true

jwt.secret=secretKeyWithAlmost32Characters
jwt.expiration=86400000
jwt.authorities.key=roles

  </code>
    </pre>
  </h3>


   <h2>Notes</h2>
    <p>Remember to manually start both the application and the database for everything to work correctly.</p>

   <!-- Spanish Version -->
  <hr>
    <h1>API de Autenticación con Spring Boot</h1>
    <p>Esta es una API de autenticación que utiliza JWT para la autenticación de usuarios, con roles de ADMIN y USER, implementada en Spring Boot y Spring Security. La base de datos utilizada es PostgreSQL.</p>

  <h2>Requisitos previos</h2>
    <ul>
        <li>Java 21</li>
        <li>PostgreSQL</li>
        <li>Spring Boot</li>
    </ul>

  <h2>Instrucciones de ejecución</h2>
    <p>La aplicación debe iniciarse manualmente, así como la base de datos PostgreSQL.</p>
    <h3>1. Iniciar la base de datos PostgreSQL</h3>
    <p>Asegúrate de tener PostgreSQL en funcionamiento.</p>
    <h3>2. Iniciar la aplicación</h3>

<h2>Endpoints</h2>
    <p>Los siguientes son los endpoints disponibles en la API:</p>
    <ul>
        <li><strong>POST /api/auth/register</strong> - Registro de usuario</li>
        <li><strong>POST /api/auth/authenticate</strong> - Login de usuario (autentica y devuelve un JWT)</li>
        <li><strong>GET /api/auth/hello-admin</strong> - Ruta solo para administradores (requiere rol ADMIN)</li>
        <li><strong>GET /api/auth/hello-users</strong> - Ruta solo para usuarios (requiere rol USER)</li>
        <li><strong>GET /api/auth/admin-users</strong> - Ruta accesible para todos los roles (ADMIN, USER)</li>
</ul>

<h2>Autenticación</h2>
    <p>Para interactuar con las rutas protegidas, se debe incluir un token JWT válido en el encabezado <code>Authorization</code> de la solicitud. El formato es:</p>
    <pre><code>
    Authorization: Bearer *token_jwt*
</code></pre>

<h2>Roles</h2>
    <p>Los roles disponibles son:</p>
<ul>
        <li><strong>ADMIN</strong>: Acceso completo a todas las rutas.</li>
        <li><strong>USER</strong>: Acceso a rutas específicas para usuarios.</li>
    </ul>

  <h3>
    El archivo application.properties se debe ver de esta manera (utilizando los mismos nombres que estan en el codigo):
    <pre>
      <code>
spring.application.name=security-jwt

spring.datasource.url=jdbc:postgresql://localhost:5432/databasename
spring.datasource.username=postgres
spring.datasource.password=yourPasswordOfDatabase
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.sql.init.mode=always
spring.jpa.defer-datasource-initialization=true

jwt.secret=secretKeyWithAlmost32Characters
jwt.expiration=86400000
jwt.authorities.key=roles

  </code>
    </pre>
  </h3>

<h2>Notas</h2>
<p>Recuerda que debes iniciar la aplicación y la base de datos manualmente para que todo funcione correctamente.</p>
