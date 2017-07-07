package com.helloworld.deepvoidmanager;

import java.util.Random;

public class Generator {
    private String[] syllables;
    private String symbols;
    private final Random randomizer;
    private final int SYLLABLE_COUNT = 197;

    private final String master_key;

    private final String seed;
    private static final double USERNAME_NUMERAL_PROBABILITY = 0.15;
    private static final double USERNAME_SYMBOLIC_PROBABILITY = 0.05;
    private static final double USERNAME_REGRESSION_FACTOR = 0.85;
    private static final double PASSWORD_REGRESSION_FACTOR = 0.85;
    private static final double PASSWORD_SYMBOLIC_PROBABILITY = 0.3;
    private static final double PASSWORD_NUMERIC_PROBABILITY = 0.15;
    private static final double DOUBLING_PROBABILITY = 0.1;

    public Generator(String master_key, String seed) {
        this.master_key = master_key;
        this.seed = seed;

        this.randomizer = new Random(master_key.hashCode() + seed.hashCode());

        populate_syllables();
        populate_symbols();
    }

    public String generate_username() {
        StringBuilder username = new StringBuilder();
        double probability = 1.0;

        while (randomizer.nextDouble() < probability) {
            String next_part;
            if (randomizer.nextDouble() < probability * USERNAME_NUMERAL_PROBABILITY) {
                next_part = String.valueOf(randomizer.nextInt(10000));
            } else if (randomizer.nextDouble() < probability * USERNAME_SYMBOLIC_PROBABILITY) {
                next_part = String.valueOf(symbols.charAt(randomizer.nextInt(symbols.length())));
                probability *= 1.2;
            } else {
                next_part = getNextPart(randomizer);
            }
            username.append(next_part);

            if (randomizer.nextDouble() < probability * DOUBLING_PROBABILITY) {
                username.append(next_part); // add the same part
                probability *= USERNAME_REGRESSION_FACTOR;
            }

            probability *= USERNAME_REGRESSION_FACTOR;
        }

        return username.toString();
    }

    public String generate_password() {
        StringBuilder password = new StringBuilder();
        double probability = 1.0;

        while (randomizer.nextDouble() < probability) {
            String next_part;
            if (randomizer.nextDouble() < probability * PASSWORD_NUMERIC_PROBABILITY) {
                next_part = String.valueOf(randomizer.nextInt(10000));
            } else if (randomizer.nextDouble() < probability * PASSWORD_SYMBOLIC_PROBABILITY) {
                next_part = String.valueOf(symbols.charAt(randomizer.nextInt(symbols.length())));
                probability *= 1.2;
            } else {
                next_part = getNextPart(randomizer);
            }
            password.append(next_part);

            if (randomizer.nextDouble() < probability * DOUBLING_PROBABILITY) {
                password.append(next_part); // add the same part
                probability *= PASSWORD_REGRESSION_FACTOR;
            }

            probability *= PASSWORD_REGRESSION_FACTOR;
        }

        return password.toString();
    }

    private String getNextPart(Random randomizer) {
        String next = syllables[(int) (randomizer.nextInt(syllables.length))];

        return next;
    }

    private boolean is_vowel(char c) {
        CharSequence dict = "aeyiuo";

        for (int i = 0; i < dict.length(); i++) {
            if (c == dict.charAt(i)) return true;
        }
        return false;
    }

    private boolean is_consonant(char c) {
        return !is_vowel(c);
    }

    private void populate_syllables() {
        syllables = new String[]{
                "abc",
                "ab",
                "act",
                "acc",
                "ad",
                "adm",
                "adv",
                "af",
                "afa",
                "ag",
                "aga",
                "ak",
                "aks",
                "al",
                "ale",
                "all",
                "an",
                "ana",
                "and",
                "ao",
                "ar",
                "art",
                "avd",
                "avt",
                "ba",
                "ban",
                "bar",
                "bil",
                "bas",
                "baz",
                "bal",
                "bl",
                "bo",
                "bob",
                "bog",
                "bu",
                "bus",
                "ce",
                "cr",
                "ca",
                "cat",
                "cam",
                "com",
                "con",
                "cor",
                "de",
                "des",
                "dir",
                "dr",
                "ec",
                "eco",
                "ed",
                "edu",
                "em",
                "er",
                "en",
                "ex",
                "exp",
                "fa",
                "fab",
                "far",
                "fi",
                "fin",
                "feat",
                "ge",
                "ga",
                "go",
                "gon",
                "get",
                "gl",
                "glo",
                "he",
                "hel",
                "ha",
                "hap",
                "hal",
                "ham",
                "hat",
                "ho",
                "hol",
                "hom",
                "hor",
                "hz",
                "in",
                "inf",
                "int",
                "inn",
                "ing",
                "jo",
                "job",
                "ja",
                "jab",
                "jar",
                "jac",
                "ke",
                "ko",
                "ku",
                "ka",
                "le",
                "la",
                "li",
                "lo",
                "ma",
                "me",
                "men",
                "man",
                "met",
                "mea",
                "mao",
                "mew",
                "meow",
                "mar",
                "mi",
                "mon",
                "mor",
                "my",
                "of",
                "or",
                "ok",
                "ol",
                "oll",
                "pa",
                "pas",
                "par",
                "pan",
                "po",
                "pe",
                "pl",
                "peo",
                "per",
                "pet",
                "pos",
                "post",
                "pr",
                "qwerty",
                "qu",
                "ra",
                "ro",
                "ray",
                "row",
                "rat",
                "ri",
                "rrr",
                "root",
                "rom",
                "ram",
                "rel",
                "ris",
                "sa",
                "sad",
                "sal",
                "so",
                "se",
                "see",
                "saw",
                "sl",
                "su",
                "sudo",
                "sy",
                "sel",
                "sup",
                "to",
                "ta",
                "tor",
                "tes",
                "ti",
                "too",
                "th",
                "tha",
                "tho",
                "thu",
                "the",
                "the",
                "tra",
                "tro",
                "tri",
                "tre",
                "up",
                "ult",
                "ul",
                "um",
                "us",
                "use",
                "usa",
                "ut",
                "we",
                "web",
                "wet",
                "wa",
                "war",
                "wal",
                "wall",
                "wol",
                "win",
                "was",
                "www",
                "wir",
                "wirl",
                "whoo",
                "who",
                "whe",
                "wro",
                "wr",
                // Second
                "mov",
                "min",
                "mol",
                "str",
                "star",
                "thor",
                "ver",
                "vod",
                "vert",
                "ag",
                "agi",
                "now",
                "nov",
                "new",
                "at",
                "ax",
                "axe",
                "alt",
                "arr",
                "hey",
                "hay",
                "enc",
                "etc",
                "ev",
                "ya",
                "yii",
                "yay",
                "yo",
                "jh",
                "jho",
                "jhe",
                "je",
                "jet",
                "jr",
                "ze",
                "foo",
                "tol",
                "ase",
                "as",
                "sov",
                "ill",
                "rov",
                "ess",
                "ass",
                "tux",
                "tool",
                "ol",
                "any",
                "ann",
                "ail",
                "ert",
                "mis",
                "mist",
                "mice",
                "nice",
                "boom",
                "let",
                "lie",
                "law",
                "cac",
                "cact"
        };
    }

    private void populate_symbols() {
        symbols = "~!#$%^&*()_+{}|:\"<>?`-=[];',./";
    }
}
