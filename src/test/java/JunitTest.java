/**
 * @author tonytaotao
 */

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("测试用例")
public class JunitTest {

    /**
     * @BeforeAll 需要static 方法，或者使用  @TestInstance(TestInstance.Lifecycle.PER_CLASS) 注解在类上，才能使用非static方法
     */
    @BeforeAll
    public static void init() {
        System.out.println("测试用例初始化");
    }

    @AfterAll
    public static void cleanup() {
        System.out.println("测试用例运行完毕");
    }

    @BeforeEach
    public void caseStart() {
        System.out.println("当前测试用例开始");
    }

    @AfterEach
    public void caseEnd() {
        System.out.println("当前测试用例结束");
    }

    @DisplayName("测试")
    @RepeatedTest(2)
    public void testAssert() {
        Assertions.assertEquals(2, 1 + 1);
    }

    @ParameterizedTest
    @ValueSource(ints = {2,4,6})
    public void testValueSource(int num) {
        Assertions.assertEquals(0, num % 2);
    }

    @ParameterizedTest
    @CsvSource({"1,1", "2,4"})
    public void testCsvSource(int num, int result){
        Assertions.assertEquals(result, Math.pow(num, 2));
    }

    @DisplayName("测试禁用")
    @Disabled
    @Test
    public void testDisable() {
        System.out.println("测试用例禁用");
    }

}
