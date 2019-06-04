package edu.tsinghua.paratrooper;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Base junit test of the application
 * @author cuiods
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = ParatrooperApplication.class)
public class BaseTest {
}
