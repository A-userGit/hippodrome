import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

class HorseTest {

    static MockedStatic<Horse> horseMockedStatic;

    @BeforeAll
    static void init()
    {
        horseMockedStatic = Mockito.mockStatic(Horse.class);
    }

    static Stream<String> blankStrProvider() {
        return Stream.of(" ", "\t", "\n", "\f", "\r");
    }

    @ParameterizedTest
    @MethodSource("blankStrProvider")
    void blankNameExceptionTest(String name)
    {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,()-> new Horse(name,1));
        assertEquals("Name cannot be blank.",thrown.getMessage());
    }

    @Test
    void getNegativeDistanceException()
    {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,()-> new Horse("test",1,-1));
        assertEquals("Distance cannot be negative.",thrown.getMessage());
    }

    @Test
    void getNegativeSpeedException()
    {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,()-> new Horse("test",-1));
        assertEquals("Speed cannot be negative.",thrown.getMessage());
    }

    @Test
    void getNullNameException()
    {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,()-> new Horse(null,1));
        assertEquals("Name cannot be null.",thrown.getMessage());
    }

    @Test
    void getName() {
        String name = "testTest";
        Horse horse = new Horse(name,1);
        assertEquals(name,horse.getName());
    }

    @Test
    void getSpeed() {
        String name = "testTest";
        Horse horse = new Horse(name,10);
        assertEquals(10,horse.getSpeed());
    }

    @Test
    void getDistance() {
        String name = "testTest";
        Horse horse = new Horse(name,1,10);
        assertEquals(10,horse.getDistance());
        Horse horse2 = new Horse(name,1);
        assertEquals(0,horse2.getDistance());
    }

    @Test
    void move() {
        Horse horse = new Horse("test",1,1);
        horse.move();
        horseMockedStatic.verify(()->Horse.getRandomDouble(0.2,0.9),times(1));

    }

    @ParameterizedTest
    @ValueSource(doubles = { 0.3, 0.4, 0.5 })
    void moveDistanceTest(double randSubstitute)
    {
        horseMockedStatic.when(()->Horse.getRandomDouble(0.2,0.9)).thenReturn(randSubstitute);
        Horse horse = new Horse("test",5,10);
        horse.move();
        assertEquals(10+randSubstitute*5,horse.getDistance());
    }
}