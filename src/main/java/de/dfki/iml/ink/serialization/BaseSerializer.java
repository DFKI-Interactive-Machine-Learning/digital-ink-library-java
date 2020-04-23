package de.dfki.iml.ink.serialization;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public abstract class BaseSerializer implements Serializer {


    @Override
    public void dump(SerializableInkObject inkObject, String filePath) throws IOException {
        dump(inkObject, new File(filePath));
    }

    @Override
    public abstract void dump(SerializableInkObject inkObject, File file) throws IOException;

    @Override
    public abstract String dumps(SerializableInkObject inkObject);

    @Override
    public SerializableInkObject load(String filePath, Class<? extends SerializableInkObject> expectedClass) throws FileNotFoundException {
        return load(new File(filePath), expectedClass);
    }

    @Override
    public abstract SerializableInkObject load(File file, Class<? extends SerializableInkObject> expectedClass) throws FileNotFoundException;

    @Override
    public abstract SerializableInkObject loads(String serializedString, Class<? extends SerializableInkObject> expectedClass);
}
