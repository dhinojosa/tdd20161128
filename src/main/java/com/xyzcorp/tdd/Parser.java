package com.xyzcorp.tdd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by danno on 11/29/16.
 */
public class Parser {
    public Parser() {

    }

    public Checkout parseLine(String data, String delimiter) {
        String[] split = data.split(delimiter);
        if (split.length != 3) throw new
                IllegalArgumentException("Line is in an invalid format");
        return new Checkout(split[0], split[1], LocalDate.parse(split[2]));
    }

    public List<Checkout> parseStream(Stream<String> items) {
        Stream<Checkout> checkoutStream = items.map(x -> parseLine(x, "~"));
        return checkoutStream.collect(Collectors.toList());
    }

}
