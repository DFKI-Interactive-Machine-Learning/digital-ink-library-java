package de.dfki.iml.ink.serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class JsonSerializer extends BaseSerializer {

    private Gson gson;

    public JsonSerializer() {
        this(false);
    }

    public JsonSerializer(boolean prettyPrinting) {
        if (prettyPrinting)
            gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
        else
            gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }

    @Override
    public void dump(SerializableInkObject inkObject, File file) throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        gson.toJson(inkObject, fileWriter);
        fileWriter.close();
    }

    @Override
    public String dumps(SerializableInkObject inkObject) {
        return gson.toJson(inkObject);
    }

    @Override
    public SerializableInkObject load(File file, Class<? extends SerializableInkObject> expectedClass) throws FileNotFoundException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        return gson.fromJson(bufferedReader, expectedClass);
    }

    @Override
    public SerializableInkObject loads(String serializedString, Class<? extends SerializableInkObject> expectedClass) {
        return gson.fromJson(serializedString, expectedClass);
    }
}
