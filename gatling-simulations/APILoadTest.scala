import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class APILoadTest extends Simulation {
	
	val httpProtocol = http
		.baseUrl("http://localhost:8080")
		.inferHtmlResources()
		.acceptHeader("*/*")
		.userAgentHeader("curl/7.68.0")

	val headers_0 = Map(
		"Content-Type" -> "application/json",
		"Proxy-Connection" -> "Keep-Alive")

	val headers_1 = Map("Proxy-Connection" -> "Keep-Alive")

	object CreateCat {
		val create = exec(
		  http("create_cat")
			.post("/api/1/cats")
			.headers(headers_0)
			.body(RawFileBody("api/0000_request.json"))
		)
	}

	object GetCat {
		val get = exec(
			http("get_cat")
			  .get("/api/1/cats/12efb849-e1cc-499a-9093-70d3c4871d0b")
			  .headers(headers_1)
		)
	}

	object ListCats {
		val list = exec(
			http("list_cats")
			  .get("/api/1/cats")
			  .headers(headers_1)
		)
	}
 
	val users = scenario("AllAPIEndpoints").exec(ListCats.list, CreateCat.create, GetCat.get)
		
	setUp(users.inject(rampUsers(5000).during(10.seconds)).protocols(httpProtocol))
}