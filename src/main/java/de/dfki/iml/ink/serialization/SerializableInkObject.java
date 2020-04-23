package de.dfki.iml.ink.serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

public abstract class SerializableInkObject<T> {

    @Expose
    protected Type type = Type.unknown;

    protected SerializableInkObject(Type type) {
        this.type = type;
    }

    public abstract String toJson();
    protected String toJson(T src) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return gson.toJson(src);
    }

    public abstract T fromJson(String jsonString);
    protected T fromJson(String jsonString, Class<T> classOfT) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(jsonString, classOfT);
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        unknown,
        stroke,
        sketch
    }
}
