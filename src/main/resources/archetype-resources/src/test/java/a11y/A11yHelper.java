package ${package}.a11y;

import com.deque.html.axecore.axeargs.AxeRunOptions;
import com.deque.html.axecore.results.Rule;
import com.deque.html.axecore.selenium.AxeBuilder;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class A11yHelper {


    public static List<Rule> hasAccessibilityIssues(WebDriver driver, List<String> a11yExclusions) {
        List<String> ruleSet = List.of("wcag2a", "wcag2aa", "wcag21a", "wcag21aa");

        AxeBuilder axeRunner = new AxeBuilder()
                .withOptions(new AxeRunOptions())
                .withTags(ruleSet)
                .disableRules(a11yExclusions);
        List<Rule> violations = axeRunner.analyze(driver).getViolations();


        if (violations.isEmpty()) {
            return Collections.emptyList();
        } else {
            return violations;
        }
    }
}