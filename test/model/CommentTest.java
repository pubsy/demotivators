package model;

import java.util.Date;
import java.util.List;

import models.Comment;
import models.Demotivator;
import models.User;

import org.junit.Before;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;

public class CommentTest extends UnitTest {

	@Before
	public void deleteModels(){
		Fixtures.deleteAllModels();
	}
	
	/**
	 * 
	 */
	@Test
	public void loadComment() {
		Fixtures.loadModels("data/comment.yml");
		
	    List<Comment> commentsList = Comment.findAll();

	    assertNotNull(commentsList);
	    assertEquals(1, commentsList.size());
	    
	    Comment comment = commentsList.get(0);
	    assertEquals("A very motivating demotivator", comment.getText());
	    assertEquals(1394323200000L, comment.getDate().getTime());
	    
	    User user = User.find("byEmail", "frank.sinatra@gmail.com").first();
	    assertEquals(user, comment.getUser());
	    
	    List<Demotivator> demoList = Demotivator.findAll();
	    Demotivator demo = demoList.get(0);
	    assertEquals(demo, comment.getDemotivator());
	}
	
	@Test
	public void addComment(){
		Fixtures.loadModels("data/comment.yml");
		
		User user = User.find("byEmail", "frank.sinatra@gmail.com").first();
		
		List<Demotivator> demoList = Demotivator.findAll();
		assertEquals(1,demoList.size());
	    Demotivator demo = demoList.get(0);
	    
	    assertEquals(1, demo.getComments().size());
	    
	    Comment newComment = new Comment();
	    newComment.setDate(new Date(1394323200000L));
	    newComment.setDemotivator(demo);
	    newComment.setUser(user);
	    newComment.setText("Yet another comment!");
	    
	    newComment.save();
	    demo.getComments().add(newComment);
	    
	    Demotivator sameDemo = (Demotivator) Demotivator.findAll().get(0);
	    assertEquals(2, sameDemo.getComments().size());
	    assertEquals("A very motivating demotivator" , sameDemo.getComments().get(0).getText());
	    assertEquals("Yet another comment!" , sameDemo.getComments().get(1).getText());
	}
	
}
