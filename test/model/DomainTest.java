package model;

import java.util.List;

import models.Domain;

import org.junit.Before;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;

public class DomainTest extends UnitTest {

	@Before
	public void deleteModels() {
		Fixtures.deleteAllModels();
	}
	
	@Test
	public void saveAndGetDomain() {
		Fixtures.loadModels("data/domain.yml");
		
	    Domain domain = Domain.find("byName", "localhost").first();

	    assertNotNull(domain);
	    assertEquals("localhost", domain.getName());
	}
	
	@Test
	public void saveGetOrCreateExists() {
		Fixtures.loadModels("data/domain.yml");
		
		List<Domain> domains = Domain.findAll();
		assertEquals(1, domains.size());
	    Domain domain = Domain.getOrCreate("localhost");

	    assertNotNull(domain);
	    assertEquals("localhost", domain.getName());
	    
	    domains = Domain.findAll();
		assertEquals(1, domains.size());
	}
	
	@Test
	public void saveGetOrCreateNotExists() {
		Fixtures.loadModels("data/domain.yml");
		
		List<Domain> domains = Domain.findAll();
		assertEquals(1, domains.size());
	    Domain domain = Domain.getOrCreate("localhost1");

	    assertNotNull(domain);
	    assertEquals("localhost1", domain.getName());
	    
	    domains = Domain.findAll();
		assertEquals(2, domains.size());
	}
}
