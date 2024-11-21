package com.apptware.interview.stream;

import java.util.stream.Stream;

public interface DataReader {

  Stream<String> fetchLimitedData(int limit);

  Stream<String> fetchFullData();
}