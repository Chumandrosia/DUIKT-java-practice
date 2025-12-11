import org.example.FiniteAutomaton;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

public class FiniteAutomatonTest {

    @ParameterizedTest(name = "Input: \"{0}\" should result in state {1}")
    @CsvSource({
            "xxxTEST, F",
            "abcTESTabc, F",
            "xyzTEST, F",
            "qqqTESTxyz, F",

            "qqqTES, THREE",
            "wwwTES, THREE",
            "zzzTE, TWO",
            "yyyT, ONE",
            "'', S",
            "abc, S",

            "rrTESTEST, F",
            "ssTESTTEST, F",

            "ttTTEST, F",
            "uuTETEST, F",
            "vvTESTEST, F",
            "wwTTTEST, F",
            "xxTTTTEST, F",

            "yyTESTTESTTEST, F",
            "zzaTESTbTESTc, F",

            "mmmTEZT, S",
            "nnnTAST, S",
            "pppTEST1, F",
            "qqq1TEST, F",

            "rrrtest, S",
            "sssTest, S",
            "tttTeSt, S",

            "uuuTTESTT, F",
            "vvvTETESTEST, F",
            "pppTTES, TWO"
    })
    public void testAutomatonWithVariousInputs(String input, String expectedState) {
        FiniteAutomaton automaton = new FiniteAutomaton();
        FiniteAutomaton.State result = automaton.process(input);
        FiniteAutomaton.State expected = FiniteAutomaton.State.valueOf(expectedState);

        assertEquals(expected, result,
                String.format("For input '%s', expected state %s but got %s",
                        input, expected, result));
    }

    @Test
    public void testNullInput() {
        FiniteAutomaton automaton = new FiniteAutomaton();
        FiniteAutomaton.State result = automaton.process(null);
        assertEquals(FiniteAutomaton.State.S, result);
    }

    @Test
    public void testEmptyString() {
        FiniteAutomaton automaton = new FiniteAutomaton();
        FiniteAutomaton.State result = automaton.process("");
        assertEquals(FiniteAutomaton.State.S, result);
    }

    @ParameterizedTest(name = "String with TEST at position {0}: \"{1}\"")
    @CsvSource({
            "0, aaaTEST",
            "1, xbbbTEST",
            "5, hellocccTEST",
            "3, abcdddTESTxyz"
    })
    public void testTESTAtDifferentPositions(int position, String input) {
        FiniteAutomaton automaton = new FiniteAutomaton();
        FiniteAutomaton.State result = automaton.process(input);
        assertEquals(FiniteAutomaton.State.F, result,
                String.format("TEST at position %d should be recognized", position));
    }

    @ParameterizedTest(name = "Overlapping pattern: \"{0}\"")
    @ValueSource(strings = {
            "eeeTTEST",
            "fffTETEST",
            "gggTESTEST",
            "hhhTTTEST",
            "iiiTTTTEST",
            "jjjTETESTEST",
            "kkkTTESTTESTT"
    })
    public void testOverlappingPatterns(String input) {
        FiniteAutomaton automaton = new FiniteAutomaton();
        FiniteAutomaton.State result = automaton.process(input);
        assertEquals(FiniteAutomaton.State.F, result,
                String.format("String '%s' contains TEST and should reach state F", input));
    }

    @Test
    public void testLongStringWithTEST() {
        String longString = "a".repeat(1000) + "lllTEST" + "b".repeat(1000);
        FiniteAutomaton automaton = new FiniteAutomaton();
        FiniteAutomaton.State result = automaton.process(longString);
        assertEquals(FiniteAutomaton.State.F, result);
    }

    @Test
    public void testMultipleResets() {
        FiniteAutomaton automaton = new FiniteAutomaton();

        assertEquals(FiniteAutomaton.State.F, automaton.process("mmmTEST"));

        assertEquals(FiniteAutomaton.State.THREE, automaton.process("nnnTES"));

        assertEquals(FiniteAutomaton.State.S, automaton.process("oooabc"));
    }
}