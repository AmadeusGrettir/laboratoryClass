package LR1;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


class ArabicCalcsTest {


     @BeforeAll
     public static void hi() {
         System.out.println("testing start");
     }

    @BeforeEach
    public void hiTest() {
        System.out.println("next test");
    }

    @Test
    @DisplayName("TestFirst")
    @RepeatedTest(3)
    void addition() {

        String a = "5";
        String b = "4";

        ArabicCalcs calc = new ArabicCalcs();

        String ans = calc.addition(a,b);

        Assertions.assertEquals("9",ans);

    }
}