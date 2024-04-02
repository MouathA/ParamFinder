package org.Mouath;

import org.apache.commons.cli.*;

import java.io.*;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {


    private static final Set<String> BLACKLIST = new HashSet<>();

    static {
        //  for param blacklist
        BLACKLIST.add(".jpg");
        BLACKLIST.add(".jpeg");
        BLACKLIST.add(".png");
        BLACKLIST.add(".gif");
        BLACKLIST.add(".pdf");
        BLACKLIST.add(".svg");
        BLACKLIST.add(".json");
        BLACKLIST.add(".css");
        BLACKLIST.add(".js");
        BLACKLIST.add(".webp");
        BLACKLIST.add(".woff");
        BLACKLIST.add(".woff2");
        BLACKLIST.add(".eot");
        BLACKLIST.add(".ttf");
        BLACKLIST.add(".otf");
        BLACKLIST.add(".mp4");
        BLACKLIST.add(".txt");
    }

    public static void main(String[] args) {
        System.out.println(ConsoleColors.GREEN_BOLD+"\n  ____                                       _____   _               _\n" +
                " |  _ \\    __ _   _ __    __ _   _ __ ___   |  ___| (_)  _ __     __| |   ___   _ __\n" +
                " | |_) |  / _` | | '__|  / _` | | '_ ` _ \\  | |_    | | | '_ \\   / _` |  / _ \\ | '__|\n" +
                " |  __/  | (_| | | |    | (_| | | | | | | | |  _|   | | | | | | | (_| | |  __/ | |\n" +
                " |_|      \\__,_| |_|     \\__,_| |_| |_| |_| |_|     |_| |_| |_|  \\__,_|  \\___| |_|\n" +
                "\n" +
                "\n\t\t\t\t\t\t\thttps://github.com/MouathA");
        Options options = new Options();
        options.addOption("d", "domain", true, "scan domain and subdomains");
        options.addOption("o", "output", true, "output");
        options.addOption("p", "params", false, "extract URLs with parameters only");

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);

            if (cmd.hasOption("d") && cmd.hasOption("o")) {
                String domain = cmd.getOptionValue("d");
                String output = cmd.getOptionValue("o");
                boolean extractParamsOnly = cmd.hasOption("p");

                System.out.println(ConsoleColors.GREEN_BOLD + "[+] Scanning domain and subdomains: " + domain);
                outputDP(domain, output, extractParamsOnly);
                System.out.println(ConsoleColors.GREEN_BOLD + "[*] Task completed successfully.");
                System.out.println(ConsoleColors.RESET);
                System.exit(0);
            } else {
                throw new IllegalArgumentException("[-] Please provide both domain (-d) and output (-o) options.");
            }
        } catch (ParseException | IOException | InterruptedException e) {
            System.err.println(ConsoleColors.RED_BOLD + "[!] Error: " + e.getMessage());
            System.out.println(ConsoleColors.RESET);
            System.exit(1);
        } catch (IllegalArgumentException e) {
            System.err.println(ConsoleColors.RED_BOLD + "[-] Error: " + e.getMessage());
            System.out.println(ConsoleColors.RESET);
            System.exit(1);
        }
    }

    private static void outputDP(String domain, String outputFilePath, boolean extractParamsOnly) throws IOException, InterruptedException {

        URL url = new URL("https://web.archive.org/cdx/search/cdx?url=" + domain + "/*&output=txt&collapse=urlkey&fl=original&page=/%22");
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath));
        String line;
        while ((line = reader.readLine()) != null) {
            if (!shouldSkipURL(line)) {
                if (extractParamsOnly) {
                    Pattern pattern = Pattern.compile("(https?://\\S+\\?\\S+)");
                    Matcher matcher = pattern.matcher(line);
                    while (matcher.find()) {
                        String matchedUrl = matcher.group();
                        writer.write(matchedUrl);
                        writer.newLine();
                    }
                } else {
                    writer.write(line);
                    writer.newLine();
                }
            }
        }

        reader.close();
        writer.close();
        System.out.println(ConsoleColors.GREEN_BOLD + "[*] URL content saved to " + outputFilePath);
        System.out.println(ConsoleColors.RESET);
    }

    private static boolean shouldSkipURL(String url) {
        for (String extension : BLACKLIST) {
            if (url.contains(extension)) {
                return true;
            }
        }
        return false;
    }

    private static class ConsoleColors {
        public static final String RESET = "\u001B[0m";
        public static final String RED_BOLD = "\033[1;31m";
        public static final String GREEN_BOLD = "\033[1;32m";
    }
}
