package com.wrapper.spotify;

import com.wrapper.spotify.exceptions.WebApiException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.CharBuffer;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestUtil {

  private static final String TEST_DATA_DIR = "src/test/fixtures/";

  private static final int MAX_TEST_DATA_FILE_SIZE = 65536;

  public static String readTestData(String fileName) throws IOException {
    return readFromFile(new File(TEST_DATA_DIR, fileName));
  }

  private static String readFromFile(File file) throws IOException {
    InputStreamReader reader = new InputStreamReader(new FileInputStream(file), "UTF-8");
    CharBuffer charBuffer = CharBuffer.allocate(MAX_TEST_DATA_FILE_SIZE);
    reader.read(charBuffer);
    charBuffer.position(0);
    return charBuffer.toString();
  }

  public static class MockedHttpManager {

    public static HttpManager returningJson(String jsonFixture) throws Exception {
      // Mocked HTTP Manager to get predictable responses
      final HttpManager mockedHttpManager = mock(HttpManager.class);
      final String fixture = readTestData(jsonFixture);
      when(mockedHttpManager.get((UtilProtos.Url) any())).thenReturn(fixture);
      when(mockedHttpManager.post((UtilProtos.Url) any())).thenReturn(fixture);
      when(mockedHttpManager.put((UtilProtos.Url) any())).thenReturn(fixture);
      when(mockedHttpManager.delete((UtilProtos.Url) any())).thenReturn(fixture);

      return mockedHttpManager;
    }

    public static HttpManager returningString(String returnedString) throws IOException, WebApiException {
      final HttpManager mockedHttpManager = mock(HttpManager.class);
      when(mockedHttpManager.get((UtilProtos.Url) any())).thenReturn(returnedString);
      when(mockedHttpManager.post((UtilProtos.Url) any())).thenReturn(returnedString);
      when(mockedHttpManager.put((UtilProtos.Url) any())).thenReturn(returnedString);
      when(mockedHttpManager.delete((UtilProtos.Url) any())).thenReturn(returnedString);

      return mockedHttpManager;
    }
  }

}
