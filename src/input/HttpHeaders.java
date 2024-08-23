package input;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class HttpHeaders {
    private final HttpHeader[] headers = new HttpHeader[256];

    public void addHeader(String key, String value) {
        int index = key.hashCode() & 0xFF;
        headers[index] = new HttpHeader(key, value);
    }

    public String getHeader(String key) {
        int index = key.hashCode() & 0xFF;
        var item = headers[index];
        return (item == null)
                ? null
                : item.value();
    }

    public List<HttpHeader> getHeaders() {
        return Arrays.stream(headers)
                .filter(Objects::nonNull)
                .toList();
    }
}
