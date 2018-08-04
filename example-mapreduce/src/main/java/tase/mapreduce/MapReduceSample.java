package tase.mapreduce;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;
import com.mongodb.MongoClient;

/**
 * The Amazing Software Experiment
 * 
 * Mastering MapReduce once and for all
 * 
 * @author plopcas
 */
public class MapReduceSample {

	public static final Logger LOGGER = LogManager
			.getLogger(MapReduceSample.class);

	public static void main(String[] args) {
		MongoClient mongo;

		try {
			// Connect to MongoDB
			mongo = new MongoClient("localhost", 27017);

			// Open or create the database
			DB db = mongo.getDB("tase-opinions");
			db.dropDatabase();

			// Get or create the collection
			DBCollection scores = db.getCollection("scores");

			// Initialise DB with dummy data
			dummyDataGenerator(scores);

			// Define map and reduce functions, MongoDB language is Javascript
			String map = "function() { "
					+ "var category; "
					+ "if ( this.value >= 5 )  {"
					+ "category = 'Supporters';"
					+ "} "
					+ "else {"
					+ "category = 'Detractors';"
					+ "} "
					+ "emit(category, {user: this.user});}";

			String reduce = "function(key, values) { "
					+ "var sum = 0; "
					+ "values.forEach(function(doc) { "
					+ "sum += 1; "
					+ "}); "
					+ "return {people: sum};} ";

			MapReduceCommand cmd = new MapReduceCommand(scores, map, reduce,
					null, MapReduceCommand.OutputType.INLINE, null);

			// Run mapReduce on our collection and show the results
			MapReduceOutput out = scores.mapReduce(cmd);
			for (DBObject o : out.results()) {
				System.out.println(o.toString());
			}
		} catch (Exception e) {
			LOGGER.error("Error trying mapReduce");
		}
	}

	/**
	 * Populates the DB with dummy data
	 * 
	 * @param scores
	 *            scores collection to populate
	 */
	private static void dummyDataGenerator(DBCollection scores) {
		BasicDBObject score = new BasicDBObject();

		score.put("user", "Fredrick");
		score.put("value", 10);
		scores.insert(score);

		score = new BasicDBObject();
		score.put("user", "Randal");
		score.put("value", 1);
		scores.insert(score);

		score = new BasicDBObject();
		score.put("user", "Kelsey");
		score.put("value", 7);
		scores.insert(score);

		score = new BasicDBObject();
		score.put("user", "Shirley");
		score.put("value", 8);
		scores.insert(score);

		score = new BasicDBObject();
		score.put("user", "Royle");
		score.put("value", 4);
		scores.insert(score);
	}
}
