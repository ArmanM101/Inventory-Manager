package persistence;

import org.json.JSONObject;

// Code was created using https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git as a model

public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
