package com.robby.app.commons.datatype.wrappers;

import com.google.gson.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Created @ 2019/11/11
 *
 * @author liuwei
 */
public class JsonElementLazyWrapper extends JsonElement implements Serializable {
    private static final JsonParser PARSER = new JsonParser();
    private final String json;
    private JsonElement node;

    public JsonElementLazyWrapper(String json) {
        this.json = json;
    }

    public String getJsonSource() {
        return this.json;
    }

    @Override
    public JsonElement deepCopy() {
        return this.element().deepCopy();
    }

    private JsonElement element() {
        if (this.node == null) {
            synchronized(this) {
                if (this.node == null) {
                    this.node = PARSER.parse(this.json);
                }
            }
        }

        return this.node;
    }

    @Override
    public boolean isJsonArray() {
        return this.element().isJsonArray();
    }

    @Override
    public boolean isJsonObject() {
        return this.element().isJsonObject();
    }

    @Override
    public boolean isJsonPrimitive() {
        return this.element().isJsonPrimitive();
    }

    @Override
    public boolean isJsonNull() {
        return this.element().isJsonNull();
    }

    @Override
    public String toString() {
        return this.element().toString();
    }

    @Override
    public JsonObject getAsJsonObject() {
        return this.element().getAsJsonObject();
    }

    @Override
    public JsonArray getAsJsonArray() {
        return this.element().getAsJsonArray();
    }

    @Override
    public JsonPrimitive getAsJsonPrimitive() {
        return this.element().getAsJsonPrimitive();
    }

    @Override
    public JsonNull getAsJsonNull() {
        return this.element().getAsJsonNull();
    }

    @Override
    public boolean equals(Object o) {
        return this.element().equals(o);
    }

    @Override
    public boolean getAsBoolean() {
        return this.element().getAsBoolean();
    }

    @Override
    public Number getAsNumber() {
        return this.element().getAsNumber();
    }

    @Override
    public String getAsString() {
        return this.element().getAsString();
    }

    @Override
    public double getAsDouble() {
        return this.element().getAsDouble();
    }

    @Override
    public float getAsFloat() {
        return this.element().getAsFloat();
    }

    @Override
    public long getAsLong() {
        return this.element().getAsLong();
    }

    @Override
    public int getAsInt() {
        return this.element().getAsInt();
    }

    @Override
    public byte getAsByte() {
        return this.element().getAsByte();
    }

    @Override
    public char getAsCharacter() {
        return this.element().getAsCharacter();
    }

    @Override
    public BigDecimal getAsBigDecimal() {
        return this.element().getAsBigDecimal();
    }

    @Override
    public BigInteger getAsBigInteger() {
        return this.element().getAsBigInteger();
    }

    @Override
    public short getAsShort() {
        return this.element().getAsShort();
    }

    @Override
    public int hashCode() {
        return this.element().hashCode();
    }
}
