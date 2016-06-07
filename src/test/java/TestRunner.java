import com.rnelson.server.RequestHandler;
import com.rnelson.server.unitTests.RequestHandlerTests;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin={"pretty"}, glue="com.rnelson.server", features="src/test/resources")

public class TestRunner {
}
