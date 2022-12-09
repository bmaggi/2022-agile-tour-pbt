package bubble.po;

import bubble.data.Client;
import bubble.implem.CardBusinessImplem;
import net.jqwik.api.*;
import net.jqwik.api.arbitraries.IntegerArbitrary;
import net.jqwik.api.arbitraries.StringArbitrary;
import net.jqwik.api.constraints.IntRange;
import net.jqwik.engine.properties.arbitraries.DefaultStringArbitrary;

import static org.assertj.core.api.Assertions.assertThat;

public class PoQuestions {

    //region 1 : Est-ce qu'on peut ouvrir notre service à des clients Allemands ?
    // (Nouveau cas d'usage du service)
    @Example
    void testJürgen() {
        Client jurgen = new Client(
            "Jürgen",
            "Schmidt",
            "Berlin",
            17
        );
        assertThat(new CardBusinessImplem().printCard(jurgen)).isTrue();
    }

    @Example
    void testAdelheid() {
        Client adelheid = new Client(
            "Adelheid",
            "Müller",
            "Buchholz in der Nordheide",
            42
        );
        assertThat(new CardBusinessImplem().printCard(adelheid)).isTrue();
    }

    @Property
    boolean allClientArePrintable(@ForAll(supplier = ClientArbitrary.class) Client client) {
        return new CardBusinessImplem().printCard(client);
    }

    public static class ClientArbitrary implements ArbitrarySupplier<Client> {

        // https://fr.wikipedia.org/wiki/Alphabet_latin
        private static final char[] LATIN_ALPHABET =
            {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

        // https://fr.wikipedia.org/wiki/Alphabet_fran%C3%A7ais
        private static final char[] FRENCH_ADDON = {'à', 'â', 'æ', 'ç', 'é', 'è', 'ê', 'ë', 'î', 'ï', 'ô', 'œ', 'ù', 'û', 'ü', 'ÿ'};

        //https://fr.wikipedia.org/wiki/Allemand
        private static final char[] GERMAN_ADDON = {'a', 'à', 'ä', 'ö', 'ß', 'ü'};

        @Override
        public Arbitrary<Client> get() {
            StringArbitrary alphabetForMyApp = new DefaultStringArbitrary()
                .ofMinLength(1)
                .withChars(LATIN_ALPHABET)
                .withChars(FRENCH_ADDON)
               .withChars(GERMAN_ADDON)
                ;
            IntegerArbitrary age = Arbitraries.integers().between(16, 99);
            return Combinators.combine(alphabetForMyApp, alphabetForMyApp, alphabetForMyApp, age).as(Client::new);
        }

    }

    // https://fr.wikipedia.org/wiki/Bad_Gottleuba-Berggie%C3%9Fh%C3%BCbel

    //endregion 1

    //region 2 : Est-ce qu'il est possible qu'un client n'ait pas de réduction ?
    // (Le cas qui ne devrait pas être possible)
    @Property
    boolean isNoReductionPossible(
        @ForAll @IntRange(min = 16, max = 99) int age,
        @ForAll String city,
        @ForAll @IntRange(min = 30, max = 10000) int totalPayment,
        @ForAll @IntRange(min = 15, max = 50) int numberInGroup,
        @ForAll CardBusinessImplem.OPTIONS options
    ) {
        return new CardBusinessImplem().getDiscount(age, city, totalPayment, numberInGroup, options) != 0;
    }

    //endregion 2

    //region 3 : Est-ce qu'on est d'accord que tous les clients de Grenoble devraient avoir une réduction fixe de 6% ?
    // (Rétro-ingénierie / extraction de spec)
    @Property
    boolean allClientInGrenobleHaveSixDiscount(
        @ForAll @IntRange(min = 16, max = 99) int age,
        @ForAll @IntRange(min = 30, max = 10000) int totalPayment,
        @ForAll @IntRange(min = 15, max = 50) int numberInGroup,
        @ForAll CardBusinessImplem.OPTIONS options
    ) {
              Assume.that(age > 16 && age < 40 );
        return new CardBusinessImplem().getDiscount(age, "Grenoble", totalPayment, numberInGroup, options) == 6;
    }

    //endregion 3
}
