package Model;

import java.net.http.HttpResponse;

public class Parser {
  String json;

  public void parseJSON(HttpResponse response) {
    json = response.toString();
  }

}
