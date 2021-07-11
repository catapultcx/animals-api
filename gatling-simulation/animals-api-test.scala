// basic Gatling imports  
import io.Gatling.core.Predef._  
import io.Gatling.http.Predef._  
   
   
// 1. every scenario class should extend from Simulation class  
class createUser extends Simulation{  
   
  // 2. define protocol  
  val httpProtocol = http  
    .baseUrl("http://localhost:8080")  
    .acceptHeader("application/json")  
   
   
  // 3. define scenario  
  def createCat() = {  
      exec(http("create cat")  
        .post("/api/1/cats")  
        .headers(headers_0)
        .body(RawFileBody("api/1111_request.json")) 
  }  
   

    def FetchCat() ={
		exec(http("fetch cat")
			  .get("/api/1/cats/12efb849-e1cc-499a-9093-70d3c4871d0b")
			  .headers(headers_1)
		)
	}

	def ListCats()= {
		exec(http("list_cats")
			  .get("/api/1/cats")
			  .headers(headers_1)
		)
	}
  val scn = scenario("ALLAPIEndpoints")  
    .exec(createCat(),FetchCat(),ListCats())  
   
  // 4. setup scenario with load profile for execution  
  setUp(scn.inject(rampUsers(5000).during(10.seconds))  
    .protocols(httpProtocol))  
   
}