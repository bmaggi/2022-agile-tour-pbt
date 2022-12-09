package bubble.slides;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.IntRange;
import net.jqwik.api.constraints.Positive;
import net.jqwik.api.constraints.StringLength;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

class SnippetTest {

    // TU classique pour distributivity
    @Test void distributivityWithO() {assert 0 * (0+ 0) == 0*0+0*0;}
    @Test void distributivityWith1() {assert 1 * (1+ 1) == 1*1+1*1;}
    @Test void distributivityWithO10() {assert  0 * (1+ 0) == 0*1+0*0;}
    @Test void distributivityWith110() {assert   1 * (1+ 0) == 1*1+0*0;}
    @Test void distributivityWithClassicNumber() {assert 42 * (42+ 42) == 42*42+42*42;}
    @Test void distributivityWithBigNumber() {assert 100000 * (100000+ 100000) == 100000*100000+100000*100000;}

    @Property
    boolean distributivity(@ForAll int a, @ForAll int b, @ForAll int c) {
        return a * (b+c) == a*b + a*c ;
    }

    // Pattern
    @Property
    boolean roundTripping(@ForAll @IntRange(max = 700) int i) {
        return Math.log(Math.exp(i)) == i ;
    }

    @Property
    boolean commutativity(@ForAll @Positive long i) {
        return Math.abs(Math.round(i)) == Math.round(Math.abs(i)) ;
    }

    @Property
    boolean invariant(@ForAll List<String> list) {
        int invariant = list.size();
        Collections.sort(list);
        return list.size() == invariant;
    }

    @Property
    boolean idempotence(@ForAll String s) {
        return s.trim().equals(s.trim().trim());
    }

    @Property
    boolean oracle(@ForAll @StringLength(min = 3,max = 45) String s) {
        return myVeryFastCustomTrimFunction(s).equals(s.trim());
    }

    // simple rebranding ;)
    private String myVeryFastCustomTrimFunction(String string) {
        return string.trim();
    }

    private static Stream<Arguments> randomIntTierce() {
        Random random = new Random();
        return Stream.of(
            Arguments.of(random.nextInt(), random.nextInt(), random.nextInt()),
            Arguments.of(random.nextInt(), random.nextInt(), random.nextInt())
            // Arguments.of(random.nextInt(), random.nextInt(), null)  failing data
        );
    }

    @ParameterizedTest
    @MethodSource("randomIntTierce")
    void distributivity(Integer a, Integer b, Integer c) {
        Assertions.assertEquals(a * (b+c), a*b + a*c);
    }

}
