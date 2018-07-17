package org.dump2csv;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ResourceHelpers {


    public static boolean textMatch(String text, String regex) {
        Pattern p = Pattern.compile(regex, Pattern.MULTILINE);
        Matcher m = p.matcher(text);
        return m.find();
    }

    public static URL getURL(String name) {
        return ClassLoader.getSystemClassLoader().getResource(name);
    }

    public static InputStream getStream(String name) {
        return ClassLoader.getSystemClassLoader().getResourceAsStream(name);
    }

    public static BufferedReader getReader(String name) {

        return new BufferedReader(new InputStreamReader(ClassLoader.getSystemClassLoader()
                .getResourceAsStream(name), StandardCharsets.UTF_8));
    }

    public static String printText(Object object) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(bytes);
        printStream.print(object);
        return bytes.toString();
    }

    public static String readText(Reader reader) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(reader);
        StringWriter writer = new StringWriter();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            writer.append(line);
            writer.append('\n');
        }
        return writer.toString();

    }

}
