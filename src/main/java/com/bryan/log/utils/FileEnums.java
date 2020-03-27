package com.bryan.log.utils;

public enum FileEnums {

    CUSTOM("/Lang/custom.yml"),
    DE("/Lang/de.yml"),
    EN("/Lang/en.yml"),
    ES("/Lang/es.yml"),
    FR("/Lang/fr.yml"),
    NL("/Lang/nl.yml"),
    PL("/Lang/pl.yml"),
    PT("/Lang/pt.yml"),
    ZH("/Lang/zh.yml");

    public String fileName;

    FileEnums(String name) {
        this.fileName = name;
    }

}
