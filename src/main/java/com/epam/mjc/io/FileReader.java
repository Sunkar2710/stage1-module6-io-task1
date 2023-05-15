package com.epam.mjc.io;

import java.io.*;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileReader {
    private HashMap<String, String> map = new HashMap<>();

    public Profile getDataFromFile(File file) {

        StringBuilder text = new StringBuilder();

        try (FileInputStream fileInputStream = new FileInputStream(file)){
            int ch;
            while((ch=fileInputStream.read()) != -1) {
                text.append((char) ch);
            }
        } catch (IOException ex) {
            // exception handling
        }

        map = parseDataFromFile(text.toString());

        return new Profile(map.get("Name"),
                Integer.parseInt(map.get("Age")),
                map.get("Email"),
                Long.parseLong(map.get("Phone")));
    }

    private HashMap<String, String> parseDataFromFile(String text) {
        String regexKey = "^(.*)(?=:)";
        String regexValue = "(?<=: )(.*)";

        Pattern patternKey = Pattern.compile(regexKey, Pattern.MULTILINE);
        Pattern patternValue = Pattern.compile(regexValue);

        Matcher matcherKey = patternKey.matcher(text);
        Matcher matcherValue = patternValue.matcher(text);

        while (matcherKey.find() && matcherValue.find()) {
            map.put(text.substring(matcherKey.start(), matcherKey.end()), text.substring(matcherValue.start(), matcherValue.end()));
        }
        return map;
    }
}
