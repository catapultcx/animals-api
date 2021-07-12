import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class GatlingSimulation extends Simulation {

  private val baseUrl = "http://localhost:"
  private val apiPort = "8080"
  private val uiPort = "2997"
  private val getCatsByID = baseUrl+apiPort+"/api/1/cats/c6387468-aa1a-48b9-a3dc-29225d6d3098"
  private val getAllCats = baseUrl+apiPort+"/api/1/cats"
  private val createCat = baseUrl+apiPort+"/api/1/cats"
  private val getCatsHtmlPage = baseUrl+uiPort+"/cats"



  private val rampUpTime = 10
  private val numberOfUsers =1000
  private val HTTPS_OK = 200;
  private val HTTPS_CREATED = 201;


  val headersCreateRequest = Map(
    "Content-Type" -> "application/json",
    "Proxy-Connection" -> "Keep-Alive")

  val httpProtocol: HttpProtocolBuilder = http
    .baseURL(baseUrl)
    .inferHtmlResources()
    .disableCaching


  val get_cats_by_id: ScenarioBuilder = scenario("GET a Cat by ID")
    .exec(http("getCatsByID")
      .get(getCatsByID)
      .check(status.is(HTTPS_OK),substring("name")))

  val get_all_cats: ScenarioBuilder = scenario("GET all cats")
    .exec(http("getAllCats")
      .get(getAllCats)
      .check(status.is(HTTPS_OK),substring("name")))

  val create_cat: ScenarioBuilder = scenario("Create a new cat")
    .exec(http("createCat")

      .post(createCat)
        .headers(headersCreateRequest)
      .body(RawFileBody("src/test/fixture/new_cat_payload.json"))
      .check(status.is(HTTPS_CREATED),substring("id")))

  val get_cats_on_ui: ScenarioBuilder = scenario("GET cats HTML page")
    .exec(http("getCatsHtmlPage")
      .get(getCatsHtmlPage)
      .check(status.is(HTTPS_OK),substring("Garfield")))


  val allScenarios: ScenarioBuilder = get_cats_by_id
                      .exec(get_all_cats)
                      .exec(create_cat)
                      .exec(get_cats_on_ui)

  val onlyAPI: ScenarioBuilder = get_cats_by_id
    .exec(get_all_cats)
    .exec(create_cat)


  val onlyUI : ScenarioBuilder = create_cat


  setUp(
    allScenarios.inject(rampUsers(numberOfUsers) over rampUpTime)
  ).protocols(httpProtocol)
    .assertions(
      global.failedRequests.percent.is(0.1),
      global.responseTime.mean.lt(1000)
    )
}