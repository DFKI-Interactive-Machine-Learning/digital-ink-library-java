package de.dfki.iml.ink.serialization;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface Serializer {
    public void dump(SerializableInkObject inkObject, String filePath) throws IOException;
    public void dump(SerializableInkObject inkObject, File file) throws IOException;
    public String dumps(SerializableInkObject inkObject);
    public SerializableInkObject load(String filePath, Class<? extends SerializableInkObject> expectedClass) throws FileNotFoundException;
    public SerializableInkObject load(File file, Class<? extends SerializableInkObject> expectedClass) throws FileNotFoundException;
    public SerializableInkObject loads(String serializedString, Class<? extends SerializableInkObject> expectedClass);
}
