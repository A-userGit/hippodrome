import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class HippodromeTest {

    Hippodrome hippodrome;

    @Test
    void getConstructorNullException()
    {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, ()->{
            new Hippodrome(null);
        },"Exception null test failed");
        assertEquals("Horses cannot be null.",thrown.getMessage());

    }

    @Test
    void getConstructorEmptyListException()
    {
        IllegalArgumentException thrown2 = assertThrows(IllegalArgumentException.class, ()->{
            new Hippodrome(new ArrayList<>());
        },"Exception zero units test failed");
        assertEquals("Horses cannot be empty.",thrown2.getMessage());
    }

    @Test
    void getHorses() {

        List<Horse> horses = new ArrayList<>(30);
        for (int i = 0; i < 30; i++) {
            horses.add(new Horse(""+i,i,i));
        }
        hippodrome = new Hippodrome(horses);
        assertEquals(horses,hippodrome.getHorses());
    }

    @Test
    void move() {
        List<Horse> horses = new ArrayList<>(50);
        for (int i = 0; i < 50; i++) {
            horses.add(Mockito.mock(Horse.class));
        }
        hippodrome = new Hippodrome(horses);
        hippodrome.move();
        for (Horse horse:hippodrome.getHorses()) {
            Mockito.verify(horse).move();
        }
    }

    @Test
    void getWinner() {
        List<Horse> horses = new ArrayList<>(30);
        for (int i = 0; i < 30; i++) {
            horses.add(new Horse(""+i,i,i));
        }
        hippodrome = new Hippodrome(horses);
        Horse winner = hippodrome.getWinner();
        Optional<Horse> max = horses.stream().max(Comparator.comparing(Horse::getDistance));
        assertEquals(max.get(),winner);

    }
}
