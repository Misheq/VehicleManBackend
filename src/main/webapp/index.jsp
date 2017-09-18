<html>
<body>
    <h2>Jersey RESTful Web Application!</h2>
    <p><a href="api/users">List all users</a></p>
    <p><a href="api/vehicles">List all vehicles</a></p>
    <p><a href="api/persons">List all persons</a></p>
    <p>Your paths at the moment are http://localhost:8080/vehicleman/api/</p>
    <ul>
    	<li>User related endpoints</li>
    	<li>GET: /users</li>
    	<li>GET: /users/id</li>
    	<li>POST: /users</li>
    	<li>PUT: /users/id</li>
    	<li>DELETE: /users/id</li>
    </ul>
    <ul>
    	<li>Vehicle related endpoints</li>
    	<li>GET: /vehicles</li>
    	<li>GET: /vehicles/id</li>
    	<li>POST: /vehicles</li>
    	<li>PUT: /vehicles/id</li>
    	<li>DELETE: /vehicles/id</li>
    </ul>
     <ul>
    	<li>Person related endpoints</li>
    	<li>GET: /persons</li>
    	<li>GET: /persons/id</li>
    	<li>POST: /persons</li>
    	<li>PUT: /persons/id</li>
    	<li>DELETE: /persons/id</li>
    </ul>
</body>
</html>

http://.../api/persons/id/vehicle PUT
{
	"id": "12315121";
}
