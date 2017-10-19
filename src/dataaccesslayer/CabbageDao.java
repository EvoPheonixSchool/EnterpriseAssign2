/* File: CabbageDao.java
 * Author: Stanley Pieda
 * Date: Sept, 2017
 * Description: Sample solution to Assignment 2
 * References:
 * Ram N. (2013). Data Access Object Design Pattern or DAO Pattern [blog] Retrieved from
 * http://ramj2ee.blogspot.in/2013/08/data-access-object-design-pattern-or.html
 */
package dataaccesslayer;

// import java.util.List;
import datatransfer.Cabbage; 

/**
 * Interface with abstract methods for CRUD operations
 * not all parts are implemented
 * @author Stan Pieda
 *
 */
public interface CabbageDao {
	// List<Cabbage> getAllTasks();
	// Cabbage getCabbageById(int taskId);
	Cabbage getCabbageByUUID(String uuid);
	String insertCabbage(Cabbage cabbage);
	// void updateCabbage(Cabbage task);
	// void deleteCabbage(Cabbage task);
}
