package persistence;

import org.json.JSONObject;

// json persistence code based off of code in JsonSerializationDemo example
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
