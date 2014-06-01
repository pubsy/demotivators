package controllers;
import models.Demotivator;

import org.junit.Before;
import org.junit.Test;

import play.i18n.Lang;
import play.modules.paginate.ModelPaginator;
import play.mvc.Http.Header;
import play.mvc.Http.Request;
import play.mvc.Http.Response;
import play.test.Fixtures;
import play.test.FunctionalTest;

public class ApplicationTest extends FunctionalTest {

	@Before
	public void deleteModels() {
		Fixtures.deleteAllModels();
	}
	
	/**
	 * Going to index page. Sanity test. Verifying response content type and encoding.
	 */
    @Test
    public void testIndexPage() {
    	
        Response response = GET("/");
        
        assertNotNull(response);
        assertIsOk(response);
        assertContentType("text/html", response);
        assertCharset(play.Play.defaultWebEncoding, response);
        
    }
    
    /**
     * Verifying index controller is rendering ten latest ordered Demotivators.
     */
    @Test
    public void testIndexPageRenderingCorrectData() {

    	Fixtures.loadModels("data/index.yml");

    	Request r = newRequest();
    	r.domain = "localhost";
    	GET(r, "/");
    	
    	ModelPaginator<Demotivator> demotivators = (ModelPaginator<Demotivator>)renderArgs("paginator");
        assertNotNull(demotivators);
        assertEquals(10, demotivators.size());
        assertEquals(10, demotivators.getCurrentPage().size());
        assertEquals("A pretty demotivator", demotivators.getCurrentPage().get(0).getTitle());
        assertEquals("George Washington", demotivators.get(1).getAuthor().getDisplayName());
        assertEquals("five.jpg", demotivators.get(9).getFileName());
    }
    
  
    /**
	 * Going to single Demotivator page. Sanity test.
	 */
	@Test
	public void testSinglePage() {
		
    	Fixtures.loadModels("data/single.yml");

		Demotivator demo = Demotivator.find("order by date desc").first();

		Response response = GET("/single/" + demo.id);

		assertNotNull(response);
		assertIsOk(response);
	}
    
    /**
     * Verifying single controller is rendering Demotivator passed by id.
     */
    @Test
    public void testSinglePageRenderingCorrectData() {
    	
    	Fixtures.loadModels("data/single.yml");
    	
    	Demotivator demo = Demotivator.find("order by date desc").first();
    	
    	GET("/single/" + demo.id);//id is changing from one tests run to another

    	Demotivator demotivator = (Demotivator)renderArgs("demotivator");
        assertNotNull(demotivator);
        assertEquals("An ugly demotivator", demotivator.getTitle());
        assertEquals("Martin Fowler", demotivator.getAuthor().getDisplayName());
        assertEquals("ugly.jpg", demotivator.getFileName());   
    }
    
    
    @Test
    public void testLocaleControllerAccessible(){
    	Response response = GET("/locale/ua");
    	
    	assertNotNull(response);
    	assertStatus(302, response);
    }
    
    /**
     * Verifying locale controller. Try changing locale. Then changing back.
     */
    @Test
    public void testLocaleChange(){
    	//Cheking default locale
    	assertEquals("en", Lang.getLocale().getLanguage());

    	//Changing to Ukrainian
    	Response response = GET("/locale/ua");
    	String value = response.cookies.get("PLAY_LANG").value;
    	assertEquals("ua", value);

    	//Changing to English
    	response = GET("/locale/en");
    	value = response.cookies.get("PLAY_LANG").value;
    	assertEquals("en", value);
   
    }
    
    @Test
    public void testLocaleControllerRedirectsBackToReferingPage(){

    	//Verifying redirected to default path
    	Response response = GET("/locale/ua");
    	
    	assertHeaderEquals("Location", "/", response);
    	
    	//Verifying redirected to the page where the call was made
    	Request request = newRequest();
    	request.headers.put("referer", new Header("Location", "/users/register"));
    	response = GET(request, "/locale/en");

     	assertHeaderEquals("Location", "/users/register", response);
    }
    
    @Test
    public void testReuestWithoutWWWDoesntGetRedirected(){
    	Request r = newRequest();
    	r.domain = "test.com";
    	Response response = GET(r, '/');
    	
    	assertStatus(200, response);
    }
	
    @Test
    public void testReuestToWWWGetsRedirected(){
    	Request r = newRequest();
    	r.domain = "www.test.com";
    	Response response = GET(r, '/');
    	
    	assertStatus(302, response);
    	assertHeaderEquals("Location", "http://test.com/", response);
    }
    
    @Test
    public void testNext(){
    	Fixtures.loadModels("data/two_demos.yml");
    	
    	Demotivator first = Demotivator.find("order by date desc").first();
    	Demotivator second = Demotivator.find("id < ? and domain = ? order by date desc", first.id, first.getDomain()).first();
    	
    	Response response = GET("/next/" + first.id);//id is changing from one tests run to another

    	assertEquals("/single/" + second.id, response.getHeader("Location"));
    }
    
    @Test
    public void testNextRedirectsHome(){
    	Fixtures.loadModels("data/two_demos.yml");
    	
    	Demotivator first = Demotivator.find("order by date desc").first();
    	Demotivator second = Demotivator.find("id < ? order by date desc", first.id).first();
    	
    	Response response = GET("/next/" + second.id);//id is changing from one tests run to another

    	assertEquals("/", response.getHeader("Location"));
    }
    
    @Test
    public void testNextChecksDomain(){
    	Fixtures.loadModels("data/next_w_diff_domains.yml");
    	
    	Demotivator first = Demotivator.find("order by date desc").first();
    	Demotivator second = Demotivator.find("id < ? order by date desc", first.id).first();
    	
    	assertNotNull(first);
    	assertEquals("localhost", first.getDomain().getName());
    	
    	assertNotNull(second);
    	assertEquals("other_domain", second.getDomain().getName());
    	
    	Response response = GET("/next/" + first.id);//id is changing from one tests run to another

    	assertEquals("/", response.getHeader("Location"));
    }
    
}