package com.xyzcorp.tdd;


import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class ParserTest {


    @Test
    public void testParsePerfectLine() {
        String data = "Beth Brown~The Leftovers~2013-03-31";
        Parser parser = new Parser();
        Checkout result = parser.parseLine(data, "~");
        assertThat(result.getName()).isEqualTo("Beth Brown");
        assertThat(result.getTitle()).isEqualTo("The Leftovers");
        assertThat(result.getCheckoutDate()).isEqualTo(LocalDate.of(2013, 3, 31));
    }

    @Test
    public void testParsePerfectLineDifferentData() {
        String data = "Sivabalan Thirunavukkarasu~Harry Potter Order of the Phoenix~2014-05-11";
        Parser parser = new Parser();
        Checkout result = parser.parseLine(data, "~");
        assertThat(result.getName()).isEqualTo("Sivabalan Thirunavukkarasu");
        assertThat(result.getTitle()).isEqualTo("Harry Potter Order of the Phoenix");
        assertThat(result.getCheckoutDate()).isEqualTo(LocalDate.of(2014, 5, 11));
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testBlankLine() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Line is in an invalid format");
        String data = "";
        Parser parser = new Parser();
        parser.parseLine(data, "~");
    }

    @Test
    public void testBlankLineWithSpaces() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Line is in an invalid format");
        String data = "        ";
        Parser parser = new Parser();
        parser.parseLine(data, "~");
    }

    @Test
    public void testParseFromStream() {
        Parser parser = new Parser();
        Stream<String> items =
                Stream.of("Beth Brown~The Leftovers~2013-03-31",
                        "Amit Sharma~Effective Java~2016-05-31");

        List<Checkout> checkoutItems = parser.parseStream(items);
        assertThat(checkoutItems).hasSize(2);
        assertThat(checkoutItems.get(0).getName()).isEqualTo("Beth Brown");
    }

    @Test
    @Category(value = IntegrationTest.class)
    public void testParse() {
        Parser parser = new Parser();

        InputStream is = getClass().getResourceAsStream("/library.txt");
        InputStreamReader inputStreamReader = new InputStreamReader(is);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        List<Checkout> checkoutItems = parser.parseStream(bufferedReader.lines());
        assertThat(checkoutItems).hasSize(16);
    }

    @Test
    @Category(value = IntegrationTest.class)
    public void testParseWebService() throws IOException {
        String uri = "https://raw.githubusercontent.com/dhinojosa/tdd20161128/master/src/main/resources/library.txt";
        URL url = new URL(uri);
        InputStream inputStream = url.openConnection().getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        Parser parser = new Parser();
        List<Checkout> checkoutItems = parser.parseStream(bufferedReader.lines());
        assertThat(checkoutItems).hasSize(16);
    }


    class PersonOwed {
        private String name;
        private int penalty;

        public PersonOwed(String name, int penalty) {
            this.name = name;
            this.penalty = penalty;
        }

        public String getName() {
            return name;
        }

        public Integer getPenalty() {
            return penalty;
        }

        @Override
        public String toString() {
            return "PersonOwed{" +
                    "name='" + name + '\'' +
                    ", penalty=" + penalty +
                    '}';
        }
    }

    @Test
    @Category(value = IntegrationTest.class)
    public void testParseWebServiceWithCheckout() throws IOException {
        String uri = "https://raw.githubusercontent.com/dhinojosa/tdd20161128/master/src/main/resources/library.txt";
        URL url = new URL(uri);
        InputStream inputStream = url.openConnection().getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        Parser parser = new Parser();
        Calculator calculator = new Calculator();
        calculator.setRate(10);

        List<Checkout> checkoutItems = parser.parseStream(bufferedReader.lines());
        checkoutItems.stream().map(checkout -> new PersonOwed(checkout.getName(), calculator.calculate(checkout.getCheckoutDate(), LocalDate.now())))
                .sorted((x, y) -> y.getPenalty().compareTo(x.getPenalty())).limit(5).peek(System.out::println).collect(Collectors.toList());
    }
}